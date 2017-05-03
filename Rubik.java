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
    System.out.println(moveToMake + " " + moveToMake / 10 + " " + moveToMake % 10);
    boolean verticalOrHorizontal=false; // false -> Vertical ; true -> Horizotal
    int partsToMove=0; // 0 -> whole cobe ; 1,2,3 -> one part (Top to Down, Left to Right order)

    if(moveToMake / 10 > 4) // First Part of Move
      verticalOrHorizontal = true;
    partsToMove = (moveToMake / 10) % 4; // Second Part of Move

    // TODO Transpose the cube according to the move

    // End TODO

    return true;
  }

  public static void main(String[] argv){

    // Copying Initial Position to Current Cube
    for(int i=0;i<3;i++)
      for(int j=0;j<3;j++)
        for(int k=0;k<3;k++)
          currentCube[i][j][k] = solvedCube[i][j][k];

    System.out.println(makeMove(21));
  }
}
