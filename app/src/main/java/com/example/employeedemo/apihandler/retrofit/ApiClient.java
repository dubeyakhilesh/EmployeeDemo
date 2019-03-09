package com.example.employeedemo.apihandler.retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by XT-DS-20 on 13 Dec 2018.
 * @CompanyName: Xipe Tech
 * @ProjectName: compete4Hope Android App
 * @Project Version: 1.0
 * @PageName: ApiClient.java
 * @Module Name: Retrofit
 * @Description : base ApiClient networking class
 * @Author: Akhilesh Dubey
 *
 */

public class ApiClient {
    /*--define global class variable--*/
   public static final String BASE_URL = "https://simplifiedcoding.net/demos/view-flipper/";
    private static Retrofit retrofit = null;

    /*--user methods--*/
    public static Retrofit getUserClient() {
        //String url = BASE_URL + user_service_version;
        if (retrofit==null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient getHttpClient() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS).build();

        return client;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private static final Gson gson = new GsonBuilder()
            .setLenient()
            .create();


    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(GroupResponse.class, new CustomDeserializer())
            .create();

    public static class CustomDeserializer implements JsonDeserializer<List<Map<Integer, String>>> {

        @Override
        public List<Map<Integer, String>> deserialize(JsonElement element, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            List<Map<Integer, String>> randomList = new ArrayList<>();
            JsonObject parentJsonObject = element.getAsJsonObject();
            Map<Integer, String> childMap;
            for(Map.Entry<String, JsonElement> entry : parentJsonObject.entrySet()){
                childMap = new HashMap<>();
                for(Map.Entry<String, JsonElement> entry1 : entry.getValue().getAsJsonObject().entrySet()){
                    childMap.put(Integer.parseInt(entry1.getKey()), entry1.getValue().toString());
                }
                randomList.add(childMap);
            }
            return randomList;
        }
    }


}
