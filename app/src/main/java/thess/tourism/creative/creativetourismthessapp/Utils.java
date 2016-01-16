package thess.tourism.creative.creativetourismthessapp;

import android.content.res.Resources;


public class Utils {
    public static String getPhoneLanguage(Resources resources) {
        return resources.getConfiguration().locale.toString().substring(0, 2);
    }

    public static boolean isPhoneLangGreek(Resources resources) {
        return getPhoneLanguage(resources).equals("el");
    }
}
