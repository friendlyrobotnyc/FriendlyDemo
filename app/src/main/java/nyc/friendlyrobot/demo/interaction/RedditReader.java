package nyc.friendlyrobot.demo.interaction;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import nyc.friendlyrobot.demo.data.model.Children;
import nyc.friendlyrobot.demo.data.model.Post;
import nyc.friendlyrobot.demo.data.model.RedditData;
import nyc.friendlyrobot.demo.data.store.RedditStore;
import rx.Observable;
import rx.schedulers.Schedulers;

public class RedditReader extends Interaction {
    public static final String LIMIT = "50";

    private final RedditStore store;

    @Inject
    public RedditReader(RedditStore store) {
        this.store = store;
    }

    public Observable<List<Post>> posts() {
        return store.get(LIMIT)
                .subscribeOn(Schedulers.io())
                .flatMap(this::posts)
                .toList();

    }

    @NonNull
    private Observable<Post> posts(RedditData redditData) {
        return Observable.from(redditData.data().children()).map(Children::data);
    }
}
