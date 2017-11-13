package io.jassdev.jarvis.utils.rx

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Jackson on 01/11/2017.
 */

class IOAsync<T> : ObservableTransformer<T, T> {
    override fun apply(upstream: Observable<T>): ObservableSource<T> =
            upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}
