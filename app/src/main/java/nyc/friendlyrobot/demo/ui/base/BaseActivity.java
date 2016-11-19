package nyc.friendlyrobot.demo.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import nyc.friendlyrobot.demo.androidboilerplate.R;
import nyc.friendlyrobot.demo.injection.ActivityComponentFactory;
import nyc.friendlyrobot.demo.injection.component.ActivityComponent;

public abstract class BaseActivity extends AppCompatActivity {

    protected ActivityComponent activityComponent;


    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = ActivityComponentFactory.create(this);
        }
        return activityComponent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }



    protected abstract int getLayout();
}
