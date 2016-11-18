package nyc.friendlyrobot.demo.ui.reddit;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import nyc.friendlyrobot.demo.data.model.Children;
import nyc.friendlyrobot.demo.data.model.Post;
import nyc.friendlyrobot.demo.data.model.RedditData;
import nyc.friendlyrobot.demo.data.store.RedditStore;
import nyc.friendlyrobot.demo.ui.base.BasePresenter;
import nyc.friendlyrobot.demo.util.FriendlyScheduler;
import rx.Observable;
import rx.Subscription;

public class RedditPresenter extends BasePresenter<RedditMVPView> {

    private Subscription subscription;
    private final RedditStore redditStore;

    @Inject
    public RedditPresenter(RedditStore redditStore) {
        this.redditStore = redditStore;
    }

    @Override
    public void attachView(RedditMVPView mvpView) {
        super.attachView(mvpView);
    }


    public void loadPosts() {
        checkViewAttached();
        subscription = redditStore.get("aww")
                .flatMap(this::sanitizeData)
                .toList()
                .compose(FriendlyScheduler.schedule())
                .subscribe(posts -> getMvpView().showPosts(posts),
                        throwable -> getMvpView().showError());
    }

    @NonNull
    private Observable<Post> sanitizeData(RedditData redditData) {
        return Observable.from(redditData.data().children())
                .map(Children::data)
                .filter(post -> post.nestedThumbnail().isPresent());
    }

    @Override
    public void detachView() {
        super.detachView();
        if (subscription != null) subscription.unsubscribe();
    }

}
