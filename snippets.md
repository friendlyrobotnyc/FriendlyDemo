``` java
Posts.java
@Nullable
    public abstract Preview preview();

    public abstract String title();

    public abstract String url();

    @Nullable
    public abstract Integer height();

    @Nullable
    public abstract Integer width();

    @Value.Derived
    public Optional<Image> nestedThumbnail() {
        if (preview() == null || preview().images() == null || preview().images().get(0).source() == null)
            return Optional.absent();
        return Optional.of(preview().images().get(0).source());
    }
```

```Java
Api.java
    @GET("r/{subredditName}/new/.json")
    Observable<RedditData> fetchSubreddit(@Path("subredditName") String subredditName, @Query("limit") String limit, @Header("fresh") String fresh);
 ```   
 
 ```Java
 RedditStore.java
  @Inject
    Lazy<Api> api;

    @Inject
    public RedditStore() {

    }

    protected Observable<RedditData> fetch(String redditName,String forceNetwork) {
        return api.get().fetchSubreddit(redditName, "50", forceNetwork);
    }
```

ApplicationModule.java
```java
 @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(new GsonAdaptersModel())
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
    }
```

```java

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

```

``` java

    @Provides
    @NonNull
    @Singleton
    public Picasso providePicasso(@NonNull Application application, @NonNull OkHttpClient okHttpClient) {
        return new Picasso.Builder(application)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
    }
```
