package nyc.friendlyrobot.demo.data.store.base;

import android.support.annotation.NonNull;

import rx.Observable;

public interface Store<T, V> {
    Observable<T> get(@NonNull V requestObject);

    Observable<T> fresh(@NonNull V requestObject);
}
