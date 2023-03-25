package com.cariq.test.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;

public class ApiUtils {

    public static BigDecimal getFare(String str) {
        JSONObject obj = new JSONObject(str);
        JSONArray fares = obj.getJSONObject("root").getJSONObject("fares").getJSONArray("fare");
        for (int i=0; i<fares.length(); i++) {
            JSONObject fare = fares.getJSONObject(i);
            String sclass = fare.getString("@class");
            if (sclass.equalsIgnoreCase("clipper") || sclass.equalsIgnoreCase("cash")) {
                return new BigDecimal(fare.getString("@amount"));
            }
        }

        return null;
    }
}
