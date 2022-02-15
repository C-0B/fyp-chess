package log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.System.Logger;
import java.nio.channels.NonWritableChannelException;
import java.time.Instant;

import javax.sound.midi.VoiceStatus;

public class logger {

	public logger() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {}
	
	public boolean logMove(int colourInt, String peice, String startSqaure, String endSqaure, String endFEN) {
	    try {
	    	String colour = "";
	    	if(colourInt == 1) {colour="white";}else {colour="black";}
	    	
	    	
	        String s = now()+" MOVE | "+colour+" "+peice+" "+startSqaure+" "+endSqaure+" | "+endFEN;
	        
	        FileWriter fw = new FileWriter("log.log", true);
	        BufferedWriter bw = new BufferedWriter(fw);
	        bw.write(s);
	        bw.newLine();
	        bw.close();
	        return true;
	      } catch (IOException e) {
	        System.out.println("Error in logging");
	        e.printStackTrace();
	      }
	    return false;
	}
	
	public boolean logAction (String action) {
	    try {
	        String s = now()+" ACTION | "+action;
	        FileWriter fw = new FileWriter("log.log", true);
	        BufferedWriter bw = new BufferedWriter(fw);
	        bw.write(s);
	        bw.newLine();
	        bw.close();
	        return true;
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    return false;
	}
	

	
	
	private static String now() {
		return "[ "+Instant.now()+" ]";
	}
	
}