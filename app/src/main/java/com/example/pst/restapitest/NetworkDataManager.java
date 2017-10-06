package com.example.pst.restapitest;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.pst.restapitest.interfaces.IPostListener;
import com.example.pst.restapitest.interfaces.PostObservable;
import com.example.pst.restapitest.interfaces.PostObserver;
import com.example.pst.restapitest.model.Post;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.pst.restapitest.Constants.BASE_URL;

/**
 * Created by dahak on 05.09.2017.
 */

public class NetworkDataManager implements PostObservable {
    private static NetworkDataManager sInstance;
    private static final String TAG = NetworkDataManager.class.getSimpleName();
    private Call<List<Post>> callPosts;
    private List<Post> listPosts;
    private List<WeakReference<PostObserver>> postObservers = new ArrayList<>();

    private NetworkDataManager() {
        initRetrofit();
        getPosts();
    }

    static NetworkDataManager getInstance() {
        if (sInstance == null) {
            Class var = NetworkDataManager.class;
            synchronized (NetworkDataManager.class) {
            }
            if (sInstance == null) {
                sInstance = new NetworkDataManager();
            }
        }
        return sInstance;
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitInterface service = retrofit.create(RetrofitInterface.class);
        callPosts = service.listPosts();
    }

    private void getPosts() {
        callPosts.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                listPosts = response.body();
                notifyObservers();
                Log.d(TAG, "onResponse: code = " + response.code());
                Log.d(TAG, "onResponse: listResponse = " + listPosts);
            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void addObserver(PostObserver postObserver) {
        WeakReference<PostObserver> reference = new WeakReference<PostObserver>(postObserver);
        postObservers.add(reference);
        if (listPosts != null && listPosts.size() > 0) {
            postObserver.onPostsReceived(listPosts);
        }
    }

    @Override
    public void removeObserver(PostObserver postObserver) {
        for (WeakReference<PostObserver> reference : postObservers) {
            PostObserver observer = reference.get();
            if (observer == null || observer.equals(postObserver)) {
                postObservers.remove(reference);
                break;
            }
        }
//yfuf
    }

    @Override
    public void notifyObservers() {

        for (WeakReference<PostObserver> reference : postObservers) {
            PostObserver postObserver = reference.get();
            if (postObserver != null) {
                postObserver.onPostsReceived(listPosts);
            }
        }
    }
}
