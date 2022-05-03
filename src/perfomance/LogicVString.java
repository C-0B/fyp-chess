package perfomance;

import java.util.Random;

import engineByte.byteGame;

public class LogicVString {

	static String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	static byte[] pieceVals = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ,11, 12};
	
	
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		

		long average = 0;
		int repititions = 1;
		for(int y = 0;y<repititions;y++) {
			startTime = System.nanoTime();		
			
//			for(int i = 0; i<10000000; i++) {
//				compare8x8String();
//			}
			
//			for(int i = 0; i<10000000; i++) {
//				compareByteArr();
//			}
			
//			for(int i = 0; i<10000000; i++) {
//				or64BitLongs();
//			}
			
//			for(int i = 0; i<10000000; i++) {
//				and64BitLongs();
//			}
			
//			for(int i = 0; i<10000000; i++) {
//				shift64bitLongs();
//			}			
			
			long endTime = System.nanoTime();
			long duration = endTime - startTime;
			System.out.println((duration/1000000)+" ms");
			
			average += duration;
		}
		average = average/repititions;
		average = average/1000000;
		System.out.println("average: "+average+" ms");
	}
	
	
// ======================= Byte =======================
	
	
	private static byte[][] getRandomByteArray(){
		byte[][] randomArr = new byte[8][8]; 
		Random random = new Random();
		for(int row = 0; row <8; row++) {
			random.nextBytes(randomArr[row]);
		}
		return randomArr;
	}
	
	private static boolean compareByteArr() {
		boolean result = true;
		
		byte[][] arr1 = getRandomByteArray();
		byte[][] arr2 = getRandomByteArray();
		for(int row = 0; row <8; row++) {
			for(int column = 0; column <8; column++) {
				if(arr1[row][column] == arr2[row][column]) {
					result = !result;
				}
			}
		}
		return result;
	}
	
	
// ======================= Long =======================
	
	
	private static long getRandom64Bit() {
		Random random = new Random();
		return random.nextLong();
	}
	
	private static long shift64bitLongs() {
		long result = 0;
		long l1 = getRandom64Bit();
		result = l1 << 1;
		return result;
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
		if(l1 < l2) {
			return result;
		}
		result = l1 & l2;
		return result;
	}
	
	
// ======================= String =======================
	
	
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
	
}
