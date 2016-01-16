package thess.tourism.creative.creativetourismthessapp;

import com.google.gson.annotations.SerializedName;

public class Crtr {
    @SerializedName("nameEL")
    public String nameEl;
    @SerializedName("nameEN")
    public String nameEn;
    @SerializedName("organization")
    public String organization;

    @SerializedName("professionEL")
    public String proEl;

    @SerializedName("professionEN")
    public String proEn;

    @SerializedName("website")
    public String website;

    @SerializedName("addressEN")
    public String addressEn;


    @SerializedName("addressEL")
    public String addressEl;

    @SerializedName("image")
    public String image;

    @SerializedName("startingPoint")
    public int startingPoi;
    @SerializedName("category")
    public long cat;

    @SerializedName("lat")
    public double lat;

    @SerializedName("longit")
    public double longit;
}
