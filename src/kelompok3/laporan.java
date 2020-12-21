package kelompok3;

import java.sql.*;
import java.util.Scanner;

public class laporan extends koneksi{
	
	Scanner input = new Scanner(System.in);
	Scanner input1 = new Scanner(System.in);
	
	static Connection conn;
	static Statement stmt;
	static ResultSet rs;
	
	private int id, harga_beli, harga_jual, jumlah, harga, hbeli_total, hjual_total, untung;
	private String sku, nama, noresi;
	private Date tanggal;
	
	public void menu() {
		hbeli_total=0;
		hjual_total=0;
		untung=0;
		System.out.println("\n+--------------------------------+");
		System.out.println("|          MENU LAPORAN          |");
		System.out.println("+--------------------------------+");
		System.out.println("1. Laporan Harian");
		System.out.println("2. Laporan Bulanan");
		System.out.println("0. Kembali");
		System.out.print("Masukkan Pilihan Anda : ");
		int masuk = input.nextInt();
		switch(masuk) {
			case 1:
				laporanHari();
				break;
			case 2:
				laporanBulan();
				break;
			case 3:
				user menu = new user();
				menu.user_pilih();
				break;
			default:
				System.out.println("\nPilihan Anda Salah");
				System.out.println("\n");
				menu();
		}
	}
	
	public void laporanHari() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
	        
	        conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	        stmt = conn.createStatement();
	        
	        String format = "|%s\t| %s\t| %s\t| %s\t| %s\t\t| %s\t| %s\t| %s\t| %s\t\t|";
	        String format1 = "|%d\t| %s\t\t| %s\t|  %s\t| %s  \t|   %d\t|   %d\t|    %d\t\t| %d   \t|";
	        
	        System.out.print("\nMasukkan Tanggal (YYYY-MM-DD) : ");
	        String tgl = input1.nextLine();
	        
	        String sql = "SELECT * FROM transaksi_detail INNER JOIN barang  ON transaksi_detail.sku = barang.sku INNER JOIN transaksi ON transaksi_detail.noresi = transaksi.noresi WHERE tanggal='"+tgl+"'";
	        rs = stmt.executeQuery(sql);
	        
	        if(rs.next()==false) {
	        	System.out.println("Data Tidak Ada atau Format Salah");
	        	System.out.println("\n");
	        	menu();
	        }
	        else {
	        	System.out.println("\n+---------------------------------------------------------------------------------------------------------------+");
	        	System.out.println("|                                       TAMPILAN LAPORAN PENJUALAN HARIAN                                       |");
	        	System.out.println("+---------------------------------------------------------------------------------------------------------------+");
	        	System.out.printf(format, "ID", "No.Resi", "Tanggal", "SKU", "Nama", "Harga Beli", "Harga Jual", "Jumlah", "Total");
	          	do {
			        	id = rs.getInt("id");
			        	noresi = rs.getString("noresi");
			        	tanggal = rs.getDate("tanggal");
			        	sku = rs.getString("sku");
			        	nama = rs.getString("nama");
			        	harga_beli = rs.getInt("harga_beli");
			        	harga_jual = rs.getInt("harga_jual");
			        	jumlah = rs.getInt("jumlah");
			        	harga = rs.getInt("harga");
			        	
			        	hbeli_total=hbeli_total+(harga_beli*jumlah);
			        	hjual_total=hjual_total+(harga_jual*jumlah);
			        	
			        	System.out.println("\n");
			        	System.out.printf(format1, id, noresi, tanggal, sku, nama, harga_beli, harga_jual, jumlah, harga);
	          	} while(rs.next());
		        untung = hjual_total-hbeli_total;
		        System.out.println("\n+---------------------------------------------------------------------------------------------------------------+");
		        System.out.println("|                                                                                                               |");
		        System.out.println("|                                                                                                               |");
		        System.out.println("|Total penjualan barang  Pada Tanggal "+tanggal+"      : "+hjual_total+"                                                                    |");
		        System.out.println("|Total modal barang terpakai  Pada Tanggal "+tanggal+" : "+hbeli_total+"                                                                    |");
		        System.out.println("|Keuntungan Pada Tanggal "+tanggal+"                   : "+untung+"                                                                    |");
		        System.out.println("+---------------------------------------------------------------------------------------------------------------+");
		        menu();
	        } 
	        
		}
		catch (Exception e){
			 e.printStackTrace();
		}

	}
	public void laporanBulan() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
	        
	        conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	        stmt = conn.createStatement();
	        
	        String format = "|%s\t| %s\t| %s\t| %s\t| %s\t\t| %s\t| %s\t| %s\t| %s\t|";
	        String format1 = "|%d\t| %s\t\t| %s\t|  %s\t| %s   \t|   %d\t|   %d\t|    %d\t\t| %d\t|";
	        
	        Bulan();
	        System.out.print("Pilih Bulan : ");
	        String tgl = input1.nextLine();
	        System.out.print("Masukkan Tahun (YYYY) : ");
	        String tgl1 = input1.nextLine();
	        
	        String sql = "SELECT * FROM transaksi_detail INNER JOIN barang  ON transaksi_detail.sku = barang.sku INNER JOIN transaksi ON transaksi_detail.noresi = transaksi.noresi WHERE MONTH(tanggal)='"+tgl+"' AND YEAR(tanggal)='"+tgl1+"'";
	        rs = stmt.executeQuery(sql);
	        
	        if(rs.next()==false) {
	        	System.out.println("\nData Tidak Ada atau Format Salah");
	        	System.out.println("\n");
	        	menu();
	        }
	        else {
	        	System.out.println("\n+---------------------------------------------------------------------------------------------------------------+");
	        	System.out.println("|                                       TAMPILAN LAPORAN PENJUALAN BULANAN                                      |");
	        	System.out.println("+---------------------------------------------------------------------------------------------------------------+");
	        	System.out.printf(format, "ID", "No.Resi", "Tanggal", "SKU", "Nama", "Harga Beli", "Harga Jual", "Jumlah", "Total");
	          	do {
			        	id = rs.getInt("id");
			        	noresi = rs.getString("noresi");
			        	tanggal = rs.getDate("tanggal");
			        	sku = rs.getString("sku");
			        	nama = rs.getString("nama");
			        	harga_beli = rs.getInt("harga_beli");
			        	harga_jual = rs.getInt("harga_jual");
			        	jumlah = rs.getInt("jumlah");
			        	harga = rs.getInt("harga");
			        	
			        	hbeli_total=hbeli_total+(harga_beli*jumlah);
			        	hjual_total=hjual_total+(harga_jual*jumlah);
			        	
			        	System.out.println("\n");
			        	System.out.printf(format1, id, noresi, tanggal, sku, nama, harga_beli, harga_jual, jumlah, harga);
	          	} while(rs.next());
		        untung = hjual_total-hbeli_total;
	          	System.out.println("\n+---------------------------------------------------------------------------------------------------------------+");
	          	System.out.println("|                                                                                                               |");
		        System.out.println("|                                                                                                               |");
		        System.out.println("|Total penjualan barang Bulan Ke "+tgl+" Tahun "+tgl1+"     : "+hjual_total+"                                                                    |");
		        System.out.println("|Total modal barang Bulan Ke "+tgl+" Tahun "+tgl1+"         : "+hbeli_total+"                                                                    |");
		        System.out.println("|Keuntungan Bulan Ke "+tgl+" Tahun "+tgl1+"                 : "+untung+"                                                                    |");
		        System.out.println("+---------------------------------------------------------------------------------------------------------------+");
		        menu();
	        }    
		}
		catch (Exception e) {
			 e.printStackTrace();
		}
	}
		
	private void Bulan() {
		System.out.println("\n+------------------------------------------------------+");
		System.out.println("|                     Daftar Bulan                     |");
		System.out.println("+------------------------------------------------------+");
		System.out.println("|1. Januari    2.  Februari  3.  Maret     4.  April   |");
		System.out.println("|5. Mei        6.  Juni      7.  Juli      8.  Agustus |");
		System.out.println("|9. September  10. Oktober   11. November  12. Desember|");
		System.out.println("+------------------------------------------------------+");
	}
}