package com.swirl.pocketarcade.utils.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <A, B> LiveData<A>.combineWith(other: LiveData<B>): LiveData<Pair<A?, B?>> {
    val result = MediatorLiveData<Pair<A?, B?>>()
    result.addSource(this) { a -> result.value = Pair(a, other.value) }
    result.addSource(other) { b -> result.value = Pair(this.value, b) }
    return result
}
