package com.example.hp.graphqltesting;

import android.util.Log;

import com.apollographql.apollo.ApolloClient;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApolloConnector {
    private static final String BASE_URL = "https://incampus-test.herokuapp.com/v1/graphql";

    public static ApolloClient setupApollo(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .header("x-hasura-admin-secret", "incampus")
                        .header("content-type","application/json"); // <-- this is the important line

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        OkHttpClient okHttpClient = httpClient.build();
        Log.i("REtutf","fwe");
        return ApolloClient.builder().serverUrl(BASE_URL).okHttpClient(okHttpClient).build();
    }
}
