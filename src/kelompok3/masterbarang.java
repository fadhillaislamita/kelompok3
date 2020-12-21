package kelompok3;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
public class masterbarang {
  
static Connection conn;
	static Statement stmt;
	static ResultSet rs;
	
	Scanner input = new Scanner(System.in);
	Scanner input1 = new Scanner(System.in);
	
	String sku;
	String nama;
	Integer stock;
	Integer harga_beli;
	Integer harga_jual;
	String jwb;
  
  //menu
  public void menu(){
     System.out.println("\n--MENU DATA BARANG--");
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
                    System.out.println("Pilihan yang anda masukkan tidak valid!");
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
  }
}
