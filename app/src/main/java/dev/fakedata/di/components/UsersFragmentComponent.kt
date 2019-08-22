package dev.fakedata.di.components

import dagger.Component
import dev.fakedata.di.modules.UsersFragmentModule
import dev.fakedata.di.modules.UsersModule
import dev.fakedata.ui.main.fragments.users.UsersFragment
import javax.inject.Singleton


@Singleton
@Component(modules = [UsersFragmentModule::class])
interface UsersFragmentComponent {
    fun inject(usersFragment: UsersFragment)
}