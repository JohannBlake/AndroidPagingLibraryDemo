package dev.fakedata.da

import android.annotation.SuppressLint
import androidx.paging.DataSource
import dev.fakedata.App
import dev.fakedata.R
import dev.fakedata.bo.UsersAPIOptions
import dev.fakedata.da.local.room.RoomDao
import dev.fakedata.da.web.FakeDataAPI
import dev.fakedata.model.UserInfo
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Acts as a single interface to data throughout the app.
 */
@Singleton
class Repository @Inject constructor(
    private val mFakeDataAPI: FakeDataAPI,
    private val mAppDao: RoomDao
) {
    fun getUsersFromLocalDB(options: UsersAPIOptions): DataSource.Factory<Int, UserInfo> {
        if (options.sortDesc)
            return mAppDao.getUsersDesc()
        else
            return mAppDao.getUsersAsc()
    }

    fun getUsersFromServer(options: UsersAPIOptions): Observable<List<UserInfo>> {
        return mFakeDataAPI.getUsers(if (options.useFacePhotos) "f" else "", options.startPos, options.pageSize, if (options.sortDesc) "desc" else "asc", options.imageSize)
    }

    @SuppressLint("CheckResult")
    fun getUsersFromServerAndCacheToLocalDB(options: UsersAPIOptions) {

        mFakeDataAPI.getUsers(if (options.useFacePhotos) "f" else "", options.startPos, options.pageSize, if (options.sortDesc) "desc" else "asc", options.imageSize)
            .doOnNext { users ->
                options.startPos += users.size
                mAppDao.storeUsers(users)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                },
                { ex ->
                    App.context.displayErrorMessage(R.string.problem_retrieving_users)
                }
            )
    }
}