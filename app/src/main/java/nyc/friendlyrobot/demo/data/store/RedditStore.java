package nyc.friendlyrobot.demo.data.store;

import android.app.Application;

import javax.inject.Inject;

import dagger.Lazy;
import nyc.friendlyrobot.demo.data.model.RedditData;
import nyc.friendlyrobot.demo.data.remote.Api;
import nyc.friendlyrobot.demo.data.store.base.BaseStore;
import rx.Observable;


public class RedditStore extends BaseStore<RedditData, String> {
    @Inject
    Lazy<Api> api;

    @Inject
    Application application;


    @Inject
    public RedditStore() {

    }

    protected Observable<RedditData> fetch(String redditName,String forceNetwork) {
        return api.get().fetchSubreddit(redditName, "50", forceNetwork);
    }
}
