/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
import java.sql.Statement;
 import javax.annotation.Resource;
 import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
 import javax.sql.DataSource;
 import javax.sql.rowset.CachedRowSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author PELIN
 */
@ManagedBean ( name="musteriBean" )
@SessionScoped
public class MusteriBean 
{
private String musteriAd;
private String musteriSoyad;
private String musteriBolum;
private String musteriKimlikNo;
private String musteriSifre;
private String bulunacak_kitap;
private String bulunacakTc;
private String bulunacakSifre;
private String yuklenecekBakiye;
private String musteriMesaj;

    public String getMusteriMesaj() {
        return musteriMesaj;
    }

    public void setMusteriMesaj(String musteriMesaj) {
        this.musteriMesaj = musteriMesaj;
    }

    public void setBulunacak_kitap(String bulunacak_kitap) {
        this.bulunacak_kitap = bulunacak_kitap;
    }

    public String getBulunacak_kitap() {
        return bulunacak_kitap;
    }
    
CachedRowSet rowSet=null;
private String silinecek_isbn;
    /**
     * Creates a new instance of KitapBean
     */
//@Resource(name="jdbc/addressbook")
  DataSource dataSource;
   public MusteriBean() {
        try {
			Context ctx = new InitialContext();
			dataSource = (DataSource)ctx.lookup("jdbc/addressbook");
		} catch (NamingException e) {
			e.printStackTrace();
		}
    }
   
    public void setBulunacakSifre(String bulunacakSifre) {
        this.bulunacakSifre = bulunacakSifre;
    }

    public String getBulunacakSifre() {
        return bulunacakSifre;
    }
    
    public void setYuklenecekBakiye (String yuklenecekBakiye) {
        this.yuklenecekBakiye = yuklenecekBakiye;
    }

    public String getYuklenecekBakiye() {
        return yuklenecekBakiye;
    }

    public void setBulunacakTc(String bulunacakTc) {
        this.bulunacakTc = bulunacakTc;
    }

    public String getBulunacakTc() {
        return bulunacakTc;
    }
    
    
    
  public String getMusteriAd()
 {
 return musteriAd;
 } 

 
 public void setMusteriAd( String musteriAd )
 {
 this.musteriAd = musteriAd;
 } 
  
 public String getMusteriSoyad()
 {
 return musteriSoyad;
 } 

 
 public void setMusteriSoyad( String musteriSoyad )
 {
 this.musteriSoyad = musteriSoyad;
 } 


 public String getMusteriBolum()
 {
 return musteriBolum;
 } 

 
 public void setMusteriBolum( String musteriBolum )
 {
 this.musteriBolum = musteriBolum;
 } 

public String getMusteriKimlikNo()
 {
 return musteriKimlikNo;
 } 

 
 public void setMusteriKimlikNo( String musteriKimlikNo )
 {
 this.musteriKimlikNo = musteriKimlikNo;
 } 

 public String getMusteriSifre()
 {
 return musteriSifre;
 } 

 
 public void setMusteriSifre( String musteriSifre )
 {
 this.musteriSifre = musteriSifre;
 }

  
 
 
  public String getSilinecek_isbn()
 {
 return silinecek_isbn;
 } 

 
 public void setSilinecek_isbn( String silinecek_isbn )
 {
 this.silinecek_isbn = silinecek_isbn;
 } 
 
/* public void tckimlikkontrolu(getKimlikNo()){
     
         System.out.println("Hatali giris");
     
 }*/

  public String VeriCek1() throws SQLException {
        String girisKimlik = "";
        String girisSifre = "";
        
        if (dataSource == null) {
            throw new SQLException("Unable to obtain DataSource");
        }

        // obtain a connection from the connection pool
        Connection connection = dataSource.getConnection();

        // check whsether connection was successful
        if (connection == null) {
            throw new SQLException("Unable to connect to DataSource");
        }

        Statement komut = connection.createStatement();

        
        PreparedStatement ps = connection.prepareStatement("select kimlikno,sifre from musteriler where musteriler.kimlikno=?");
        ps.setString(1, getBulunacakTc());
        rowSet = new com.sun.rowset.CachedRowSetImpl();
        rowSet.populate(ps.executeQuery());

        if (rowSet.next()) {
            girisKimlik = rowSet.getString("kimlikno");
            girisSifre = rowSet.getString("sifre");
            
        }


        if (girisKimlik.equals(getBulunacakTc()) && girisSifre.equals(getBulunacakSifre())) {
            connection.close();
            return "musteriSayfasi?faces-redirect=true";
        } else {
            connection.close();
            return "index?faces-redirect=true";
        }

       
    }
  
  public String musteriMesajGonder() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("Unable to obtain DataSource");
        }

        // obtain a connection from the connection pool
        Connection connection = dataSource.getConnection();

        // check whether connection was successful
        if (connection == null) {
            throw new SQLException("Unable to connect to DataSource");
        }

        try {
            // create a PreparedStatement to insert a new address book entry
            PreparedStatement addEntry
                    = connection.prepareStatement("INSERT INTO MESAJ"  + "(uyarı)" + "VALUES(?)");

            addEntry.setString(1, getMusteriMesaj()); 
addEntry.executeUpdate();
            return "musteriSayfasi";
        } // end try
        finally {
            connection.close(); // return this connection to pool
        }

    }
 
 
 public String OzlukBilgileri(String deneme)throws SQLException{
     String girisKimlik = "";
        String girisSifre = "";
        if (dataSource == null) {
            throw new SQLException("Unable to obtain DataSource");
        }

        // obtain a connection from the connection pool
        Connection connection = dataSource.getConnection();

        // check whsether connection was successful
        if (connection == null) {
            throw new SQLException("Unable to connect to DataSource");
        }

        Statement komut = connection.createStatement();

        
        PreparedStatement ps = connection.prepareStatement("select kimlikno,ad,soyad,bolum,bakiye from musteriler where musteriler.kimlikno=?");
        ps.setString(1, getBulunacakTc());
        rowSet = new com.sun.rowset.CachedRowSetImpl();
        rowSet.populate(ps.executeQuery());
        
         if (rowSet.next()) {
            return rowSet.getString(deneme);
        }
         
             
          return "hatali giris";      
     
        
 }
 
 public int musteriBakiyeHesapla() throws SQLException {
   
     int ilkBakiye=0;
     int sonBakiye;
     
             
        if (dataSource == null) {
            throw new SQLException("Unable to obtain DataSource");
        }

        // obtain a connection from the connection pool
        Connection connection = dataSource.getConnection();

        // check whsether connection was successful
        if (connection == null) {
            throw new SQLException("Unable to connect to DataSource");
        }


        PreparedStatement ps = connection.prepareStatement("SELECT BAKIYE FROM MUSTERILER WHERE KIMLIKNO=?");
        ps.setString(1, getBulunacakTc());
        
         if (rowSet.next()) {
             ilkBakiye = rowSet.getInt("BAKIYE");
                        
        }
         
         
        
        ilkBakiye=ilkBakiye+Integer.parseInt(yuklenecekBakiye);
        
        return ilkBakiye;
     
 }
 
 public void musteriDolumYap()throws SQLException{
     
     
    
     
             
        if (dataSource == null) {
            throw new SQLException("Unable to obtain DataSource");
        }

        // obtain a connection from the connection pool
        Connection connection = dataSource.getConnection();

        // check whsether connection was successful
        if (connection == null) {
            throw new SQLException("Unable to connect to DataSource");
        }


                    
        int deger=musteriBakiyeHesapla();
        
        PreparedStatement ps = connection.prepareStatement("UPDATE MUSTERILER SET BAKIYE=? WHERE KIMLIKNO=?");
        ps.setInt(1, deger);
        ps.setString(2, getBulunacakTc());
        
        rowSet = new com.sun.rowset.CachedRowSetImpl();
        ps.executeUpdate();
        
        PreparedStatement ps1 = connection.prepareStatement("INSERT BAKIYEHAREKETI" + "(bakiye,kımlıkno)" +"VALUES(?,?)");
        
        ps1.setInt(1, Integer.parseInt(getYuklenecekBakiye()));
        ps1.setString(2, getMusteriKimlikNo());
        ps1.executeUpdate();
        
        
        
        }
 
 
 public void ekle() throws SQLException
 {
 // check whether dataSource was injected by the server
 if ( dataSource == null )
 throw new SQLException( "Unable to obtain DataSource" );

 // obtain a connection from the connection pool
 Connection connection = dataSource.getConnection();

 // check whether connection was successful
 if ( connection == null )
 throw new SQLException( "Unable to connect to DataSource" );

 try
 {
 // create a PreparedStatement to insert a new address book entry
 PreparedStatement addEntry =
 connection.prepareStatement( "INSERT INTO MUSTERILER" +
 "(Ad,soyad,bolum,kimlikno,sifre)" +
 "VALUES ( ?, ?, ?, ?, ? )" );

 // specify the PreparedStatement's arguments
 addEntry.setString( 1, getMusteriAd() );
 addEntry.setString( 2, getMusteriSoyad() );
 addEntry.setString( 3, getMusteriBolum() );
 addEntry.setString( 4, getMusteriKimlikNo() );
 addEntry.setString( 5, getMusteriSifre() );


addEntry.executeUpdate(); // insert the entry
  // go back to index.xhtml home page
 } // end try
 finally
 {
 connection.close(); // return this connection to pool
 } // end finally
 } 


 public ResultSet bul() throws SQLException
 {
 // check whether dataSource was injected by the server
 if ( dataSource == null )
 throw new SQLException( "Unable to obtain DataSource" );

 // obtain a connection from the connection pool
 Connection connection = dataSource.getConnection();

 // check whether connection was successful
 if ( connection == null )
 throw new SQLException( "Unable to connect to DataSource" );

 try
 {
 // create a PreparedStatement to insert a new address book entry
 PreparedStatement ps =
 connection.prepareStatement( "select kitaplar.ISBN,kitaplar.ad,yazarlar.ad as yazar_ad,yazarlar.soyad as yazar_soyad,"
         + "yazarlar.id,kitaplar.basim_yili, kitaplar.yayin_evi from kitaplar,yazarlar " +
         "where kitaplar.ad=? and kitaplar.yazar_id=yazarlar.id" );
 ps.setString( 1, getBulunacak_kitap() );
 rowSet = new com.sun.rowset.CachedRowSetImpl();
 rowSet.populate( ps.executeQuery() );
return rowSet;
 } // end try
 finally
 {
 connection.close(); // return this connection to pool
 } // end finally
 } 

 public ResultSet musteriMenuGoruntule() throws SQLException
 {
 // check whether dataSource was injected by the server
 if ( dataSource == null )
 throw new SQLException( "Unable to obtain DataSource" );

 // obtain a connection from the connection pool
 Connection connection = dataSource.getConnection();

 // check whether connection was successful
 if ( connection == null )
 throw new SQLException( "Unable to connect to DataSource" );

 try
 {
 // create a PreparedStatement to insert a new address book entry
 PreparedStatement ps =
 connection.prepareStatement( "select tarih, corba, anayemek, icecek, tatli from menu " );
 
 rowSet = new com.sun.rowset.CachedRowSetImpl();
 rowSet.populate( ps.executeQuery() );
return rowSet;
 } // end try
 finally
 {
 connection.close(); // return this connection to pool
 } // end finally
 } 


 
 
 public String sil() throws SQLException
 {
 // check whether dataSource was injected by the server
 if ( dataSource == null ){ 
      throw new SQLException( "Unable to obtain DataSource" );
 }

 // obtain a connection from the connection pool
 Connection connection = dataSource.getConnection();

 // check whether connection was successful
 if ( connection == null )
 throw new SQLException( "Unable to connect to DataSource" );

 try
 {
 // create a PreparedStatement to insert a new address book entry
 PreparedStatement myObject =
 connection.prepareStatement( "delete from kitaplar where isbn=?");

 // specify the PreparedStatement's arguments
 myObject.setInt( 1, Integer.parseInt(getSilinecek_isbn()) );
 
myObject.executeUpdate(); // insert the entry
 return "index"; // go back to index.xhtml page
 } // end try
 finally
 {
 connection.close(); // return this connection to pool
 } // end finally
 } 


 
 }


