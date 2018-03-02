package com.example.jason.route_application_kotlin.di.network;

import com.example.jason.route_application_kotlin.data.api.ApiService;
import com.example.jason.route_application_kotlin.data.database.DatabaseService;
import com.example.jason.route_application_kotlin.di.AppScope;
import com.google.gson.Gson;

import javax.inject.Named;

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

    @Provides
    @AppScope
    public Gson provideGson (){
        Gson gson = new Gson();
        return gson;
    }

    @Provides
    @AppScope
    @Named("api")
    public Retrofit provideRetrofitForApi(OkHttpClient okHttpClient, Gson gson){
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://10.163.48.179:8080/webapi/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @AppScope
    @Named("database")
    public Retrofit provideRetrofitForDatabase(OkHttpClient okHttpClient, Gson gson){
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://10.163.48.179/map/v1/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @AppScope
    @Provides
    public ApiService provideApiService(@Named("api") Retrofit retrofit){
        ApiService apiService = retrofit.create(ApiService.class);
        return apiService;
    }

    @AppScope
    @Provides
    public DatabaseService provideDatabaseService(@Named("database") Retrofit retrofit){
        DatabaseService databaseService = retrofit.create(DatabaseService.class);
        return databaseService;
    }

}
