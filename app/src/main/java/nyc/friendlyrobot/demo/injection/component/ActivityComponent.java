package nyc.friendlyrobot.demo.injection.component;

import dagger.Subcomponent;
import nyc.friendlyrobot.demo.injection.ScopeActivity;
import nyc.friendlyrobot.demo.injection.module.ActivityModule;
import nyc.friendlyrobot.demo.ui.reddit.PostViewHolder;
import nyc.friendlyrobot.demo.ui.reddit.RedditActivity;
import nyc.friendlyrobot.demo.ui.reddit.RedditRecyclerView;

/**
 * This component inject dependencies to all Activities across the application
 */
@Subcomponent(modules = {
        ActivityModule.class})
@ScopeActivity
public interface ActivityComponent {

    void inject(RedditActivity activity);
    void inject(RedditRecyclerView recyclerView);
    void inject(PostViewHolder holder);
}
