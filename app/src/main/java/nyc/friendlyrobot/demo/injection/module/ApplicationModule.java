package nyc.friendlyrobot.demo.injection.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;
import com.squareup.sqlbrite.SqlBrite;

import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nyc.friendlyrobot.demo.androidboilerplate.BuildConfig;
import nyc.friendlyrobot.demo.data.model.GsonAdaptersModel;
import nyc.friendlyrobot.demo.data.remote.Api;
import nyc.friendlyrobot.demo.util.DateDeserializer;
import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import timber.log.Timber;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mApplication;
    }


    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(new GsonAdaptersModel())
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
    }

    @Provides
    @NonNull
    @Singleton
    public Api provideApi(@NonNull OkHttpClient okHttpClient,
                             @NonNull Gson gson) {

        return new Retrofit.Builder()
                .baseUrl("http://reddit.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .validateEagerly(BuildConfig.DEBUG)  // Fail early: check Retrofit configuration at creation time in Debug build.
                .build()
                .create(Api.class);
    }

    @Provides
    @NonNull
    @Singleton
    public Picasso providePicasso(@NonNull Application application, @NonNull OkHttpClient okHttpClient) {
        return new Picasso.Builder(application)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
    }
}
