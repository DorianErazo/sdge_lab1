package edu.upf.parser;

import java.io.IOException;
import java.util.Optional;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class SimplifiedTweet {

  // All classes use the same instance
  private static JsonParser parser = new JsonParser(); // Deprecated?

  private final long tweetId; // the id of the tweet ('id')
  private final String text; // the content of the tweet ('text')
  private final long userId; // the user id ('user->id')
  private final String userName; // the user name ('user'->'name')
  private final String language; // the language of a tweet ('lang')
  private final long timestampMs; // seconduserIds from epoch ('timestamp_ms')

  public SimplifiedTweet(long tweetId, String text, long userId, String userName,
      String language, long timestampMs) {

    // PLACE YOUR CODE HERE!
    // We assign every element of the class SimplifiedTweet with the values received
    // as
    // parameters on this function with their respective element
    this.tweetId = tweetId;
    this.text = text;
    this.userId = userId;
    this.userName = userName;
    this.language = language;
    this.timestampMs = timestampMs;
  }

  /**
   * Returns a {@link SimplifiedTweet} from a JSON String.
   * If parsing fails, for any reason, return an {@link Optional#empty()}
   *
   * @param jsonStr
   * @return an {@link Optional} of a {@link SimplifiedTweet}
   */
  public static Optional<SimplifiedTweet> fromJson(String jsonStr) {
    // PLACE YOUR CODE HERE!
    try {
      // Parse each element of the JSON String so in case that a line contains all the
      // elements
      // it will @return an {@link Optional} of a {@link SimplifiedTweet} otherwise
      // return an {@link Optional#empty()}
      JsonElement jEle = JsonParser.parseString(jsonStr);
      JsonObject jObj = null;
      if (jEle instanceof JsonObject) {
        jObj = jEle.getAsJsonObject();

      } else if (jEle instanceof JsonArray) {
        JsonArray jArray = jEle.getAsJsonArray();
        jObj = jArray.getAsJsonObject();

      } else {
        return Optional.empty();
      }

      long tweetId;
      if (jObj.has("id")) {
        tweetId = jObj.get("id").getAsLong();
      } else {
        return Optional.empty();
      }
      String text = null;
      if (jObj.has("text")) {
        text = jObj.get("text").getAsString();
      } else {
        return Optional.empty();
      }
      long userId;
      String userName = null;
      if (jObj.has("user")) {
        JsonObject userObj = jObj.get("user").getAsJsonObject();
        if (userObj.has("id")) {
          userId = userObj.get("id").getAsLong();
        } else {
          return Optional.empty();
        }
        if (userObj.has("name")) {
          userName = userObj.get("name").getAsString();
        } else {
          return Optional.empty();
        }
      } else {
        return Optional.empty();
      }
      String language = null;
      if (jObj.has("lang")) {
        language = jObj.get("lang").getAsString();
      } else {
        return Optional.empty();
      }
      long timestampsMs;
      if (jObj.has("timestamp_ms")) {
        timestampsMs = jObj.get("timestamp_ms").getAsLong();
      } else {
        return Optional.empty();
      }

      return Optional.of(new SimplifiedTweet(tweetId, text, userId, userName, language, timestampsMs));

    } catch (JsonSyntaxException e) {
      // In case something fails with the JSON String retrieved it returns an {@link
      // Optional#empty()}
      return Optional.empty();
    }
  }

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }

  // Typical getters created in a java object structure
  public long getTweetId() {
    return tweetId;
  }

  public String getText() {
    return text;
  }

  public long getUserId() {
    return userId;
  }

  public String getUserName() {
    return userName;
  }

  public String getLanguage() {
    return language;
  }

  public long getTimestampMs() {
    return timestampMs;
  }
}
