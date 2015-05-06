package com.codepath.instagramviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by pavan on 5/4/15.
 */
public class PhotoOverviewAdapter extends ArrayAdapter<InstagramPhoto> {
    public PhotoOverviewAdapter(Context context, int resource, List<InstagramPhoto> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        InstagramPhoto photo = getItem(position);

        if (convertView == null ) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_view,
                                                                    parent,
                                                                    false);
        }

        ImageView ivPhoto =  (ImageView)convertView.findViewById(R.id.ivPhoto);
        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.thumbnailUrl).into(ivPhoto);

        TextView tvCaption = (TextView)convertView.findViewById(R.id.tvCaption);
        tvCaption.setText(photo.caption);

        return convertView;
    }
}
