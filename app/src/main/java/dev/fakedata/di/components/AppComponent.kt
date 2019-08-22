package dev.fakedata.di.components

import dagger.Component
import dev.fakedata.App
import dev.fakedata.di.modules.NotificationsModule
import dev.fakedata.di.modules.RepositoryModule
import javax.inject.Singleton


@Singleton
@Component(modules = [RepositoryModule::class, NotificationsModule::class])
interface AppComponent {
    fun inject(app: App)
}