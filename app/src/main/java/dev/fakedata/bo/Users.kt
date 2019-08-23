package dev.fakedata.bo

import dev.fakedata.App

class Users : BaseBusinessObject() {
    fun getUsersFromLocalDB(options: UsersAPIOptions) = App.context.repository.getUsersFromLocalDB(options)

    fun getUsersFromServerAndCacheToLocalDB(options: UsersAPIOptions) {
        App.context.repository.getUsersFromServerAndCacheToLocalDB(options)
    }

    fun getUsersFromServer(options: UsersAPIOptions) {
        App.context.repository.getUsersFromServer(options)
    }
}