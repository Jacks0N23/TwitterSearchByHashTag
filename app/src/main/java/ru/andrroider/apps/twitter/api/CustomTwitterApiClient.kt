package ru.andrroider.apps.twitter.api

import com.twitter.sdk.android.core.TwitterApiClient

class CustomTwitterApiClient : TwitterApiClient() {

    val customService: CustomService
        get() = getService(CustomService::class.java)
}