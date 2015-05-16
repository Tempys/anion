package ua.org.shaddy.anion;



public class MainTsLoader {
	public static void main(String[] args) throws InterruptedException {
		byte source = (byte) 0b11001100;
		int result = source;
		System.out.println(Integer.toBinaryString(result));
	}
}
