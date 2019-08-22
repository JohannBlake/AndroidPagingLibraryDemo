package dev.fakedata.bo

import dev.fakedata.App
import dev.fakedata.model.UserInfo
import io.reactivex.Observable

class Users : BaseBusinessObject() {
    fun getUsersFromLocalDB(options: UsersAPIOptions) = App.context.repository.getUsersFromLocalDB(options)

    fun getUsersFromServer(options: UsersAPIOptions) {
        App.context.repository.getUsersFromServer(options)
    }
}