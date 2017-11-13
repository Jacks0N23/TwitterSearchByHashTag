package ru.andrroider.apps.twitter.model

import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.models.Search
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.andrroider.apps.twitter.api.CustomService
import ru.andrroider.apps.twitter.api.CustomTwitterApiClient


/**
 * Created by Jackson on 13/11/2017.
 */

class LoadingTwittsModel {

    private lateinit var searchService: CustomService

    private val searchSubject = PublishSubject.create<Observable<Search>>()

    init {
        setupTwitterCore()
    }

    private fun setupTwitterCore() {
        //можно было бы сделать лэзи инициализацию, но у твиттера внутри это и так сделано))
        searchService = (TwitterCore.getInstance().guestApiClient as CustomTwitterApiClient).customService
    }

    fun getSearchSubject(): Observable<Search> {
        return Observable.switchOnNext(searchSubject)
    }

    fun search(query: String, lastLoadedId: Long?) {
        searchSubject.onNext(searchTwittsByHashTag(query, lastLoadedId))
    }

    private fun searchTwittsByHashTag(query: String, lastLoadedId: Long?): Observable<Search> {
        return Observable.create<Search> {
            searchService.search(query, 30, lastLoadedId).enqueue(object : Callback<Search> {
                override fun onResponse(call: Call<Search>?, response: Response<Search>?) {
                    if (response?.isSuccessful!! && response.body()?.tweets?.isNotEmpty()!!)
                        it.onNext(response.body()!!)
                }

                override fun onFailure(call: Call<Search>?, t: Throwable?) {
                    it.onError(t!!)
                }
            })
        }.subscribeOn(Schedulers.io())
    }
}
