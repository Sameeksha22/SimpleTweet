package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

        final Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        Log.i(TAG, "intent received");
        tvName.setText(tweet.user.name);
        tvScreenName.setText("@" + tweet.user.screenName);
        tvBody.setText(tweet.body);
        tvDate.setText(tweet.getDate() + ".");
        tvRetweets.setText(tweet.retweetCount + " retweets");
        tvLikes.setText(tweet.likesCount+" likes");
        Glide.with(this).load(tweet.user.profileImageUrl).fitCenter().transform(new RoundedCornersTransformation(50, 10)).placeholder(R.drawable.placeholder).into(ivProfileImage);
        if (tweet.expandedUrls.size() > 0){
            Iterator<String> it = tweet.expandedUrls.iterator();
            while (it.hasNext()){
                String embedded_url = it.next();
                embedded_url.replace("http", "https");
                ivEmbeddedImage.setVisibility(View.VISIBLE);
                Glide.with(this).load(embedded_url).placeholder(R.drawable.placeholder).into(ivEmbeddedImage);
            }
        }

    }

}