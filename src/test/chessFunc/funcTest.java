package chessFunc;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.PasswordAuthentication;

import org.junit.jupiter.api.Test;


class funcTest {
	public static void main(String[] args) {
		int[] plyrKingCoords = {7, 4};//0 indexed
		System.out.println(func.sqIntToStr(func.coordTosqInt(plyrKingCoords)));

	}
	
	
	@Test
	void squareIntToStringCoord(){
		try {
			for(int i = 0; i<64; i++) {
			System.out.println(i+" "+func.sqIntToStr(i));
			}
		}catch (Exception e) {fail();}
	}
	//Test the 4 corners of the board
	@Test
	void squareIntToStringCoorda1() {assertEquals("a1", func.sqIntToStr(56));}
	@Test
	void squareIntToStringCoorda8() {assertEquals("a8", func.sqIntToStr(0));}
	@Test
	void squareIntToStringCoordh1() {assertEquals("h1", func.sqIntToStr(63));}
	@Test
	void squareIntToStringCoordh8() {assertEquals("h8", func.sqIntToStr(7));}
	
	@Test
	void squareIntToStringCoordErroneous() {assertFalse( func.sqIntToStr(70).length() == 1 );}
	@Test
	void squareIntToStringCoordErroneous2() {assertFalse( func.sqIntToStr(700).length() == 1 );}
	
	
	
	@Test
	void sqStrToIntTestRange() {
		String[] coords = {"a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8",
						   "b1", "b2", "b3", "b4", "b5", "b6", "b7", "b8",
						   "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8",
						   "d1", "d2", "d3", "d4", "d5", "d6", "d7", "d8",
						   "e1", "e2", "e3", "e4", "e5", "e6", "e7", "e8",
						   "f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8",
						   "g1", "g2", "g3", "g4", "g5", "g6", "g7", "g8",
						   "h1", "h2", "h3", "h4", "h5", "h6", "h7", "h8"};
		try {
			for(String coord : coords) {
				if(0 > func.sqStrToInt(coord) || 64 <= func.sqStrToInt(coord)) {
					//if less than 0 or greater than or equal 64
					fail();
				}
			}
		}catch (Exception e) {fail();}
		//pass
	}
	@Test
	void sqStrToIntTesta1() {assertEquals(56, func.sqStrToInt("a1"));}
	@Test
	void sqStrToIntTesta8() {assertEquals(0, func.sqStrToInt("a8"));}
	@Test
	void sqStrToIntTesth1() {assertEquals(63, func.sqStrToInt("h1"));}
	@Test
	void sqStrToIntTesth8() {assertEquals(7, func.sqStrToInt("h8"));}

}
