package com.example.pst.restapitest.interfaces;

import com.example.pst.restapitest.model.Post;

import java.util.List;

/**
 * Created by dahak on 07.09.2017.
 */

public interface PostObserver {
   void onPostsReceived(List<Post> postList);
}
