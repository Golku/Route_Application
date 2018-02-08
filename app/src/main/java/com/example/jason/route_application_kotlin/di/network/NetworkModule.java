package com.example.jason.route_application_kotlin.di.network;

import com.example.jason.route_application_kotlin.di.AppScope;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

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
    public Retrofit provideRetrofit(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("")
                .build();
    }

}
