package com.retreatspa.retreatspa;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.AWSConfiguration;
import com.amazonaws.mobilehelper.config.AWSMobileHelperConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.*;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobile.AWSConfiguration;

import com.amazonaws.models.nosql.RatedItemsDO;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;

import java.util.Date;

public class Review extends AppCompatActivity implements RatingBar.OnRatingBarChangeListener {
    RatedItemsDO rate;
    RatingBar rate1, rate2, rate3, rate4, rate5;
    Button submit;
    DynamoDBMapper mapper;
    AWSMobileClient ddbClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        //final AWSMobileClient.Builder client = new AWSMobileClient.Builder(getApplicationContext());
        //AWSMobileClient cl = new AWSMobileClient(getApplicationContext(),AWSConfiguration.AMAZON_COGNITO_IDENTITY_POOL_ID,AWSConfiguration.AMAZON_COGNITO_REGION);
        //DynamoDBMapper mapper = new DynamoDBMapper(client);
        //AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient();









        rate = new RatedItemsDO();
        rate1 = (RatingBar)findViewById(R.id.ratingBar3);
        rate2 = (RatingBar)findViewById(R.id.ratingBar4);
        rate3 = (RatingBar)findViewById(R.id.ratingBar5);
        rate4 = (RatingBar)findViewById(R.id.ratingBar6);
        rate5 = (RatingBar)findViewById(R.id.ratingBar7);
        submit = (Button)findViewById(R.id.submit);
        rate1.setOnRatingBarChangeListener(this);
        rate2.setOnRatingBarChangeListener(this);
        rate3.setOnRatingBarChangeListener(this);
        rate4.setOnRatingBarChangeListener(this);
        rate5.setOnRatingBarChangeListener(this);

        rate1.setStepSize(1.0f);
        rate1.setRating(1.0f);

        rate2.setStepSize(1.0f);
        rate2.setRating(1.0f);

        rate3.setStepSize(1.0f);
        rate3.setRating(1.0f);

        rate4.setStepSize(1.0f);
        rate4.setRating(1.0f);

        rate5.setStepSize(1.0f);
        rate5.setRating(1.0f);

        String msg = ""+rate1.getNumStars()+" "+rate1.getRating()+" "+rate1.getStepSize();
        Log.e("msg", msg);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rate.setLevelOfService(""+rate1.getRating());
                rate.setCleanliness(""+rate2.getRating());
                rate.setPrices(""+rate3.getRating());
                rate.setArrivalOnTime(""+rate4.getRating());
                rate.setRecommendForOthers(""+rate5.getRating());
                Date d = new Date();
                rate.setTimeStamp(d.toString());



//                WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//                WifiInfo info = manager.getConnectionInfo();
//                String address = info.getMacAddress();
                rate.setUserId(d.toString());


                Runnable runnable = new Runnable() {
                    public void run() {
                        //DynamoDB calls go here
                        Log.e("msg", "in run");
                        AWSMobileClient.initializeMobileClientIfNecessary(getApplicationContext());
                        ddbClient = AWSMobileClient.defaultMobileClient();
                        if(ddbClient == null){
                            Log.e("msg", "null");
                        }
                        mapper = ddbClient.getDynamoDBMapper();
                        mapper.save(rate);

                    }
                };
                Thread mythread = new Thread(runnable);
                mythread.start();
                Toast.makeText(Review.this, "Thanks for review", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        if(rating<1.0f) {
            ratingBar.setRating(1.0f);
        }
    }
    }

