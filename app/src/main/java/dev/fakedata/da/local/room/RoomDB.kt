package dev.fakedata.da.local.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.fakedata.App
import dev.fakedata.model.UserInfo

@Database(entities = arrayOf(UserInfo::class), version = 1, exportSchema = true)
abstract class RoomDB : RoomDatabase() {
    abstract fun roomDao(): RoomDao

    companion object {
        fun getInstance(): RoomDB =
            Room.databaseBuilder(App.context, RoomDB::class.java, "FakeDataDB")
                .addCallback(object : RoomDatabase.Callback() {
                }).build()
    }
}