package ru.andrroider.apps.twitter

import android.app.Application
import android.util.Log
import com.twitter.sdk.android.core.*
import ru.andrroider.apps.twitter.api.CustomTwitterApiClient

/**
 * Created by Jackson on 13/11/2017.
 */

class TwitterApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val config = TwitterConfig.Builder(this)
                .logger(DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(TwitterAuthConfig(
                        getString(R.string.com_twitter_sdk_android_CONSUMER_KEY),
                        getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)))
                .debug(true)
                .build()
        Twitter.initialize(config)
        TwitterCore.getInstance().addGuestApiClient(CustomTwitterApiClient())
    }
}
