package ru.andrroider.apps.twitter.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.twitter.sdk.android.core.models.Tweet
import kotlinx.android.synthetic.main.activity_main.*
import ru.andrroider.apps.twitter.R
import ru.andrroider.apps.twitter.adapter.TwittsAdapter
import ru.andrroider.apps.twitter.presenter.SearchTwittsPresenter
import ru.andrroider.apps.twitter.utils.ui.EndlessRecyclerOnScrollListener

class MainActivity : MvpAppCompatActivity(), SearchView {

    @InjectPresenter
    lateinit var presenter: SearchTwittsPresenter

    private lateinit var llm: LinearLayoutManager
    private val twittsAdapter = TwittsAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        llm = LinearLayoutManager(this)
        setupRecyclerView()
        twittsList.addOnScrollListener(object : EndlessRecyclerOnScrollListener(llm) {
            override fun onLoadMore() {
                presenter.doSearch(searchView.text.toString())
            }
        })
        setupSearchView()
    }

    private fun setupSearchView() {
        setInitialValue()
        searchView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(searchQuery: Editable?) {
                if (!searchQuery.isNullOrBlank()) {
                    presenter.doSearch(searchQuery.toString())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.length == 0) {
                    setInitialValue()
                }
            }
        })

    }

    fun setInitialValue() {
        searchView.setText("#")
        searchView.setSelection(1)
    }

    private fun setupRecyclerView() {
        twittsList.layoutManager = llm
        twittsList.setHasFixedSize(true)
        twittsList.adapter = twittsAdapter
    }

    override fun onTweetsLoaded(list: List<Tweet>) {
        twittsAdapter.setList(list)
        //а тут можно сделать через DiffUtils но мне лень ( как говорится вообще я супермен, но сейчас не хочу)
        twittsAdapter.notifyDataSetChanged()
    }

    override fun onLoadingFailed(error: Throwable) {
        Log.e("MainActivity", "error ", error)
    }
}
