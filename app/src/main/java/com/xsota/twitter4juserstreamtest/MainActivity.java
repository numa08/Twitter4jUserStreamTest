package com.xsota.twitter4juserstreamtest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class MainActivity extends AppCompatActivity {

  ListView listView;
  ArrayAdapter<String> adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    listView = (ListView) findViewById(R.id.list);
    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
    listView.setAdapter(adapter);

    String consumerKey = "ここに";
    String consumerSecret = "あなたの";
    String accessToken = "キーを";
    String accessTokenSecret = "入力";

    // config
    Configuration conf = new ConfigurationBuilder()
        .setDebugEnabled(true)
        .setOAuthConsumerKey(consumerKey)
        .setOAuthConsumerSecret(consumerSecret)
        .setOAuthAccessToken(accessToken)
        .setOAuthAccessTokenSecret(accessTokenSecret)
        .build();

    StatusListener listener = new StatusListener(){
      @Override
      public void onStatus(final Status status) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
          @Override
          public void run() {
            adapter.add("UserName: "+status.getUser().getName() + " Tweet: "+status.getText());
          }
        });
      }

      @Override
      public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}

      @Override
      public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}

      @Override
      public void onScrubGeo(long userId, long upToStatusId) {}

      @Override
      public void onStallWarning(StallWarning warning) {}

      @Override
      public void onException(Exception ex) {}
    };

    TwitterStream twitterStream = new TwitterStreamFactory(conf).getInstance();
    twitterStream.addListener(listener);
    twitterStream.user();

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
