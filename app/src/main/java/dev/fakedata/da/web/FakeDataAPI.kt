package dev.fakedata.da.web

import dev.fakedata.model.UserInfo
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*


/**
 * Retrofit API declarations. Pay attention to how paths are declared. Some require forward slashes at the start of the
 * path while others do not. Adding or not adding a slash at the start can cause the api to fail.
 */
interface FakeDataAPI {

    /**
     * Get users from the list of 1,000 non-random users.
     */
    @GET("get_users")
    fun getUsers(
        @Query("sp") startPos: Int, @Query("ps") pageSize: Int, @Query("sd") sortDirection: String, @Query("is") imageSize: Int
    ): Observable<List<UserInfo>>
}