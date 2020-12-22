package kelompok3;

import java.sql.*;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import java.sql.ResultSet;
import java.util.Random;

public class user extends koneksi implements akun{
	
	static Connection conn;
	static Statement stmt;
	static ResultSet result;
	
	Scanner input = new Scanner(System.in);
	Date date = new Date();
	
	String usr;
	String pass;

	String username;
	String password;
	String email;
	String str; //last login
	String query;
	
	//login
	public void login() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		
		int login  = 0;
		do {
			System.out.println("\n=====================================");
			System.out.println("               L O G I N              ");
	    	System.out.println("=====================================");
	    	
	    	System.out.print("Username : ");
			this.username = input.next();
			
			System.out.print("Password : ");
			this.password = input.next();

			this.str = String.format("%tF", date);

			this.query = "SELECT*FROM user WHERE username='"+username+"'"
	    				+ " AND password='"+password+"'";
			
			stmt = conn.createStatement(); 
			ResultSet result = stmt.executeQuery(query);
			
			if (result.next()) {
				String sql = "UPDATE user SET login_terakhir='"+str+"' WHERE username='"+username+"'";
				stmt.executeUpdate(sql);
				System.out.println("Login Berhasil");
				usr=username;
				pass=password;
				user_pilih();
			} else {
				System.out.println("Username dan Password salah");
				login++;
				if (login==3) {
					reset();
				}
			}
		} while (login>=0 && login<=2);
    	
	}
    	
	public void reset () throws Exception {
		String resetpass = "abcdefghijklmnopqrstuvwxyz1234567890";
		String randompass = "";
		int length = 5;
		Random random = new Random();
		char [] pass = new char [length];
		
		for (int a=0 ; a<length ; a++  ) {
			pass[a] = resetpass.charAt(random.nextInt(resetpass.length()));
		}
		
		for (int a=0 ; a<pass.length ; a++) {
			randompass += pass[a];
		}
		System.out.println("--------------------------------------");
		System.out.println("Password Baru Anda : "+ randompass);
		System.out.println("--------------------------------------");
		System.out.println("SILAHAKN LOGIN KEMBALI DENGAN PASSWORD BARU");
		String sql = "UPDATE user SET password='"+randompass+"' WHERE username='"+username+"'";
		stmt.executeUpdate(sql);
		login();
	}
		

	//Daftar Akun
	public void TambahAkun() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

		System.out.println("\n\n>>>>>>>-SIGN UP-<<<<<<<");

		System.out.print("Masukkan Username\t: ");
		this.username = input.next();

		this.str = String.format("%tF", date);

		System.out.print("Masukkan Email\t\t: ");
		this.email = input.next();

		System.out.print("Masukkan Password\t: ");
		this.password = input.next();

		// Melakukan pengecekan validitas email
		if (email.contains("@")) {

			// Melakukan pengecekan username sudah tersedia atau belum
			String cek = "SELECT username FROM user WHERE username='"+username+"'";
			try {
				stmt = conn.createStatement();
				ResultSet resultCek = stmt.executeQuery(cek);
				
				if (resultCek.next()) {
					System.out.println("Username Sudah Terdaftar");
					TambahAkun();
				} else{

					this.query = "INSERT INTO user VALUES ('"+username+"','"+str+"','"+email+"','"+password+"')";
		
					try {
						stmt = conn.createStatement();

						if (stmt.executeUpdate(query) == 1) {
							System.out.println("Data Berhasil Diinputkan");
							login();
						} else{
							System.out.println("Data Gagal Diinputkan");
							login.landingPage();
						}
						
					} catch (SQLException e) {
						System.out.println("Terjadi Kesalahan");
						TambahAkun();
					}

				}

			} catch (SQLException e) {
				System.out.println("Terjadi Kesalahan");
				TambahAkun();
			}

		} else{
			System.out.println("Masukkan Email Dengan Benar");
			TambahAkun();
		}

		conn.close();

	}

	// Pilihan menu
	  	public void user_pilih(){

	  		Scanner scan = new Scanner(System.in);

	  		System.out.println("\n\n>>>>>-DAFTAR MENU-<<<<<");
	  		System.out.println("1. Pengaturan Akun");
	  		System.out.println("2. Beli Barang");
	  		System.out.println("3. Data Master");
	  		System.out.println("4. Laporan");
	  		System.out.println("0. Logout");
	  		System.out.print("Tentukan Pilihanmu : ");
	  		
	  		try {
	  			Integer pilihan = scan.nextInt();

	  			switch (pilihan) {
	  				case 1:
	  					Pengelolaan_User user = new Pengelolaan_User();
	  					user.usr=usr;
	  					user.pass=pass;
	  					user.connection();
	  					break;
	  				
	  				case 2:
	  					transaksi transaksi = new transaksi();
	  					transaksi.user=usr;
	  					transaksi.noresi();
	  					transaksi.insertall();
	  					break;	

	  				case 3:
	  					masterbarang menu = new masterbarang();
	  					menu.menu();
	  					break;

	  				case 4:
	  					laporan laporan = new laporan();
	  					laporan.menu();
	  					break;	
	  				
	  				case 0:
	  					logout();
	  					exit();
	  					break;
	  					
	  				default:
	  					System.out.println("Pilihan Tidak Tersedia");
	  					break;
	  			}
	  		} catch (InputMismatchException e) {
	  			System.out.println("Pilihan Tidak Tersedia");
	  			user_pilih();
	  		}
	  		scan.close();

	  	}

	  	// LogOut
	  	public void logout() {
	  		try {
		  		String jawab;
		  			System.out.println("Apakah Anda Ingin Keluar ? ");
		  			System.out.print("Jawab Y/T? ");
		  			jawab = input.next();
		  			if(jawab.equalsIgnoreCase("Y")) {
		  				System.out.println("Anda Berhasil Keluar");
		  			}else {
		  				user_pilih();
		  			}
			} catch (Exception e) {
				e.printStackTrace();
			}
	  	}
	  	
	  	public void exit() {
	  		System.out.println("+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+");
			System.out.println("|>>>>>>>>-Program Telah Selesai-<<<<<<<<|");
			System.out.println("+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+");
	  		System.exit(0);
			
	  	}
}
