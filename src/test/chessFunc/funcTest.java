package chessFunc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;


class funcTest {
	public static void main(String[] args) {

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
}
