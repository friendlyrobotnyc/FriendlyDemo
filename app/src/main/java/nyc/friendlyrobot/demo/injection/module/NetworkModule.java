package nyc.friendlyrobot.demo.injection.module;

import android.app.Application;
import android.support.annotation.NonNull;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nyc.friendlyrobot.demo.injection.ClientCache;
import nyc.friendlyrobot.demo.util.CacheInterceptor;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

@Module
public class NetworkModule {


    @Provides
    @NonNull
    @Singleton
    public OkHttpClient providClient(@ClientCache File cacheDir, CacheInterceptor interceptor) {
        final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        Cache cache = new Cache(cacheDir, 20 * 1024 * 1024);
        okHttpBuilder.cache(cache);
        okHttpBuilder.interceptors().add(interceptor); //needed for force network
        okHttpBuilder.networkInterceptors().add(interceptor); //needed for offline mode
        return okHttpBuilder.build();
    }

    @Singleton
    @Provides
    @ClientCache
    File provideCacheFile(Application context)     {
        return new File(context.getCacheDir(), "cache_file");
    }


}
