package nyc.friendlyrobot.demo.ui.reddit;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.List;

import javax.inject.Inject;

import nyc.friendlyrobot.demo.data.model.Post;
import nyc.friendlyrobot.demo.ui.base.BaseActivity;

public class RedditRecyclerView extends RecyclerView implements RedditMVPView {

    @Inject
    PostAdapter postAdapter;

    @Inject
    RedditPresenter presenter;

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
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.detachView();
    }

    private void setOrientation() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        setLayoutManager(layoutManager);
    }

    @Override
    public void showError() {
        // do domething
    }

}
