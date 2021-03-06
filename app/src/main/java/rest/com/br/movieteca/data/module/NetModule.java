package rest.com.br.movieteca.data.module;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LUCAS RODRIGUES on 13/10/2017.
 */

@Module
public class NetModule {

    private final String baseUrl;

    public NetModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application){
        return PreferenceManager.getDefaultSharedPreferences( application );
    }

    @Provides
    @Singleton
    Cache providesHttpCache( Application application ){
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    Gson providesGson(){
        GsonBuilder gson = new GsonBuilder();
        gson.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES );
        return gson.create();
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient( Cache cache ){
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.cache( cache );
        return client.build();
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(Gson gson, OkHttpClient client){
        Retrofit retrofit = new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create( gson ))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create() )
                .client( client )
                .baseUrl( baseUrl )
                .build();
        return retrofit;
    }

}
