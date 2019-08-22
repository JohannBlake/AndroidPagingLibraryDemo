package dev.fakedata.ui.main.fragments.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.fakedata.bo.Users

class UsersViewModelFactory(
    private val users: Users
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UsersViewModel(users) as T
    }
}