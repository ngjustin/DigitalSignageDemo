package com.momentum.digitalsignagedemo;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Justin on 10/29/2016.
 */

public class DetailedAdDisplayActivity extends Activity {
    private ArrayList<Ad> ads;
    TextView title;
    TextView details;
    TextView description;
    TextView link;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_ad_display);

        ads = getIntent().getParcelableArrayListExtra("adsList");

        title = (TextView)findViewById(R.id.ad_title);
        details = (TextView) findViewById(R.id.ad_details);
        description = (TextView) findViewById(R.id.ad_description);
        link = (TextView) findViewById(R.id.ad_link);
        image = (ImageView) findViewById(R.id.photo);

        updateText();
    }

    public void updateText() {
        String intentTitle = getIntent().getExtras().getString("TitleKey");
        if (!ads.isEmpty()) {
            for (Ad d : ads) {
                if (d.getTitle().toLowerCase().equals(intentTitle.toLowerCase())) {
                    new ImageDownloaderTask(image).execute(d.getPicture());
                    title.setText(d.getTitle());
                    details.setText(d.getDetails());
                    description.setText(d.getDescription());
                    link.setClickable(true);
                    link.setMovementMethod(LinkMovementMethod.getInstance());
                    String href = "<a href ='" + d.getLink() + "'>" + d.getLink() + "</a>";
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        link.setText(Html.fromHtml(href, Html.FROM_HTML_MODE_LEGACY));
                    } else {
                        link.setText(Html.fromHtml(href));
                    }
                }
            }
        }
    }
}
