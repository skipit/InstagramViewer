package com.codepath.instagramviewer;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by pavan on 5/8/15.
 */
public class CommentViewAdapter extends ArrayAdapter<InstagramPhotoComment> {

    private static class ViewHolder {
        ImageView ivCommentUserImage;
        TextView tvCommentUserName;
        TextView tvComment;
    }

    public CommentViewAdapter(Context context, int resource, List<InstagramPhotoComment> objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        InstagramPhotoComment comment = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null ) {
            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_comment_view,
                    parent,
                    false);
            viewHolder.ivCommentUserImage = (ImageView)convertView.findViewById(R.id.commentUserImage);
            viewHolder.tvCommentUserName = (TextView)convertView.findViewById(R.id.tvCommentUserName);
            viewHolder.tvComment = (TextView)convertView.findViewById(R.id.tvComment);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        Picasso.with(getContext())
                .load(comment.profilePictureUrl)
                .into(viewHolder.ivCommentUserImage);


        viewHolder.tvCommentUserName.setText("@" + comment.userName);
        viewHolder.tvComment.setText(Html.fromHtml(comment.comment));

        convertView.setBackgroundColor(Color.WHITE);
        if (position % 2 == 1) {
            convertView.setBackgroundColor(Color.LTGRAY);
        }
        return convertView;
    }


}
