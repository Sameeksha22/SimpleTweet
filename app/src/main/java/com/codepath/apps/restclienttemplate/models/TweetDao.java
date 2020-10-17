package com.codepath.apps.restclienttemplate.models;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TweetDao {

    @Query("SELECT Tweet.body as tweet_body, Tweet.createdAt as tweet_createdAt, Tweet.id as tweet_id, User.*, Tweet.retweetCount as tweet_retweetCount, Tweet.likesCount as tweet_likedCount, Tweet.expandedUrls as tweet_expandedUrls, Tweet.embeddedVideoUrls as tweet_embeddedVideoUrls, Tweet.retweeted as tweet_retweeted, Tweet.favorited as tweet_favorited " +
            "FROM Tweet INNER JOIN User ON Tweet.UserId = User.id ORDER BY Tweet.createdAt DESC LIMIT 300")
    List<TweetWithUser> recentItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertModel(Tweet... tweets);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertModel(User... users);
}
