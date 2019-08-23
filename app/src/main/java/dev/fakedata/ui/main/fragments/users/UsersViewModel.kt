package dev.fakedata.ui.main.fragments.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import dev.fakedata.bo.Users
import dev.fakedata.bo.UsersAPIOptions
import dev.fakedata.da.datasources.UsersDataSourceFactory
import dev.fakedata.model.UserInfo
import javax.inject.Inject

class UsersViewModel @Inject constructor(private val users: Users) : ViewModel() {

    private var mAPIOptions = UsersAPIOptions()
    private var mLoadMore = false

    fun getUsersFromServer(): LiveData<PagedList<UserInfo>> {

        val config = PagedList.Config.Builder()
            .setPageSize(mAPIOptions.pageSize)
            .setEnablePlaceholders(false)
            .build()

        return LivePagedListBuilder(UsersDataSourceFactory(mAPIOptions), config).setBoundaryCallback(object : PagedList.BoundaryCallback<UserInfo>() {
            override fun onZeroItemsLoaded() {
                super.onZeroItemsLoaded()
                users.getUsersFromServer(mAPIOptions)
            }

            override fun onItemAtFrontLoaded(itemAtFront: UserInfo) {
                super.onItemAtFrontLoaded(itemAtFront)
            }

            override fun onItemAtEndLoaded(itemAtEnd: UserInfo) {
                super.onItemAtEndLoaded(itemAtEnd)
                users.getUsersFromServer(mAPIOptions)
            }
        }).build()
    }

//    lateinit var nonCachedUsers: LiveData<PagedList<UserInfo>>
//
//    fun getUsersFromServer() {
//        val config = PagedList.Config.Builder()
//            .setPageSize(20)
//            .setEnablePlaceholders(false)
//            .build()
//
//        nonCachedUsers = LivePagedListBuilder<Int, UserInfo>(UsersDataSourceFactory(mAPIOptions), config).build()
//    }


    fun getUsersFromLocalDB(): LiveData<PagedList<UserInfo>> {
        return LivePagedListBuilder(users.getUsersFromLocalDB(mAPIOptions), mAPIOptions.pageSize).setBoundaryCallback(object : PagedList.BoundaryCallback<UserInfo>() {
            override fun onZeroItemsLoaded() {
                super.onZeroItemsLoaded()
                users.getUsersFromServerAndCacheToLocalDB(mAPIOptions)
            }

            override fun onItemAtFrontLoaded(itemAtFront: UserInfo) {
                super.onItemAtFrontLoaded(itemAtFront)
            }

            override fun onItemAtEndLoaded(itemAtEnd: UserInfo) {
                super.onItemAtEndLoaded(itemAtEnd)
                users.getUsersFromServerAndCacheToLocalDB(mAPIOptions)
            }
        }).build()
    }


    fun totalPageItemsRetrieved(total: Int) {
        mAPIOptions.startPos += total

        if (mLoadMore) {
            mLoadMore = false
            users.getUsersFromServerAndCacheToLocalDB(mAPIOptions)
        }
    }

}
