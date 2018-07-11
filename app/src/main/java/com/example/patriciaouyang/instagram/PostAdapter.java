package com.example.patriciaouyang.instagram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.patriciaouyang.instagram.model.Post;

import java.lang.ref.WeakReference;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    private List<Post> mPosts;
    Context context;
    private final ClickListener listener;

    public PostAdapter(List<Post> posts, ClickListener listener) {
        mPosts = posts;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = mPosts.get(position);
        holder.tvUser.setText(post.getUser().getUsername());
        holder.tvCaption.setText(post.getDescription());

        Glide.with(context).load(post.getImage().getUrl()).into(holder.ivPostImage);
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView ivPostImage;
        public TextView tvCaption;
        public TextView tvUser;
        private WeakReference<ClickListener> listenerRef;

        public ViewHolder(@NonNull View itemView, ClickListener listener) {
            super(itemView);
            listenerRef = new WeakReference<>(listener);
            ivPostImage = itemView.findViewById(R.id.ivPostImage);
            tvCaption = itemView.findViewById(R.id.tvCaption);
            tvUser = itemView.findViewById(R.id.tvUser);

            itemView.setOnClickListener(this);
        }


        // onClick Listener for view
        @Override
        public void onClick(View v) {
            listenerRef.get().onPositionClicked(getAdapterPosition());
        }
    }

    // Clean all elements of the recycler
    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        mPosts.addAll(list);
        notifyDataSetChanged();
    }
}
