package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class DetailActivity extends AppCompatActivity {

    public static final String TAG = "DetailActivity";

    ImageView ivProfileImage;
    TextView tvName;
    TextView tvScreenName;
    TextView tvBody;
    TextView tvDate;
    TextView tvRetweets;
    TextView tvLikes;


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

        final Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        Log.i(TAG, "intent received");
        tvName.setText(tweet.user.name);
        tvScreenName.setText("@" + tweet.user.screenName);
        tvBody.setText(tweet.body);
        tvDate.setText(tweet.getDate() + ".");
        tvRetweets.setText(tweet.retweetCount + " retweets");
        tvLikes.setText(tweet.likesCount+" likes");
        Glide.with(this).load(tweet.user.profileImageUrl).fitCenter().transform(new RoundedCornersTransformation(70, 10)).placeholder(R.drawable.placeholder).into(ivProfileImage);


    }

}