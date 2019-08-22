package dev.fakedata.da.local.room

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.fakedata.model.UserInfo

const val sqlGetUsers = "SELECT * FROM Users ORDER BY firstName COLLATE NOCASE "

@Dao
interface RoomDao {
    // Get users in ascending order.
    @Query(sqlGetUsers + "ASC")
    fun getUsersAsc(): DataSource.Factory<Int, UserInfo>

    // Get users in descending order.
    @Query(sqlGetUsers + "DESC")
    fun getUsersDesc(): DataSource.Factory<Int, UserInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun storeUsers(users: List<UserInfo>)
}
