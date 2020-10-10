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
    public List<String> embeddedVideoUrls;
    public User user;
    public boolean retweeted;
    public boolean favorited;

    // Empty constructor needed by Parceler library
    public Tweet(){}

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.retweeted = jsonObject.getBoolean("retweeted");
        tweet.favorited = jsonObject.getBoolean("favorited");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.id = jsonObject.getLong("id");
        tweet.retweetCount = jsonObject.getLong("retweet_count");
        tweet.likesCount = jsonObject.getLong("favorite_count");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.expandedUrls = new ArrayList<>();
        tweet.embeddedVideoUrls = new ArrayList<>();
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

        if (jsonObject.has("extended_entities")) {
            JSONObject extendedEntities = jsonObject.getJSONObject("extended_entities");
            JSONArray mediaArray = extendedEntities.getJSONArray("media");
            for (int i = 0; i < mediaArray.length(); i++) {
                JSONObject media = mediaArray.getJSONObject(i);
                if(media.has("video_info")){
                    JSONArray variants = media.getJSONObject("video_info").getJSONArray("variants");
                    for(int j = 0; j < variants.length(); j++){
                        int bit_rate = 0;
                        if (variants.getJSONObject(i).has("bitrate"))
                            bit_rate = variants.getJSONObject(i).getInt("bitrate");
                        String content_type = variants.getJSONObject(i).getString("content_type");
                        if (bit_rate == 832000 || content_type.contains("video")){
                            tweet.embeddedVideoUrls.add(variants.getJSONObject(i).getString("url"));
                            break;
                        }
                    }
                }

            }
        }

        Log.d("Tweet - Video", tweet.embeddedVideoUrls.toString());
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
