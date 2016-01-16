package thess.tourism.creative.creativetourismthessapp;

import android.app.Application;
import android.graphics.Typeface;

import thess.tourism.creative.creativetourismthessapp.DbFiles.SqliteUtils;

/**
 * Created by antonislatas on 06/01/16.
 */
public class BaseApp extends Application {
    public Typeface getFont() {
        return font;
    }

    public void setFont(Typeface font) {
        this.font = font;
    }

    Typeface font;

    @Override
    public void onCreate() {
        super.onCreate();
        font = Typeface.createFromAsset(getApplicationContext().getAssets(),
                "m_typeface.ttf");

        SqliteUtils.initDataBase(getApplicationContext());

    }
}
