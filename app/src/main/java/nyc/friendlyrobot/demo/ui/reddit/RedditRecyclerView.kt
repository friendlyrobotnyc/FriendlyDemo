package nyc.friendlyrobot.demo.ui.reddit

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import nyc.friendlyrobot.demo.ui.base.BaseActivity
import javax.inject.Inject

/**
 * Created by brianplummer on 12/16/15.
 */
class RedditRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet = null!!, defStyle: Int = 0) : RecyclerView(context, attrs, defStyle) {

    @Inject
    lateinit  var postAdapter: PostAdapter

    init {
        (context as BaseActivity).activityComponent.inject(this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        setOrientation()
        adapter = postAdapter
    }

    private fun setOrientation() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        setLayoutManager(layoutManager)
    }
}
