import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.time.Duration;
import java.time.Instant;

public class RubikTest{

  private static int[] allPossibleMoves         = new int[]{11,12,13,21,22,23,31,32,33,41,42,43,51,52,53,61,62,63,71,72,73,81,82,83,
                                                            91,92,93,101,102,103,111,112,113,121,122,123};
  private static int[] allPossibleMovesReversal = new int[]{12,11,13,22,21,23,32,31,33,42,41,43,52,51,53,62,61,63,72,71,73,82,81,83,
                                                            92,91,93,102,101,103,112,111,113,122,121,123};

  private static ArrayList<Integer> movesMade         = new ArrayList<Integer>();
  private static ArrayList<Integer> partialMovesMade  = new ArrayList<Integer>();

  private static int level                      = 0;
  private static int numberofMovesToMake        = 5;

  private static int partialLevel               = 0;
  private static String intermediateLevel       = "";

  private static String[][][] solvedCube = new String[][][]{{{"aeb",   "ab",    "abc"},  //1 - 0 0
                                                             {"eb",    "b",     "bc"},   //2 - 0 1
                                                             {"ebf",   "bf",    "bcf"}}, //3 - 0 2

                                                            {{"ae",    "a",     "ac"},   //8 - 1 0
                                                             {"e",     "abcdef","c"},    //Axis for Vertical move - 1 1
                                                             {"ef",    "f",     "cf"}},  //4 - 1 2

                                                            {{"ade",   "ad",    "acd"},  //7 - 2 0
                                                             {"de",    "d",     "cd"},   //6 - 2 1
                                                             {"def",   "df",    "cdf"}}};//5 - 2 2

  private static String[][][] currentCube = new String[][][]{{{"aeb",   "ab",    "abc"},
                                                              {"eb",    "b",     "bc"},
                                                              {"ebf",   "bf",    "bcf"}},
                                                             {{"ae",    "a",     "ac"},
                                                              {"e",     "abcdef","c"},
                                                              {"ef",    "f",     "cf"}},
                                                             {{"ade",   "ad",    "acd"},
                                                              {"de",    "d",     "cd"},
                                                              {"def",   "df",    "cdf"}}};

  private static String[] cubePartiallySolved = new String[]{"001", "102", "201", "100",  // Top Center Pieces
                                                             "000", "002", "202", "200",  // Top Corner Pieces
                                                             "010", "012", "212", "210",  // Middle Center Pieces
                                                             "021", "122", "221", "120",  // Bottom Center Pieces
                                                             "020", "022", "222", "220"}; // Bottom Corner Pieces

  private static String[][] intermediateLevelForTopCenterPieces = new String[][]{{"022", "020", "022", "020"},
                                                                                 {"220", "222", "220", "222"}};

  private static int[] numberofPartialMovesToMake = new int[]{ 4,4,4,4,
                                                               4,4,5,5,
                                                               5,5,5,5,
                                                               5,5,5,5,
                                                               5,5,5,5};

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
      boolean intermediatePosition1 = false, intermediatePosition2 = false;

      a = Integer.parseInt(cubePartiallySolved[partialLevel].substring(0,1));
      b = Integer.parseInt(cubePartiallySolved[partialLevel].substring(1,2));
      c = Integer.parseInt(cubePartiallySolved[partialLevel].substring(2));

      intermediateIndex = partialLevel % 4;
      d = Integer.parseInt(intermediateLevelForTopCenterPieces[0][intermediateIndex].substring(0,1));
      e = Integer.parseInt(intermediateLevelForTopCenterPieces[0][intermediateIndex].substring(1,2));
      f = Integer.parseInt(intermediateLevelForTopCenterPieces[0][intermediateIndex].substring(2));

      g = Integer.parseInt(intermediateLevelForTopCenterPieces[1][intermediateIndex].substring(0,1));
      h = Integer.parseInt(intermediateLevelForTopCenterPieces[1][intermediateIndex].substring(1,2));
      i = Integer.parseInt(intermediateLevelForTopCenterPieces[1][intermediateIndex].substring(2));

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
      if ( move == 1 ) { // Counter Clockwise
        if ( partsToMove == 0 ) { // Move Whole Cube
            for ( int j = 0 ; j < 3 ; j++ ) {
               tempOddRow[j]  = currentCube[j][oddMove[4]][oddMove[5]];
               tempEvenRow[j] = currentCube[j][evenMove[4]][evenMove[5]];
            }
            for ( int i = 1 ; i < 4 ; i++ ) {
              for ( int j = 0 ; j < 3 ; j++ ) {
                currentCube[j][oddMove[i-1]][oddMove[i]]   = currentCube[j][oddMove[i]][oddMove[i+1]];
                currentCube[j][evenMove[i-1]][evenMove[i]] = currentCube[j][evenMove[i]][evenMove[i+1]];
              }
            }
            for ( int j = 0 ; j < 3 ; j++ ) {
              currentCube[j][oddMove[3]][oddMove[4]]   = tempOddRow[j];
              currentCube[j][evenMove[3]][evenMove[4]] = tempEvenRow[j];
            }
        }
        else { // Move the part specified by 'partsToMove'

            tempOddRow[partsToMove-1]  = currentCube[partsToMove-1][oddMove[4]][oddMove[5]];
            tempEvenRow[partsToMove-1] = currentCube[partsToMove-1][evenMove[4]][evenMove[5]];

            for ( int i = 1 ; i < 4 ; i++ ) {
                currentCube[partsToMove-1][oddMove[i-1]][oddMove[i]]   = currentCube[partsToMove-1][oddMove[i]][oddMove[i+1]];
                currentCube[partsToMove-1][evenMove[i-1]][evenMove[i]] = currentCube[partsToMove-1][evenMove[i]][evenMove[i+1]];
            }
            currentCube[partsToMove-1][oddMove[3]][oddMove[4]]   = tempOddRow[partsToMove-1];
            currentCube[partsToMove-1][evenMove[3]][evenMove[4]] = tempEvenRow[partsToMove-1];

        }
        /* After Counter Clockwise Move
        aeb     ab      abc ->  abc     bc      bcf ->  cab     cb      cbf
        eb      b       bc  ->  ab      b       bf  ->  ab      b       bf
        ebf     bf      bcf ->  aeb     eb      ebf ->  abe     be      bfe

        ae      a       ac  ->  ac      c       cf  ->  ca      c       cf
        e       abcdef  c   ->  a       aaaaaa  f   ->  a       cbfdae  f
        ef      f       cf  ->  ae      e       ef  ->  ae      e       fe

        ade     ad      acd ->  acd     cd      cdf ->  cda     cd      cfd
        de      d       cd  ->  ad      d       df  ->  da      d       fd
        def     df      cdf ->  ade     de      def ->  dae     de      fde*/

        if(partsToMove == 0 || partsToMove == 1) {
          currentCube[0][0][0] = currentCube[0][0][0].substring(2) + currentCube[0][0][0].substring(0,2);
          currentCube[0][0][1] = currentCube[0][0][1].substring(1) + currentCube[0][0][1].substring(0,1);
          currentCube[0][0][2] = currentCube[0][0][2].substring(1,2) + currentCube[0][0][2].substring(0,1) + currentCube[0][0][2].substring(2);

          //currentCube[0][1][0] = currentCube[0][1][0].substring() + currentCube[0][1][0].substring();
          //currentCube[0][1][1] = currentCube[0][1][1].substring() + currentCube[0][1][1].substring();
          //currentCube[0][1][2] = currentCube[0][1][2].substring() + currentCube[0][1][2].substring();

          currentCube[0][2][0] = currentCube[0][2][0].substring(0,1) + currentCube[0][2][0].substring(2) + currentCube[0][2][0].substring(1,2);
          currentCube[0][2][1] = currentCube[0][2][1].substring(1) + currentCube[0][2][1].substring(0,1);
          currentCube[0][2][2] = currentCube[0][2][2].substring(1) + currentCube[0][2][2].substring(0,1);
        }
        if(partsToMove == 0 || partsToMove == 2) {
          currentCube[1][0][0] = currentCube[1][0][0].substring(1) + currentCube[1][0][0].substring(0,1);
          //currentCube[1][0][1] = currentCube[1][0][1].substring() + currentCube[1][0][1].substring();
          //currentCube[1][0][2] = currentCube[1][0][2].substring() + currentCube[1][0][2].substring();

          //currentCube[1][1][0] = currentCube[1][1][0].substring() + currentCube[1][1][0].substring();
          currentCube[1][1][1] = currentCube[1][0][1] + currentCube[0][1][1] + currentCube[1][1][2] +
                                 currentCube[2][1][1] + currentCube[1][1][0] + currentCube[1][2][1];
          //currentCube[1][1][2] = currentCube[1][1][2].substring() + currentCube[1][1][2].substring();

          //currentCube[1][2][0] = currentCube[1][2][0].substring() + currentCube[1][2][0].substring();
          //currentCube[1][2][1] = currentCube[1][2][1].substring() + currentCube[1][2][1].substring();
          currentCube[1][2][2] = currentCube[1][2][2].substring(1) + currentCube[1][2][2].substring(0,1);

        }
        if(partsToMove == 0 || partsToMove == 3) {

          currentCube[2][0][0] = currentCube[2][0][0].substring(1) + currentCube[2][0][0].substring(0,1);
          //currentCube[2][0][1] = currentCube[2][0][1].substring() + currentCube[2][0][1].substring();
          currentCube[2][0][2] = currentCube[2][0][2].substring(0,1) + currentCube[2][0][2].substring(2) + currentCube[2][0][2].substring(1,2);

          currentCube[2][1][0] = currentCube[2][1][0].substring(1) + currentCube[2][1][0].substring(0,1);
          //currentCube[2][1][1] = currentCube[2][1][1].substring() + currentCube[2][1][1].substring();
          currentCube[2][1][2] = currentCube[2][1][2].substring(1) + currentCube[2][1][2].substring(0,1);

          currentCube[2][2][0] = currentCube[2][2][0].substring(1,2) + currentCube[2][2][0].substring(0,1) + currentCube[2][2][0].substring(2);
          //currentCube[2][2][1] = currentCube[2][2][1].substring() + currentCube[2][2][1].substring();
          currentCube[2][2][2] = currentCube[2][2][2].substring(2) + currentCube[2][2][2].substring(0,2);
        }
      }
      else if ( move == 2 ) { // Clockwise
        if ( partsToMove == 0 ) { // Move Whole Cube
          for ( int j = 0 ; j < 3 ; j++ ) {
            tempOddRow[j]  = currentCube[j][oddMove[4]][oddMove[5]];
            tempEvenRow[j] = currentCube[j][evenMove[4]][evenMove[5]];
          }
          for ( int i = 4 ; i > 1 ; i-- ) {
            for ( int j = 0 ; j < 3 ; j++ ) {
              currentCube[j][oddMove[i]][oddMove[i+1]]   = currentCube[j][oddMove[i-1]][oddMove[i]];
              currentCube[j][evenMove[i]][evenMove[i+1]] = currentCube[j][evenMove[i-1]][evenMove[i]];
            }
          }
          for ( int j = 0 ; j < 3 ; j++ ) {
            currentCube[j][oddMove[1]][oddMove[2]]   = tempOddRow[j];
            currentCube[j][evenMove[1]][evenMove[2]] = tempEvenRow[j];
          }
        }
        else { // Move the part specified by 'partsToMove'

          tempOddRow[partsToMove-1]  = currentCube[partsToMove-1][oddMove[4]][oddMove[5]];
          tempEvenRow[partsToMove-1] = currentCube[partsToMove-1][evenMove[4]][evenMove[5]];

          for ( int i = 4 ; i > 1 ; i-- ) {
            currentCube[partsToMove-1][oddMove[i]][oddMove[i+1]]   = currentCube[partsToMove-1][oddMove[i-1]][oddMove[i]]; currentCube[partsToMove-1][evenMove[i]][evenMove[i+1]] = currentCube[partsToMove-1][evenMove[i-1]][evenMove[i]];
          }

          currentCube[partsToMove-1][oddMove[1]][oddMove[2]]   = tempOddRow[partsToMove-1];
          currentCube[partsToMove-1][evenMove[1]][evenMove[2]] = tempEvenRow[partsToMove-1];

        }

        /* After Clockwise Move

        aeb     ab      abc ->  ebf     eb      aeb   ->  efb     eb      eba
        eb      b       bc  ->  bf      b       ab    ->  fb      b       ba
        ebf     bf      bcf ->  bcf     bc      abc   ->  fbc     bc      bac

        ae      a       ac  ->  ef      e       ae    ->  ef      e       ea
        e       abcdef  c   ->  f       abcdef  a     ->  f       ebadfc  a
        ef      f       cf  ->  cf      c       ac    ->  fc      c       ac

        ade     ad      acd ->  def     de      ade   ->  edf     ed      ead
        de      d       cd  ->  df      d       ad    ->  df      d       ad
        def     df      cdf ->  cdf     cd      acd   ->  dfc     dc      adc */

        if(partsToMove == 0 || partsToMove == 1) {
          currentCube[0][0][0] = currentCube[0][0][0].substring(0,1) + currentCube[0][0][0].substring(2) + currentCube[0][0][0].substring(1,2);
          //currentCube[0][0][1] = currentCube[0][0][1].substring(1) + currentCube[0][0][1].substring(0,1);
          currentCube[0][0][2] = currentCube[0][0][2].substring(1) + currentCube[0][0][2].substring(0,1);

          currentCube[0][1][0] = currentCube[0][1][0].substring(1) + currentCube[0][1][0].substring(0,1);
          //currentCube[0][1][1] = currentCube[0][1][1].substring() + currentCube[0][1][1].substring();
          currentCube[0][1][2] = currentCube[0][1][2].substring(1) + currentCube[0][1][2].substring(0,1);

          currentCube[0][2][0] = currentCube[0][2][0].substring(2) + currentCube[0][2][0].substring(0,2);
          //currentCube[0][2][1] = currentCube[0][2][1].substring() + currentCube[0][2][1].substring();
          currentCube[0][2][2] = currentCube[0][2][2].substring(1,2) + currentCube[0][2][2].substring(0,1) + currentCube[0][2][2].substring(2);

        }
        if(partsToMove == 0 || partsToMove == 2) {
          //currentCube[1][0][0] = currentCube[1][0][0].substring() + currentCube[1][0][0].substring();
          //currentCube[1][0][1] = currentCube[1][0][1].substring() + currentCube[1][0][1].substring();
          currentCube[1][0][2] = currentCube[1][0][2].substring(1) + currentCube[1][0][2].substring(0,1);

          //currentCube[1][1][0] = currentCube[1][1][0].substring() + currentCube[1][1][0].substring();
          currentCube[1][1][1] = currentCube[1][0][1] + currentCube[0][1][1] + currentCube[1][1][2] +
                                 currentCube[2][1][1] + currentCube[1][1][0] + currentCube[1][2][1];
          //currentCube[1][1][2] = currentCube[1][1][2].substring() + currentCube[1][1][2].substring();

          currentCube[1][2][0] = currentCube[1][2][0].substring(1) + currentCube[1][2][0].substring(0,1);
          //currentCube[1][2][1] = currentCube[1][2][1].substring() + currentCube[1][2][1].substring();
          //currentCube[1][2][2] = currentCube[1][2][2].substring(1) + currentCube[1][2][2].substring(0,1);

        }
        if(partsToMove == 0 || partsToMove == 3) {

          currentCube[2][0][0] = currentCube[2][0][0].substring(1,2) + currentCube[2][0][0].substring(0,1) + currentCube[2][0][0].substring(2);
          currentCube[2][0][1] = currentCube[2][0][1].substring(1) + currentCube[2][0][1].substring(0,1);
          currentCube[2][0][2] = currentCube[2][0][2].substring(2) + currentCube[2][0][2].substring(0,2);

          //currentCube[2][1][0] = currentCube[2][1][0].substring() + currentCube[2][1][0].substring();
          //currentCube[2][1][1] = currentCube[2][1][1].substring() + currentCube[2][1][1].substring();
          //currentCube[2][1][2] = currentCube[2][1][2].substring() + currentCube[2][1][2].substring();

          currentCube[2][2][0] = currentCube[2][2][0].substring(1) + currentCube[2][2][0].substring(0,1);
          currentCube[2][2][1] = currentCube[2][2][1].substring(1) + currentCube[2][2][1].substring(0,1);
          currentCube[2][2][2] = currentCube[2][2][2].substring(0,1) + currentCube[2][2][2].substring(2) + currentCube[2][2][2].substring(1,2);
        }
      }
      else if ( move == 3 ) { // Inverse
        if ( partsToMove == 0 ) { // Move Whole Cube
          for ( int i = 0 ; i < 2 ; i++ ) {
            for ( int j = 0 ; j < 3 ; j++ ) {

              tempOddRow[j]  = currentCube[j][oddMove[i]][oddMove[i+1]];
              tempEvenRow[j] = currentCube[j][evenMove[i]][evenMove[i+1]];

              currentCube[j][oddMove[i]][oddMove[i+1]]   = currentCube[j][oddMove[i+2]][oddMove[i+3]];
              currentCube[j][evenMove[i]][evenMove[i+1]] = currentCube[j][evenMove[i+2]][evenMove[i+3]];

              currentCube[j][oddMove[i+2]][oddMove[i+3]]   = tempOddRow[j];
              currentCube[j][evenMove[i+2]][evenMove[i+3]] = tempEvenRow[j];

            }
          }
        }
        else { // Move the part specified by 'partsToMove'
          for ( int i = 0 ; i < 2 ; i++ ) {

              tempOddRow[partsToMove-1]  = currentCube[partsToMove-1][oddMove[i]][oddMove[i+1]];
              tempEvenRow[partsToMove-1] = currentCube[partsToMove-1][evenMove[i]][evenMove[i+1]];

              currentCube[partsToMove-1][oddMove[i]][oddMove[i+1]]   = currentCube[partsToMove-1][oddMove[i+2]][oddMove[i+3]];
              currentCube[partsToMove-1][evenMove[i]][evenMove[i+1]] = currentCube[partsToMove-1][evenMove[i+2]][evenMove[i+3]];

              currentCube[partsToMove-1][oddMove[i+2]][oddMove[i+3]]   = tempOddRow[partsToMove-1];
              currentCube[partsToMove-1][evenMove[i+2]][evenMove[i+3]] = tempEvenRow[partsToMove-1];

          }
        }

        /* After Inverse Move

        aeb     ab      abc ->  bcf     bf      ebf   ->  fcb     fb      fbe
        eb      b       bc  ->  bc      b       eb    ->  cb      b       be
        ebf     bf      bcf ->  abc     ab      aeb   ->  cba     ba      bea

        ae      a       ac  ->  cf      f       ef    ->  fc      f       fe
        e       abcdef  c   ->  c       abcdef  e     ->  c       fbedca  e
        ef      f       cf  ->  ac      a       ae    ->  ca      a       ea

        ade     ad      acd ->  cdf     df      def   ->  fdc     fd      fed
        de      d       cd  ->  cd      d       de    ->  dc      d       ed
        def     df      cdf ->  acd     ad      ade   ->  dca     da      eda */

        if(partsToMove == 0 || partsToMove == 1) {
          currentCube[0][0][0] = currentCube[0][0][0].substring(2) + currentCube[0][0][0].substring(1,2) + currentCube[0][0][0].substring(0,1);
          currentCube[0][0][1] = currentCube[0][0][1].substring(1) + currentCube[0][0][1].substring(0,1);
          currentCube[0][0][2] = currentCube[0][0][2].substring(2) + currentCube[0][0][2].substring(1,2) + currentCube[0][0][2].substring(0,1);

          currentCube[0][1][0] = currentCube[0][1][0].substring(1) + currentCube[0][1][0].substring(0,1);
          //currentCube[0][1][1] = currentCube[0][1][1].substring() + currentCube[0][1][1].substring();
          currentCube[0][1][2] = currentCube[0][1][2].substring(1) + currentCube[0][1][2].substring(0,1);

          currentCube[0][2][0] = currentCube[0][2][0].substring(2) + currentCube[0][2][0].substring(1,2) + currentCube[0][2][0].substring(0,1);
          currentCube[0][2][1] = currentCube[0][2][1].substring(1) + currentCube[0][2][1].substring(0,1);
          currentCube[0][2][2] = currentCube[0][2][2].substring(2) + currentCube[0][2][2].substring(1,2) + currentCube[0][2][2].substring(0,1);
        }
        if(partsToMove == 0 || partsToMove == 2) {
          currentCube[1][0][0] = currentCube[1][0][0].substring(1) + currentCube[1][0][0].substring(0,1);
          //currentCube[1][0][1] = currentCube[1][0][1].substring() + currentCube[1][0][1].substring();
          currentCube[1][0][2] = currentCube[1][0][2].substring(1) + currentCube[1][0][2].substring(0,1);

          //currentCube[1][1][0] = currentCube[1][1][0].substring() + currentCube[1][1][0].substring();
          currentCube[1][1][1] = currentCube[1][0][1] + currentCube[0][1][1] + currentCube[1][1][2] +
                                 currentCube[2][1][1] + currentCube[1][1][0] + currentCube[1][2][1];
          //currentCube[1][1][2] = currentCube[1][1][2].substring() + currentCube[1][1][2].substring();

          currentCube[1][2][0] = currentCube[1][2][0].substring(1) + currentCube[1][2][0].substring(0,1);
          //currentCube[1][2][1] = currentCube[1][2][1].substring() + currentCube[1][2][1].substring();
          currentCube[1][2][2] = currentCube[1][2][2].substring(1) + currentCube[1][2][2].substring(0,1);
        }
        if(partsToMove == 0 || partsToMove == 3) {
          currentCube[2][0][0] = currentCube[2][0][0].substring(2) + currentCube[2][0][0].substring(1,2) + currentCube[2][0][0].substring(0,1);
          currentCube[2][0][1] = currentCube[2][0][1].substring(1) + currentCube[2][0][1].substring(0,1);
          currentCube[2][0][2] = currentCube[2][0][2].substring(2) + currentCube[2][0][2].substring(1,2) + currentCube[2][0][2].substring(0,1);

          currentCube[2][1][0] = currentCube[2][1][0].substring(1) + currentCube[2][1][0].substring(0,1);
          //currentCube[2][1][1] = currentCube[2][1][1].substring() + currentCube[2][1][1].substring();
          currentCube[2][1][2] = currentCube[2][1][2].substring(1) + currentCube[2][1][2].substring(0,1);

          currentCube[2][2][0] = currentCube[2][2][0].substring(2) + currentCube[2][2][0].substring(1,2) + currentCube[2][2][0].substring(0,1);
          currentCube[2][2][1] = currentCube[2][2][1].substring(1) + currentCube[2][2][1].substring(0,1);
          currentCube[2][2][2] = currentCube[2][2][2].substring(2) + currentCube[2][2][2].substring(1,2) + currentCube[2][2][2].substring(0,1);
        }
      }
      else
        return false;
    }
    else
      return false;

    return true;
  }

  private static boolean makeMoveTranspose(int moveToMake) {
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
    level++;
    int lastMove = 0;
    int secondLastMove = 0;
    if(level > 1)
      lastMove = movesMade.get(movesMade.size()-1);
    if(level > 2)
      secondLastMove = movesMade.get(movesMade.size()-2);
    for ( int i = 0 ; i < allPossibleMoves.length ; i++ ) {
      // Trim the Moves
      if ( lastMove / 10 == allPossibleMoves[i] / 10 )
        continue;
      // if ( (secondLastMove % 10 == lastMove % 10) &&
      //      (lastMove % 10 == allPossibleMoves[i] % 10) &&
      //      (allPossibleMoves[i] % 10 == secondLastMove % 10) )
      //   if ( (secondLastMove / 10 + lastMove / 10 + allPossibleMoves[i] / 10 == 6) ||
      //        (secondLastMove / 10 + lastMove / 10 + allPossibleMoves[i] / 10 == 18))
      //     continue;

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
    level++;
    int lastMove = 0;
    int secondLastMove = 0;
    if(level > 1)
      lastMove = partialMovesMade.get(partialMovesMade.size()-1);
    if(level > 2)
      secondLastMove = partialMovesMade.get(partialMovesMade.size()-2);
    for ( int i = 0 ; i < allPossibleMoves.length ; i++ ) {
      if ( lastMove / 10 == allPossibleMoves[i] / 10 )
        continue;
      // if ( (secondLastMove % 10 == lastMove % 10) &&
      //      (lastMove % 10 == allPossibleMoves[i] % 10) &&
      //      (allPossibleMoves[i] % 10 == secondLastMove % 10) )
      //   if ( (secondLastMove / 10 + lastMove / 10 + allPossibleMoves[i] / 10 == 6) ||
      //        (secondLastMove / 10 + lastMove / 10 + allPossibleMoves[i] / 10 == 18))
      //     continue;
      makeMove(allPossibleMoves[i]);
      partialMovesMade.add(allPossibleMoves[i]);

      if ( isCubePartiallySolved(partialLevel) == true )
        return true;

      if ( level < numberofPartialMovesToMake[partialLevel])
        if( rubikPartialSolver() == true)
          return true;
      makeMove(allPossibleMovesReversal[i]); // Unmake the previous move!
      partialMovesMade.remove(partialMovesMade.size()-1);
    }
    level--;
    return false;
  }

  private static boolean rubikSolverUsingSpeedCubing() {
    // TODO

    boolean solved = false;
    int currentLevel = 0;

    //solveTop();

    // A. Solve Top Center Pieces
    do{
      level = 0;
      partialMovesMade.clear();
      partialLevel = currentLevel;
      intermediateLevel = "";
      solved = rubikPartialSolver();
      //System.out.println("Rubik's Cube Level " + currentLevel + " Solved : " + solved);
      if(solved == false)
        return solved;
      currentLevel++;
    }while(currentLevel < 4);

    // B. Solve Top Corner Pieces
    do{
      level = 0;
      partialMovesMade.clear();
      partialLevel = currentLevel;
      intermediateLevel = "TopCornerPieces";
      solved = rubikPartialSolver();
      //System.out.println("Rubik's Cube Level " + currentLevel + " Intermediate Solved : " + solved);
      if(solved) {
        level = 0;
        partialMovesMade.clear();
        intermediateLevel = "";
        solved = rubikPartialSolver();
        //System.out.println("Rubik's Cube Level " + currentLevel + " Final Solved : " + solved);
      }
      if(solved == false)
        return solved;
      currentLevel++;
    }while(currentLevel < 8);

    //solveMiddle();

    //solveBottom();

    return solved;
  }

  public static void main(String[] args) {

    int firstArg = 0;
    if (args.length > 0) {
        try {
            firstArg = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.err.println("Argument" + args[0] + " must be an integer.");
            System.exit(1);
        }
    }

    //boolean looper=true;
    do {
        // Copying Initial Position to Current Cube
        for ( int i=0;i<3;i++ )
          for ( int j=0;j<3;j++ )
            for ( int k=0;k<3;k++ )
              currentCube[i][j][k] = solvedCube[i][j][k];

        Instant start = Instant.now();
        for ( int i=0 ; i<5000 ; i++ ) {
          makeMove(firstArg);
        }
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);

        // Should be longer - 3 moves instead of one move === RubikOld.java
        Instant start2 = Instant.now();
        for ( int i=0 ; i<5000 ; i++ ) {
          makeMoveTranspose(firstArg);
        }
        Instant end2 = Instant.now();
        Duration timeElapsed2 = Duration.between(start2, end2);

        System.out.println(" Time taken: Move - "+ timeElapsed.toMillis() +" , "+ timeElapsed2.toMillis()+" milliseconds");

        // Scramble the Cube
        // scrambleCube(100);
        //
        // Instant start = Instant.now();
        // looper = rubikSolverUsingSpeedCubing();
        // System.out.print("Rubik's Cube Solved : " + looper);
        // Instant end = Instant.now();
        // Duration timeElapsed = Duration.between(start, end);
        // System.out.println(" Time taken: "+ timeElapsed.toMillis() +" milliseconds");

        // System.out.print("Rubik's Cube Solution : ");
        // for(int currentMove:partialMovesMade)
        //   System.out.print(currentMove + " ");
        // System.out.println("");

        //System.out.println("");
        //System.out.println("");

    //}while(looper);
    if(timeElapsed.toMillis() > timeElapsed2.toMillis())
      break;
  }while(true);
  }
}
