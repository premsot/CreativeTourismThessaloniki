package thess.tourism.creative.creativetourismthessapp.DbFiles;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table BIKES_PARKING_POIS.
 */
public class BikesParkingPois {

    private Long id;
    private Double latitude;
    private Double longitude;
    private String name;

    public BikesParkingPois() {
    }

    public BikesParkingPois(Long id) {
        this.id = id;
    }

    public BikesParkingPois(Long id, Double latitude, Double longitude, String name) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
