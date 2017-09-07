package com.example.pst.restapitest;

import com.example.pst.restapitest.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by pst on 29.08.2017.
 */

public interface RetrofitInterface {
    @GET("/posts")
    Call<List<Post>> listPosts();
}
