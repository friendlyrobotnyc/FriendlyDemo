package nyc.friendlyrobot.demo.data.model;

import android.support.annotation.Nullable;

import com.google.common.base.Optional;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Post implements PostModel {
    @Nullable
    public abstract Preview preview();

    abstract String title();

    abstract String url();

    @Nullable
    abstract Integer height();

    @Nullable
    abstract Integer width();

    @Value.Derived
    public Optional<Image> nestedThumbnail() {
        if (preview() == null || preview().images() == null || preview().images().get(0).source() == null)
            return Optional.absent();
        return Optional.of(preview().images().get(0).source());
    }

}
