package thess.tourism.creative.creativetourismthessapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import thess.tourism.creative.creativetourismthessapp.DbFiles.SqliteUtils;
import thess.tourism.creative.creativetourismthessapp.DbFiles.StartPoint;

public class OptionsActivity extends AppCompatActivity implements View.OnClickListener {
    int filter = -1;
    private ArrayList<Integer> selectedCategories;
    List<StartPoint> startPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_layout);
        Button selection1 = (Button) findViewById(R.id.selection1);
        Button selection2 = (Button) findViewById(R.id.selection2);
        Button selection3 = (Button) findViewById(R.id.selection3);
        selection1.setOnClickListener(this);
        selection2.setOnClickListener(this);
        selection3.setOnClickListener(this);
        selectedCategories = getIntent().getIntegerArrayListExtra("sel_categories");
        ImageButton backBtn = (ImageButton) findViewById(R.id.btn_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView head = (TextView) findViewById(R.id.header);
        head.setTypeface(((BaseApp) getApplicationContext()).getFont());
        selection1.setTypeface(((BaseApp) getApplicationContext()).getFont());
        selection2.setTypeface(((BaseApp) getApplicationContext()).getFont());
        selection3.setTypeface(((BaseApp) getApplicationContext()).getFont());

        Intent intent = getIntent();
        filter = intent.getIntExtra("filter", 1);

        if (filter == 1) {
            head.setText(getResources().getString(R.string.current_distance));
            selection1.setText("<200");
            selection2.setText("<500");
            selection3.setText("<1KM");

        } else {
            startPoints = SqliteUtils.getDaoSession().getStartPointDao().loadAll();
            head.setText(getResources().getString(R.string.starting_point));
            if (Utils.isPhoneLangGreek(getResources())) {
                selection1.setText(startPoints.get(0).getNameEl());
                selection2.setText(startPoints.get(1).getNameEl());
                selection3.setText(startPoints.get(2).getNameEl());
            } else {
                selection1.setText(startPoints.get(0).getNameEn());
                selection2.setText(startPoints.get(1).getNameEn());
                selection3.setText(startPoints.get(2).getNameEn());
            }
        }


    }

    @Override
    public void onClick(View v) {
        Intent mIntent = new Intent(OptionsActivity.this, MapsActivity.class);
        mIntent.putExtra("sel_categories", selectedCategories);
        mIntent.putExtra("filter", filter);

        switch (v.getId()) {
            case R.id.selection1:
                if (filter == 1) {
                    mIntent.putExtra("distance", 200);
                } else {
                    mIntent.putExtra("startingPoint", startPoints.get(0).getId());
                }
                startActivity(mIntent);
                break;
            case R.id.selection2:
                if (filter == 1) {
                    mIntent.putExtra("distance", 500);
                } else {
                    mIntent.putExtra("startingPoint", startPoints.get(1).getId());
                }
                startActivity(mIntent);
                break;
            case R.id.selection3:
                if (filter == 1) {
                    mIntent.putExtra("distance", 1000);
                } else {
                    mIntent.putExtra("startingPoint", startPoints.get(2).getId());
                }
                startActivity(mIntent);
                break;
        }
    }
}
