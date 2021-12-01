package engine_bb;

public class board{
    /* 
    i NEED TO USE BIT BAORDS
    12 bit baords, one for each class of peices
    pawns x2
    knights x2
    bishops x2
    rooks x2
    queens x2
    kings x2

    functions:
        fen/borad -> 2d array
        2darray -> bitbords
    */
    public static void main(String[] args) {
        System.out.println("helo engine");

        String startFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

        // initalise all bit boards
        // long bb_wP, bb_wR, bb_wN, bb_wB, bb_wQ, bb_wK, bb_bp, bb_br, bb_bn, bb_bb, bb_bq, bb_bk = 0L;
        // long bb_P = 0b0000000000000000000000000000000000000000000000001111111100000000L;
        // long bb_p = 0b0000000011111111000000000000000000000000000000000000000000000000L;
        // bb_P = bb_P << 14;
        // printBB(bb_P);
        // System.out.println();
        // printBB(bb_p);

        FENto2DARRAY(startFEN);
    }

    public static void printBB(long BB){
        int upToEight = 0;

        // Print leading zeros
        for(int i = 0; i < Long.numberOfLeadingZeros(BB); i++) {
            System.out.print('0');
            if(upToEight++ == 7){
                System.out.println("");
                upToEight = 0;
            }
        }
        String strBB = Long.toBinaryString(BB);

        // Print long value = leading zeros are removed
        for (char digit: strBB.toCharArray()) {
            System.out.print(digit);
            if(upToEight++ == 7){
                System.out.println("");
                upToEight = 0;
            }
        }
    }

            
    private static String[][] FENto2DARRAY(String FEN){
        String[][] board = new String[8][8];
		String[] fenArray = FEN.split("(?!^)");
        int index = 0;

        int rank = 0, file = 0;
        for(String s : fenArray){
            System.err.println(s);
        }

        for(int squaresCovered = 0; squaresCovered < 64; squaresCovered++){
            String s = fenArray[index];
            System.out.println(squaresCovered/8 +" "+squaresCovered%8);
            if(isNumber(s)){
                for(int i = 0; i<Integer.parseInt(s); i++){
                    board[squaresCovered/8][squaresCovered%8] = " ";
                }
            }else if(!s.equals("/")){
                board[squaresCovered/8][squaresCovered%8] = s;
            }
        }
        return board;
    }

    private static boolean isNumber(String s){
        if (s == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}