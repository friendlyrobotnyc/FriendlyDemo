package nyc.friendlyrobot.demo.ui.reddit

import nyc.friendlyrobot.demo.data.model.Post
import nyc.friendlyrobot.demo.interaction.RedditReader
import nyc.friendlyrobot.demo.ui.base.BasePresenter
import nyc.friendlyrobot.demo.util.FriendlyScheduler
import rx.Subscription
import javax.inject.Inject

class RedditPresenter
@Inject
constructor(private val redditReader: RedditReader) : BasePresenter<RedditMVPView>() {
    private var subscription: Subscription? = null

    override fun attachView(mvpView: RedditMVPView) {
        super.attachView(mvpView)
    }

    override fun detachView() {
        super.detachView()
        if (subscription != null) subscription!!.unsubscribe()
    }

    fun loadPosts() {
        checkViewAttached()
        subscription = redditReader.readPosts().compose(FriendlyScheduler.schedule<List<Post>>()).subscribe({ posts -> mvpView.showPosts(posts) }
        ) { throwable -> mvpView.showError() }
    }

}
