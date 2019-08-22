package dev.fakedata.di.modules

import dagger.Module
import dagger.Provides
import dev.fakedata.da.Repository
import dev.fakedata.da.local.room.RoomDao
import dev.fakedata.da.web.FakeDataAPI
import javax.inject.Singleton

@Module(includes = [FakeDataAPIModule::class, RoomDaoModule::class])
class RepositoryModule {
    @Provides
    @Singleton
    fun provideRepositoryImpl(fakeDataApi: FakeDataAPI, roomDao: RoomDao): Repository {
        return Repository(fakeDataApi, roomDao)
    }
}