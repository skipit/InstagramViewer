package com.codepath.instagramviewer;

import java.io.Serializable;

/**
 * Created by pavan on 5/8/15.
 */
public class InstagramPhotoComment implements Serializable {
    public String userName;
    public String comment;
    public String profilePictureUrl;

    @Override
    public String toString() {
        return   "<b> <font color = \"blue\">"
               + userName
               + "</font></b> "
               + comment;
    }
}
