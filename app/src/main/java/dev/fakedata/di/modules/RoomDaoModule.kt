package dev.fakedata.di.modules

import dagger.Module
import dagger.Provides
import dev.fakedata.da.local.room.RoomDB
import dev.fakedata.da.local.room.RoomDao

@Module
class RoomDaoModule {
    @Provides
    fun providesRoomDao(): RoomDao {
        return RoomDB.getInstance().roomDao()
    }
}