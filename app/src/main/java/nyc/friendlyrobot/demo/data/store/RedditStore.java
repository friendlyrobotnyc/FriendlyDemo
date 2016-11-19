package nyc.friendlyrobot.demo.data.store;

import nyc.friendlyrobot.demo.data.model.RedditData;
import nyc.friendlyrobot.demo.data.store.base.BaseStore;
import rx.Observable;


public class RedditStore extends BaseStore<RedditData, String> {

    @Override
    protected Observable<RedditData> fetch(String s, String forceNetwork) {
        return null;
    }
}
