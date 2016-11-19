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















    private void setLayoutmanager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        setLayoutManager(layoutManager);
    }



}
