package thess.tourism.creative.creativetourismthessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import thess.tourism.creative.creativetourismthessapp.DbFiles.Category;
import thess.tourism.creative.creativetourismthessapp.DbFiles.SqliteUtils;

public class CategoriesActivity extends AppCompatActivity {
    ListView categoriesListView;
    ImageButton nextBtn, backBtn;
    List<Category> categories;
    ArrayList<Integer> selectedCats = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_categories);
        categoriesListView = (ListView) findViewById(R.id.categories_list);
        categories = SqliteUtils.getDaoSession().getCategoryDao().loadAll();
        final CustomListCategoryAdapter customListAdapter = new CustomListCategoryAdapter(categories, this);
        categoriesListView.setAdapter(customListAdapter);
        TextView head = (TextView) findViewById(R.id.header);
        head.setTypeface(((BaseApp) getApplicationContext()).getFont());
        nextBtn = (ImageButton) findViewById(R.id.btn_next);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCats.clear();
                selectedCats = customListAdapter.getSelectedCategories();
                if (selectedCats.size() == 0) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_select_a_cat), Toast.LENGTH_LONG).show();
                    return;
                }

                Intent mIntent = new Intent(CategoriesActivity.this, NavModeSelectionActivity.class);
                mIntent.putIntegerArrayListExtra("sel_cats", selectedCats);
                startActivity(mIntent);

            }
        });
    }
}
