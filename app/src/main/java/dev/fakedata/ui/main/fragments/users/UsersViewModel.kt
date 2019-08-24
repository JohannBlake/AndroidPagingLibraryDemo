package dev.fakedata.ui.main.fragments.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import dev.fakedata.bo.Users
import dev.fakedata.bo.UsersAPIOptions
import dev.fakedata.da.datasources.UsersDataSourceFactory
import dev.fakedata.model.UserInfo
import javax.inject.Inject

class UsersViewModel @Inject constructor(private val users: Users) : ViewModel() {

    var apiOptions = UsersAPIOptions()
    private var mLoadMore = false

    var onLoadDataFromServer: LiveData<PagedList<UserInfo>> = MutableLiveData()
    var onLoadDataFromLocalDB: LiveData<PagedList<UserInfo>> = MutableLiveData()

    fun getUsersFromServer() {

        val config = PagedList.Config.Builder()
            .setPageSize(apiOptions.pageSize)
            .setEnablePlaceholders(false)
            .build()

        onLoadDataFromServer = LivePagedListBuilder(UsersDataSourceFactory(apiOptions), config).setBoundaryCallback(object : PagedList.BoundaryCallback<UserInfo>() {
            override fun onZeroItemsLoaded() {
                super.onZeroItemsLoaded()
                users.getUsersFromServer(apiOptions)
            }

            override fun onItemAtFrontLoaded(itemAtFront: UserInfo) {
                super.onItemAtFrontLoaded(itemAtFront)
            }

            override fun onItemAtEndLoaded(itemAtEnd: UserInfo) {
                super.onItemAtEndLoaded(itemAtEnd)
                users.getUsersFromServer(apiOptions)
            }
        }).build()
    }


    fun getUsersFromLocalDB() {
        onLoadDataFromLocalDB = LivePagedListBuilder(users.getUsersFromLocalDB(apiOptions), apiOptions.pageSize).setBoundaryCallback(object : PagedList.BoundaryCallback<UserInfo>() {
            override fun onZeroItemsLoaded() {
                super.onZeroItemsLoaded()
                users.getUsersFromServerAndCacheToLocalDB(apiOptions)
            }

            override fun onItemAtFrontLoaded(itemAtFront: UserInfo) {
                super.onItemAtFrontLoaded(itemAtFront)
            }

            override fun onItemAtEndLoaded(itemAtEnd: UserInfo) {
                super.onItemAtEndLoaded(itemAtEnd)
                users.getUsersFromServerAndCacheToLocalDB(apiOptions)
            }
        }).build()
    }


    fun totalPageItemsRetrieved(total: Int) {
        apiOptions.startPos += total

        if (mLoadMore) {
            mLoadMore = false
            users.getUsersFromServerAndCacheToLocalDB(apiOptions)
        }
    }

}
