package dev.fakedata.da.datasources

import androidx.paging.DataSource
import dev.fakedata.bo.UsersAPIOptions
import dev.fakedata.model.UserInfo

class UsersDataSourceFactory(private val options: UsersAPIOptions) : DataSource.Factory<Int, UserInfo>() {
    override fun create(): DataSource<Int, UserInfo> {
        return UsersDataSource(options)
    }
}