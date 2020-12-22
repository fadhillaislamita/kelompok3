package kelompok3;

import java.util.Date;
import java.util.Scanner;
import java.util.InputMismatchException;

public class login {
	
	static Date date = new Date();
	static Scanner input = new Scanner(System.in);
	
	public static int landingPage() {
		
		Integer pilih = 0;
		
		System.out.println("==========================================");
		System.out.println("       ||    Kasir Kelompok 3    ||");
		System.out.println("==========================================");
		System.out.println(date+"\n");
		
		
		try {
			System.out.println("--------------------------------------");
			System.out.println("               DAFTAR MENU            ");
			System.out.println("--------------------------------------");
			System.out.println("1. Login");
			System.out.println("2. Daftar Akun");
			System.out.print("Silakan Dipilih : ");
			pilih = input.nextInt();
		} 
		
		catch (InputMismatchException e) {
			System.out.println("Pilihan yang anda masukkan salah. Silakan coba lagi");
		}
		return pilih;
	}
}
