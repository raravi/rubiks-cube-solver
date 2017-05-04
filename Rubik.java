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
    partsToMove = ( moveToMake / 10 ) % 4; // Cube or Row
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

        aeb     ab      abc ->  ebf     bf      bcf   ->  bef     bf      bfc
        eb      b       bc  ->  ef      f       cf    ->  ef      f       fc
        ebf     bf      bcf ->  def     df      cdf   ->  efd     fd      fcd

        ae      a       ac  ->  eb      b       bc    ->  be      b       bc
        e       abcdef  c   ->  e       abcdef  c     ->  e       bfcaed  c
        ef      f       cf  ->  de      d       cd    ->  ed      d       cd

        ade     ad      acd ->  aeb     ab      abc   ->  bae     ba      bca
        de      d       cd  ->  ae      a       ac    ->  ae      a       ca
        def     df      cdf ->  ade     ad      acd   ->  aed     ad      cad */

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

        aeb     ab      abc ->  ade     ad      acd   ->  dea     da      dac
        eb      b       bc  ->  ae      a       ac    ->  ea      a       ac
        ebf     bf      bcf ->  aeb     ab      abc   ->  eab     ab      acb

        ae      a       ac  ->  de      d       cd    ->  de      d       dc
        e       abcdef  c   ->  e       abcdef  c     ->  e       dacfeb  c
        ef      f       cf  ->  eb      b       bc    ->  eb      b       cb

        ade     ad      acd ->  def     df      cdf   ->  dfe     df      dcf
        de      d       cd  ->  ef      f       cf    ->  fe      f       cf
        def     df      cdf ->  ebf     bf      bcf   ->  feb     fb      cfb */

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
        currentCube[1][1][1] = currentCube[1][1][1].substring(0,1) + currentCube[1][1][1].substring(4,5) + currentCube[1][1][1].substring(1,2) +
                               currentCube[1][1][1].substring(2,3) + currentCube[1][1][1].substring(3,4) + currentCube[1][1][1].substring(5);
      }
      else if ( move == 2 ) { // Left
        System.out.println("Left: verticalOrHorizontal: "+verticalOrHorizontal + " " +
                           "partsToMove: "+partsToMove + " " +
                           "move: "+move);
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
        currentCube[1][1][1] = currentCube[1][1][1].substring(0,1) + currentCube[1][1][1].substring(2,3) + currentCube[1][1][1].substring(3,4) +
                               currentCube[1][1][1].substring(4,5) + currentCube[1][1][1].substring(1,2) + currentCube[1][1][1].substring(5);
      }
      else if ( move == 3 ) { // Inverse
        System.out.println("Inv Hor: verticalOrHorizontal: "+verticalOrHorizontal + " " +
                           "partsToMove: "+partsToMove + " " +
                           "move: "+move);
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

        currentCube[1][1][1] = currentCube[1][1][1].substring(0,1) + currentCube[1][1][1].substring(3,4) + currentCube[1][1][1].substring(4,5) +
                               currentCube[1][1][1].substring(1,2) + currentCube[1][1][1].substring(2,3) + currentCube[1][1][1].substring(5);

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

    System.out.println("\n\nAfter Right Move : " + makeMove(83));

    for ( int i=0;i<3;i++ ){
      for ( int j=0;j<3;j++ ){
        for ( int k=0;k<3;k++ )
          System.out.print(currentCube[i][j][k] + "\t");
        System.out.println("");
      }
    }
  }
}
