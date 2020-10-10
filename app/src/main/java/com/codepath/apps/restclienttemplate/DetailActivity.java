package com.codepath.apps.restclienttemplate;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.util.Iterator;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class DetailActivity extends AppCompatActivity {

    public static final String TAG = "DetailActivity";

    ImageView ivProfileImage;
    TextView tvName;
    TextView tvScreenName;
    TextView tvBody;
    TextView tvDate;
    TextView tvRetweets;
    TextView tvLikes;
    ImageView ivEmbeddedImage;
    VideoView vvEmbeddedVideo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvName = findViewById(R.id.tvName);
        tvScreenName = findViewById(R.id.tvScreenName);
        tvBody = findViewById(R.id.tvBody);
        tvDate = findViewById(R.id.tvDate);
        tvRetweets = findViewById(R.id.tvRetweets);
        tvLikes = findViewById(R.id.tvLikes);
        ivEmbeddedImage = findViewById(R.id.ivEmbeddedImage);
        vvEmbeddedVideo = findViewById(R.id.vvEmbeddedVide);

        final Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        Log.i(TAG, "intent received");
        tvName.setText(tweet.user.name);
        tvScreenName.setText("@" + tweet.user.screenName);
        tvBody.setText(tweet.body);
        tvDate.setText(tweet.getDate() + ".");
        tvRetweets.setText(tweet.retweetCount + " retweets");
        tvLikes.setText(tweet.likesCount+" likes");
        Glide.with(this).load(tweet.user.profileImageUrl).fitCenter().transform(new RoundedCornersTransformation(50, 10)).placeholder(R.drawable.placeholder).into(ivProfileImage);

        ivEmbeddedImage.setVisibility(View.GONE);
        vvEmbeddedVideo.setVisibility(View.GONE);


        if (tweet.embeddedVideoUrls.size() > 0){
            Uri uri = Uri.parse(tweet.embeddedVideoUrls.get(0));
            vvEmbeddedVideo.setVisibility(View.VISIBLE);
            vvEmbeddedVideo.setVideoURI(uri);
            vvEmbeddedVideo.start();
        }

        else if (tweet.expandedUrls.size() > 0){
            ivEmbeddedImage.setVisibility(View.VISIBLE);
            Glide.with(this).load(tweet.expandedUrls.get(0)).placeholder(R.drawable.placeholder).into(ivEmbeddedImage);
        }



    }

}