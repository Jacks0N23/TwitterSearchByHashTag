package ru.andrroider.apps.twitter.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.disposables.CompositeDisposable
import ru.andrroider.apps.twitter.model.LoadingTwittsModel
import ru.andrroider.apps.twitter.view.SearchView
import java.util.*

/**
 * Created by Jackson on 13/11/2017.
 */

@InjectViewState
class SearchTwittsPresenter : MvpPresenter<SearchView>() {

    private val model = LoadingTwittsModel()

    private var nextMaxId: Long? = null

    private var lastQuery = "#"

    private var lastLoadedList = LinkedList<Tweet>()

    private val disposable = CompositeDisposable()

    init {
        subscribeToResults()
        doSearch(lastQuery)
    }

    private fun subscribeToResults() {
        disposable.add(model.getSearchSubject().subscribe({
            it.searchMetadata.nextResults?.split("&")?.filter { it.contains("max_id") }?.forEach {
                val maxId = it.split("=")
                nextMaxId = maxId[1].toLong()
            }
            if (lastLoadedList != it.tweets) {
                lastLoadedList.addAll(it.tweets)
                viewState.onTweetsLoaded(lastLoadedList)
            }
        }, {
            viewState.onLoadingFailed(it)
            subscribeToResults()
        }))
    }

    fun doSearch(query: String) {
        if (lastQuery != query) {
            nextMaxId = null
            lastLoadedList.clear()
        }
        lastQuery = query
        model.search(lastQuery, nextMaxId)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}
