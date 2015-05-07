package com.codepath.instagramviewer;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/**
 * Created by pavan on 5/4/15.
 */
public class PhotoViewAdapter extends ArrayAdapter<InstagramPhoto> {

    private static class ViewHolder {
        ImageView ivUserImage;
        ImageView ivPhoto;
        TextView tvCaption;
        TextView tvUserName;
        TextView tvLikes;
        TextView tvCreated;
    }

    public PhotoViewAdapter(Context context, int resource, List<InstagramPhoto> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        InstagramPhoto photo = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null ) {
            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_view,
                                                                    parent,
                                                                    false);
            viewHolder.ivPhoto = (ImageView)convertView.findViewById(R.id.ivPhoto);
            viewHolder.ivUserImage = (ImageView)convertView.findViewById(R.id.userImage);
            viewHolder.tvCaption = (TextView)convertView.findViewById(R.id.tvCaption);
            viewHolder.tvUserName = (TextView)convertView.findViewById(R.id.tvUserName);
            viewHolder.tvLikes = (TextView)convertView.findViewById(R.id.tvLikes);
            viewHolder.tvCreated = (TextView)convertView.findViewById(R.id.tvDate);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        Picasso.with(getContext())
                .load(photo.userImageUrl)
                .into(viewHolder.ivUserImage);

        Picasso.with(getContext()).load(photo.imageUrl).into(viewHolder.ivPhoto);

        viewHolder.tvCaption.setText(photo.caption);
        viewHolder.tvUserName.setText("@" + photo.userName);
        viewHolder.tvLikes.setText(String.valueOf(photo.likesCount));
        viewHolder.tvCreated.setText(DateUtils.getRelativeTimeSpanString(1000*photo.creationDate));
        return convertView;
    }
}
