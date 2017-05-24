import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.time.Duration;
import java.time.Instant;

public class Rubik{

  // Used in All Solvers
  private static String[][][] solvedCube                              = new String[][][]{{{"aeb",   "ab",    "abc"},  //1 - 0 0
                                                                                          {"eb",    "b",     "bc"},   //2 - 0 1
                                                                                          {"ebf",   "bf",    "bcf"}}, //3 - 0 2
                                                                                         {{"ae",    "a",     "ac"},   //8 - 1 0
                                                                                          {"e",     "abcdef","c"},    //Axis for Vertical move - 1 1
                                                                                          {"ef",    "f",     "cf"}},  //4 - 1 2
                                                                                         {{"ade",   "ad",    "acd"},  //7 - 2 0
                                                                                          {"de",    "d",     "cd"},   //6 - 2 1
                                                                                          {"def",   "df",    "cdf"}}};//5 - 2 2
  private static String[][][] currentCube                             = new String[][][]{{{"aeb",   "ab",    "abc"},
                                                                                          {"eb",    "b",     "bc"},
                                                                                          {"ebf",   "bf",    "bcf"}},
                                                                                         {{"ae",    "a",     "ac"},
                                                                                          {"e",     "abcdef","c"},
                                                                                          {"ef",    "f",     "cf"}},
                                                                                         {{"ade",   "ad",    "acd"},
                                                                                          {"de",    "d",     "cd"},
                                                                                          {"def",   "df",    "cdf"}}};
  private static int[] allPossibleMoves                               = new int[]{11,12,13,21,22,23,31,32,33,41,42,43,
                                                                                  51,52,53,61,62,63,71,72,73,81,82,83,
                                                                                  91,92,93,101,102,103,111,112,113,121,122,123};
  private static int[] allPossibleMovesReversal                       = new int[]{12,11,13,22,21,23,32,31,33,42,41,43,
                                                                                  52,51,53,62,61,63,72,71,73,82,81,83,
                                                                                  92,91,93,102,101,103,112,111,113,122,121,123};
  private static int level                                            = 0;

 // - Brute Forcing
 // Used in rubikSolver()
  private static ArrayList<Integer> movesMade                         = new ArrayList<Integer>();
  private static int numberofMovesToMake                              = 5;

  // - Solving by Parts - Speed Cubing Technique
  // --- Used in rubikPartialSolver(), rubikPartialSolverForBottomCenterPieces() & rubikSolverUsingSpeedCubing()
  private static ArrayList<Integer> partialMovesMade                  = new ArrayList<Integer>();
  private static int partialLevel                                     = 0;
  private static String intermediateLevel                             = "";
  private static boolean intermediatePosition1                        = false;
  private static boolean intermediatePosition2                        = false;
  private static String[] cubePartiallySolved                         = new String[]{"001", "102", "201", "100",  // Top Center Pieces
                                                                                     "000", "002", "202", "200",  // Top Corner Pieces
                                                                                     "010", "012", "212", "210",  // Middle Center Pieces
                                                                                     "021", "122", "221", "120",  // Bottom Center Pieces
                                                                                     "020", "022", "222", "220"}; // Bottom Corner Pieces
  private static String[][] intermediateLevelForTopCornerPieces       = new String[][]{{"022", "020", "022", "020"},
                                                                                       {"220", "222", "220", "222"}};
  private static String[][] intermediateLevelForMiddleCenterPieces    = new String[][]{{"021", "122", "221", "120"},
                                                                                       {"120", "021", "122", "221"}};
  private static int[] numberofPartialMovesToMake                     = new int[]{ 4,4,4,4,
                                                                                   4,4,5,5,
                                                                                   1,1,1,1,
                                                                                   4,5,5,2,
                                                                                   5,5,5,5};
  private static int maximumDepthOfMoves                              = 0;
  private static boolean optimalSolution                              = false;
  private static ArrayList<Integer> optimalPartialMovesMade           = new ArrayList<Integer>();

  // - Solving by Parts - Speed Cubing Technique
  // --- For Bottom Center Pieces
  private static ArrayList<Integer> movesToRotate                     = new ArrayList<Integer>();
  private static String[] solvedBottomCenterPieces                    = new String[]{"b","c","d","e"};
  private static String[] currentBottomCenterPieces                   = new String[4];
  private static int[] allPossibleMovesForBottomCenterPieces          = new int[]{1,2,3,11,12};//,21,22,31,32,41,42};
  private static int[] allPossibleMovesReversalForBottomCenterPieces  = new int[]{2,1,3,12,11};//,22,21,32,31,42,41};

  // Testing Purposes
  private static int makeMoveCount                                    = 0;

  private static boolean isCubeSolved() {

    if ( currentCube[1][1][1].equals(solvedCube[1][1][1]) == false )
      return false;

    for ( int i=0 ; i<3 ; i++ )
      for ( int j=0 ; j<3 ; j++ )
        for ( int k=0 ; k<3 ; k++ )
          if (currentCube[i][j][k].equals(solvedCube[i][j][k]) == false)
            return false;

    return true;
  }

  private static boolean isCubePartiallySolved(int partialLevel) {

    int a,b,c;

    if ( currentCube[1][1][1].equals(solvedCube[1][1][1]) == false )
      return false;

    for ( int j=0 ; j<partialLevel ; j++ ) {
      a = Integer.parseInt(cubePartiallySolved[j].substring(0,1));
      b = Integer.parseInt(cubePartiallySolved[j].substring(1,2));
      c = Integer.parseInt(cubePartiallySolved[j].substring(2));

      if (currentCube[a][b][c].equals(solvedCube[a][b][c]) == false)
        return false;
    }

    if(intermediateLevel == "TopCornerPieces") {

      int d,e,f,g,h,i,intermediateIndex;

      intermediatePosition1 = false;
      intermediatePosition2 = false;

      a = Integer.parseInt(cubePartiallySolved[partialLevel].substring(0,1));
      b = Integer.parseInt(cubePartiallySolved[partialLevel].substring(1,2));
      c = Integer.parseInt(cubePartiallySolved[partialLevel].substring(2));

      intermediateIndex = partialLevel % 4;
      d = Integer.parseInt(intermediateLevelForTopCornerPieces[0][intermediateIndex].substring(0,1));
      e = Integer.parseInt(intermediateLevelForTopCornerPieces[0][intermediateIndex].substring(1,2));
      f = Integer.parseInt(intermediateLevelForTopCornerPieces[0][intermediateIndex].substring(2));

      g = Integer.parseInt(intermediateLevelForTopCornerPieces[1][intermediateIndex].substring(0,1));
      h = Integer.parseInt(intermediateLevelForTopCornerPieces[1][intermediateIndex].substring(1,2));
      i = Integer.parseInt(intermediateLevelForTopCornerPieces[1][intermediateIndex].substring(2));

      if (solvedCube[a][b][c].contains(currentCube[d][e][f].substring(0,1)) == true &&
          solvedCube[a][b][c].contains(currentCube[d][e][f].substring(1,2)) == true &&
          solvedCube[a][b][c].contains(currentCube[d][e][f].substring(2)) == true)
          intermediatePosition1 = true;
      if (solvedCube[a][b][c].contains(currentCube[g][h][i].substring(0,1)) == true &&
          solvedCube[a][b][c].contains(currentCube[g][h][i].substring(1,2)) == true &&
          solvedCube[a][b][c].contains(currentCube[g][h][i].substring(2)) == true)
          intermediatePosition2 = true;

      if (intermediatePosition1 == false && intermediatePosition2 == false)
        return false;
    }
    else if(intermediateLevel == "MiddleCenterPieces") {
      int d,e,f,g,h,i,intermediateIndex;

      intermediatePosition1 = false;
      intermediatePosition2 = false;

      a = Integer.parseInt(cubePartiallySolved[partialLevel].substring(0,1));
      b = Integer.parseInt(cubePartiallySolved[partialLevel].substring(1,2));
      c = Integer.parseInt(cubePartiallySolved[partialLevel].substring(2));

      intermediateIndex = partialLevel % 4;
      d = Integer.parseInt(intermediateLevelForMiddleCenterPieces[0][intermediateIndex].substring(0,1));
      e = Integer.parseInt(intermediateLevelForMiddleCenterPieces[0][intermediateIndex].substring(1,2));
      f = Integer.parseInt(intermediateLevelForMiddleCenterPieces[0][intermediateIndex].substring(2));

      g = Integer.parseInt(intermediateLevelForMiddleCenterPieces[1][intermediateIndex].substring(0,1));
      h = Integer.parseInt(intermediateLevelForMiddleCenterPieces[1][intermediateIndex].substring(1,2));
      i = Integer.parseInt(intermediateLevelForMiddleCenterPieces[1][intermediateIndex].substring(2));

      if (solvedCube[a][b][c].equals((new StringBuilder(currentCube[d][e][f])).reverse().toString()) == true) {
          intermediatePosition1 = true;
      }
      if (solvedCube[a][b][c].equals(currentCube[g][h][i]) == true) {
          intermediatePosition2 = true;
      }
      if (intermediatePosition1 == false && intermediatePosition2 == false)
        return false;
    }
    else {
      a = Integer.parseInt(cubePartiallySolved[partialLevel].substring(0,1));
      b = Integer.parseInt(cubePartiallySolved[partialLevel].substring(1,2));
      c = Integer.parseInt(cubePartiallySolved[partialLevel].substring(2));

      if (currentCube[a][b][c].equals(solvedCube[a][b][c]) == false)
        return false;
    }
    return true;
  }

  private static boolean makeMove(int moveToMake) {
    /* Moves -
          a: Single Part
                vertical:   1 2 3 - u(up) d(down) i(invert)
                horizontal: 5 6 7 - r(right) l(left) i(invert)
                rotate:     9 10 11 - counterclock clock invert
          b: Entire Cube
                vertical:   4 -  u d i
                horizontal: 8 -  r l i
                rotate:     12 - c c i
    */

    String typeOfMove = "vertical"; // "vertical" or "horizotal" or "rotate"
    int partsToMove=0; // 0 -> whole cube ; 1,2,3 -> one part (Top to Down, Left to Right order)
    int move; // Actual Move to make -> 1,2 or 3 for (u,d or i) or (r,l or i)
    int oddMove[]  = new int[]{0,0,2,2,0,0};
    int evenMove[] = new int[]{0,1,2,1,0,1};
    String tempOddRow[] = new String[3];
    String tempEvenRow[] = new String[3];

    makeMoveCount++;

    if ( moveToMake / 10 > 4 ) {// First Part of Move
      typeOfMove = "horizontal";
      if ( moveToMake / 10 > 8 && moveToMake / 10 < 13)
        typeOfMove = "rotate";
    }
    partsToMove = ( moveToMake / 10 ) % 4; // Cube or Row
    move = moveToMake % 10;// Second Part of Move

    // Transpose the cube according to the move
    if ( typeOfMove == "vertical" ) { // Vertical
      if ( move == 1 ) { // Up
        if ( partsToMove == 0 ) { // Move Whole Cube
            for ( int j = 0 ; j < 3 ; j++ ) {
               tempOddRow[j]  = currentCube[oddMove[4]][oddMove[5]][j];
               tempEvenRow[j] = currentCube[evenMove[4]][evenMove[5]][j];
            }
            for ( int i = 1 ; i < 4 ; i++ ) {
              for ( int j = 0 ; j < 3 ; j++ ) {
                currentCube[oddMove[i-1]][oddMove[i]][j]   = currentCube[oddMove[i]][oddMove[i+1]][j];
                currentCube[evenMove[i-1]][evenMove[i]][j] = currentCube[evenMove[i]][evenMove[i+1]][j];
              }
            }
            for ( int j = 0 ; j < 3 ; j++ ) {
              currentCube[oddMove[3]][oddMove[4]][j]   = tempOddRow[j];
              currentCube[evenMove[3]][evenMove[4]][j] = tempEvenRow[j];
            }
        }
        else { // Move the part specified by 'partsToMove'

            tempOddRow[partsToMove-1]  = currentCube[oddMove[4]][oddMove[5]][partsToMove-1];
            tempEvenRow[partsToMove-1] = currentCube[evenMove[4]][evenMove[5]][partsToMove-1];

            for ( int i = 1 ; i < 4 ; i++ ) {
                currentCube[oddMove[i-1]][oddMove[i]][partsToMove-1]   = currentCube[oddMove[i]][oddMove[i+1]][partsToMove-1];
                currentCube[evenMove[i-1]][evenMove[i]][partsToMove-1] = currentCube[evenMove[i]][evenMove[i+1]][partsToMove-1];
            }
            currentCube[oddMove[3]][oddMove[4]][partsToMove-1]   = tempOddRow[partsToMove-1];
            currentCube[evenMove[3]][evenMove[4]][partsToMove-1] = tempEvenRow[partsToMove-1];

        }

        /* Position of Current Cube - After Move 41

        aeb     ab      abc ->  ebf     bf      bcf   ->  bef     bf      bfc
        eb      b       bc  ->  ef      f       cf    ->  ef      f       fc
        ebf     bf      bcf ->  def     df      cdf   ->  efd     fd      fcd

        ae      a       ac  ->  eb      b       bc    ->  be      b       bc
        e       abcdef  c   ->  e       abcdef  c     ->  e       bfcaed  c
        ef      f       cf  ->  de      d       cd    ->  ed      d       cd

        ade     ad      acd ->  aeb     ab      abc   ->  bae     ba      bca
        de      d       cd  ->  ae      a       ac    ->  ae      a       ca
        def     df      cdf ->  ade     ad      acd   ->  aed     ad      cad */

        if(partsToMove == 0 || partsToMove == 1) {
          currentCube[0][0][0] = currentCube[0][0][0].substring(1,2) + currentCube[0][0][0].substring(0,1) + currentCube[0][0][0].substring(2);
          //currentCube[0][1][0] = currentCube[0][1][0].substring() + currentCube[0][1][0].substring();
          currentCube[0][2][0] = currentCube[0][2][0].substring(1) + currentCube[0][2][0].substring(0,1);
          currentCube[1][0][0] = currentCube[1][0][0].substring(1) + currentCube[1][0][0].substring(0,1);
          //currentCube[1][1][0] = currentCube[1][1][0].substring() + currentCube[1][1][0].substring();
          currentCube[1][2][0] = currentCube[1][2][0].substring(1) + currentCube[1][2][0].substring(0,1);
          currentCube[2][0][0] = currentCube[2][0][0].substring(2) + currentCube[2][0][0].substring(0,2);
          //currentCube[2][1][0] = currentCube[2][1][0].substring() + currentCube[2][1][0].substring();
          currentCube[2][2][0] = currentCube[2][2][0].substring(0,1) + currentCube[2][2][0].substring(2) + currentCube[2][2][0].substring(1,2);
        }
        if(partsToMove == 0 || partsToMove == 2) {
          //currentCube[0][0][1] = currentCube[0][0][1].substring() + currentCube[0][0][1].substring();
          //currentCube[0][1][1] = currentCube[0][1][1].substring() + currentCube[0][1][1].substring();
          currentCube[0][2][1] = currentCube[0][2][1].substring(1) + currentCube[0][2][1].substring(0,1);
          //currentCube[1][0][1] = currentCube[1][0][1].substring() + currentCube[1][0][1].substring();
          currentCube[1][1][1] = currentCube[1][0][1] + currentCube[0][1][1] + currentCube[1][1][2] +
                                 currentCube[2][1][1] + currentCube[1][1][0] + currentCube[1][2][1];
          //currentCube[1][2][1] = currentCube[1][2][1].substring() + currentCube[1][2][1].substring();
          currentCube[2][0][1] = currentCube[2][0][1].substring(1) + currentCube[2][0][1].substring(0,1);
          //currentCube[2][1][1] = currentCube[2][1][1].substring() + currentCube[2][1][1].substring();
          //currentCube[2][2][1] = currentCube[2][2][1].substring() + currentCube[2][2][1].substring();
        }
        if(partsToMove == 0 || partsToMove == 3) {
          currentCube[0][0][2] = currentCube[0][0][2].substring(0,1) + currentCube[0][0][2].substring(2) + currentCube[0][0][2].substring(1,2);
          currentCube[0][1][2] = currentCube[0][1][2].substring(1) + currentCube[0][1][2].substring(0,1);
          currentCube[0][2][2] = currentCube[0][2][2].substring(2) + currentCube[0][2][2].substring(0,2);
          //currentCube[1][0][2] = currentCube[1][0][2].substring() + currentCube[1][0][2].substring();
          //currentCube[1][1][2] = currentCube[1][1][2].substring() + currentCube[1][1][2].substring();
          //currentCube[1][2][2] = currentCube[1][2][2].substring() + currentCube[1][2][2].substring();
          currentCube[2][0][2] = currentCube[2][0][2].substring(1) + currentCube[2][0][2].substring(0,1);
          currentCube[2][1][2] = currentCube[2][1][2].substring(1) + currentCube[2][1][2].substring(0,1);
          currentCube[2][2][2] = currentCube[2][2][2].substring(1,2) + currentCube[2][2][2].substring(0,1) + currentCube[2][2][2].substring(2);
        }
      }
      else if ( move == 2 ) { // Down
        if ( partsToMove == 0 ) { // Move Whole Cube
          for ( int j = 0 ; j < 3 ; j++ ) {
            tempOddRow[j]  = currentCube[oddMove[4]][oddMove[5]][j];
            tempEvenRow[j] = currentCube[evenMove[4]][evenMove[5]][j];
          }
          for ( int i = 4 ; i > 1 ; i-- ) {
            for ( int j = 0 ; j < 3 ; j++ ) {
              currentCube[oddMove[i]][oddMove[i+1]][j]   = currentCube[oddMove[i-1]][oddMove[i]][j];
              currentCube[evenMove[i]][evenMove[i+1]][j] = currentCube[evenMove[i-1]][evenMove[i]][j];
            }
          }
          for ( int j = 0 ; j < 3 ; j++ ) {
            currentCube[oddMove[1]][oddMove[2]][j]   = tempOddRow[j];
            currentCube[evenMove[1]][evenMove[2]][j] = tempEvenRow[j];
          }
        }
        else { // Move the part specified by 'partsToMove'

          tempOddRow[partsToMove-1]  = currentCube[oddMove[4]][oddMove[5]][partsToMove-1];
          tempEvenRow[partsToMove-1] = currentCube[evenMove[4]][evenMove[5]][partsToMove-1];

          for ( int i = 4 ; i > 1 ; i-- ) {
            currentCube[oddMove[i]][oddMove[i+1]][partsToMove-1]   = currentCube[oddMove[i-1]][oddMove[i]][partsToMove-1]; currentCube[evenMove[i]][evenMove[i+1]][partsToMove-1] = currentCube[evenMove[i-1]][evenMove[i]][partsToMove-1];
          }

          currentCube[oddMove[1]][oddMove[2]][partsToMove-1]   = tempOddRow[partsToMove-1];
          currentCube[evenMove[1]][evenMove[2]][partsToMove-1] = tempEvenRow[partsToMove-1];

        }

        /* Position of Current Cube - After Move 42

        aeb     ab      abc ->  ade     ad      acd   ->  dea     da      dac
        eb      b       bc  ->  ae      a       ac    ->  ea      a       ac
        ebf     bf      bcf ->  aeb     ab      abc   ->  eab     ab      acb

        ae      a       ac  ->  de      d       cd    ->  de      d       dc
        e       abcdef  c   ->  e       abcdef  c     ->  e       dacfeb  c
        ef      f       cf  ->  eb      b       bc    ->  eb      b       cb

        ade     ad      acd ->  def     df      cdf   ->  dfe     df      dcf
        de      d       cd  ->  ef      f       cf    ->  fe      f       cf
        def     df      cdf ->  ebf     bf      bcf   ->  feb     fb      cfb */

        if(partsToMove == 0 || partsToMove == 1) {
          currentCube[0][0][0] = currentCube[0][0][0].substring(1) + currentCube[0][0][0].substring(0,1);
          currentCube[0][1][0] = currentCube[0][1][0].substring(1) + currentCube[0][1][0].substring(0,1);
          currentCube[0][2][0] = currentCube[0][2][0].substring(1,2) + currentCube[0][2][0].substring(0,1) + currentCube[0][2][0].substring(2);
          //currentCube[1][0][0] = currentCube[1][0][0].substring() + currentCube[1][0][0].substring();
          //currentCube[1][1][0] = currentCube[1][1][0].substring() + currentCube[1][1][0].substring();
          //currentCube[1][2][0] = currentCube[1][2][0].substring() + currentCube[1][2][0].substring();
          currentCube[2][0][0] = currentCube[2][0][0].substring(0,1) + currentCube[2][0][0].substring(2) + currentCube[2][0][0].substring(1,2);
          currentCube[2][1][0] = currentCube[2][1][0].substring(1) + currentCube[2][1][0].substring(0,1);
          currentCube[2][2][0] = currentCube[2][2][0].substring(2) + currentCube[2][2][0].substring(0,2);
        }
        if(partsToMove == 0 || partsToMove == 2) {
          currentCube[0][0][1] = currentCube[0][0][1].substring(1) + currentCube[0][0][1].substring(0,1);
          //currentCube[0][1][1] = currentCube[0][1][1].substring() + currentCube[0][1][1].substring();
          //currentCube[0][2][1] = currentCube[0][2][1].substring() + currentCube[0][2][1].substring();
          //currentCube[1][0][1] = currentCube[1][0][1].substring() + currentCube[1][0][1].substring();
          currentCube[1][1][1] = currentCube[1][0][1] + currentCube[0][1][1] + currentCube[1][1][2] +
                                 currentCube[2][1][1] + currentCube[1][1][0] + currentCube[1][2][1];
          //currentCube[1][2][1] = currentCube[1][2][1].substring() + currentCube[1][2][1].substring();
          //currentCube[2][0][1] = currentCube[2][0][1].substring() + currentCube[2][0][1].substring();
          //currentCube[2][1][1] = currentCube[2][1][1].substring() + currentCube[2][1][1].substring();
          currentCube[2][2][1] = currentCube[2][2][1].substring(1) + currentCube[2][2][1].substring(0,1);
        }
        if(partsToMove == 0 || partsToMove == 3) {
          currentCube[0][0][2] = currentCube[0][0][2].substring(2) + currentCube[0][0][2].substring(0,2);
          //currentCube[0][1][2] = currentCube[0][1][2].substring() + currentCube[0][1][2].substring();
          currentCube[0][2][2] = currentCube[0][2][2].substring(0,1) + currentCube[0][2][2].substring(2) + currentCube[0][2][2].substring(1,2);
          currentCube[1][0][2] = currentCube[1][0][2].substring(1) + currentCube[1][0][2].substring(0,1);
          //currentCube[1][1][2] = currentCube[1][1][2].substring() + currentCube[1][1][2].substring();
          currentCube[1][2][2] = currentCube[1][2][2].substring(1) + currentCube[1][2][2].substring(0,1);
          currentCube[2][0][2] = currentCube[2][0][2].substring(1,2) + currentCube[2][0][2].substring(0,1) + currentCube[2][0][2].substring(2);
          //currentCube[2][1][2] = currentCube[2][1][2].substring() + currentCube[2][1][2].substring();
          currentCube[2][2][2] = currentCube[2][2][2].substring(1) + currentCube[2][2][2].substring(0,1);
        }
      }
      else if ( move == 3 ) { // Inverse
        if ( partsToMove == 0 ) { // Move Whole Cube
          for ( int i = 0 ; i < 2 ; i++ ) {
            for ( int j = 0 ; j < 3 ; j++ ) {

              tempOddRow[j]  = currentCube[oddMove[i]][oddMove[i+1]][j];
              tempEvenRow[j] = currentCube[evenMove[i]][evenMove[i+1]][j];

              currentCube[oddMove[i]][oddMove[i+1]][j]   = currentCube[oddMove[i+2]][oddMove[i+3]][j];
              currentCube[evenMove[i]][evenMove[i+1]][j] = currentCube[evenMove[i+2]][evenMove[i+3]][j];

              currentCube[oddMove[i+2]][oddMove[i+3]][j]   = tempOddRow[j];
              currentCube[evenMove[i+2]][evenMove[i+3]][j] = tempEvenRow[j];

            }
          }
        }
        else { // Move the part specified by 'partsToMove'
          for ( int i = 0 ; i < 2 ; i++ ) {

              tempOddRow[partsToMove-1]  = currentCube[oddMove[i]][oddMove[i+1]][partsToMove-1];
              tempEvenRow[partsToMove-1] = currentCube[evenMove[i]][evenMove[i+1]][partsToMove-1];

              currentCube[oddMove[i]][oddMove[i+1]][partsToMove-1]   = currentCube[oddMove[i+2]][oddMove[i+3]][partsToMove-1];
              currentCube[evenMove[i]][evenMove[i+1]][partsToMove-1] = currentCube[evenMove[i+2]][evenMove[i+3]][partsToMove-1];

              currentCube[oddMove[i+2]][oddMove[i+3]][partsToMove-1]   = tempOddRow[partsToMove-1];
              currentCube[evenMove[i+2]][evenMove[i+3]][partsToMove-1] = tempEvenRow[partsToMove-1];

          }
        }

        /* Position of Current Cube - After Move

        def     df      cdf ->  fed     fd      fdc
        de      d       cd  ->  ed      d       dc
        ade     ad      acd ->  eda     da      dca

        ef      f       cf  ->  fe      f       fc
        e       abcdef  c   ->  e       fdcbea  c
        ae      a       ac  ->  ea      a       ca

        ebf     bf      bcf ->  fbe     fb      fcb
        eb      b       bc  ->  be      b       cb
        aeb     ab      abc ->  bea     ba      cba */

        if(partsToMove == 0 || partsToMove == 1) {
          currentCube[0][0][0] = currentCube[0][0][0].substring(2) + currentCube[0][0][0].substring(1,2) + currentCube[0][0][0].substring(0,1);
          currentCube[0][1][0] = currentCube[0][1][0].substring(1) + currentCube[0][1][0].substring(0,1);
          currentCube[0][2][0] = currentCube[0][2][0].substring(2) + currentCube[0][2][0].substring(1,2) + currentCube[0][2][0].substring(0,1);
          currentCube[1][0][0] = currentCube[1][0][0].substring(1) + currentCube[1][0][0].substring(0,1);
          //currentCube[1][1][0] = currentCube[1][1][0].substring() + currentCube[1][1][0].substring();
          currentCube[1][2][0] = currentCube[1][2][0].substring(1) + currentCube[1][2][0].substring(0,1);
          currentCube[2][0][0] = currentCube[2][0][0].substring(2) + currentCube[2][0][0].substring(1,2) + currentCube[2][0][0].substring(0,1);
          currentCube[2][1][0] = currentCube[2][1][0].substring(1) + currentCube[2][1][0].substring(0,1);
          currentCube[2][2][0] = currentCube[2][2][0].substring(2) + currentCube[2][2][0].substring(1,2) + currentCube[2][2][0].substring(0,1);
        }
        if(partsToMove == 0 || partsToMove == 2) {
          currentCube[0][0][1] = currentCube[0][0][1].substring(1) + currentCube[0][0][1].substring(0,1);
          //currentCube[0][1][1] = currentCube[0][1][1].substring() + currentCube[0][1][1].substring();
          currentCube[0][2][1] = currentCube[0][2][1].substring(1) + currentCube[0][2][1].substring(0,1);
          //currentCube[1][0][1] = currentCube[1][0][1].substring() + currentCube[1][0][1].substring();
          currentCube[1][1][1] = currentCube[1][0][1] + currentCube[0][1][1] + currentCube[1][1][2] +
                                 currentCube[2][1][1] + currentCube[1][1][0] + currentCube[1][2][1];
          //currentCube[1][2][1] = currentCube[1][2][1].substring() + currentCube[1][2][1].substring();
          currentCube[2][0][1] = currentCube[2][0][1].substring(1) + currentCube[2][0][1].substring(0,1);
          //currentCube[2][1][1] = currentCube[2][1][1].substring() + currentCube[2][1][1].substring();
          currentCube[2][2][1] = currentCube[2][2][1].substring(1) + currentCube[2][2][1].substring(0,1);
        }
        if(partsToMove == 0 || partsToMove == 3) {
          currentCube[0][0][2] = currentCube[0][0][2].substring(2) + currentCube[0][0][2].substring(1,2) + currentCube[0][0][2].substring(0,1);
          currentCube[0][1][2] = currentCube[0][1][2].substring(1) + currentCube[0][1][2].substring(0,1);
          currentCube[0][2][2] = currentCube[0][2][2].substring(2) + currentCube[0][2][2].substring(1,2) + currentCube[0][2][2].substring(0,1);
          currentCube[1][0][2] = currentCube[1][0][2].substring(1) + currentCube[1][0][2].substring(0,1);
          //currentCube[1][1][2] = currentCube[1][1][2].substring() + currentCube[1][1][2].substring();
          currentCube[1][2][2] = currentCube[1][2][2].substring(1) + currentCube[1][2][2].substring(0,1);
          currentCube[2][0][2] = currentCube[2][0][2].substring(2) + currentCube[2][0][2].substring(1,2) + currentCube[2][0][2].substring(0,1);
          currentCube[2][1][2] = currentCube[2][1][2].substring(1) + currentCube[2][1][2].substring(0,1);
          currentCube[2][2][2] = currentCube[2][2][2].substring(2) + currentCube[2][2][2].substring(1,2) + currentCube[2][2][2].substring(0,1);
        }
      }
      else
        return false;
    }
    else if ( typeOfMove == "horizontal" ) { // Horizontal
      if ( move == 1 ) { // Right
        // Position of Current Cube - After Right Move (Clockwise)
        /*
        After Right Move : true
        aeb     ab      abc ->  ade     ae      aeb
        eb      b       bc  ->  de      e       eb
        ebf     bf      bcf ->  def     ef      ebf
        ae      a       ac  ->  ad      a       ab
        e       abcdef  c   ->  d       aebcdf  b
        ef      f       cf  ->  df      f       bf
        ade     ad      acd ->  acd     ac      abc
        de      d       cd  ->  cd      c       bc
        def     df      cdf ->  cdf     cf      bcf

        000 001 002
        010 011 012
        020 021 022

        100 101 102
        110 111 112
        120 121 122

        200 201 202
        210 211 212
        220 221 222*/
        if ( partsToMove == 0 ) { // Move Whole Cube
          for ( int j = 0 ; j < 3 ; j++ ) {
            tempOddRow[j]  = currentCube[oddMove[4]][j][oddMove[5]];
            tempEvenRow[j] = currentCube[evenMove[4]][j][evenMove[5]];
          }
          for ( int i = 4 ; i > 1 ; i-- ) {
            for ( int j = 0 ; j < 3 ; j++ ) {
              currentCube[oddMove[i]][j][oddMove[i+1]]   = currentCube[oddMove[i-1]][j][oddMove[i]];
              currentCube[evenMove[i]][j][evenMove[i+1]] = currentCube[evenMove[i-1]][j][evenMove[i]];
            }
          }
          for ( int j = 0 ; j < 3 ; j++ ) {
            currentCube[oddMove[1]][j][oddMove[2]]   = tempOddRow[j];
            currentCube[evenMove[1]][j][evenMove[2]] = tempEvenRow[j];
          }
        }
        else { // Move the part specified by 'partsToMove'

          tempOddRow[partsToMove-1]  = currentCube[oddMove[4]][partsToMove-1][oddMove[5]];
          tempEvenRow[partsToMove-1] = currentCube[evenMove[4]][partsToMove-1][evenMove[5]];

          for ( int i = 4 ; i > 1 ; i-- ) {
              currentCube[oddMove[i]][partsToMove-1][oddMove[i+1]]   = currentCube[oddMove[i-1]][partsToMove-1][oddMove[i]];
              currentCube[evenMove[i]][partsToMove-1][evenMove[i+1]] = currentCube[evenMove[i-1]][partsToMove-1][evenMove[i]];
          }

          currentCube[oddMove[1]][partsToMove-1][oddMove[2]]   = tempOddRow[partsToMove-1];
          currentCube[evenMove[1]][partsToMove-1][evenMove[2]] = tempEvenRow[partsToMove-1];

        }
        currentCube[1][1][1] = currentCube[1][0][1] + currentCube[0][1][1] + currentCube[1][1][2] +
                               currentCube[2][1][1] + currentCube[1][1][0] + currentCube[1][2][1];
      }
      else if ( move == 2 ) { // Left
        // Position of Current Cube - After Left Move (Counter Clockwise)
        /*
        After Left Move : true
        aeb     ab      abc ->  abc     ac      acd
        eb      b       bc  ->  bc      c       cd
        ebf     bf      bcf ->  bcf     cf      cdf
        ae      a       ac  ->  ab      a       ad
        e       abcdef  c   ->  b       acdebf  d
        ef      f       cf  ->  bf      f       df
        ade     ad      acd ->  aeb     ae      ade
        de      d       cd  ->  eb      e       de
        def     df      cdf ->  ebf     ef      def
        */
        if ( partsToMove == 0 ) { // Move Whole Cube
          for ( int j = 0 ; j < 3 ; j++ ) {
             tempOddRow[j]  = currentCube[oddMove[4]][j][oddMove[5]];
             tempEvenRow[j] = currentCube[evenMove[4]][j][evenMove[5]];
          }
          for ( int i = 1 ; i < 4 ; i++ ) {
            for ( int j = 0 ; j < 3 ; j++ ) {
              currentCube[oddMove[i-1]][j][oddMove[i]]   = currentCube[oddMove[i]][j][oddMove[i+1]];
              currentCube[evenMove[i-1]][j][evenMove[i]] = currentCube[evenMove[i]][j][evenMove[i+1]];
            }
          }
          for ( int j = 0 ; j < 3 ; j++ ) {
            currentCube[oddMove[3]][j][oddMove[4]]   = tempOddRow[j];
            currentCube[evenMove[3]][j][evenMove[4]] = tempEvenRow[j];
          }
        }
        else { // Move the part specified by 'partsToMove'

          tempOddRow[partsToMove-1]  = currentCube[oddMove[4]][partsToMove-1][oddMove[5]];
          tempEvenRow[partsToMove-1] = currentCube[evenMove[4]][partsToMove-1][evenMove[5]];

          for ( int i = 1 ; i < 4 ; i++ ) {
              currentCube[oddMove[i-1]][partsToMove-1][oddMove[i]]   = currentCube[oddMove[i]][partsToMove-1][oddMove[i+1]];
              currentCube[evenMove[i-1]][partsToMove-1][evenMove[i]] = currentCube[evenMove[i]][partsToMove-1][evenMove[i+1]];
          }

          currentCube[oddMove[3]][partsToMove-1][oddMove[4]]   = tempOddRow[partsToMove-1];
          currentCube[evenMove[3]][partsToMove-1][evenMove[4]] = tempEvenRow[partsToMove-1];

        }
        currentCube[1][1][1] = currentCube[1][0][1] + currentCube[0][1][1] + currentCube[1][1][2] +
                               currentCube[2][1][1] + currentCube[1][1][0] + currentCube[1][2][1];
      }
      else if ( move == 3 ) { // Inverse
        // Position of Current Cube - After Inverse Move - 180 degree rotation
        /*
        After Inverse Move : true
        aeb     ab      abc ->  acd     ad      ade
        eb      b       bc  ->  cd      d       de
        ebf     bf      bcf ->  cdf     df      def
        ae      a       ac  ->  ac      a       ae
        e       abcdef  c   ->  c       adebcf  e
        ef      f       cf  ->  cf      f       ef
        ade     ad      acd ->  abc     ab      aeb
        de      d       cd  ->  bc      b       eb
        def     df      cdf ->  bcf     bf      ebf
        */
        if ( partsToMove == 0 ) { // Move Whole Cube
          for ( int i = 0 ; i < 2 ; i++ ) {
            for ( int j = 0 ; j < 3 ; j++ ) {

              tempOddRow[j]  = currentCube[oddMove[i]][j][oddMove[i+1]];
              tempEvenRow[j] = currentCube[evenMove[i]][j][evenMove[i+1]];

              currentCube[oddMove[i]][j][oddMove[i+1]]   = currentCube[oddMove[i+2]][j][oddMove[i+3]];
              currentCube[evenMove[i]][j][evenMove[i+1]] = currentCube[evenMove[i+2]][j][evenMove[i+3]];

              currentCube[oddMove[i+2]][j][oddMove[i+3]]   = tempOddRow[j];
              currentCube[evenMove[i+2]][j][evenMove[i+3]] = tempEvenRow[j];

            }
          }
        }
        else { // Move the part specified by 'partsToMove'
          for ( int i = 0 ; i < 2 ; i++ ) {

            tempOddRow[partsToMove-1]  = currentCube[oddMove[i]][partsToMove-1][oddMove[i+1]];
            tempEvenRow[partsToMove-1] = currentCube[evenMove[i]][partsToMove-1][evenMove[i+1]];

            currentCube[oddMove[i]][partsToMove-1][oddMove[i+1]]   = currentCube[oddMove[i+2]][partsToMove-1][oddMove[i+3]];
            currentCube[evenMove[i]][partsToMove-1][evenMove[i+1]] = currentCube[evenMove[i+2]][partsToMove-1][evenMove[i+3]];

            currentCube[oddMove[i+2]][partsToMove-1][oddMove[i+3]]   = tempOddRow[partsToMove-1];
            currentCube[evenMove[i+2]][partsToMove-1][evenMove[i+3]] = tempEvenRow[partsToMove-1];

          }
        }

        currentCube[1][1][1] = currentCube[1][0][1] + currentCube[0][1][1] + currentCube[1][1][2] +
                               currentCube[2][1][1] + currentCube[1][1][0] + currentCube[1][2][1];
      }
      else
        return false;
    }
    else if ( typeOfMove == "rotate" ) {
      int newMoveToMake;

      if ( partsToMove == 0 )
        newMoveToMake = 40 + move;
      else
        newMoveToMake = (((moveToMake / 10) % 4) * 10) + move;

      makeMove(82);
      makeMove(newMoveToMake);
      makeMove(81);
    }
    else
      return false;

    return true;
  }

  private static void scrambleCube(int numberOfMovesForScrambling) {
    int move;

    //System.out.print("Scrambled Moves : ");
    for ( int i = 0 ; i < numberOfMovesForScrambling ; i++ ) {
      move = (ThreadLocalRandom.current().nextInt(1, 13) * 10) + (ThreadLocalRandom.current().nextInt(1, 4));
      makeMove(move);
      //System.out.print(move + " ");
    }
    //System.out.println("");
  }

  private static boolean rubikSolver() {
    // Depth First Search(DFS) with Depth limited
    // Returns first solution
    // Not Optimal

    level++;
    int lastMove = 0;

    if(level > 1)
      lastMove = movesMade.get(movesMade.size()-1);

    for ( int i = 0 ; i < allPossibleMoves.length ; i++ ) {
      // Trim the Moves
      if ( lastMove / 10 == allPossibleMoves[i] / 10 )
        continue;

      // Make the move!
      makeMove(allPossibleMoves[i]);
      movesMade.add(allPossibleMoves[i]);

      // Terminating Condition
      if ( isCubeSolved() == true )
        return true;

      // Recursive Call
      if ( level < numberofMovesToMake)
        if( rubikSolver() == true)
          return true;

      // Unmake the previous move!
      makeMove(allPossibleMovesReversal[i]);
      movesMade.remove(movesMade.size()-1);
    }
    level--;
    return false;
  }

  private static boolean rubikPartialSolver() {
    // Depth First Search(DFS) with Depth limited
    // Returns Optimal Solution

    level++;
    int lastMove = 0;

    if(level > 1)
      lastMove = partialMovesMade.get(partialMovesMade.size()-1);

    for ( int i = 0 ; i < allPossibleMoves.length ; i++ ) {
      // Trim the Moves
      if ( lastMove / 10 == allPossibleMoves[i] / 10 )
        continue;

      // Make the move!
      makeMove(allPossibleMoves[i]);
      partialMovesMade.add(allPossibleMoves[i]);

      // Terminating Condition
      if ( isCubePartiallySolved(partialLevel) == true ) {
        optimalSolution = true;
        optimalPartialMovesMade = new ArrayList<Integer>(partialMovesMade);

        makeMove(allPossibleMovesReversal[i]);
        partialMovesMade.remove(partialMovesMade.size()-1);
        level--;
        maximumDepthOfMoves--;
        return true;
      }

      // Recursive Call
      if ( level < maximumDepthOfMoves)
        rubikPartialSolver();

        // Unmake the previous move!
        makeMove(allPossibleMovesReversal[i]);
      partialMovesMade.remove(partialMovesMade.size()-1);
    }
    level--;
    if (optimalSolution == true)
      return true;
    return false;
  }

  private static void scrambleCubeForBottomCenterPieces(int numberOfMovesForScrambling) {
    int move;

    //System.out.print("Scrambled Moves : ");
    for ( int i = 0 ; i < numberOfMovesForScrambling ; i++ ) {
      int part1 = ThreadLocalRandom.current().nextInt(1, 3) * 10;
      if (part1 == 10)
        move = ThreadLocalRandom.current().nextInt(1, 4);
      else
        move = 10 + (ThreadLocalRandom.current().nextInt(1, 3));
      makeMoveForBottomCenterPieces(move);
      //System.out.print(move + " ");
    }
    //System.out.println("");
  }

  private static boolean makeMoveForBottomCenterPieces(int moveToMake) {
    int typeOfMove = moveToMake / 10;
    int move = moveToMake % 10;

    if (typeOfMove == 0) { // Rotate the Cube
      if (move == 1) { // Counterclockwise
        String temp = currentBottomCenterPieces[0];
        for (int i=0 ; i<3 ; i++)
          currentBottomCenterPieces[i] = currentBottomCenterPieces[i+1];
        currentBottomCenterPieces[3] = temp;
      }
      else if (move == 2) { // Clockwise
        String temp = currentBottomCenterPieces[3];
        for (int i=3 ; i>0 ; i--)
          currentBottomCenterPieces[i] = currentBottomCenterPieces[i-1];
        currentBottomCenterPieces[0] = temp;
      }
      else if (move == 3) { // Rotate
        String temp1 = currentBottomCenterPieces[0];
        String temp2 = currentBottomCenterPieces[1];

        currentBottomCenterPieces[0] = currentBottomCenterPieces[2];
        currentBottomCenterPieces[1] = currentBottomCenterPieces[3];

        currentBottomCenterPieces[2] = temp1;
        currentBottomCenterPieces[3] = temp2;
      }
      else
        return false;
    }
    else { // Rotate Partial Cube
      if (move == 1) { // Counterclockwise
        String temp = currentBottomCenterPieces[1];
        for (int i=1 ; i<3 ; i++)
          currentBottomCenterPieces[i] = currentBottomCenterPieces[i+1];
        currentBottomCenterPieces[3] = temp;
      }
      else if (move == 2) { // Clockwise
        String temp = currentBottomCenterPieces[3];
        for (int i=3 ; i>1 ; i--)
          currentBottomCenterPieces[i] = currentBottomCenterPieces[i-1];
        currentBottomCenterPieces[1] = temp;
      }
      else
        return false;
    }
    return true;
  }

  private static boolean isCubePartiallySolvedForBottomCenterPieces() {
    for ( int i=0 ; i<4 ; i++ )
      if (currentBottomCenterPieces[i].equals(solvedBottomCenterPieces[i]) == false)
        return false;
    return true;
  }

  private static boolean rubikPartialSolverForBottomCenterPieces() {
    // Depth First Search(DFS) with Depth limited
    // Returns Optimal Solution

    level++;
    int lastMove = 30;

    if(level > 1)
      lastMove = partialMovesMade.get(partialMovesMade.size()-1);

    for (int i=0; i<allPossibleMovesForBottomCenterPieces.length ; i++) {
      // Trim the Moves
      if ( lastMove / 10 == allPossibleMovesForBottomCenterPieces[i] / 10 )
        continue;

      // Make the move!
      makeMoveForBottomCenterPieces(allPossibleMovesForBottomCenterPieces[i]);
      partialMovesMade.add(allPossibleMovesForBottomCenterPieces[i]);

      // Terminating Condition
      if (isCubePartiallySolvedForBottomCenterPieces() == true){
        optimalSolution = true;
        optimalPartialMovesMade = new ArrayList<Integer>(partialMovesMade);

        makeMoveForBottomCenterPieces(allPossibleMovesReversalForBottomCenterPieces[i]);
        partialMovesMade.remove(partialMovesMade.size()-1);
        level--;
        maximumDepthOfMoves--;
        return true;
      }

      // Recursive Call
      if (level < maximumDepthOfMoves)
        rubikPartialSolverForBottomCenterPieces();

      // Unmake the previous move!
      makeMoveForBottomCenterPieces(allPossibleMovesReversalForBottomCenterPieces[i]);
      partialMovesMade.remove(partialMovesMade.size()-1);
    }
    level--;
    if (optimalSolution == true)
      return true;
    return false;
  }

  private static boolean rubikSolverUsingSpeedCubing() {

    boolean solved = false;
    int currentLevel = 0;

    //solveTop();

    // A. Solve Top Center Pieces
    do {
      solved = false;
      partialLevel = currentLevel;
      intermediateLevel = "";
      if (isCubePartiallySolved(partialLevel) == true) {
        solved = true;
      }
      else {
        level = 0;
        partialMovesMade.clear();
        optimalSolution = false;
        optimalPartialMovesMade.clear();
        maximumDepthOfMoves = numberofPartialMovesToMake[partialLevel];
        solved = rubikPartialSolver();
        if(solved)
        for(int currentMove:optimalPartialMovesMade)
          makeMove(currentMove);
      }

      if(solved == false)
        return solved;
      currentLevel++;
    }while(currentLevel < 4);

    System.out.print("Level " + (currentLevel-1) + " Solved!");

    // B. Solve Top Corner Pieces
    do{
      solved = false;
      partialLevel = currentLevel;
      intermediateLevel = "";
      if (isCubePartiallySolved(partialLevel) == true){
        solved = true;
      }
      else {
        intermediateLevel = "TopCornerPieces";
        if (isCubePartiallySolved(partialLevel) == true){
          solved = true;
        }
        else {
          level = 0;
          partialMovesMade.clear();
          optimalSolution = false;
          optimalPartialMovesMade.clear();
          maximumDepthOfMoves = numberofPartialMovesToMake[partialLevel];
          solved = rubikPartialSolver();
          if(solved)
          for(int currentMove:optimalPartialMovesMade)
            makeMove(currentMove);
        }
        if(solved) {
          level = 0;
          partialMovesMade.clear();
          intermediateLevel = "";
          optimalSolution = false;
          optimalPartialMovesMade.clear();
          maximumDepthOfMoves = numberofPartialMovesToMake[partialLevel];
          solved = rubikPartialSolver();
          if(solved)
          for(int currentMove:optimalPartialMovesMade)
            makeMove(currentMove);
        }
      }

      if(solved == false)
        return solved;
      currentLevel++;
    }while(currentLevel < 8);

    System.out.print("Level " + (currentLevel-1) + " Solved!");

    //solveMiddle();
    do{
      solved = false;
      partialLevel = currentLevel;
      intermediateLevel = "";
      if (isCubePartiallySolved(partialLevel) == true){
        solved = true;
      }
      else {
        int a = Integer.parseInt(cubePartiallySolved[partialLevel].substring(0,1));
        int b = Integer.parseInt(cubePartiallySolved[partialLevel].substring(1,2));
        int c = Integer.parseInt(cubePartiallySolved[partialLevel].substring(2));
        int d = -1;
        int e = -1;
        int f = -1;
        int noOfRotations = 0;
        boolean foundMiddleCenterPieceInCenter = false;
        for ( int i=partialLevel ; i<12 ; i++ ) {
            d = Integer.parseInt(cubePartiallySolved[i].substring(0,1));
            e = Integer.parseInt(cubePartiallySolved[i].substring(1,2));
            f = Integer.parseInt(cubePartiallySolved[i].substring(2));
            if (solvedCube[a][b][c].contains(currentCube[d][e][f].substring(0,1)) == true &&
                solvedCube[a][b][c].contains(currentCube[d][e][f].substring(1)) == true) {
                  foundMiddleCenterPieceInCenter = true;
                  noOfRotations = i - 9; // 9 is the Index of Middle Center Right Piece of Front Face!!!
                  break;
                }
        }
        if (foundMiddleCenterPieceInCenter == true) {
          switch(noOfRotations) {
            case -1: makeMove(81);
                     makeMove(32); makeMove(71); makeMove(31); makeMove(71); makeMove(92); makeMove(72); makeMove(91);
                     makeMove(82);
                     break;
            case 0 : makeMove(32); makeMove(71); makeMove(31); makeMove(71); makeMove(92); makeMove(72); makeMove(91);
                     break;
            case 1 : makeMove(82);
                     makeMove(32); makeMove(71); makeMove(31); makeMove(71); makeMove(92); makeMove(72); makeMove(91);
                     makeMove(81);
                     break;
            case 2 : makeMove(83);
                     makeMove(32); makeMove(71); makeMove(31); makeMove(71); makeMove(92); makeMove(72); makeMove(91);
                     makeMove(83);
                     break;
            default: System.out.println("Invalid case");
                     break;
          }
        }

        intermediateLevel = "MiddleCenterPieces";
        if (isCubePartiallySolved(partialLevel) == true){
          solved = true;
        }
        else {
          level = 0;
          partialMovesMade.clear();
          optimalSolution = false;
          optimalPartialMovesMade.clear();
          maximumDepthOfMoves = numberofPartialMovesToMake[partialLevel];
          solved = rubikPartialSolver();
          if(solved)
          for(int currentMove:optimalPartialMovesMade)
            makeMove(currentMove);
        }

        // Note: intermediatePosition1 & intermediatePosition2 retain the value for solution only because the solution is one move long.
        // Which mens the recursive call returns right after finding the solution.
        if(solved) {
          noOfRotations = partialLevel - 9;
          switch(noOfRotations) {
            case -1: makeMove(81);
                     if (intermediatePosition1 == true) {
                       makeMove(71); makeMove(92); makeMove(72); makeMove(91); makeMove(72); makeMove(32); makeMove(71); makeMove(31);
                     }
                     else if (intermediatePosition2 == true) {
                       makeMove(72); makeMove(32); makeMove(71); makeMove(31); makeMove(71); makeMove(92); makeMove(72); makeMove(91);
                     }
                     makeMove(82);
                     break;
            case 0 : if (intermediatePosition1 == true) {
                       makeMove(71); makeMove(92); makeMove(72); makeMove(91); makeMove(72); makeMove(32); makeMove(71); makeMove(31);
                     }
                     else if (intermediatePosition2 == true) {
                       makeMove(72); makeMove(32); makeMove(71); makeMove(31); makeMove(71); makeMove(92); makeMove(72); makeMove(91);
                     }
                     break;
            case 1 : makeMove(82);
                     if (intermediatePosition1 == true) {
                       makeMove(71); makeMove(92); makeMove(72); makeMove(91); makeMove(72); makeMove(32); makeMove(71); makeMove(31);
                     }
                     else if (intermediatePosition2 == true) {
                       makeMove(72); makeMove(32); makeMove(71); makeMove(31); makeMove(71); makeMove(92); makeMove(72); makeMove(91);
                     }
                     makeMove(81);
                     break;
            case 2 : makeMove(83);
                     if (intermediatePosition1 == true) {
                       makeMove(71); makeMove(92); makeMove(72); makeMove(91); makeMove(72); makeMove(32); makeMove(71); makeMove(31);
                     }
                     else if (intermediatePosition2 == true) {
                       makeMove(72); makeMove(32); makeMove(71); makeMove(31); makeMove(71); makeMove(92); makeMove(72); makeMove(91);
                     }
                     makeMove(83);
                     break;
            default: System.out.println("Invalid case");
                     break;
          }
        }
      }

      if(solved == false)
        return solved;
      currentLevel++;
    }while(currentLevel < 12);

    System.out.print("Level " + (currentLevel-1) + " Solved!");

    //solveBottom();

    // A. Solve Bottom Center Pieces
    {
      solved = false;
      partialLevel = currentLevel;
      intermediateLevel = "";
      // Get the Position of Bottom Center Pieces
      for (int i=12 ; i<16 ; i++) {
        int a = Integer.parseInt(cubePartiallySolved[i].substring(0,1));
        int b = Integer.parseInt(cubePartiallySolved[i].substring(1,2));
        int c = Integer.parseInt(cubePartiallySolved[i].substring(2));

        if (solvedCube[1][2][1].equals(currentCube[a][b][c].substring(0,1)) == true)
          currentBottomCenterPieces[i-12] = currentCube[a][b][c].substring(1);
        else
          currentBottomCenterPieces[i-12] = currentCube[a][b][c].substring(0,1);
      }

      if (isCubePartiallySolvedForBottomCenterPieces() == true) {
        solved = true;
      }
      else {
        // Solve the position thru Recursion
        level = 0;
        partialMovesMade.clear();
        optimalSolution = false;
        optimalPartialMovesMade.clear();
        maximumDepthOfMoves = numberofPartialMovesToMake[12];
        solved = rubikPartialSolverForBottomCenterPieces();

        if(solved) {
          // Make moves for it
          for ( int currentMove:optimalPartialMovesMade ) {
            switch(currentMove) {
              case 1 : makeMove(72);
                       break;
              case 2 : makeMove(71);
                       break;
              case 3 : makeMove(73);
                       break;
              case 11 : makeMove(81);
                        // 71 92 72 91 72 - 32 73 31 71 92 - 72 91 72
                        makeMove(71); makeMove(92); makeMove(72); makeMove(91); makeMove(72);
                        makeMove(32); makeMove(73); makeMove(31); makeMove(71); makeMove(92);
                        makeMove(72); makeMove(91); makeMove(72);
                        makeMove(82);
                        break;
              case 12 : // 72 32 71 31 71 - 92 73 91 72 32 - 71 31 71
                        makeMove(72); makeMove(32); makeMove(71); makeMove(31); makeMove(71);
                        makeMove(92); makeMove(73); makeMove(91); makeMove(72); makeMove(32);
                        makeMove(71); makeMove(31); makeMove(71);
                        break;
              default: System.out.println("Invalid case");
                       break;
            }
          }
        }
      }

      if(solved == false)
        return solved;

      // Correct the orientation if required!
      boolean firstTime = true;
      movesToRotate.clear();

      for (int i=12 ; i<16 ; i++) {
        int a = Integer.parseInt(cubePartiallySolved[i].substring(0,1));
        int b = Integer.parseInt(cubePartiallySolved[i].substring(1,2));
        int c = Integer.parseInt(cubePartiallySolved[i].substring(2));

        if (solvedCube[a][b][c].equals(currentCube[a][b][c]) == false)
          movesToRotate.add(i-12);
      }

      if (movesToRotate.size() > 0) {
        firstTime = true;
        int previousMove = 0;

        for (int currentMove:movesToRotate) {
          if(firstTime == true) {
            // rotate cube
            switch(currentMove) {
              case 0 : break;
              case 1 : makeMove(82);
                       break;
              case 2 : makeMove(83);
                       break;
              case 3 : makeMove(81);
                       break;
              default: System.out.println("Invalid case");
                       break;
            }
            previousMove = currentMove;
            firstTime = false;
          }
          else {
            // Rotate Bottom Row
            switch( currentMove - previousMove ) {
              case 0 : break;
              case 1 : makeMove(72);
                       break;
              case 2 : makeMove(73);
                       break;
              case 3 : makeMove(71);
                       break;
              default: System.out.println("Invalid case");
                       break;
            }
            previousMove = currentMove;
          }

          makeMove(92); makeMove(62);
          makeMove(92); makeMove(62);
          makeMove(92); makeMove(62);
          makeMove(92); makeMove(62);
        } // End of For - Correct orientation

        level = 0;
        partialMovesMade.clear();
        optimalSolution = false;
        optimalPartialMovesMade.clear();
        maximumDepthOfMoves = numberofPartialMovesToMake[15];
        solved = rubikPartialSolver();
        if(solved)
        for(int thisMove:optimalPartialMovesMade)
          makeMove(thisMove);
      }

      if(solved == false)
        return solved;
      currentLevel = 16;
    }

    System.out.print(" Level " + (currentLevel-1) + " Solved! ");

    // B. Solve Bottom Corner Pieces

    return solved;
  }

  public static void main(String[] args) {

    boolean looper = true;
    do {
        makeMoveCount = 0;

        // Copying Initial Position to Current Cube
        for ( int i=0;i<3;i++ )
         for ( int j=0;j<3;j++ )
           for ( int k=0;k<3;k++ )
             currentCube[i][j][k] = solvedCube[i][j][k];

        // Scramble the Cube
        scrambleCube(100);

        Instant start = Instant.now();
        looper = rubikSolverUsingSpeedCubing();
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Rubik's Cube Solved : " + looper + " Time taken: "+ timeElapsed.toMillis() +" milliseconds, "
                           + "Count: " + makeMoveCount + " Ratio: " + (makeMoveCount/timeElapsed.toMillis()));

    }while(looper);
  }
}
