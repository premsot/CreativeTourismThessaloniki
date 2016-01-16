package thess.tourism.creative.creativetourismthessapp.DbFiles;

import java.io.File;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;


public class DbGenerator {
    public static void main(String[] args) throws Exception {

        int mDatabaseVersion = 1;

        Schema mSchema = new Schema(mDatabaseVersion, "thess.tourism.creative.creativetourismthessapp.DbFiles");

        addCategoriesTable(mSchema);

        new DaoGenerator().generateAll(mSchema, System.getProperty("user.dir") + File.separator + "app" + File.separator + "src" + File.separator + "main" + File.separator + "java");
    }

    private static void addCategoriesTable(Schema mSchema) {
        Entity category = mSchema.addEntity("Category");
        category.addIdProperty().primaryKey().autoincrement();
        category.addStringProperty("NameEn");
        category.addStringProperty("NameEl");


        Entity startPoints = mSchema.addEntity("StartPoint");
        startPoints.addIdProperty().primaryKey().autoincrement();
        startPoints.addStringProperty("NameEn");
        startPoints.addStringProperty("NameEl");
        startPoints.addDoubleProperty("lat");
        startPoints.addDoubleProperty("longitude");


        Entity creators = mSchema.addEntity("Creator");
        creators.addIdProperty().primaryKey().autoincrement();
        creators.addStringProperty("NameEn");
        creators.addStringProperty("NameEl");
        creators.addStringProperty("Organization");
        creators.addStringProperty("JobEn");
        creators.addStringProperty("JobEl");
        creators.addStringProperty("AddressEn");
        creators.addStringProperty("AddressEl");
        creators.addStringProperty("websiteAddress");
        creators.addDoubleProperty("lat");
        creators.addDoubleProperty("longitude");
        creators.addLongProperty("categoryId");
        creators.addLongProperty("startingPointId");
        creators.addStringProperty("map_image");
        creators.addStringProperty("details_image");

        Entity bikes = mSchema.addEntity("BikesParkingPois");
        bikes.addIdProperty().primaryKey().autoincrement();
        bikes.addDoubleProperty("latitude");
        bikes.addDoubleProperty("longitude");
        bikes.addStringProperty("name");

    }


}
