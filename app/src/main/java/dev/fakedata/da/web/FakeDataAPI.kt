package dev.fakedata.da.web

import dev.fakedata.model.UserInfo
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*


/**
 * Retrofit API declarations.
 * NOTE: If a path starts with a forward slash, it means that it is relative to the root domain. Without a prefixed forward slash, the path is appended to whatever
 * the base url is set to.
 */
interface FakeDataAPI {

    /**
     * Retrieves a list of users. If the "av" parameter is set, up to 1,000 users are retrieved with photos of actual faces. If not set, up to 1 million users
     * will be retrieved with photos being a mixture of real phases and avatars.
     */
    @GET("get_users")
    fun getUsers(
        @Query("av") avatarsToUse: String, @Query("sp") startPos: Int, @Query("ps") pageSize: Int, @Query("sd") sortDirection: String, @Query("is") imageSize: Int
    ): Observable<List<UserInfo>>
}