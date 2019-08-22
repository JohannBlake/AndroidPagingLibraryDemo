package dev.fakedata.di.modules

import dagger.Module
import dagger.Provides
import dev.fakedata.bo.Users
import dev.fakedata.ui.main.fragments.users.UsersViewModelFactory

@Module(includes = [UsersModule::class])
class UsersFragmentModule {
    @Provides
    fun providesViewModelFactory(users: Users): UsersViewModelFactory {
        return UsersViewModelFactory(users)
    }
}