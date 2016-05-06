package nyc.friendlyrobot.demo.injection

import android.app.Activity

import nyc.friendlyrobot.demo.DemoApplication
import nyc.friendlyrobot.demo.injection.component.ActivityComponent
import nyc.friendlyrobot.demo.injection.module.ActivityModule

object ActivityComponentFactory {

    fun create(activity: Activity): ActivityComponent {
        return DemoApplication.get(activity).component.plus(ActivityModule(activity))
    }
}//no op
