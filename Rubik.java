public class Rubik{

  private static String[][][] solvedCube = new String[][][]{{{"aeb",   "ab",    "abc"},  //1 - 0 0
                                                             {"eb",    "b",     "bc"},   //2 - 0 1
                                                             {"ebf",   "bf",    "bcf"}}, //3 - 0 2

                                                            {{"ae",    "a",     "ac"},   //8 - 1 0
                                                             {"e",     "abcdef","c"},    //Axis for Vertical move - 1 1
                                                             {"ef",    "f",     "cf"}},  //4 - 1 2

                                                            {{"ade",   "ad",    "acd"},  //7 - 2 0
                                                             {"de",    "d",     "cd"},   //6 - 2 1
                                                             {"def",   "df",    "cdf"}}};//5 - 2 2

  private static String[][][] currentCube = new String[3][3][3];

  private static boolean makeMove(int moveToMake){
    /* Moves -
          a: Single Part
                vertical:   1 2 3 - u(up) d(down) i(invert)
                horizontal: 5 6 7 - r(right) l(left) i(invert)
          b: Entire Cube
                vertical:   4 - u d i
                horizontal: 8 - r l i
    */

    boolean verticalOrHorizontal=false; // false -> Vertical ; true -> Horizotal
    int partsToMove=0; // 0 -> whole cobe ; 1,2,3 -> one part (Top to Down, Left to Right order)
    int move; // Actual Move to make -> 1,2 or 3 for (u,d or i) or (r,l or i)
    int oddMove[]  = new int[]{0,0,2,2,0,0};
    int evenMove[] = new int[]{0,1,2,1,0,1};
    String tempOddRow[] = new String[3];
    String tempEvenRow[] = new String[3];

    System.out.println(moveToMake + " " + moveToMake / 10 + " " + moveToMake % 10);

    if ( moveToMake / 10 > 4 ) // First Part of Move
      verticalOrHorizontal = true;
    partsToMove = (moveToMake / 10) % 4; // Cube or Row
    move = moveToMake % 10;// Second Part of Move

    // TODO Transpose the cube according to the move
    if ( verticalOrHorizontal == false ) { // Vertical
      if ( move == 1 ) { // Up
        System.out.println("Up: verticalOrHorizontal: "+verticalOrHorizontal + " " +
                           "partsToMove: "+partsToMove + " " +
                           "move: "+move);
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
        
        /* Position of Current Cube - After Move 41

        ebf     bf      bcf   ->  bef     bf      bfc
        ef      f       cf    ->  ef      f       fc
        def     df      cdf   ->  efd     fd      fcd

        eb      b       bc    ->  be      b       bc
        e       abcdef  c     ->  e       bfcaed  c
        de      d       cd    ->  ed      d       cd

        aeb     ab      abc   ->  bae     ba      bca
        ae      a       ac    ->  ae      a       ca
        ade     ad      acd   ->  aed     ad      cad */

        currentCube[0][0][0] = currentCube[0][0][0].substring(1,2) + currentCube[0][0][0].substring(0,1) + currentCube[0][0][0].substring(2);
        //currentCube[0][0][1] = currentCube[0][0][1].substring() + currentCube[0][0][1].substring();
        currentCube[0][0][2] = currentCube[0][0][2].substring(0,1) + currentCube[0][0][2].substring(2) + currentCube[0][0][2].substring(1,2);

        //currentCube[0][1][0] = currentCube[0][1][0].substring() + currentCube[0][1][0].substring();
        //currentCube[0][1][1] = currentCube[0][1][1].substring() + currentCube[0][1][1].substring();
        currentCube[0][1][2] = currentCube[0][1][2].substring(1) + currentCube[0][1][2].substring(0,1);

        currentCube[0][2][0] = currentCube[0][2][0].substring(1) + currentCube[0][2][0].substring(0,1);
        currentCube[0][2][1] = currentCube[0][2][1].substring(1) + currentCube[0][2][1].substring(0,1);
        currentCube[0][2][2] = currentCube[0][2][2].substring(2) + currentCube[0][2][2].substring(0,2);

        currentCube[1][0][0] = currentCube[1][0][0].substring(1) + currentCube[1][0][0].substring(0,1);
        //currentCube[1][0][1] = currentCube[1][0][1].substring() + currentCube[1][0][1].substring();
        //currentCube[1][0][2] = currentCube[1][0][2].substring() + currentCube[1][0][2].substring();

        //currentCube[1][1][0] = currentCube[1][1][0].substring() + currentCube[1][1][0].substring();
        currentCube[1][1][1] = currentCube[1][1][1].substring(1,2) + currentCube[1][1][1].substring(5) + currentCube[1][1][1].substring(2,3) +
                               currentCube[1][1][1].substring(0,1) + currentCube[1][1][1].substring(4,5) + currentCube[1][1][1].substring(3,4);
        //currentCube[1][1][2] = currentCube[1][1][2].substring() + currentCube[1][1][2].substring();

        currentCube[1][2][0] = currentCube[1][2][0].substring(1) + currentCube[1][2][0].substring(0,1);
        //currentCube[1][2][1] = currentCube[1][2][1].substring() + currentCube[1][2][1].substring();
        //currentCube[1][2][2] = currentCube[1][2][2].substring() + currentCube[1][2][2].substring();

        currentCube[2][0][0] = currentCube[2][0][0].substring(2) + currentCube[2][0][0].substring(0,2);
        currentCube[2][0][1] = currentCube[2][0][1].substring(1) + currentCube[2][0][1].substring(0,1);
        currentCube[2][0][2] = currentCube[2][0][2].substring(1) + currentCube[2][0][2].substring(0,1);

        //currentCube[2][1][0] = currentCube[2][1][0].substring() + currentCube[2][1][0].substring();
        //currentCube[2][1][1] = currentCube[2][1][1].substring() + currentCube[2][1][1].substring();
        currentCube[2][1][2] = currentCube[2][1][2].substring(1) + currentCube[2][1][2].substring(0,1);

        currentCube[2][2][0] = currentCube[2][2][0].substring(0,1) + currentCube[2][2][0].substring(2) + currentCube[2][2][0].substring(1,2);
        //currentCube[2][2][1] = currentCube[2][2][1].substring() + currentCube[2][2][1].substring();
        currentCube[2][2][2] = currentCube[2][2][2].substring(1,2) + currentCube[2][2][2].substring(0,1) + currentCube[2][2][2].substring(2);
      }
      else if ( move == 2 ) { // Down
        System.out.println("Down: verticalOrHorizontal: "+verticalOrHorizontal + " " +
                           "partsToMove: "+partsToMove + " " +
                           "move: "+move);
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

        /* Position of Current Cube - After Move 42

        ade     ad      acd   ->  dea     da      dac
        ae      a       ac    ->  ea      a       ac
        aeb     ab      abc   ->  eab     ab      acb

        de      d       cd    ->  de      d       dc
        e       abcdef  c     ->  e       dacfeb  c
        eb      b       bc    ->  eb      b       cb

        def     df      cdf   ->  dfe     df      dcf
        ef      f       cf    ->  fe      f       cf
        ebf     bf      bcf   ->  feb     fb      cfb */

        currentCube[0][0][0] = currentCube[0][0][0].substring(1) + currentCube[0][0][0].substring(0,1);
        currentCube[0][0][1] = currentCube[0][0][1].substring(1) + currentCube[0][0][1].substring(0,1);
        currentCube[0][0][2] = currentCube[0][0][2].substring(2) + currentCube[0][0][2].substring(0,2);

        currentCube[0][1][0] = currentCube[0][1][0].substring(1) + currentCube[0][1][0].substring(0,1);
        //currentCube[0][1][1] = currentCube[0][1][1].substring() + currentCube[0][1][1].substring();
        //currentCube[0][1][2] = currentCube[0][1][2].substring() + currentCube[0][1][2].substring();

        currentCube[0][2][0] = currentCube[0][2][0].substring(1,2) + currentCube[0][2][0].substring(0,1) + currentCube[0][2][0].substring(2);
        //currentCube[0][2][1] = currentCube[0][2][1].substring() + currentCube[0][2][1].substring();
        currentCube[0][2][2] = currentCube[0][2][2].substring(0,1) + currentCube[0][2][2].substring(2) + currentCube[0][2][2].substring(1,2);

        //currentCube[1][0][0] = currentCube[1][0][0].substring() + currentCube[1][0][0].substring();
        //currentCube[1][0][1] = currentCube[1][0][1].substring() + currentCube[1][0][1].substring();
        currentCube[1][0][2] = currentCube[1][0][2].substring(1) + currentCube[1][0][2].substring(0,1);

        //currentCube[1][1][0] = currentCube[1][1][0].substring() + currentCube[1][1][0].substring();
        currentCube[1][1][1] = currentCube[1][1][1].substring(3,4) + currentCube[1][1][1].substring(0,1) + currentCube[1][1][1].substring(2,3) +
                               currentCube[1][1][1].substring(5) + currentCube[1][1][1].substring(4,5) + currentCube[1][1][1].substring(1,2);
        //currentCube[1][1][2] = currentCube[1][1][2].substring() + currentCube[1][1][2].substring();

        //currentCube[1][2][0] = currentCube[1][2][0].substring() + currentCube[1][2][0].substring();
        //currentCube[1][2][1] = currentCube[1][2][1].substring() + currentCube[1][2][1].substring();
        currentCube[1][2][2] = currentCube[1][2][2].substring(1) + currentCube[1][2][2].substring(0,1);

        currentCube[2][0][0] = currentCube[2][0][0].substring(0,1) + currentCube[2][0][0].substring(2) + currentCube[2][0][0].substring(1,2);
        //currentCube[2][0][1] = currentCube[2][0][1].substring() + currentCube[2][0][1].substring();
        currentCube[2][0][2] = currentCube[2][0][2].substring(1,2) + currentCube[2][0][2].substring(0,1) + currentCube[2][0][2].substring(2);

        currentCube[2][1][0] = currentCube[2][1][0].substring(1) + currentCube[2][1][0].substring(0,1);
        //currentCube[2][1][1] = currentCube[2][1][1].substring() + currentCube[2][1][1].substring();
        //currentCube[2][1][2] = currentCube[2][1][2].substring() + currentCube[2][1][2].substring();

        currentCube[2][2][0] = currentCube[2][2][0].substring(2) + currentCube[2][2][0].substring(0,2);
        currentCube[2][2][1] = currentCube[2][2][1].substring(1) + currentCube[2][2][1].substring(0,1);
        currentCube[2][2][2] = currentCube[2][2][2].substring(1) + currentCube[2][2][2].substring(0,1);
      }
      else if ( move == 3 ) { // Inverse
        System.out.println("Inv Ver: verticalOrHorizontal: "+verticalOrHorizontal + " " +
                           "partsToMove: "+partsToMove + " " +
                           "move: "+move);
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

        currentCube[0][0][0] = currentCube[0][0][0].substring(2) + currentCube[0][0][0].substring(1,2) + currentCube[0][0][0].substring(0,1);
        currentCube[0][0][1] = currentCube[0][0][1].substring(1) + currentCube[0][0][1].substring(0,1);
        currentCube[0][0][2] = currentCube[0][0][2].substring(2) + currentCube[0][0][2].substring(1,2) + currentCube[0][0][2].substring(0,1);

        currentCube[0][1][0] = currentCube[0][1][0].substring(1) + currentCube[0][1][0].substring(0,1);
        //currentCube[0][1][1] = currentCube[0][1][1].substring() + currentCube[0][1][1].substring();
        currentCube[0][1][2] = currentCube[0][1][2].substring(1) + currentCube[0][1][2].substring(0,1);

        currentCube[0][2][0] = currentCube[0][2][0].substring(2) + currentCube[0][2][0].substring(1,2) + currentCube[0][2][0].substring(0,1);
        currentCube[0][2][1] = currentCube[0][2][1].substring(1) + currentCube[0][2][1].substring(0,1);
        currentCube[0][2][2] = currentCube[0][2][2].substring(2) + currentCube[0][2][2].substring(1,2) + currentCube[0][2][2].substring(0,1);

        currentCube[1][0][0] = currentCube[1][0][0].substring(1) + currentCube[1][0][0].substring(0,1);
        //currentCube[1][0][1] = currentCube[1][0][1].substring() + currentCube[1][0][1].substring();
        currentCube[1][0][2] = currentCube[1][0][2].substring(1) + currentCube[1][0][2].substring(0,1);

        //currentCube[1][1][0] = currentCube[1][1][0].substring() + currentCube[1][1][0].substring();
        currentCube[1][1][1] = currentCube[1][1][1].substring(5) + currentCube[1][1][1].substring(3,4) + currentCube[1][1][1].substring(2,3) +
                              currentCube[1][1][1].substring(1,2) + currentCube[1][1][1].substring(4,5) + currentCube[1][1][1].substring(0,1);
        //currentCube[1][1][2] = currentCube[1][1][2].substring() + currentCube[1][1][2].substring();

        currentCube[1][2][0] = currentCube[1][2][0].substring(1) + currentCube[1][2][0].substring(0,1);
        //currentCube[1][2][1] = currentCube[1][2][1].substring() + currentCube[1][2][1].substring();
        currentCube[1][2][2] = currentCube[1][2][2].substring(1) + currentCube[1][2][2].substring(0,1);

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
    else { // Horizontal
      if ( move == 1 ) { // Right
        System.out.println("Right: verticalOrHorizontal: "+verticalOrHorizontal + " " +
                           "partsToMove: "+partsToMove + " " +
                           "move: "+move);

      }
      else if ( move == 2 ) { // Left
        System.out.println("Left: verticalOrHorizontal: "+verticalOrHorizontal + " " +
                           "partsToMove: "+partsToMove + " " +
                           "move: "+move);

      }
      else if ( move == 3 ) { // Inverse
        System.out.println("Inv Hor: verticalOrHorizontal: "+verticalOrHorizontal + " " +
                           "partsToMove: "+partsToMove + " " +
                           "move: "+move);

      }
    }
    // End TODO

    return true;
  }

  public static void main(String[] argv){

    // Copying Initial Position to Current Cube
    for ( int i=0;i<3;i++ )
      for ( int j=0;j<3;j++ )
        for ( int k=0;k<3;k++ )
          currentCube[i][j][k] = solvedCube[i][j][k];

    System.out.println("\n\nAfter Inverse Move : " + makeMove(43));

    // Position of Current Cube - After Inverse Move
    /*
    After Inverse Move : true
        fed     fd      fdc
        ed      d       dc
        eda     da      dca
        fe      f       fc
        e       fdcbea  c
        ea      a       ca
        fbe     fb      fcb
        be      b       cb
        bea     ba      cba
    */
    for ( int i=0;i<3;i++ ){
      for ( int j=0;j<3;j++ ){
        for ( int k=0;k<3;k++ )
          System.out.print(currentCube[i][j][k] + "\t");
        System.out.println("");
      }
    }
  }
}
