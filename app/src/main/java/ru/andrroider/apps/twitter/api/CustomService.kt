package ru.andrroider.apps.twitter.api

import com.twitter.sdk.android.core.models.Search
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CustomService {
    @GET("/1.1/search/tweets.json?" + "lang=ru&result_type=mixed")
    fun search(@Query("q") query: String, @Query("count") count: Int, @Query("max_id")
    since_id: Long?): Call<Search> //кто до сих пор пользуется этим, твиттер??? все уже на rx пишут………
    //а возможности подпихнуть свою версию ретрофита или хотя бы rx factory - нет
}