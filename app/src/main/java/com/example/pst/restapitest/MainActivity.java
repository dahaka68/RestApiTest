package com.example.pst.restapitest;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.pst.restapitest.adapters.PostAdapter;
import com.example.pst.restapitest.interfaces.IPostListener;
import com.example.pst.restapitest.interfaces.PostObserver;
import com.example.pst.restapitest.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.pst.restapitest.Constants.BASE_URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private PostObserver postObserver = new PostObserver() {
        @Override
        public void onPostsReceived(List<Post> postList) {
            adapter.setList(postList);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRV();

    }

    private void initRV() {
        recyclerView = (RecyclerView) findViewById(R.id.rvPost);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new PostAdapter();
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        NetworkDataManager.getInstance().addObserver(postObserver);

    }

    @Override
    protected void onStop() {
        super.onStop();
        NetworkDataManager.getInstance().removeObserver(postObserver);
    }

}
