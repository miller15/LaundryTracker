package laundry_tracker;

import java.util.Arrays;
import java.util.UUID;

public class debug {
	public static void print(String printStr) {
		System.out.println(printStr);
	}
		
	public static void main(String[] args) {
/*		String password = "myPassword";
		print(password);
		byte[] passwordByte = password.getBytes();
		String passwordString = passwordByte
		print(passwordByte.toString());
*/		

		//byte[] salt = Password.getNextSalt();
		char[] pword = "tim".toCharArray();
		//byte[] hash = Password.hash(pword, salt);
		System.out.println("PWORD two lines: " + pword.toString());
		char[] tim = "tim".toCharArray();
		System.out.println("TIM one line: " + "tim".toCharArray().toString());
		System.out.println("tim two lines: " + tim.toString());
//		System.out.println("Salt: " + salt);
//		System.out.println("pword: " + pword);
//		System.out.println("hash: " + hash);
		//boolean same = Password.isExpectedPassword("tim".toCharArray(), salt, hash);
		//System.out.println(same);
	}
}
