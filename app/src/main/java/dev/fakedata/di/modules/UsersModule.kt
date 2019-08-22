package dev.fakedata.di.modules

import dagger.Module
import dagger.Provides
import dev.fakedata.bo.Users

@Module
object UsersModule {
    @JvmStatic
    @Provides
    fun provideUsers() : Users {
        return Users()
    }
}