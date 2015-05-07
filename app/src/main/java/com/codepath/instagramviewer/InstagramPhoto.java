package com.codepath.instagramviewer;

import java.io.Serializable;

/**
 * Created by pavan on 5/2/15.
 */
public class InstagramPhoto implements Serializable {

    public String userName;
    public String caption;
    public String imageUrl;
    public String thumbnailUrl;
    public int imageHeight;
    public int likesCount;
}
