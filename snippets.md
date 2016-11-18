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
NetworkModule

```java
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
```

RedditActivity.java
```java
protected int getLayout() {
        return R.layout.activity_main;
    }

```
RedditMVPView.java
```java
public interface RedditMVPView extends MvpView {

    void showError();
    void showPosts(List<Post> posts);
}
```
RedditRecyclerView
```java
implements RedditMVPView
```
```java
public RedditRecyclerView(Context context) {
        this(context, null);
    }

    public RedditRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RedditRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ((BaseActivity) context)
                .getActivityComponent()
                .inject(this);
    }
```
```java
 @Inject
    PostAdapter postAdapter;

    @Inject
    RedditPresenter presenter;
```

```java
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        presenter.attachView(this);
        presenter.loadPosts();
        setOrientation();
        setAdapter(postAdapter);
    }

    @Override
    public void showPosts(List<Post> posts) {
        postAdapter.setPosts(posts);
    }

    @Override
    public void showError() {
        // do domething
    }
```

```java
@Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.detachView();
    }

```

```java
public PostViewHolder(View itemView) {
        super(itemView);
        performInjection(itemView);
        findViews(itemView);
        setMaxDimensions(itemView);
    }

    public void onBind(Post article) {
        title.setText(article.title());
        if (article.nestedThumbnail().isPresent()) {
            showImage(article);
        }
    }

    private void showImage(Post article) {
        Image image = mapToImage(article);
        BitmapTransform bitmapTransform = new BitmapTransform(maxWidth, maxHeight, image);
        setupview(bitmapTransform);

        Picasso.with(itemView.getContext())
                .load(image.url())
                .transform(bitmapTransform)
                .resize(bitmapTransform.targetWidth, bitmapTransform.targetHeight)
                .centerInside()
                .placeholder(R.color.gray80)
                .into(thumbnail);
    }
```
