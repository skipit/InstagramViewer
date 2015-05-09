package com.codepath.instagramviewer;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class PhotoCommentActivity extends ActionBarActivity {

    private InstagramPhoto photo;
    private CommentViewAdapter viewComment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_comment);

        photo = (InstagramPhoto) getIntent().getSerializableExtra("photo");
        setupListView();
    }

    public void setupListView() {
        // Get the Handle to the Photo-Overview Adapter
        viewComment = new CommentViewAdapter(this, R.layout.list_comment_view, photo.comments);
        // Set the adapter for the ListView
        ListView lvPhotos = (ListView)findViewById(R.id.lvComments);
        lvPhotos.setAdapter(viewComment);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo_comment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
