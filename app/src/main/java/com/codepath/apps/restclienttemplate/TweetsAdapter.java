package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>{

    Context context;
    List<Tweet> tweets;

    //Pass in the context and list of tweets
    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    // For each row, inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    // Bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data at position
        Tweet tweet = tweets.get(position);
        //Bind the tweet with the viewholder
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // Clean all elements of the recycler
    public void clear(){
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Tweet> tweetList){
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }

    // Define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivProfilemage;
        TextView tvBody;
        TextView tvScreenName;
        TextView tvName;
        TextView tvRelativeTimestamp;
        RelativeLayout container;
        TextView tvRetweets;
        TextView tvLikes;
        ImageView ivEmbeddedImage;
        VideoView vvEmbdeddedVideo;
        ImageView ivVerified;
        ImageView ivRetweeted;
        ImageView ivLike;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfilemage = itemView.findViewById(R.id.ivProfilename);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvName = itemView.findViewById(R.id.tvName);
            tvRelativeTimestamp = itemView.findViewById(R.id.tvRelativeTimestamp);
            container = itemView.findViewById(R.id.relativeLayout);
            tvRetweets = itemView.findViewById(R.id.tvRetweet);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            ivEmbeddedImage = itemView.findViewById(R.id.ivEmbeddedImage);
            vvEmbdeddedVideo = itemView.findViewById(R.id.vvEmbeddedVide);
            ivVerified = itemView.findViewById(R.id.ivVerified);

        }

        public void bind(final Tweet tweet) {
            tvBody.setText(tweet.body);
            tvScreenName.setText("@" + tweet.user.screenName);
            tvName.setTypeface(null, Typeface.BOLD);
            tvName.setText(tweet.user.name);
            tvRelativeTimestamp.setText(" . " + tweet.getCreatedAt());
            tvRetweets.setText(tweet.retweetCount + " Retweets");
            tvLikes.setText(tweet.likesCount + " Likes");
            Glide.with(context).load(tweet.user.profileImageUrl).fitCenter().transform(new RoundedCornersTransformation(50, 10)).placeholder(R.drawable.placeholder).into(ivProfilemage);
            ivEmbeddedImage.setVisibility(View.GONE);
            vvEmbdeddedVideo.setVisibility(View.GONE);
            ivVerified.setVisibility(View.GONE);

            if (! tweet.embeddedVideoUrls.isEmpty()){
                Uri uri = Uri.parse(tweet.embeddedVideoUrls);
                vvEmbdeddedVideo.setVisibility(View.VISIBLE);
                vvEmbdeddedVideo.setVideoURI(uri);
                vvEmbdeddedVideo.start();
            }

            else if (! tweet.expandedUrls.isEmpty()){
                ivEmbeddedImage.setVisibility(View.VISIBLE);
                Glide.with(context).load(tweet.expandedUrls).placeholder(R.drawable.placeholder).into(ivEmbeddedImage);
            }

            if (tweet.user.verified){
                ivVerified.setVisibility(View.VISIBLE);
            }

            if (tweet.retweeted){
                tvRetweets.setTextColor(context.getResources().getColor(R.color.medium_green));
            }

            if (tweet.favorited){
                tvLikes.setTextColor(context.getResources().getColor(R.color.medium_red));
            }


            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Adapter", "container clicked");
                    Intent i =new Intent(context, DetailActivity.class);
                    i.putExtra("tweet", Parcels.wrap(tweet));
                    context.startActivity(i);
                }
            });
        }
    }

}
