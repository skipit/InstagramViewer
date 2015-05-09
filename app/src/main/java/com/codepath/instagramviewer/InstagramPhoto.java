package com.codepath.instagramviewer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by pavan on 5/2/15.
 */
public class InstagramPhoto implements Serializable {

    public String userName;
    public String userImageUrl;
    public String caption;
    public String imageUrl;
    public String thumbnailUrl;
    public long creationDate;
    public int imageHeight;
    public int likesCount;
    public ArrayList<InstagramPhotoComment> comments;
}
