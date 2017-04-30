package com.amazonaws.models.nosql;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "retreatspa-mobilehub-1208223069-RatedItems")

public class RatedItemsDO {
    private String _userId;
    private String _arrivalOnTime;
    private String _cleanliness;
    private String _levelOfService;
    private String _prices;
    private String _recommendForOthers;
    private String _timeStamp;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBIndexHashKey(attributeName = "userId", globalSecondaryIndexName = "Ratings")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBAttribute(attributeName = "arrivalOnTime")
    public String getArrivalOnTime() {
        return _arrivalOnTime;
    }

    public void setArrivalOnTime(final String _arrivalOnTime) {
        this._arrivalOnTime = _arrivalOnTime;
    }
    @DynamoDBAttribute(attributeName = "cleanliness")
    public String getCleanliness() {
        return _cleanliness;
    }

    public void setCleanliness(final String _cleanliness) {
        this._cleanliness = _cleanliness;
    }
    @DynamoDBAttribute(attributeName = "levelOfService")
    public String getLevelOfService() {
        return _levelOfService;
    }

    public void setLevelOfService(final String _levelOfService) {
        this._levelOfService = _levelOfService;
    }
    @DynamoDBAttribute(attributeName = "prices")
    public String getPrices() {
        return _prices;
    }

    public void setPrices(final String _prices) {
        this._prices = _prices;
    }
    @DynamoDBAttribute(attributeName = "recommendForOthers")
    public String getRecommendForOthers() {
        return _recommendForOthers;
    }

    public void setRecommendForOthers(final String _recommendForOthers) {
        this._recommendForOthers = _recommendForOthers;
    }
    @DynamoDBAttribute(attributeName = "timeStamp")
    public String getTimeStamp() {
        return _timeStamp;
    }

    public void setTimeStamp(final String _timeStamp) {
        this._timeStamp = _timeStamp;
    }

}
