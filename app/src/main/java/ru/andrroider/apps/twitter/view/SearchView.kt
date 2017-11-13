package ru.andrroider.apps.twitter.view

import com.arellomobile.mvp.MvpView
import com.twitter.sdk.android.core.models.Tweet

/**
 * Created by Jackson on 13/11/2017.
 */
interface SearchView : MvpView {
    fun onTweetsLoaded(list: List<Tweet>)
    fun onLoadingFailed(error: Throwable)
}