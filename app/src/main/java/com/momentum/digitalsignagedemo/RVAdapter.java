package com.momentum.digitalsignagedemo;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Justin on 10/11/2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.AdViewHolder> {
    ArrayList<Ad> ads;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class AdViewHolder extends RecyclerView.ViewHolder {

        private CardView cv;
        TextView adTitle;
        TextView adDetails;
        ImageView adPhoto;

        AdViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);

            adTitle = (TextView)itemView.findViewById(R.id.ad_title);
            adDetails = (TextView)itemView.findViewById(R.id.ad_description);
            adPhoto = (ImageView)itemView.findViewById(R.id.ad_photo);
        }
    }

    RVAdapter(ArrayList<Ad> ads){
        this.ads = ads;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public AdViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_activity, viewGroup, false);
        AdViewHolder pvh = new AdViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final AdViewHolder adViewHolder, int i) {
        adViewHolder.adTitle.setText(ads.get(i).getTitle());
        adViewHolder.adDetails.setText(ads.get(i).getDetails());
        //adViewHolder.adPhoto.setImageResource(ads.get(i).getThumbnail());
        if (adViewHolder.adPhoto != null) {
            new ImageDownloaderTask(adViewHolder.adPhoto).execute(ads.get(i).getThumbnail());
        }
        //adViewHolder.adPhoto.setImageResource(R.drawable.ic_grey_gums);
        adViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), DetailedAdDisplayActivity.class);
                i.putParcelableArrayListExtra("adsList", ads);
                i.putExtra("TitleKey", adViewHolder.adTitle.getText().toString());
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ads.size();
    }

    public void update(ArrayList<Ad> ads) {
        this.ads.clear();
        this.ads.addAll(ads);
        notifyDataSetChanged();
    }
}
