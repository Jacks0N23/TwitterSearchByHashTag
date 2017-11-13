package ru.andrroider.apps.twitter.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.tweetui.TweetView
import io.jassdev.jarvis.utils.kotlinUtils.inflate
import ru.andrroider.apps.twitter.R

/**
 * Created by Jackson on 13/11/2017.
 */


class TwittsAdapter : RecyclerView.Adapter<TwittsAdapter.TwittsViewHolder>() {

    var twitts = emptyList<Tweet>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) =
            TwittsViewHolder(parent?.inflate(R.layout.twitts_view_holder)!!)

    override fun onBindViewHolder(holder: TwittsViewHolder?, position: Int) {
        holder?.tweet?.tweet = twitts[position]
    }

    override fun getItemCount(): Int = twitts.size

    fun setList(newElements: List<Tweet>) {
        twitts = newElements
    }

    inner class TwittsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tweet: TweetView = itemView.findViewById(R.id.tweet)
    }

}
