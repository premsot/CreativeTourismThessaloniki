package thess.tourism.creative.creativetourismthessapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import thess.tourism.creative.creativetourismthessapp.DbFiles.Category;

public class CustomListCategoryAdapter extends BaseAdapter {
    public List<Category> categories;
    public Context context;
    private LayoutInflater inflater = null;
    boolean[] selectedBtns;
    private ArrayList<Integer> selectedCategories = new ArrayList<>();

    public CustomListCategoryAdapter(List<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        selectedBtns = new boolean[categories.size()];
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        if (vi == null) {
            vi = inflater.inflate(R.layout.custom_list_cell, null);
            holder = new ViewHolder();
            holder.cellBtn = (Button) vi.findViewById(R.id.list_btn);
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }
        if (selectedBtns[position]) {
            holder.cellBtn.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.b_button_bg));
        } else {
            holder.cellBtn.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.a_button_bg));
        }
        holder.btnPos = position;
        if (Utils.isPhoneLangGreek(context.getResources()))
            holder.cellBtn.setText(categories.get(position).getNameEl());
        else
            holder.cellBtn.setText(categories.get(position).getNameEn());
        holder.cellBtn.setTypeface((((BaseApp) context.getApplicationContext()).getFont()));
        holder.cellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedBtns[position]) {
                    holder.cellBtn.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.a_button_bg));
                } else {
                    holder.cellBtn.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.b_button_bg));
                }
                selectedBtns[position] = !selectedBtns[position];
            }
        });
        return vi;
    }

    public ArrayList<Integer> getSelectedCategories() {
        selectedCategories.clear();
        for (int i = 0; i < selectedBtns.length; i++) {
            if (selectedBtns[i]) {
                selectedCategories.add(categories.get(i).getId().intValue());
            }
        }
        return selectedCategories;
    }

    static class ViewHolder {
        Button cellBtn;
        int btnPos;
    }
}
