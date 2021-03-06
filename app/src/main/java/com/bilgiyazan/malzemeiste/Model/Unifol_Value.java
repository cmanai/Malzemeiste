package com.bilgiyazan.malzemeiste.Model;


/**
 * Created by Toshiba on 26/12/2016.
 */

public class Unifol_Value {


    private String Model;
    private String Kod;
    private String Renk;
    private String Miktari;
    private String Fiyat;
    private String URL;
    private String Description;
    private String Rate;
    private String Share_Link;


    public Unifol_Value(String Model, String Kod, String Renk, String Miktari, String Fiyat, String URL, String Description, String Rate, String Share_Link) {

        this.Model = Model;
        this.Kod = Kod;
        this.Renk = Renk;
        this.Miktari = Miktari;
        this.Fiyat = Fiyat;
        this.URL = URL;
        this.Description = Description;
        this.Rate = Rate;
        this.Share_Link = Share_Link;


    }

    public Unifol_Value() {


    }

    public String getShare_Link() {
        return Share_Link;
    }

    public void setShare_Link(String share_Link) {
        Share_Link = share_Link;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getRenk() {
        return Renk;
    }

    public void setRenk(String renk) {
        Renk = renk;
    }

    public String getMiktari() {
        return Miktari;
    }

    public void setMiktari(String miktari) {
        Miktari = miktari;
    }


    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getKod() {
        return Kod;
    }

    public void setKod(String kod) {
        Kod = kod;
    }

    public String getFiyat() {
        return Fiyat;
    }

    public void setFiyat(String fiyat) {
        Fiyat = fiyat;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
