package main;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import engine_1.game;

class gameTest {
	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader(new FileReader("resources/listOfFens.txt"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	game GAME = new game(line);
		    	System.out.println(" real  -> "+line);
		    	GAME.genFENfromBoard();
		    	System.out.println("genFEN -> "+GAME.getFEN());
		    }
		}catch(IOException E) {
			System.out.println("Error when testing FEN reading: exception -> "+E);
		}
	}

	@Test
	void readSetOfFENs(){
		try (BufferedReader br = new BufferedReader(new FileReader("resources/listOfFens.txt"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	game GAME = new game(line);
		    	System.out.println(line);
		    }
		}catch(IOException E) {
			System.out.println("Error when testing FEN reading: exception -> "+E);
			fail();
		}
		//fail("Not yet implemented");
	}
	
	@Test
	void generateSetOfFENsFromBoard(){
		try (BufferedReader br = new BufferedReader(new FileReader("resources/listOfFens.txt"))) {
		    String line;
		    int i=0;
		    while ((line = br.readLine()) != null) {
		    	game GAME = new game(line);
		    	System.out.println(" real  -> "+line);
		    	GAME.genFENfromBoard();
		    	System.out.println("genFEN -> "+GAME.getFEN());
		    }
		}catch(IOException E) {
			System.out.println("Error when testing FEN reading: exception -> "+E);
			fail();
		}
		//fail("Not yet implemented");
	}


}
