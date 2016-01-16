package thess.tourism.creative.creativetourismthessapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import thess.tourism.creative.creativetourismthessapp.DbFiles.Creator;
import thess.tourism.creative.creativetourismthessapp.DbFiles.SqliteUtils;

public class DetailsActivity extends Activity {
    Long creatorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creator_details_layout);
        creatorId = getIntent().getLongExtra("creator", -1);
        final Creator mainCreator = SqliteUtils.getDaoSession().getCreatorDao().load(creatorId);
        ImageView avatarImg = (ImageView) findViewById(R.id.avatar);
        avatarImg.setImageDrawable(getResources().getDrawable(getCreatorImg(mainCreator.getDetails_image())));
        TextView name = (TextView) findViewById(R.id.det_name);
        name.setTypeface(((BaseApp) getApplicationContext()).getFont());
        TextView organization = (TextView) findViewById(R.id.organization);
        TextView address = (TextView) findViewById(R.id.address);
        TextView prf = (TextView) findViewById(R.id.prfsion);
        TextView wbsite = (TextView) findViewById(R.id.website);

        name.setTypeface(((BaseApp) getApplicationContext()).getFont());
        organization.setTypeface(((BaseApp) getApplicationContext()).getFont());
        address.setTypeface(((BaseApp) getApplicationContext()).getFont());
        prf.setTypeface(((BaseApp) getApplicationContext()).getFont());
        wbsite.setTypeface(((BaseApp) getApplicationContext()).getFont());

        ImageButton backBtn = (ImageButton) findViewById(R.id.btn_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        organization.setText(mainCreator.getOrganization());
        wbsite.setText(mainCreator.getWebsiteAddress());
        if (!Utils.isPhoneLangGreek(getResources())) {
            name.setText(mainCreator.getNameEn());
            address.setText(mainCreator.getAddressEn());
            prf.setText(mainCreator.getJobEn());
        } else {
            name.setText(mainCreator.getNameEl());
            address.setText(mainCreator.getAddressEl());
            prf.setText(mainCreator.getJobEl());
        }
        wbsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent;
                if (mainCreator.getWebsiteAddress().startsWith("http"))
                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mainCreator.getWebsiteAddress()));
                else {
                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + mainCreator.getWebsiteAddress()));
                }
                startActivity(browserIntent);
            }
        });
    }


    private int getCreatorImg(String image) {
        String uri = "drawable/" + image;
        // int imageResource = R.drawable.icon;
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());

        return imageResource;
    }
}
