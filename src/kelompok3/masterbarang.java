package kelompok3;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class masterbarang{
  
	static Connection conn;
	static Statement stmt;
	static ResultSet rs;
	
	Scanner input = new Scanner(System.in);
	Scanner input1 = new Scanner(System.in);
	
	public static String URL = "jdbc:mysql://localhost/tb_bpl";
	public static String USERNAME = "root";
	public static String PASSWORD = "";
	
	barang barang = new barang();
	
	String sku;
	String nama;
	Integer stock;
	Integer harga_beli;
	Integer harga_jual;
	String jwb;
  
  //menu
  public void menu(){
     System.out.println("\n>>>>>-MENU DATA BARANG-<<<<<");
        System.out.println("1. Tambah Barang");
        System.out.println("2. Cari Barang");
        System.out.println("3. Ubah Barang");
        System.out.println("4. Hapus Barang");
        System.out.println("5. Lihat Data Barang");
        System.out.println("0. Kembali");
        System.out.print("Masukkan Pilihan : ");

        try {
            Integer pilih = input1.nextInt();
            switch (pilih) {
                case 0:
                	user menu = new user();
                	menu.user_pilih();
                    break;
                case 1:
                    TambahBarang();
                    break;
                case 2:
                    CariBarang();
                    break;
                case 3:
                    UbahBarang();
                    break;
                case 4:
                    HapusBarang();
                    break;
                case 5 :
                	LihatData();
                	break;
                default:
                    System.out.println("Masukkan Pilihan Dengan Benar");
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
  }
	public void TambahBarang() throws Exception {
    	Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		
		System.out.println(" ******************************************* ");
		System.out.println(" *                TAMBAH DATA              * ");
		System.out.println(" *******************************************\n");
		
		sku = barang.sku();
		nama = barang.nama();
		stock = barang.stock();
		harga_beli = barang.harga_beli();
		harga_jual = barang.harga_jual();
		if(harga_jual>harga_beli) {
		       if(stock>0) {
		           try {
		               stmt = conn.createStatement();
		               String sql = "INSERT INTO barang VALUES ('"+sku+"', '"+nama+"', '"+stock+"', '"+harga_beli+"', '"+harga_jual+"')";   

		               stmt.execute(sql);
		               System.out.println("\nData Berhasil Disimpan");
		       
		           } 
		           catch (Exception e) {
		               e.printStackTrace();
		           }            
		   		LihatData();
		       }else {
		    	   System.out.println("Masukkan Stock Dengan Benar");
		    	   TambahBarang();
		       }
		} else {
			System.out.println("Harga Beli Lebih Besar Dari Harga Jual");
			TambahBarang();
		}
	}
	
	//Cari Data Barang
	public void CariBarang() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		
		ArrayList <barang> data = new ArrayList<>();
		
		System.out.println("\n ********************************************* ");
		System.out.println("*                  CARI  DATA                *");
		System.out.println(" ********************************************* ");
		
		System.out.print("Masukkan barang yang di cari : ");
		String cari = input.nextLine();

		stmt = conn.createStatement();
		String sql = "SELECT * FROM barang WHERE nama='"+cari+"'";
		
		try {
			rs = stmt.executeQuery(sql);
			
        	if(rs.next()) { 
        		
        		barang.setSku(rs.getString("sku"));
        		barang.setNama(rs.getString("nama"));
        		barang.setStock(rs.getInt("stock"));
        		barang.setHarga_beli(rs.getInt("harga_beli"));
        		barang.setHarga_jual(rs.getInt("harga_jual"));
        		
        		data.add(barang);
        		
				System.out.println(" ");
         		System.out.print("  SKU");
         		System.out.print("\t\t");
         		System.out.print("  NAMA");
         		System.out.print("    \t");
         		System.out.print("  STOCK");
         		System.out.print("\t\t");
         		System.out.print("  HARGA BELI");
         		System.out.print("\t\t");
         		System.out.println("  HARGA JUAL ");
         		
         		for(barang barang : data) {
    	        	System.out.print("  " +barang.sku);
    	        	System.out.print("\t\t");
    	        	System.out.print("  " +barang.nama);
    	        	System.out.print("   \t");
    	        	System.out.print("  " +barang.stock);
    	        	System.out.print("\t\t");
    	        	System.out.print("  " +barang.harga_beli);
    	        	System.out.print("\t\t\t");
    	        	System.out.println("  " +barang.harga_jual);
            	}
        	}
        	else {
        		System.out.println("\nBarang Yang Dicari Tidak Ada");
        	}
             menu();
        		
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	//Ubah Barang
	public void UbahBarang() throws Exception {
    	Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		
		String sql;
		
        System.out.println("\n *********************************************** ");
        System.out.println(" *                UPDATE BARANG                *");
        System.out.println(" *********************************************** ");
        
        System.out.println("1. Nama");
        System.out.println("2. Stock");
        System.out.println("3. Harga Beli");
        System.out.println("4. Harga Jual");
		System.out.print("\nPilih data yang ingin diubah : ");
		Integer ubah = input1.nextInt();
		System.out.println(" ");
		
        stmt = conn.createStatement();
        
        
        try {
			switch (ubah) {
				case 1 : 
					System.out.print("Masukkan SKU pada data yang ingin di ubah : ");
					this.sku = input.nextLine();
			        nama = barang.nama();
			        
			        sql = "UPDATE barang SET nama='"+nama+"' WHERE sku='"+sku+"'";   
			        stmt.execute(sql);
					break;
				case 2 :
					System.out.print("Masukkan SKU pada data yang ingin di ubah : ");
					this.sku = input.nextLine();
			        stock = barang.stock();
			        
			        if(stock>0) {
				        sql = "UPDATE barang SET stock='"+stock+"' WHERE sku='"+sku+"'";   
				        stmt.execute(sql);
			        }else {
			        	System.out.println("Update Stok Dengan Benar");
			        	UbahBarang();
			        }
					break;
				case 3 :
					System.out.print("Masukkan SKU pada data yang ingin di ubah : ");
					this.sku = input.nextLine();
			        harga_beli = barang.harga_beli();
			        
			        stmt = conn.createStatement();
					sql = "SELECT * FROM barang WHERE sku='"+sku+"'";
					rs = stmt.executeQuery(sql);
					
					if(rs.next()) {
						if(harga_beli>0) {
					        if(harga_beli<rs.getInt("harga_jual")) {
					        sql = "UPDATE barang SET harga_beli='"+harga_beli+"' WHERE sku='"+sku+"'";   
					        stmt.execute(sql);
					        } else {
					        	System.out.println("Harga Beli Lebih Mahal Dari Harga Jual");
					        }
					    } else {
					    	System.out.println("Harga Beli Tidak Bisa 0");
					    }
					}
					break;
				case 4 :
					System.out.print("Masukkan SKU pada data yang ingin di ubah : ");
					this.sku = input.nextLine();
			        harga_jual = barang.harga_jual();
			        
			        stmt = conn.createStatement();
					sql = "SELECT * FROM barang WHERE sku='"+sku+"'";
					rs = stmt.executeQuery(sql);
					
					if(rs.next()) {
						if(harga_beli>0) {
					        if(harga_jual>rs.getInt("harga_beli")) {
						        sql = "UPDATE barang SET harga_jual='"+harga_jual+"' WHERE sku='"+sku+"'";   
						        stmt.execute(sql);
					        } else {
					        	System.out.println("Harga Jual Lebih Murah Dari Harga Beli");
					        }
					    }else {
						    System.out.println("Harga Jual Tidak Bisa 0");
						}
					}
			        
					break;
				default :
					System.out.println("Pilihan Tidak Tersedia\n");
					UbahBarang();
			}

		}
        
        catch (Exception e) {
        	e.printStackTrace();
        }			
		LihatData();
		
	}
	
	//Hapus Data Barang
	public void HapusBarang() throws Exception {
    	Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		
		System.out.println("\n ********************************************* ");
		System.out.println("*                  HAPUS DATA                *");
		System.out.println(" ********************************************* ");
		System.out.print("Masukkan SKU pada data yang akan di hapus : ");
		this.sku = input.nextLine();
		
		try {
			stmt = conn.createStatement();
			String sql = "DELETE FROM barang WHERE sku='"+sku+"'";
		
			stmt.execute(sql);
			System.out.println("\nData Berhasil Terhapus");

		}
		
		catch(Exception e) {
			e.printStackTrace();
		}			
		LihatData();

	}
	
	//Melihat Data
	public void LihatData() throws Exception {
    	Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		
    	stmt = conn.createStatement();
    	
    	ArrayList <barang> data = new ArrayList<>();
        
        String sql = "SELECT * FROM barang";
        rs = stmt.executeQuery(sql);

        try {
            System.out.println("\n =================================================================================== ");
            System.out.println(" ||>>>>>>>>>>>>>>>>>>>>>-DATA MASTER BARANG DI TOKO BERKAH-<<<<<<<<<<<<<<<<<<<<<<<||");
            System.out.println(" =================================================================================== ");
            
         		System.out.print("  SKU");
         		System.out.print("\t\t");
         		System.out.print("  NAMA     ");
         		System.out.print("   \t");
         		System.out.print("  STOCK");
         		System.out.print("\t\t");
         		System.out.print("  HARGA BELI");
         		System.out.print("\t\t");
         		System.out.println("  HARGA JUAL");
        	
        	while(rs.next()) { 
        		barang n = new barang();
        		
        		n.setSku(rs.getString("sku"));
        		n.setNama(rs.getString("nama"));
        		n.setStock(rs.getInt("stock"));
        		n.setHarga_beli(rs.getInt("harga_beli"));
        		n.setHarga_jual(rs.getInt("harga_jual"));
        		
        		data.add(n);
        	}

        	for(barang barang : data) {
	        	System.out.print("  " +barang.sku);
	        	System.out.print("\t\t");
	        	System.out.print("  " +barang.nama);
	        	System.out.print("\t\t");
	        	System.out.print("  " +barang.stock);
	        	System.out.print("\t\t");
	        	System.out.print("  " +barang.harga_beli);
	        	System.out.print("\t\t\t");
	        	System.out.println("  " +barang.harga_jual);
        	}
            menu();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
}
