package com.example.anthony.gestionstock.model.webservice;

import android.util.Log;

import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Anthony on 21/09/2016.
 */
public class OkHttpUtils {

    public static String sendGetOkHttpRequest(String url) throws Exception {
        Log.w("TAG_WEB", url);
        OkHttpClient client = new OkHttpClient();

        //Création de la requete
        Request request = new Request.Builder().url(url).build();

        //Execution de la requête
        Response response = client.newCall(request).execute();

        //Analyse du code retour
        if (response.code() != HttpURLConnection.HTTP_OK) {
            throw new Exception("Reponse du serveur incorrect : " + response.code());
        }
        else {
            //Résultat de la requete.
            return response.body().string();
        }
    }

    public static void sendGetOkHttpRequestAsync(String url, Callback okHttpCallBack) throws Exception {
        Log.w("TAG_WEB", url);
        OkHttpClient client = new OkHttpClient();

        //Création de la requete
        Request request = new Request.Builder().url(url).build();

        //Execution de la requête
        client.newCall(request).enqueue(okHttpCallBack);
    }

    public static String sendPostOkHttpRequest(String url, String paramJson) throws Exception {
        Log.w("TAG_WEB", url);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        //Corps de la requête
        RequestBody body = RequestBody.create(JSON, paramJson);
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();

        if (response.code() != HttpURLConnection.HTTP_OK) {
            throw new Exception("Reponse du serveur incorrect : " + response.code());
        }
        else {
            return response.body().string();
        }
    }
}
