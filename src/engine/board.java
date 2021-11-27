package engine;

public class board{
    
    public static void main(String[] args) {
        System.out.println("helo engine");

        String startFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

        long bb_P = 0b0000000000000000000000000000000000000000000000001111111100000000L;
        long bb_p = 0b0000000011111111000000000000000000000000000000000000000000000000L;

        // initalise all bit boards
        long bb_wP, bb_wR, bb_wN, bb_wB, bb_wQ, bb_wK, bb_bp, bb_br, bb_bn, bb_bb, bb_bq, bb_bk = 0L;
        bb_P = bb_P << 14;

        printBB(bb_P);
        System.out.println();
        printBB(bb_p);
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
    public static long[][] toBitBaords(){
        long[][] x = new long[8][];
        return x;
    }
}


/*  i NEED TO USE BIT BAORDS
    12 bit baords, one for each class of peices
    pawns x2
    knights x2
    bishops x2
    rooks x2
    queens x2
    kings x2
*/