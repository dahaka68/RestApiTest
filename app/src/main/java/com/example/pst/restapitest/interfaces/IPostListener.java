package com.example.pst.restapitest.interfaces;

import com.example.pst.restapitest.model.Post;

import java.util.List;

/**
 * Created by dahak on 05.09.2017.
 */

public interface IPostListener {
    public void onPostReceived(List<Post> list);
}
