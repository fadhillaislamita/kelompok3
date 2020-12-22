package kelompok3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.time.LocalDate;

public class transaksi extends datatransaksi{
	
	public String skubarang;
	public String noresi;
	public int hargabarang;
	public int id=1;
	public int jml;
	public int hargajual;
	String user,pilihan;
	int dbstock;
	
	Connection conn;
	Statement statement;
	PreparedStatement stmt;
	
	user menu = new user();
	Scanner input = new Scanner(System.in);
	LocalDate date = LocalDate.now();
	
	public void insertall() {
		getAll();
	}
	
	public transaksi() {	
		try {
			 conn = DriverManager.getConnection("jdbc:mysql://localhost/tb_bpl?serverTimezone=Asia/Jakarta", "root", "");
			
		} catch (SQLException e) {
			System.out.println("Aktifkan SQL terlebih dahulu ");
			e.printStackTrace();
		}
	}
	
	public void noresi() {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");	
		System.out.print("\nMasukkan No Resi : ");
		noresi =  input.nextLine();	
	}
	
	public void getAll() {
    	try {
			statement = conn.createStatement();
			String sql = "SELECT * FROM barang";
			ResultSet rs = statement.executeQuery(sql);
			System.out.println("Pilihan Data Barang\n");
			System.out.println("ID\t| SKU\t\t| Nama Barang \t\t| Harga\t\t| Stock");
			id=1;
			while(rs.next()) {
				 System.out.println(+id +"\t| "+rs.getString("sku")+"\t\t| "  +rs.getString("nama")+"    \t\t| Rp"+rs.getInt("harga_jual")+"\t| "+rs.getInt("stock"));
				 id++;
			}
			skuinput();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
    	
	}
	public void skuinput() {
		// TODO Auto-generated method stub
		System.out.print("\nMasukkan Nomor SKU Barang : ");
		skubarang =  input.next();
		inputprocess();
	}
	public void inputprocess() {
		String sql = "SELECT * FROM barang WHERE sku = ?";
		try {
	          PreparedStatement statement = conn.prepareStatement(sql);
	          statement.setString(1, skubarang);
	          ResultSet rs = statement.executeQuery();
	          if(rs.next()) {
	        	  System.out.println("barang pesanan anda adalah "+rs.getString("nama")+" dengan stock sebanyak "+rs.getInt("stock"));
	        	  Jumlahbarang();
	          }
	          else {
	        	  System.out.println("Masukkan Nomor SKU dengan tepat");
	        	  this.skubarang=null;
	        	  skuinput();
	          }
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Ada masalah di proses inputan");
		}
	}
	
	public void Jumlahbarang() {		
		System.out.print("Masukkan Jumlah Barang : ");
		jml =  input.nextInt();
		jmlprocess();
	}
	
	public void jmlprocess() {
		String sql = "SELECT * FROM barang WHERE sku = ?";
		try {
	          PreparedStatement statement = conn.prepareStatement(sql);
	          statement.setString(1, skubarang);
	          ResultSet rs = statement.executeQuery();
	          
	          if(rs.next()) {
	        	  dbstock=rs.getInt("stock");
	        	  int dbhargajual=rs.getInt("harga_jual")*jml;
	        	  if(jml<=dbstock) {
	        		  System.out.println("total pesanan : Rp"+dbhargajual);
	        		  System.out.println("pesanan anda sedang diproses");
	        		  hargajual=dbhargajual;
	        		  hasilorder();
	        	  }
	        	  else {
	        		  System.out.println("stok yang dimasukkan melebihi kapasitas.");
		        	  Jumlahbarang();  
	        	  } 
	          }
	          else {
	        	  //memasukkan kembali nilainya atau keluar
	        	  System.out.println("jumlah yang dimasukkan melebihi stock yang tersedia");
	          }
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("Ada masalah di bagian pemrosesan jumlah");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void hasilorder() {
		String sql = "SELECT * FROM transaksi ORDER by tanggal DESC";
		try {
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
	          if(rs.next()) {
	        	  System.out.println("Nomor resi Pembelian : "+noresi); 
	        	  input();
	          }
	          else {
	        	  System.out.println("masukkan Nomor SKU dengan tepat");
	        	  this.skubarang=null;
	        	 
	          }
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Ada masalah di bagian pemrosesan order");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void input() {
	try {
		Statement stmt = conn.createStatement();
		String sql = "SELECT * FROM transaksi_detail";
		ResultSet rs = statement.executeQuery(sql);
		int a=0;
		id=1;
		while(rs.next()) {
			a=rs.getInt("id");
			 id++;
		}
		while(id<=a) {
			id++;
		}

		try {
			stmt = conn.createStatement();
			String Query = "INSERT INTO transaksi(noresi,tanggal,username) "
					+ "VALUES ('"+noresi+"','"+date+"','"+ user +"')";
			stmt.executeUpdate(Query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbstock=dbstock-jml;
		String Query = "UPDATE barang SET stock='"+dbstock+"' WHERE sku='"+skubarang+"'";
		stmt.executeUpdate(Query);
		
		Query = "INSERT INTO transaksi_detail(id,sku,jumlah,harga,noresi) "
				+ "VALUES ('"+id+"','"+ skubarang +"', '"+ jml +"', '"+ hargajual +"' , '"+ noresi +"')";
		stmt.executeUpdate(Query);
		show();
		}
		catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void show() {
		try {
			String sql = "SELECT * FROM transaksi_detail JOIN  barang ON transaksi_detail.sku=barang.sku "
					+ "JOIN transaksi on transaksi_detail.noresi=transaksi.noresi "
					+ "ORDER BY id ASC ";
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(" Data Pesanan Barang\n");
			System.out.println("ID\t| SKU\t| No Resi\t|Nama Barang \t\t| Harga\t\t\t| stock");
			while(rs.next()) {
				 System.out.println(+rs.getInt("id") +"\t| "+rs.getString("sku")+"\t| "  +rs.getString("noresi")+"\t\t| "  +rs.getString("nama")+"   \t| Rp"+rs.getInt("harga")+"\t\t| "+rs.getInt("stock"));
			}
			ulang();
		} catch (SQLException e) {
			System.out.println("Ada Error");
		}
		
	}
	
	public void ulang() {
		System.out.println("Beli Lagi Y/N?");
		pilihan = input.next();
			if(pilihan.equalsIgnoreCase("Y")) {
				insertall();
			}else {
				menu.user_pilih();
			}
	}

}
