package com.example.taxigofordriver

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

fun <T> LiveData<T>.getOrAwaitValue(): T {
    var observedValue: T? = null
    val latch = CountDownLatch(1)

    val observer = Observer<T> { value ->
        observedValue = value
        latch.countDown()
    }

    this.observeForever(observer)

    try {
        latch.await(2, TimeUnit.SECONDS)
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }

    return observedValue ?: throw IllegalStateException("LiveData value was not set.")
}