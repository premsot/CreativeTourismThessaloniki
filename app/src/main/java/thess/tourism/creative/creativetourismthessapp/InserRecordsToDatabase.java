package thess.tourism.creative.creativetourismthessapp;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import thess.tourism.creative.creativetourismthessapp.DbFiles.BikesParkingPois;
import thess.tourism.creative.creativetourismthessapp.DbFiles.Category;
import thess.tourism.creative.creativetourismthessapp.DbFiles.Creator;
import thess.tourism.creative.creativetourismthessapp.DbFiles.SqliteUtils;
import thess.tourism.creative.creativetourismthessapp.DbFiles.StartPoint;

/*mogrify -resize 90x114 -quality 100  *.png*/

public class InserRecordsToDatabase extends AsyncTask {
    String[] categoriesEn = {
            "MUSIC",
            "FASHION",
            "CRAFTS",
            "ARCHITECTURE & SPACE",
            "GRAPHIC ARTS",
            "TASTE",
            "VISUAL ARTS"
    };
    String[] categoriesEl = {
            "ΜΟΥΣΙΚΗ",
            "ΜΟΔΑ",
            "ΧΕΙΡΟΤΕΧΝΙΑ",
            "ΑΡΧΙΤΕΚΤΟΝΙΚΗ",
            "ΓΡΑΦΙΚΕΣ ΤΕΧΝΕΣ",
            "ΓΕΥΣΗ",
            "ΕΙΚΑΣΤΙΚΕΣ ΤΕΧΝΕΣ"
    };
    Context context;

    public InserRecordsToDatabase(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Object[] params) {

        for (int i = 0; i < categoriesEn.length; i++) {
            Category aCat = new Category();
            aCat.setId(null);
            aCat.setNameEn(categoriesEn[i]);
            aCat.setNameEl(categoriesEl[i]);
            SqliteUtils.getDaoSession().insert(aCat);
        }

        StartPoint startPoint1 = new StartPoint();
        startPoint1.setId(null);
        startPoint1.setNameEn("WHITE TOWER");
        startPoint1.setNameEl("ΛΕΥΚΟΣ ΠΥΡΓΟΣ");
        startPoint1.setLat(40.627308);
        startPoint1.setLongitude(22.948321);
        SqliteUtils.getDaoSession().insert(startPoint1);

        StartPoint startPoint2 = new StartPoint();
        startPoint2.setId(null);
        startPoint2.setNameEn("ATHONOS SQUARE");
        startPoint2.setNameEl("ΠΛΑΤΕΙΑ ΑΘΩΝΟΣ");
        startPoint2.setLat(40.63466);
        startPoint2.setLongitude(22.944905);
        SqliteUtils.getDaoSession().insert(startPoint2);

        StartPoint startPoint3 = new StartPoint();
        startPoint3.setId(null);
        startPoint3.setNameEn("ANCIENT AGORA");
        startPoint3.setNameEl("AΡΧΑΙΑ ΑΓΟΡΑ");
        startPoint3.setLat(40.637472);
        startPoint3.setLongitude(22.946146);
        SqliteUtils.getDaoSession().insert(startPoint3);

        storeCreators();
        storeBikes();
        return null;
    }

    private void storeBikes() {
        String bikesJs = readFileFromAssets("bikes_parking.txt");
        Gson gson = new Gson();
        JsBikes[] bkesPois = gson.fromJson(bikesJs, JsBikes[].class);
        System.out.println("Stringgg  aaa " + bikesJs);
        System.out.println("json aaaa " + bkesPois.length);
        for (JsBikes aBike : bkesPois) {
            BikesParkingPois bikesParkingPois = new BikesParkingPois();
            bikesParkingPois.setId(null);
            bikesParkingPois.setLatitude(aBike.lat);
            bikesParkingPois.setLongitude(aBike.lng);
            bikesParkingPois.setName(aBike.name);
            SqliteUtils.getDaoSession().insert(bikesParkingPois);
        }
    }

    private void storeCreators() {

        String creatorsJs = readFileFromAssets("creators.txt");

        Gson gson = new Gson();
        JsCreators crtrsJs = gson.fromJson(creatorsJs, JsCreators.class);

        for (int i = 0; i < crtrsJs.creators.size(); i++) {
            Crtr mCreator = crtrsJs.creators.get(i);
            Creator aCreator = new Creator();
            aCreator.setId(null);
            aCreator.setNameEl(mCreator.nameEl);
            aCreator.setNameEn(mCreator.nameEn);
            aCreator.setAddressEl(mCreator.addressEl);
            aCreator.setAddressEn(mCreator.addressEn);
            aCreator.setCategoryId(mCreator.cat);
            aCreator.setJobEl(mCreator.proEl);
            aCreator.setJobEn(mCreator.proEn);
            aCreator.setWebsiteAddress(mCreator.website);
            aCreator.setOrganization(mCreator.organization);
            aCreator.setMap_image(mCreator.image + "_map");
            aCreator.setDetails_image(mCreator.image + "_det");
            aCreator.setLat(mCreator.lat);
            aCreator.setStartingPointId(Long.valueOf(mCreator.startingPoi));
            aCreator.setLongitude(mCreator.longit);
            SqliteUtils.getDaoSession().insert(aCreator);
        }
    }

    private String readFileFromAssets(String filename) {
        BufferedReader reader = null;
        String stringFromFile = "";
        try {

            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(filename)));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                stringFromFile += mLine;
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return stringFromFile;
    }

    @Override
    protected void onPostExecute(Object o) {
        ((MainActivity) context).removePrgBar();
        super.onPostExecute(o);
    }
}
