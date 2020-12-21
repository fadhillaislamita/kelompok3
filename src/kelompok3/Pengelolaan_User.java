package kelompok3;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;
import java.time.LocalDate;

public class Pengelolaan_User extends user{
	
	public static String URL = "jdbc:mysql://localhost/tb_bpl";
	public static String USERNAME = "root";
	public static String PASSWORD = "";
	
	public static Connection conn;
	public static Statement stmt;
	
	Scanner input = new Scanner(System.in);
	LocalDate date = LocalDate.now();

	String username,email,password,pilihan,pass,usr;
	int kel,pil;
	boolean pili;
	
	LinkedList<String> name = new LinkedList<>();
	LinkedList<String> mail = new LinkedList<>();
	LinkedList<String> pas = new LinkedList<>();
	LinkedList<String> login = new LinkedList<>();
	
	public void connection()
    {
    	try 
    	{
    		conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        	if(conn != null)
			{
				//System.out.println("Berhasil koneksi ke MySQL Server");
			}
			else
			{
				//System.out.println("Gagal koneksi ke MySQL Server");
			}
        	stmt=conn.createStatement();
        	pilihan();
        	stmt.close();
	    	conn.close();
		} 
    	catch (SQLException e) 
    	{
			System.out.println("Ada Kesalahan Pada Koneksi Ke Database");
		}
    }
	
	public void pilihan() {
		pili=false;
		System.out.println("===============================================");
		System.out.println("\n>>>>>>-User-<<<<<<\n"
				+"1. Tambah\n"
				+"2. Edit\n"
				+"3. Hapus\n"
				+"4. Cari\n"	
				+"5. List\n"
				+"0. Menu");
		System.out.print("Masukkan Pilihan : ");
		pil = input.nextInt();
		
		switch(pil){
		
		case 1 : 
			this.Utambah();
			break;
			
		case 2 :
			this.Uedit();
			break;
			
		case 3 : 
			this.Uhapus();
			break;
			
		case 4 :
			this.Ucari();
			break;
			
		case 5 : 
			this.Ulist();
			break;
			
		case 0 :
			user_pilih();
			pili=true;
			break;	
		default :
			System.out.println("Masukkan Pilihan Dengan Benar");
		
		}
		if(pili==false) {
			this.pilihan();
		} else {
			
		}
	}

	public void Utambah() {
		try {
			System.out.println("\n>>>>>-Tambah User-<<<<<");
			System.out.print("Masukkan Username\t: ");
			username = input.next();
			System.out.print("Masukkan Email User\t: ");
			email = input.next();
			System.out.print("Masukkan Password\t: ");
			password = input.next();
			
			if(email.contains("@")) {
				String sql = "INSERT INTO user(username, email, password, login_terakhir) values "
		    			+ "('"+username+"','"+email+"','"+pass+"','"+date+"')";
		    	stmt.executeUpdate(sql);    	
		    	System.out.println("Data User Telah Ditambah");
			} else {
				System.out.println("Masukkan Email Dengan Benar");
				Utambah();
			}
			
		} catch (SQLException e) {
			System.out.println("Ada Kesalahan Pada SQL Tambah User");
		}
	}

	public void Uedit() {
		System.out.println("\n>>>>>-Edit User-<<<<<");
		
		 		System.out.println("1. Ubah Username");
		 		System.out.println("2. Ubah Email");
		 		System.out.println("3. Ubah Password");
		 		System.out.println("0. Kembali");
		 		System.out.print("Tentukan Pilihanmu : ");
		 		pil = input.nextInt();
		 		
		 		try {
		 			switch (pil) {
		
		 				case 1:
							System.out.println("\n>Ubah Username<");
							
							System.out.print("Masukkan Username Baru : ");
		 					username = input.next();
		 	
		 					System.out.print("Masukkan Password Anda : ");
		 					password = input.next();
		 	
		 					if (password.equals(pass)) {
		 					
		 						query = "UPDATE user SET username='"+username+"' WHERE username='"+usr+"'";
		 		
		 						try {
		 							stmt = conn.createStatement();
		 								
		 							if (stmt.executeUpdate(query) == 1) {
		 								System.out.println("Username Berhasil Di Ubah");
		 								user_pilih();
		 							} else{
		 								System.out.println("Username Gagal Di Ubah");
		 								Uedit();
		 							}
		 								
		 						} catch (SQLException e) {
		 							System.out.println("Terjadi Kesalahan");
		 						}
		 	
		 					} else {
								System.out.println("Password Yang Anda Masukkan Salah");
								Uedit();
		 					}
		 	
		 					break;
							
		 				case 2:
		 					System.out.println("\n>Ubah Email<");
		 	
		 					System.out.print("Masukkan Email Baru\t: ");
		 					this.email = input.next();
		 	
		 					System.out.print("Masukkan Password Anda\t: ");
		 					this.password = input.next();
		 	
		 					if (email.contains("@")) {
		 	
		 						if (password.equals(pass)) {
		 					
		 							query = "UPDATE user SET email='"+email+"' WHERE username='"+usr+"'";
		 		
		 							try {
		 								stmt = conn.createStatement();
		 								
		 								if (stmt.executeUpdate(query) == 1) {
		 									System.out.println("Email Berhasil Di Ubah");
		 									user_pilih();
		 								} else{
		 									System.out.println("Email Gagal Di Ubah");
		 									Uedit();
		 								}
		 								
		 							} catch (SQLException e) {
		 								System.out.println("Terjadi Kesalahan");
		 							}
		 		
		 						} else {
		 							System.out.println("Password Yang Anda Masukkan Salah");
		 							Uedit();
		 						}
		 	
		 					} else {
		 						System.out.println("Masukkan Email Dengan Benar");
		 						Uedit();
		 					}
		 	
		 					break;
		 				
		 				case 3:
		 					System.out.println("\n>Ubah Password<");
		 	
		 					System.out.print("\nMasukkan Password Lama : ");
		 					String passwordLama = input.next();
		 	
		 					System.out.print("Masukkan Password Baru : ");
		 					String passwordBaru = input.next();
		 	
		 					if (passwordLama.equals(pass)) {
		 						
		 						query = "UPDATE user SET password='"+passwordBaru+"' WHERE username='"+usr+"'";
		 		
		 						try {
		 							stmt = conn.createStatement();
		 							
		 							if (stmt.executeUpdate(query) == 1) {
		 								System.out.println("Password Berhasil Di Ubah");
		 								user_pilih();
		 							} else {
		 								System.out.println("Password Gagal Di Ubah");
		 								Uedit();
		 							}
		 							
		 						} catch (SQLException e) {
		 							System.out.println("Terjadi Kesalahan");
		 						}
		 	
		 					} else {
		 						System.out.println("Password Yang Anda Masukkan Salah");
		 						Uedit();
		 					}
		 	
		 					break;
		 					
		 				case 0:
		 					pilihan();
		 			
		 				default:
		 					System.out.println("Pilihan Tidak Tersedia");
		 					Uedit();
		 					break;
		 			}
		
		 		} catch (InputMismatchException e) {
		 			System.out.println("Pilihan Tidak Tersedia");
		 		}
	}
	
	public void Uhapus() {
		System.out.println("\n>>>>>-Hapus User-<<<<<");
		System.out.print("Apakah Anda Yakin Y/N? ");
		pilihan = input.next();
		
		if(pilihan.equals("y")) {
			try {
				query = "DELETE FROM user WHERE username= "
						+ "'"+usr+"'";
	    		stmt.executeUpdate(query);    	
	    		System.out.println("Data User Telah Dihapus");
	    		login();
			} catch (Exception e) {
				System.out.println("Data User Gagal Dihapus");
			}
		}
	}
	
	public void Ucari() {
		try {
			String sql = "SELECT * FROM user ORDER BY login_terakhir DESC";
			ResultSet result = stmt.executeQuery(sql); 
			name.clear();
			
			while(result.next()) {
				name.add(result.getString("username"));
			}
			
			System.out.println("\n>>>>>-Cari User-<<<<<");
			System.out.println("Username\t\t: "+name);
			System.out.print("Masukkan Nama User \t: ");
			username = input.next();
			try {
				sql = "SELECT * FROM user WHERE username= "
		    			+ "'"+username+"'";
				result = stmt.executeQuery(sql); 
				if(result.next()) {
					System.out.println("Username\t:"+result.getString("username"));
					System.out.println("Email\t\t:"+result.getString("email"));
					System.out.println("Password\t:"+result.getString("password"));
					System.out.println("Login Terakhir\t:"+result.getDate("login_terakhir"));
				}
			} catch (SQLException e) {
				System.out.println("Ada Kesalahan Pada SQL Cari User");
			}
		} catch (SQLException e) {
			System.out.println("Ada Kesalahan Pada SQL Mengambil Data Cari User");
		}
	}
	
	public void Ulist() {
		try {
			String sql = "SELECT * FROM user ORDER BY login_terakhir DESC";
			ResultSet result = stmt.executeQuery(sql); 
			name.clear();
			mail.clear();
			pas.clear();
			login.clear();
			
			while(result.next()) {
				name.add(result.getString("username"));
    			mail.add(result.getString("email"));
    			pas.add(result.getString("password"));
    			login.add(result.getString("login_terakhir"));
			}
			System.out.println("\n>>>>>-List User-<<<<<");
			for(int i=0; i<name.size(); i++) {
        		System.out.println("\nUsername\t: "+name.get(i)
						+"\nEmail\t\t: "+mail.get(i)
						+"\nPassword\t: "+pas.get(i)
						+"\nLogin Terakhir\t: "+login.get(i));
        	}
		} catch (Exception e) {
			System.out.println("Ada Kesalahan Pada SQL List User");
		}
	}
	
}
