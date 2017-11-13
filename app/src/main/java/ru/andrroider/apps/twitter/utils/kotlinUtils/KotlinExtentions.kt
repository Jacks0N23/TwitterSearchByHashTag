package io.jassdev.jarvis.utils.kotlinUtils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Jackson on 11/11/2017.
 */

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}