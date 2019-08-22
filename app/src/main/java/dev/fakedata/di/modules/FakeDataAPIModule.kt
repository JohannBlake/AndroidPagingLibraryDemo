package dev.fakedata.di.modules

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dev.fakedata.da.web.FAKE_DATA_BASE_USERS_ADDRESS
import dev.fakedata.da.web.FakeDataAPI
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/**
 * Provides a Retrofit for accessing content on the backend.
 */
@Module
class FakeDataAPIModule {

    @Provides
    @Reusable
    fun provideRetrofitForLinkedIn(): FakeDataAPI {
        return Retrofit.Builder()
            .baseUrl(FAKE_DATA_BASE_USERS_ADDRESS)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(initializeRetrofit())
            .build()
            .create(FakeDataAPI::class.java)
    }

    private fun initializeRetrofit() : OkHttpClient {

        val httpClient = OkHttpClient.Builder()
        val client = httpClient.build()
        return client
    }
}