package kelompok3;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;

public class transaksi extends datatransaksi{
	public String skubarang;
	public String noresi;
	public int hargabarang;
	public int id=1;
	public int jml;
	public int hargajual;
	Connection conn;
	Statement statement;
	PreparedStatement stmt;
	Scanner input = new Scanner(System.in);
	Date date = new Date();
	SimpleDateFormat time = new SimpleDateFormat("dd-MMM-yyyy HH:mm 'WIB'", new java.util.Locale("id"));
	String str = String.format(time.format(date));
	
	public transaksi() {	
		try {
			 conn = DriverManager.getConnection("jdbc:mysql://localhost/tb_bpl?serverTimezone=Asia/Jakarta", "root", "");
			
		} catch (SQLException e) {
			System.out.println("Aktifkan SQL terlebih dahulu ");
			e.printStackTrace();
		}
	}
	public void getAll() {
    	try {
			statement = conn.createStatement();
			String sql = "SELECT * FROM barang";
			ResultSet rs = statement.executeQuery(sql);
			System.out.println("Pilihan Data Barang\n");
			System.out.println("ID\t| SKU\t\t\t| Nama Barang \t\t| Harga\t\t| Stock");
			while(rs.next()) {
				 System.out.println(+id +"\t| "+rs.getString("sku")+"\t\t| "  +rs.getString("nama")+"\t\t\t| Rp"+rs.getInt("harga_jual")+"\t| "+rs.getInt("stock"));
				 id++;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
    	
	}
	public void skuinput() {
		// TODO Auto-generated method stub
		System.out.print("\nMasukkan Nomor SKU Barang : ");
		skubarang =  input.nextLine();			
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
	        	  int dbstock=rs.getInt("stock");
	        	  int dbhargajual=rs.getInt("harga_jual")*jml;
	        	  if(jml<=dbstock) {
	        		  System.out.println("total pesanan : Rp"+dbhargajual);
	        		  System.out.println("pesanan anda sedang diproses");
	        	  }
	        	  else {
	        		  System.out.println("stok yang dimasukkan melebihi kapasitas.");
		        	  Jumlahbarang();  
	        	  }
	        	  hargajual=dbhargajual;
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
		String sql = "SELECT * FROM transaksi by tanggal DESC";
		try {
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
	          if(rs.next()) {
	        	  String dbnoresi=rs.getString("noresi");
	        	  System.out.println("Nomor resi Pembelian : "+dbnoresi);
	        	  noresi=dbnoresi;        	  
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
		String Query = "INSERT INTO transaksi_detail(id,sku,jumlah,harga,noresi) "
				+ "VALUES ('"+id+"','"+ skubarang +"', '"+ jml +"', '"+ hargajual +"' , '"+ noresi +"')";
		stmt.executeUpdate(Query);
		}
		catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void show() {
		id=1;
		try {
			String sql = "SELECT * FROM transaksi_detail JOIN  barang ON transaksi_detail.sku=barang.sku "
					+ "JOIN transaksi on transaksi_detail.noresi=transaksi.noresi "
					+ "ORDER BY tanggal DESC ";
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(" Data Pesanan Barang\n");
			System.out.println("ID\t| SKU\t| Nama Barang \t\t| Harga\t| stock");
			while(rs.next()) {
				 System.out.println(+id +"\t| "+rs.getString("sku")+"\t| "  +rs.getString("nama")+"\t| Rp"+rs.getInt("harga")+"\t| "+rs.getInt("stok"));
				 id++;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
