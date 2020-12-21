package kelompok3;
import java.sql.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.time.LocalDate;

public class Pengelolaan_User {
	
	public static String url = "jdbc:mysql://localhost/tb_bpl";
	public static String user = "root";
	public static String password = "";
	
	public static Connection conn;
	public static Statement stmt;
	
	Scanner input = new Scanner(System.in);
	LocalDate date = LocalDate.now();

	String username,email,pass;
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
    		conn = DriverManager.getConnection(url,user,password);
        	if(conn != null)
			{
				System.out.println("Berhasil koneksi ke MySQL Server");
			}
			else
			{
				System.out.println("Gagal koneksi ke MySQL Server");
			}
        	stmt=conn.createStatement();
        	pilihan();
        	stmt.close();
	    	conn.close();
		} 
    	catch (SQLException e) 
    	{
    		e.printStackTrace();
    		System.out.println("Database Belum Hidup");
		}
    }
	
	public void pilihan() {
		pili=false;
		System.out.println(">>>>>>-User-<<<<<<\n"
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
			pili=true;
			break;	
		default :
			System.out.println("Masukkan Pilihan Dengan Benar");
		
		}
		if(pili==false) {
			System.out.println("====================================");
			this.pilihan();
		} else {
			
		}
	}

	public void Utambah() {
		try {
			System.out.println(">>>>>-Tambah User-<<<<<");
			System.out.print("Masukkan Username\t: ");
			username = input.next();
			System.out.print("Masukkan Email User\t: ");
			email = input.next();
			System.out.print("Masukkan Password\t: ");
			pass = input.next();
			
			String sql = "INSERT INTO user(username, email, password, login_terakhir) values "
	    			+ "('"+username+"','"+email+"','"+pass+"','"+date+"')";
	    	stmt.executeUpdate(sql);    	
	    	System.out.println("Data User Telah Ditambah");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Data User Gagal Ditambah");
		}
	}

	public void Uedit() {
		try {
			String sql = "SELECT * FROM user ORDER BY login_terakhir DESC";
			ResultSet result = stmt.executeQuery(sql); 
			name.clear();
			
			while(result.next()) {
				name.add(result.getString("username"));
			}
			
			System.out.println(">>>>>-Edit User-<<<<<");
			System.out.println("Username : "+name);
			System.out.print("Masukkan Username\t: ");
			username = input.next();
			System.out.print("Masukkan Email Baru\t: ");
			email = input.next();
			System.out.print("Masukkan Password Baru\t: ");
			pass = input.next();
			
			try {
				sql = "UPDATE user SET email="
		    			+ "'"+email+"'"+ ", password="+"'"+pass+"'"+ ", login_terakhir="+"'"+date+"'"+"WHERE username="+ "'"+username+"'";
		    	stmt.executeUpdate(sql);    	
		    	System.out.println("Data User Telah Diubah");
			} catch (SQLException e) {
				System.out.println("Data User Gagal Diubah");
			}
		} catch (SQLException e) {
			System.out.println("Data User Telah Diubah");
			e.printStackTrace();
		}
	}
	
	public void Uhapus() {
		try {
			String sql = "SELECT * FROM user ORDER BY login_terakhir DESC";
			ResultSet result = stmt.executeQuery(sql); 
			name.clear();
			
			while(result.next()) {
				name.add(result.getString("username"));
			}
			
			System.out.println(">>>>>-Hapus User-<<<<<");
			System.out.println("Username : "+name);
			System.out.print("Masukkan Username\t: ");
			username = input.next();
			
			try {
				sql = "DELETE FROM user WHERE username= "
						+ "'"+username+"'";
	    		stmt.executeUpdate(sql);    	
	    		System.out.println("Data User Telah Dihapus");
			} catch (SQLException e) {
				System.out.println("Data User Gagal Dihapus");
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
			
			System.out.println(">>>>>-Cari User-<<<<<");
			System.out.println("Username : "+name);
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
				System.out.println("User Tidak Ditemukan");
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
			
			for(int i=0; i<name.size(); i++) {
        		System.out.println("\nUsername\t: "+name.get(i)
						+"\nEmail\t\t: "+mail.get(i)
						+"\nPassword\t: "+pas.get(i)
						+"\nLogin Terakhir\t: "+login.get(i));
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
