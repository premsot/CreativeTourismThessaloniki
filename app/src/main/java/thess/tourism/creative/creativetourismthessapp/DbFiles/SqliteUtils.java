package thess.tourism.creative.creativetourismthessapp.DbFiles;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class SqliteUtils {
    static DaoSession daoSession;

    public static void initDataBase(Context appContext) {

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(appContext, "CreativeTourism-db.sqlite", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
