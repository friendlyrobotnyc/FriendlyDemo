package nyc.friendlyrobot.demo.ui.reddit

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import nyc.friendlyrobot.demo.androidboilerplate.R
import nyc.friendlyrobot.demo.data.model.Post
import java.util.*
import javax.inject.Inject

/**
 * Created by brianplummer on 12/16/15.
 */
class PostAdapter
@Inject
constructor() : RecyclerView.Adapter<PostViewHolder>() {

    private val articles = ArrayList<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView = LayoutInflater.from(
                parent.context).inflate(R.layout.article_item, parent, false)
        return PostViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.onBind(articles[position])
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    fun setPosts(articlesToAdd: List<Post>) {
        articles.clear()
        articles.addAll(articlesToAdd)
        notifyDataSetChanged()
    }

    fun addArticle(article: Post) {
        articles.add(article)
        notifyDataSetChanged()
    }
}
