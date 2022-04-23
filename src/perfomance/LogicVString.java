package perfomance;

import java.util.Random;

public class LogicVString {

	static String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		
		for(int i = 0; i<1000000; i++) {
			compare8x8String();
		}
		
//		for(int i = 0; i<10000000; i++) {
//			and64BitLongs();
//		}
//		for(int i = 0; i<10000000; i++) {
//			or64BitLongs();
//		}
		
		long endTime = System.nanoTime();
		long duration = endTime - startTime;
		System.out.println((duration/1000000)+" ms");
	}
	
	
	private static long or64BitLongs() {
		long result = 0;
		long l1 = getRandom64Bit();
		long l2 = getRandom64Bit();
		result = l1 | l2;
		return result;
	}
	private static long and64BitLongs() {
		long result = 0;
		long l1 = getRandom64Bit();
		long l2 = getRandom64Bit();
		result = l1 & l2;
		return result;
	}
	
	private static long getRandom64Bit() {
		Random random = new Random();
		return random.nextLong();
	}
	
	private static boolean compare8x8String() {
		String[][] arr1 = getRandomStringArr();
		String[][] arr2 = getRandomStringArr();
		boolean result = true;
		for(int row = 0; row <8; row++) {
			for(int column = 0; column <8; column++) {
				if(arr1[row][column].equals(arr2[row][column])) {
					result = !result;
				}
			}
		}
		return result;
	}
	
	private static String[][] getRandomStringArr(){
		String[][] randomArr = new String[8][8]; 
		Random random = new Random();
		for(int row = 0; row <8; row++) {
			for(int column = 0; column <8; column++) {
				int randomIndex = random.nextInt(ALPHABET.length()-1);
				randomArr[row][column] = ALPHABET.substring(randomIndex, randomIndex+1);
			}
		}
		return randomArr;
	}
}
