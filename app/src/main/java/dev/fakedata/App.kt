package dev.fakedata

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.View
import dev.fakedata.da.Repository
import dev.fakedata.di.components.DaggerAppComponent
import dev.fakedata.ui.utils.Notifications
import io.reactivex.plugins.RxJavaPlugins
import javax.inject.Inject

class App: @Inject Application() {
    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var notifications: Notifications

    private val mActivityLifecycleTracker: AppLifecycleTracker = AppLifecycleTracker()

    override fun onCreate() {
        super.onCreate()
        context = this

        DaggerAppComponent
            .builder()
            .build()
            .inject(this)

        // Global error handler for exceptions not caught in Rx.
        RxJavaPlugins.setErrorHandler { error ->
            notifications.displayErrorMessage(R.string.fatal_error)
        }
    }

    companion object {
        lateinit var context: App
    }

    fun displayErrorMessage(resId: Int) {
        notifications.displayErrorMessage(resId)
    }

    fun showErrorSnackbar(message: String) {
        notifications.showErrorSnackbar(message)
    }


    // Returns the current activity.
    var currentActivity: Activity?
        get() = mActivityLifecycleTracker.currentActivity
        private set(value) {}


    // Root view of the current activity.
    var currentActivityRootView: View?
        get() {
            if (mActivityLifecycleTracker.currentActivity == null)
                return null
            else {
                return mActivityLifecycleTracker.currentActivity!!.findViewById(R.id.nav_host_fragment) // .window.decorView.rootView
            }
        }
        set(value) {}


    /**
     * Callbacks for handling the lifecycle of activities.
     */
    class AppLifecycleTracker : Application.ActivityLifecycleCallbacks {

        private var mCurrentActivity: Activity? = null

        var currentActivity: Activity?
            get() = mCurrentActivity
            private set(value) {}

        override fun onActivityCreated(activity: Activity?, bundle: Bundle?) {
        }

        override fun onActivityStarted(activity: Activity?) {
        }

        override fun onActivityResumed(activity: Activity?) {
            mCurrentActivity = activity
        }

        override fun onActivityPaused(activity: Activity?) {
        }

        override fun onActivityStopped(activity: Activity?) {
            if ((mCurrentActivity != null) && (activity == mCurrentActivity))
                mCurrentActivity = null
        }


        override fun onActivityDestroyed(activity: Activity?) {
        }

        override fun onActivitySaveInstanceState(activity: Activity?, bundle: Bundle?) {
        }
    }
}