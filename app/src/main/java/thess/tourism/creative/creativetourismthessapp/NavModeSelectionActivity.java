package thess.tourism.creative.creativetourismthessapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;


public class NavModeSelectionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_mode_layout);
        TextView head = (TextView) findViewById(R.id.header);
        Button currentDstnceBtn = (Button) findViewById(R.id.current_distance_btn);
        Button notNowBtn = (Button) findViewById(R.id.not_now_point);
        Button startinPoi = (Button) findViewById(R.id.starting_poi_btn);
        ImageButton backBtn = (ImageButton) findViewById(R.id.btn_back);
        head.setTypeface(((BaseApp) getApplicationContext()).getFont());

        currentDstnceBtn.setTypeface(((BaseApp) getApplicationContext()).getFont());
        notNowBtn.setTypeface(((BaseApp) getApplicationContext()).getFont());
        startinPoi.setTypeface(((BaseApp) getApplicationContext()).getFont());

        Intent intent = getIntent();
        final ArrayList<Integer> selectedCats = intent.getIntegerArrayListExtra("sel_cats");

        currentDstnceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(NavModeSelectionActivity.this, OptionsActivity.class);
                mIntent.putExtra("filter", 1);
                mIntent.putExtra("sel_categories", selectedCats);
                startActivity(mIntent);
            }
        });
        startinPoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(NavModeSelectionActivity.this, OptionsActivity.class);
                mIntent.putExtra("filter", 2);
                mIntent.putExtra("sel_categories", selectedCats);
                startActivity(mIntent);
            }
        });
        notNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(NavModeSelectionActivity.this, MapsActivity.class);
                mIntent.putExtra("showAll", true);
                mIntent.putExtra("sel_categories", selectedCats);
                startActivity(mIntent);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
