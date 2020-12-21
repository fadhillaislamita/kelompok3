package tb_bpl;

import java.sql.*;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.ResultSet;
import java.util.Random;

public class user extends koneksi implements akun {

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
	@Override
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
	@Override
	public void TambahAkun() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

		System.out.println("\n\n--SIGN UP--");

		System.out.print("Masukkan Username : ");
		this.username = input.next();

		this.str = String.format("%tF", date);

		System.out.print("Masukkan Email : ");
		this.email = input.next();

		System.out.print("Masukkan Password : ");
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
						}
						
					} catch (SQLException e) {
						System.out.println("Terjadi Kesalahan");
					}

				}

			} catch (SQLException e) {
				System.out.println("Terjadi Kesalahan");
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

	  		System.out.println("\n\n--DAFTAR MENU--");
	  		System.out.println("1. Pengaturan Akun");
	  		System.out.println("2. Beli Barang");
	  		System.out.println("3. Data Master");
	  		System.out.print("Tentukan Pilihanmu : ");
	  		
	  		try {
	  			Integer pilihan = scan.nextInt();

	  			switch (pilihan) {
	  				case 1:
	  					user_setting();
	  					break;
	  				
	  				case 2:
	  					// transaksi.option();
	  					break;	

	  				case 3:
	  					// proses.option();
	  					break;

	  				case 4:
	  					
	  				default:
	  					System.out.println("Pilihan Tidak Tersedia");
	  					break;
	  			}
	  		} catch (InputMismatchException e) {
	  			System.out.println("Pilihan Tidak Tersedia");
	  		}

	  		scan.close();

	  	}


	  	// Pilihan setting
	  	public void user_setting() {

	  		Scanner scan = new Scanner(System.in);
	  			
	  		System.out.println("\n\nPENGELOLAAN DATA PENGGUNA");
	  		System.out.println("1. Edit Akun");
	  		System.out.println("2. Hapus Akun");
	  		System.out.println("3. Cari Data");
	  		System.out.println("4. Lihat Data");
	  		System.out.println("5. Kembali");
	  		System.out.println("6. LogOut");
	  		System.out.print("Tentukan Pilihanmu : ");
	  		Integer pilihan = scan.nextInt();	
	  		
	  		switch (pilihan) {
	  			case 1:
	  				EditAkun();
	  				break;
	  		
	  			case 2:
	  				HapusAkun();
	  				break;

	  			case 3:
	  				CariAkun();
	  				break;

	  			case 4:
	  				LihatAkun();
	  				break;
	  				
	  			case 5:
	  				user_pilih();
	  				break;
	  				
	  			case 6:
	  				logout();

	  			default:
	  				System.out.println("Pilihan Tidak Tersedia");
	  				break;
	  		}

	  		scan.close();

	  	}
	  	
	  	// LogOut
	  	public void logout() {
	  		boolean cek = true;
	  		String jawab;
	  		do {
	  			System.out.println("Apakah Anda Ingin Keluar ? ");
	  			System.out.println("Jawab Y/T");
	  			jawab = input.next();
	  			if(jawab.equalsIgnoreCase("Y")) {
	  				cek = false;
	  				System.out.println("Anda Berhasil Keluar");
	  			}
	  		} 
	  		while(cek);
	  		
	  		Scanner scan = new Scanner(System.in);
	  		
	  		System.out.println("1. LogIn");
	  		System.out.println("2. Daftar");
	  		
	  		System.out.print("Tentukan Pilihanmu : ");
	  		Integer pilihan = scan.nextInt();
	  		
	  		switch (pilihan) {
				case 1:
				try {
					login();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					break;
			
				case 2:
				try {
					TambahAkun();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					break;
	  		}
	  		
	  		scan.close();
	  	}
		
	//Edit email dan password akun
	@Override
	public void EditAkun() {
		System.out.println("\n\n--UPDATE--");

 		System.out.println("1. Ubah Username");
 		System.out.println("2. Ubah Email");
 		System.out.println("3. Ubah Password");
 		System.out.println("4. Kembali");
 		System.out.print("Tentukan Pilihanmu : ");

 		try {
 			Integer pilihan = input.nextInt();
 			switch (pilihan) {

 				// Ubah Username
 				case 1:
					System.out.println("\n--Ubah Username--");
					
					System.out.print("Masukkan Username Baru :");
 					this.username = input.next();
 	
 					System.out.print("Masukkan Password Anda : ");
 					this.password = input.next();
 	
 					if (password.equals(pass)) {
 					
 						this.query = "UPDATE user SET username='"+username+"' WHERE username='"+usr+"'";
 		
 						try {
 							stmt = conn.createStatement();
 								
 							if (stmt.executeUpdate(query) == 1) {
 								System.out.println("Username Berhasil Di Ubah");
 								user_pilih();
 							} else{
 								System.out.println("Username Gagal Di Ubah");
 								EditAkun();
 							}
 								
 						} catch (SQLException e) {
 							System.out.println("Terjadi Kesalahan");
 						}
 	
 					} else {
						System.out.println("Password Yang Anda Masukkan Salah");
 						EditAkun();
 					}
 	
 					break;
					
 			
 				// Ubah email
 				case 2:
 					System.out.println("\n--Ubah Email--");
 	
 					System.out.print("Masukkan Email Baru :");
 					this.email = input.next();
 	
 					System.out.print("Masukkan Password Anda : ");
 					this.password = input.next();
 	
 					if (email.contains("@")) {
 	
 						if (password.equals(pass)) {
 					
 							this.query = "UPDATE user SET email='"+email+"' WHERE username='"+usr+"'";
 		
 							try {
 								stmt = conn.createStatement();
 								
 								if (stmt.executeUpdate(query) == 1) {
 									System.out.println("Email Berhasil Di Ubah");
 									user_pilih();
 								} else{
 									System.out.println("Email Gagal Di Ubah");
 									EditAkun();
 								}
 								
 							} catch (SQLException e) {
 								System.out.println("Terjadi Kesalahan");
 							}
 		
 						} else {
 							System.out.println("Password Yang Anda Masukkan Salah");
 							EditAkun();
 						}
 	
 					} else {
 						System.out.println("Masukkan Email Dengan Benar");
 						EditAkun();
 					}
 	
 					break;
 				
 				// Ubah password
 				case 3:
 					System.out.println("\n--Ubah Password--");
 	
 					System.out.print("\nMasukkan Password Lama : ");
 					String passwordLama = input.next();
 	
 					System.out.print("Masukkan Password Baru :");
 					String passwordBaru = input.next();
 	
 					if (passwordLama.equals(pass)) {
 						
 						this.query = "UPDATE user SET password='"+passwordBaru+"' WHERE username='"+usr+"'";
 		
 						try {
 							stmt = conn.createStatement();
 							
 							if (stmt.executeUpdate(query) == 1) {
 								System.out.println("Password Berhasil Di Ubah");
 								user_pilih();
 							} else {
 								System.out.println("Password Gagal Di Ubah");
 								EditAkun();
 							}
 							
 						} catch (SQLException e) {
 							System.out.println("Terjadi Kesalahan");
 						}
 	
 					} else {
 						System.out.println("Password Yang Anda Masukkan Salah");
 						EditAkun();
 					}
 	
 					break;
 					
 				//Kembali ke menu pengelolaan data user
 				case 4:
 					user_setting();
 			
 				default:
 					System.out.println("Pilihan Tidak Tersedia");
 					break;
 			}

 		} catch (InputMismatchException e) {
 			System.out.println("Pilihan Tidak Tersedia");
 		}

 	}

	//Hapus akun
	@Override
	public void HapusAkun() {
		System.out.println("\n\n--HAPUS--");

 		System.out.println("Apakah Anda Yakin Ingin Menghapus Akun Anda ?");
 		System.out.println("Jawab Y/T");
 		System.out.println("Jawabanmu : ");
 		String lanjut = input.next();

 		if (lanjut.equals("y")) {
 			this.query = "DELETE FROM user WHERE username='"+usr+"'";
 			try {
 				stmt = conn.createStatement();
 				stmt.execute(query);
 				System.out.println("Data Berhasil Di Hapus");
 			} catch (SQLException e) {
 				System.out.println("Data Gagal Di Hapus");
 			}
 		} 
 		
 		try {
			TambahAkun();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

 	}

	//Cari data akun  
	@Override
	public void CariAkun() {
		System.out.println("\n\n--CARI--");

 		System.out.print("Masukkan Username : ");
 		String kunci = input.next();

 		this.query = "SELECT*FROM user WHERE username LIKE '%"+kunci+"%'";
 		try {
 			stmt = conn.createStatement();
 			
 			ResultSet result = stmt.executeQuery(query);

 			while (result.next()) {
 					
 				System.out.print(result.getString("username"));
 				System.out.print("\t");
 				System.out.print(result.getDate("login_terakhir"));
 				System.out.print("\t");
 				System.out.print(result.getString("email"));
 				System.out.print("\t");
 				System.out.println(result.getString("password"));
 			}
 		} catch (SQLException e) {
 			System.out.println("Tidak Dapat Mengakses Database");
 		}
 		user_setting();
 	}

	//Lihat data akun
	@Override
	public void LihatAkun() {
		System.out.println("\n\n--LIHAT--");

 		this.query = "SELECT*FROM user";
 		try {
 			stmt = conn.createStatement();
 			
 			ResultSet result = stmt.executeQuery(query);

 			while (result.next()) {
 					
 				System.out.print(result.getString("username"));
 				System.out.print("\t");
 				System.out.print(result.getDate("login_terakhir"));
 				System.out.print("\t");
 				System.out.print(result.getString("email"));
 				System.out.print("\t");
 				System.out.println(result.getString("password"));
 			}
 		} catch (SQLException e) {
 			System.out.println("Tidak Dapat Mengakses Database");
 		}
 		user_setting();
 	}


}
