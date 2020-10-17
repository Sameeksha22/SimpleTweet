package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.codepath.apps.restclienttemplate.TimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId"))
public class Tweet {

    @ColumnInfo
    @PrimaryKey
    public long id;

    @ColumnInfo
    public String body;

    @ColumnInfo
    public String createdAt;

    @ColumnInfo
    public long retweetCount;

    @ColumnInfo
    public long likesCount;

    @ColumnInfo
    public String expandedUrls;

    @ColumnInfo
    public String embeddedVideoUrls;

    @ColumnInfo
    public Long userId;

    @Ignore
    public User user;

    @ColumnInfo
    public boolean retweeted;

    @ColumnInfo
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
        User user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.user = user;
        tweet.userId = user.id;
//        tweet.expandedUrls = new ArrayList<>();
//        tweet.embeddedVideoUrls = new ArrayList<>();
        tweet.expandedUrls = "";
        tweet.embeddedVideoUrls = "";
        JSONObject urls = jsonObject.getJSONObject("entities");
        if (urls.has("media")){
            JSONArray medias = urls.getJSONArray("media");
            for (int i = 0; i < medias.length(); i++){
                JSONObject media = medias.getJSONObject(i);
                if(media.getString("type").matches("photo")) {
                    tweet.expandedUrls = media.getString("media_url");
                    break;
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
                            tweet.embeddedVideoUrls = variants.getJSONObject(i).getString("url");
                            break;
                        }
                    }
                }

            }
        }
        Log.i("TWEET: ", tweet.expandedUrls);
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
