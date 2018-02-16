package com.example.jason.route_application_kotlin.di.network;

import com.example.jason.route_application_kotlin.data.api.ApiService;
import com.example.jason.route_application_kotlin.di.AppScope;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jason on 08-Feb-18.
 */

@Module
public class NetworkModule {

    @Provides
    @AppScope
    public OkHttpClient provideOkHttpClient(){

        return new OkHttpClient();
    }

    //    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Provides
    @AppScope
    public Gson provideGson (){
        Gson gson = new Gson();
        return gson;
    }

    @Provides
    @AppScope
    public Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson){
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://10.163.48.203:8080/webapi/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @AppScope
    @Provides
    public ApiService provideApiService(Retrofit retrofit){
        ApiService apiService = retrofit.create(ApiService.class);
        return apiService;
    }

}
