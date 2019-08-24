package dev.fakedata.da.datasources

import android.annotation.SuppressLint
import androidx.paging.PositionalDataSource
import dev.fakedata.App
import dev.fakedata.R
import dev.fakedata.bo.UsersAPIOptions
import dev.fakedata.model.UserInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UsersDataSource(private val options: UsersAPIOptions) : PositionalDataSource<UserInfo>() {

    @SuppressLint("CheckResult")
    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<UserInfo>) {
        App.context.repository.getUsersFromServer(options)
            .doOnNext { users ->
                options.startPos += users.size
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { users ->
                    callback.onResult(users)
                },
                { ex ->
                    App.context.displayErrorMessage(R.string.problem_retrieving_users)
                }
            )
    }

    @SuppressLint("CheckResult")
    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<UserInfo>) {
        App.context.repository.getUsersFromServer(options)
            .doOnNext { users ->
                options.startPos += users.size
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { users ->
                    callback.onResult(users, 0)
                },
                { ex ->
                    App.context.displayErrorMessage(R.string.problem_retrieving_users)
                }
            )
    }
}