/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.Convert;

@ManagedBean(name = "personelBean")
@SessionScoped
public class PersonelBean {

    private String personelAd;
    private String personelSoyad;
    private String personelDepartman;
    private String personelKimlikNo;
    private String personelSifre;
    private String personelID;
    private String bulunacakTc;
    private String bulunacakSifre;
    private String personelMesaj;
    private String personelCalismaSaati;
    private String personelCalismaSaatiGuncellenecekTc;
    private String personelSaatlikMaas;
    private String yemekTarihi;
    private String yemekCorba;
    private String yemekAna;
    private String yemekIcecek;
    private String yemekTatli;
     private String yemekUcret;

    public void setYemekUcret(String yemekUcret) {
        this.yemekUcret = yemekUcret;
    }

    public String getYemekUcret() {
        return yemekUcret;
    }

    public void setYemekTarihi(String yemekTarihi) {
        this.yemekTarihi = yemekTarihi;
    }

    public void setYemekCorba(String yemekCorba) {
        this.yemekCorba = yemekCorba;
    }

    public void setYemekAna(String yemekAna) {
        this.yemekAna = yemekAna;
    }

    public void setYemekIcecek(String yemekIcecek) {
        this.yemekIcecek = yemekIcecek;
    }

    public void setYemekTatli(String yemekTatli) {
        this.yemekTatli = yemekTatli;
    }

    public String getYemekTarihi() {
        return yemekTarihi;
    }

    public String getYemekCorba() {
        return yemekCorba;
    }

    public String getYemekAna() {
        return yemekAna;
    }

    public String getYemekIcecek() {
        return yemekIcecek;
    }

    public String getYemekTatli() {
        return yemekTatli;
    }

    CachedRowSet rowSet = null;
    private String silinecek_isbn;
    /**
     * Creates a new instance of KitapBean
     */
//@Resource(name="jdbc/addressbook")
    DataSource dataSource;

    public PersonelBean() {
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("jdbc/addressbook");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public String getPersonelAd() {
        return personelAd;
    }

    public void setPersonelAd(String personelAd) {
        this.personelAd = personelAd;
    }

    public String getPersonelSaatlikMaas() {
        return personelSaatlikMaas;
    }

    public void setPersonelSaatlikMaas(String personelSaatlikMaas) {
        this.personelSaatlikMaas = personelSaatlikMaas;
    }

    public String getPersonelCalismaSaatiGuncellenecekTc() {
        return personelCalismaSaatiGuncellenecekTc;
    }

    public void setPersonelCalismaSaatiGuncellenecekTc(String personelCalismaSaatiGuncellenecekTc) {
        this.personelCalismaSaatiGuncellenecekTc = personelCalismaSaatiGuncellenecekTc;
    }

    public String getPersonelCalismaSaati() {
        return personelCalismaSaati;
    }

    public void setPersonelCalismaSaati(String personelCalismaSaati) {
        this.personelCalismaSaati = personelCalismaSaati;
    }

    public String getPersonelMesaj() {
        return personelMesaj;
    }

    public void setPersonelMesaj(String personelMesaj) {
        this.personelMesaj = personelMesaj;
    }

    public void setBulunacakSifre(String bulunacakSifre) {
        this.bulunacakSifre = bulunacakSifre;
    }

    public String getBulunacakSifre() {
        return bulunacakSifre;
    }

    public void setBulunacakTc(String bulunacakTc) {
        this.bulunacakTc = bulunacakTc;
    }

    public String getBulunacakTc() {
        return bulunacakTc;
    }

    public void setPersonelID(String personelID) {
        this.personelID = personelID;
    }

    public String getPersonelID() {
        return personelID;
    }

    public String getPersonelSoyad() {
        return personelSoyad;
    }

    public void setPersonelSoyad(String personelSoyad) {
        this.personelSoyad = personelSoyad;
    }

    public String getPersonelDepartman() {
        return personelDepartman;
    }

    public void setPersonelDepartman(String personelDepartman) {
        this.personelDepartman = personelDepartman;
    }

    public String getPersonelKimlikNo() {
        return personelKimlikNo;
    }

    public void setPersonelKimlikNo(String personelKimlikNo) {
        this.personelKimlikNo = personelKimlikNo;
    }

    public String getPersonelSifre() {
        return personelSifre;
    }

    public void setPersonelSifre(String personelSifre) {
        this.personelSifre = personelSifre;
    }

    public String getSilinecek_isbn() {
        return silinecek_isbn;
    }

    public void setSilinecek_isbn(String silinecek_isbn) {
        this.silinecek_isbn = silinecek_isbn;
    }

    /* public void tckimlikkontrolu(getKimlikNo()){
     
         System.out.println("Hatali giris");
     
 }*/
    public String VeriCek() throws SQLException {
        String girisKimlik = "";
        String girisSifre = "";
        String girisSifati="";
        
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

        ResultSet rs = komut.executeQuery("select kimlikno,sifre,sıfat from personel where personel.kimlikno='" + getBulunacakTc() + "'");
        PreparedStatement ps = connection.prepareStatement("select kimlikno,sifre,sıfat from personel where personel.kimlikno=?");
        ps.setString(1, getBulunacakTc());
        rowSet = new com.sun.rowset.CachedRowSetImpl();
        rowSet.populate(ps.executeQuery());

        if (rowSet.next()) {
            girisKimlik = rowSet.getString("kimlikno");
            girisSifre = rowSet.getString("sifre");
            girisSifati = rowSet.getString("sifat");
        }

        if (girisSifati.equals("yonetici")) {
            if (girisKimlik.equals(getBulunacakTc()) && girisSifre.equals(getBulunacakSifre())) {
                connection.close();
                return "yoneticiSayfasi?faces-redirect=true";
            } else {
                connection.close();
                return "index?faces-redirect=true";
            }

        }

        if (girisKimlik.equals(getBulunacakTc()) && girisSifre.equals(getBulunacakSifre())) {
            connection.close();
            return "personelSayfasi?faces-redirect=true";
        } else {
            connection.close();
            return "index?faces-redirect=true";
        }

        /*if(getBulunacak_kitap()== deneme){
    connection.close();
    return "basariliLog?faces-redirect=true";
}
    
else{
    connection.close();
    return "hataliLog?faces-redirect=true";
}
         */
 /*
 kitapAdi=rs.getString("ad");;
         */
        //return "lksdjfhldsjkf";
        /*if(getBulunacak_kitap()==kitapAdi){
     connection.close();
     return "index?faces-redirect=true";
     
 }
 else{
     connection.close();
     return "kitapekle?faces-redirect=true";
                 }
         */
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

        
        PreparedStatement ps = connection.prepareStatement("select kimlikno,ad,soyad,departman from personel where personel.kimlikno=?");
        ps.setString(1, getBulunacakTc());
        rowSet = new com.sun.rowset.CachedRowSetImpl();
        rowSet.populate(ps.executeQuery());
        
         if (rowSet.next()) {
            return rowSet.getString(deneme);
        }
         
             
          return "hatali giris";      
     
        
 }

    public void yoneticiPKaydiSil() throws SQLException {
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
            PreparedStatement myObject
                    = connection.prepareStatement("delete from personel where kimlikno=?");

            // specify the PreparedStatement's arguments
            myObject.setString(1, personelCalismaSaatiGuncellenecekTc);

            myObject.executeUpdate(); // insert the entry

        } // end try
        finally {
            connection.close(); // return this connection to pool
        } // end finally

    }

    public String personelMesajGonder() throws SQLException {
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
                    = connection.prepareStatement("UPDATE PERSONEL SET MESAJ='" + getPersonelMesaj() + "' WHERE KIMLIKNO='" + getBulunacakTc() + "' ");

            addEntry.executeUpdate(); // insert the entry
            return "personelSayfasi";
        } // end try
        finally {
            connection.close(); // return this connection to pool
        }

    }

    public ResultSet yoneticiMesajCek() throws SQLException {

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
            PreparedStatement ps
                    = connection.prepareStatement("select AD, SOYAD, KIMLIKNO, MESAJ FROM PERSONEL");

            rowSet = new com.sun.rowset.CachedRowSetImpl();
            rowSet.populate(ps.executeQuery());
            return rowSet;
        } finally {
            connection.close(); // return this connection to pool
        }
    }
    
    public ResultSet yoneticiOneriCek() throws SQLException {

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
            PreparedStatement ps
                    = connection.prepareStatement("select UYARI FROM MESAJ");

            rowSet = new com.sun.rowset.CachedRowSetImpl();
            rowSet.populate(ps.executeQuery());
            return rowSet;
        } finally {
            connection.close(); // return this connection to pool
        }
    }

    public ResultSet yoneticiSaatCek() throws SQLException {

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
            PreparedStatement ps
                    = connection.prepareStatement("select AD, SOYAD, KIMLIKNO, HAFTALIKCALISMASAATI, SAATLIKMAAS FROM PERSONEL");

            rowSet = new com.sun.rowset.CachedRowSetImpl();
            rowSet.populate(ps.executeQuery());
            return rowSet;
        } finally {
            connection.close(); // return this connection to pool
        }
    }

    public void yoneticiSaatGuncelle() throws SQLException {

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
            PreparedStatement ps
                    = connection.prepareStatement("UPDATE PERSONEL SET HAFTALIKCALISMASAATI=? WHERE KIMLIKNO=?");
            ps.setString(1, personelCalismaSaati);
            ps.setString(2, personelCalismaSaatiGuncellenecekTc);

            PreparedStatement ps2 = connection.prepareStatement("UPDATE PERSONEL SET SAATLIKMAAS=? WHERE KIMLIKNO=?");
            ps2.setString(1, personelSaatlikMaas);
            ps2.setString(2, personelCalismaSaatiGuncellenecekTc);

            ps.executeUpdate();
            ps2.executeUpdate();
        } finally {
            connection.close(); // return this connection to pool
            personelCalismaSaati = "0";
            personelSaatlikMaas = "0";
        }
    }

    public void ekle() throws SQLException {
        // check whether dataSource was injected by the server
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
                    = connection.prepareStatement("INSERT INTO PERSONEL"
                            + "(Ad,soyad,DEPARTMAN,kimlikno,sifre,ID)"
                            + "VALUES ( ?, ?, ?, ?, ?, ? )");

            // specify the PreparedStatement's arguments
            addEntry.setString(1, getPersonelAd());
            addEntry.setString(2, getPersonelSoyad());
            addEntry.setString(3, getPersonelDepartman());
            addEntry.setString(4, getPersonelKimlikNo());
            addEntry.setString(5, getPersonelSifre());
            addEntry.setInt(6, Integer.parseInt(getPersonelID()));

            addEntry.executeUpdate(); // insert the entry
            //return "index"; // go back to index.xhtml home page
        } // end try
        finally {
            connection.close(); // return this connection to pool
        } // end finally
    }

    public void yemekAyarla() throws SQLException, ParseException {
        // check whether dataSource was injected by the server
        if (dataSource == null) {
            throw new SQLException("Unable to obtain DataSource");
        }

        // obtain a connection from the connection pool
        Connection connection = dataSource.getConnection();

        // check whether connection was successful
        if (connection == null) {
            throw new SQLException("Unable to connect to DataSource");
        }
          SimpleDateFormat tarihFormati2 = new SimpleDateFormat("dd MMMM yyyy");
           boolean deneme;
        
        try {
            // create a PreparedStatement to insert a new address book entry
            PreparedStatement addEntry
                    = connection.prepareStatement("INSERT INTO MENU"
                            + "(TARIH,CORBA,ANAYEMEK,ICECEK,TATLI,UCRET)"
                            + "VALUES ( ?, ?, ?, ?, ?, ? )");

            // specify the PreparedStatement's arguments
            
            
           
            addEntry.setDate(1, tipCevir(getYemekTarihi()));
         
            addEntry.setString(2, getYemekCorba());
            addEntry.setString(3, getYemekAna());
            addEntry.setString(4, getYemekIcecek());
            addEntry.setString(5, getYemekTatli());
            addEntry.setString(6, getYemekUcret());
            
            addEntry.executeUpdate(); // insert the entry
            //return "index"; // go back to index.xhtml home page
        } // end try
        finally {
            connection.close(); // return this connection to pool
        } // end finally
    }
     public Date tipCevir(String tarih) throws ParseException{
 SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
 java.util.Date date = sdf1.parse(tarih);
 java.sql.Date sqlDate = new java.sql.Date(date.getTime());
 return sqlDate;
 }

    public ResultSet bul() throws SQLException {
        // check whether dataSource was injected by the server
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
            PreparedStatement ps
                    = connection.prepareStatement("select kitaplar.ISBN,kitaplar.ad,yazarlar.ad as yazar_ad,yazarlar.soyad as yazar_soyad,"
                            + "yazarlar.id,kitaplar.basim_yili, kitaplar.yayin_evi from kitaplar,yazarlar "
                            + "where kitaplar.ad=? and kitaplar.yazar_id=yazarlar.id");
            ps.setString(1, getPersonelID());
            rowSet = new com.sun.rowset.CachedRowSetImpl();
            rowSet.populate(ps.executeQuery());
            return rowSet;
        } // end try
        finally {
            connection.close(); // return this connection to pool
        } // end finally
    }
    
     public ResultSet personelCalismaBilgileriCek() throws SQLException {
        // check whether dataSource was injected by the server
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
            PreparedStatement ps
                    = connection.prepareStatement("select personel.kimlikno,personel.ad, personel.soyad, personel.haftalikcalismasaati, personel.saatlikmaas from personel "
                            + "where personel.kimlikno=? ");
            ps.setString(1, getBulunacakTc());
            rowSet = new com.sun.rowset.CachedRowSetImpl();
            rowSet.populate(ps.executeQuery());
            return rowSet;
        } // end try
        finally {
            connection.close(); // return this connection to pool
        } // end finally
    }

    /*
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

     */

    private boolean after(Date tipCevir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
