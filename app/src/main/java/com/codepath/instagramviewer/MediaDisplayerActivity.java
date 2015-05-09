package com.codepath.instagramviewer;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class MediaDisplayerActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private SwipeRefreshLayout swipeContainer;

    public static final String CLIENT_ID = "5e4bb8b442144e2cad975512543ecdb8";
    private ArrayList<InstagramPhoto> photos;
    private PhotoViewAdapter viewPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_displayer);

        // Initialize Photos ArrayList
        photos = new ArrayList<InstagramPhoto>();

        setupSwipeContainer();
        setupListView();
        fetchPopularPhotos();
     }

    public void setupSwipeContainer() {
        //// Set up the Swipe Container
        //Get the Swipe Container
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        //Set the Listener
        swipeContainer.setOnRefreshListener(this);
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public void setupListView() {
        // Get the Handle to the Photo-Overview Adapter
        viewPhoto = new PhotoViewAdapter(this, R.layout.list_item_view, photos);
        // Set the adapter for the ListView
        ListView lvPhotos = (ListView)findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(viewPhoto);

        //-- Setup the OnClick Listener
        lvPhotos.setOnItemClickListener( this );
    }

    public void fetchPopularPhotos() {
        String url = "https://api.instagram.com/v1/media/popular?client_id="+CLIENT_ID;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler() {

                    public void onSuccess(int statusCode, org.apache.http.Header[] headers, JSONObject response ) {
                        Log.i("DEBUG", response.toString());
                        JSONArray photosJSON  = null;

                        try {
                            photosJSON = response.getJSONArray("data");

                            for (int i = 0; i < photosJSON.length(); i++ ) {
                                try {
                                    JSONObject photoJSON = photosJSON.getJSONObject(i);
                                    InstagramPhoto photo = new InstagramPhoto();
                                    Log.i("DEBUG", "Got Infor For Photo: "+i);
                                    photo.userName = photoJSON.getJSONObject("user").getString("username");
                                    photo.userImageUrl = photoJSON.getJSONObject("user").getString("profile_picture");
                                    photo.caption = photoJSON.getJSONObject("caption").getString("text");
                                    photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                                    photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                                    photo.thumbnailUrl = photoJSON.getJSONObject("images").getJSONObject("thumbnail").getString("url");
                                    photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
                                    photo.creationDate = photoJSON.getLong("created_time");

                                    photo.comments = null;

                                    try {
                                        JSONArray commentsJSON = photoJSON.getJSONObject("comments").getJSONArray("data");
                                        photo.comments = new ArrayList<InstagramPhotoComment>();
                                        for (int j = 0; j < commentsJSON.length(); j++) {
                                            JSONObject commentJSON = commentsJSON.getJSONObject(j);
                                            InstagramPhotoComment comment = new InstagramPhotoComment();
                                            comment.userName = commentJSON.getJSONObject("from").getString("username");
                                            comment.comment = commentJSON.getString("text");
                                            comment.profilePictureUrl = commentJSON.getJSONObject("from").getString("profile_picture");
                                            photo.comments.add(comment);
                                        }
                                    } catch (JSONException e ) {
                                        Log.i("DEBUG", "Cannot process Comments for Picture " + i+ ":" + e.toString());
                                    }

                                    photos.add(photo);
                                } catch (JSONException e) {
                                    Log.i("DEBUG", "Cannot insert Picture "+i+":"+e.toString());
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        viewPhoto.notifyDataSetChanged();
                    }

                    public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable) {
                        Log.i("DEBUG", "JsonHttpResponseHandler:onFailure triggered with statusCode="+statusCode);
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_media_displayer, menu);
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

    @Override
    public void onRefresh() {
        Log.i("DEBUG", "Refreshing Content");
        // Clear the Photos present
        photos.clear();

        //Update the Photos
        fetchPopularPhotos();

        // Notify the Container that refresh has completed
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        InstagramPhoto photo = photos.get(position);

        if ( photo.comments.size() > 1 ) {
            Intent  i =  new Intent(MediaDisplayerActivity.this, PhotoCommentActivity.class);
            i.putExtra("photo", photo);
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(), "This photo has only one comment",
                    Toast.LENGTH_SHORT).show();
        }

    }
}
