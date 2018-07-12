package com.example.patriciaouyang.instagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.patriciaouyang.instagram.model.Post;

public class PostDetailsActivity extends AppCompatActivity {

    TextView tvUserHandle;
    TextView tvUserHandleBottom;
    TextView tvCaption;
    ImageView ivImagePost;
    TextView tvTimestamp;
    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        //ParseQuery.getQuery(Post.class).whereEqualTo("objectId", getIntent().getStringExtra(Post.class.getSimpleName()))
        //        .findInBackground();
        //post = (Post) getIntent().getStringExtra(Post.class.getSimpleName());

        ivImagePost = findViewById(R.id.ivImagePost);
        tvTimestamp = findViewById(R.id.tvTimeStamp);
        tvUserHandle = findViewById(R.id.tvUserHandle);
        tvUserHandleBottom = findViewById(R.id.tvUserHandle2);
        tvCaption = findViewById(R.id.tvCaption);
        Glide.with(this).load(getIntent().getStringExtra("imageUrl")).into(ivImagePost);

        Log.d("PostDetailsActivity", "Showing post details.");

        String x = getIntent().getStringExtra("timestamp");

        tvTimestamp.setText(getIntent().getStringExtra("timestamp"));
        tvUserHandle.setText(getIntent().getStringExtra("username"));
        tvUserHandleBottom.setText(getIntent().getStringExtra("username"));
        tvCaption.setText(getIntent().getStringExtra("caption"));
    }
}
