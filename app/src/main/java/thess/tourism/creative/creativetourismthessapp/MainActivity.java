package thess.tourism.creative.creativetourismthessapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
    Button findCreativesBtn;
    SharedPreferences sprefs;
    ProgressBar prgBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prgBar = (ProgressBar) findViewById(R.id.prg_bar);
        prgBar.setVisibility(View.GONE);
        findCreativesBtn = (Button) findViewById(R.id.find_creatives_btn);
        findCreativesBtn.setTypeface(((BaseApp) getApplicationContext()).getFont());
        sprefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!sprefs.getBoolean("isDataStored", false)) {
            prgBar.setVisibility(View.VISIBLE);
            new InserRecordsToDatabase(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        } else {
            findCreativesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(MainActivity.this, CategoriesActivity.class);
                    startActivity(mIntent);
                }
            });
        }
    }


    public void removePrgBar() {
        SharedPreferences.Editor editor = sprefs.edit();
        editor.putBoolean("isDataStored", true);
        editor.commit();
        prgBar.setVisibility(View.GONE);
        findCreativesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MainActivity.this, CategoriesActivity.class);
                startActivity(mIntent);
            }
        });
    }
}
