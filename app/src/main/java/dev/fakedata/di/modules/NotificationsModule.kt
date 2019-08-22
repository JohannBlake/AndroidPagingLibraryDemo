package dev.fakedata.di.modules

import dagger.Module
import dagger.Provides
import dev.fakedata.ui.utils.Notifications

@Module
object NotificationsModule {
    @JvmStatic
    @Provides
    fun provideNotifications() : Notifications {
        return Notifications()
    }
}