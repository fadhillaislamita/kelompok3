package kelompok3;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Program {

	static Scanner input = new Scanner(System.in);
	static user User = new user();
	static Date date = new Date();
	int pilih; 
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		switch (login.landingPage()) {
		case 1:
			User.login();
			break;
		case 2:
			User.TambahAkun();
			break;
		default:
			System.out.println("Masukkan Pilihan Dengan Benar\n");
			login.landingPage();
		}
	}
}