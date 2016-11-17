package nyc.friendlyrobot.demo.ui.reddit;

import java.util.List;

import nyc.friendlyrobot.demo.data.model.Post;
import nyc.friendlyrobot.demo.ui.base.MvpView;

public interface RedditMVPView extends MvpView {

    void showError();
    void showPosts(List<Post> posts);
}
