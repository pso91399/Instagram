package com.example.patriciaouyang.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.patriciaouyang.instagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    PostAdapter postAdapter;
    ArrayList<Post> posts;
    RecyclerView rvPosts;
    public static final int REQUEST_CODE = 20;

    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        rvPosts = findViewById(R.id.rvPosts);
        posts = new ArrayList<>();
        postAdapter = new PostAdapter(posts, new ClickListener() {
            @Override
            public void onLongClicked(int position) {

            }

            @Override
            public void onPositionClicked(int position) {
                Post post = posts.get(position);
                Intent i = new Intent(FeedActivity.this, PostDetailsActivity.class);
                Toast.makeText(FeedActivity.this, "DetailsView launched", Toast.LENGTH_SHORT).show();
                i.putExtra("caption", post.getDescription());
                i.putExtra("imageUrl", post.getImage().getUrl());
                i.putExtra("username", post.getUser().getUsername());

                i.putExtra("timestamp", post.getRelativeTime());
                startActivity(i);
            }
        });

        rvPosts.setLayoutManager(new LinearLayoutManager(this));
        rvPosts.setAdapter(postAdapter);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync(0);
                swipeContainer.setRefreshing(false);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        loadTopPosts();
    }

    public void fetchTimelineAsync(int page) {
        final Post.Query postsQuery = new Post.Query();
        postsQuery.getTop().withUser();

        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e ==  null) {
                    postAdapter.clear();
                    for (int i = 0; i < objects.size(); i++) {
                        Log.d("FeedActivity", "Post[" + i + "] = "
                                + objects.get(i).getDescription()
                                + "\nusername = " + objects.get(i).getUser().getUsername()
                        );
                        Post post = new Post();
                        post.setDescription(objects.get(i).getDescription());
                        post.setUser(objects.get(i).getUser());
                        post.setImage(objects.get(i).getImage());
                        posts.add(post);
                        postAdapter.notifyItemInserted(posts.size() - 1);
                    }
                    postAdapter.addAll(posts);
                    // Now we call setRefreshing(false) to signal refresh has finished
                    swipeContainer.setRefreshing(false);
                } else {
                    e.printStackTrace();
                }
            }
        });
    }


    private void loadTopPosts() {
        final Post.Query postsQuery = new Post.Query();
        postsQuery.getTop().withUser();

        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e ==  null) {
                    for (int i = 0; i < objects.size(); i++) {
                        Log.d("FeedActivity", "Post[" + i + "] = "
                                + objects.get(i).getDescription()
                                + "\nusername = " + objects.get(i).getUser().getUsername()
                        );
                        Post post = new Post();
                        post.setDescription(objects.get(i).getDescription());
                        post.setUser(objects.get(i).getUser());
                        post.setImage(objects.get(i).getImage());
                        posts.add(post);
                        postAdapter.notifyItemInserted(posts.size() - 1);
                    }
                    postAdapter.addAll(posts);
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
