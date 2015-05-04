package com.codepath.instagramviewer;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MediaDisplayerActivity extends ActionBarActivity {

    public static final String CLIENT_ID = "5e4bb8b442144e2cad975512543ecdb8";
    private ArrayList<InstagramPhoto> photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_displayer);
        photos = new ArrayList<>();
        fetchPopularPhotos();
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
                                    photo.caption = photoJSON.getJSONObject("caption").getString("text");
                                    photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                                    photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                                    photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
                                    photos.add(photo);
                                } catch (JSONException e) {
                                    Log.i("DEBUG", "Cannot insert Picture "+i+":"+e.toString());
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable) {
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
}
