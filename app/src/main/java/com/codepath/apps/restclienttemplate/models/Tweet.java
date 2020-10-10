package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import com.codepath.apps.restclienttemplate.TimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Tweet {

    public String body;
    public String createdAt;
    public long id;
    public long retweetCount;
    public long likesCount;
    public List<String> expandedUrls;
    public User user;

    // Empty constructor needed by Parceler library
    public Tweet(){}

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.id = jsonObject.getLong("id");
        tweet.retweetCount = jsonObject.getLong("retweet_count");
        tweet.likesCount = jsonObject.getLong("favorite_count");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.expandedUrls = new ArrayList<>();
        JSONObject urls = jsonObject.getJSONObject("entities");
        if (urls.has("media")){
            JSONArray medias = urls.getJSONArray("media");
            for (int i = 0; i < medias.length(); i++){
                JSONObject media = medias.getJSONObject(i);
                if(media.getString("type").matches("photo")) {
                    tweet.expandedUrls.add(media.getString("media_url"));
                }
            }
        }

        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

    public String getCreatedAt() {
        return TimeFormatter.getTimeDifference(createdAt);
    }

    public String getDate(){
        return TimeFormatter.getTimeStamp(createdAt);
    }
}
