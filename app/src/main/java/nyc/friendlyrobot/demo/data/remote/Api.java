package nyc.friendlyrobot.demo.data.remote;

import nyc.friendlyrobot.demo.data.model.RedditData;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface Api {

    @GET("r/{subredditName}/new/.json")
    Observable<RedditData> fetchSubreddit(@Path("subredditName") String subredditName, @Query("limit") String limit, @Header("fresh") String fresh);
}
