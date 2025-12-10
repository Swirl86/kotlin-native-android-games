package com.swirl.pocketarcade.utils.components

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * Combines two LiveData sources into a single LiveData.
 *
 * The resulting LiveData updates whenever either source changes,
 * using the latest values from both inputs and applying the provided
 * combine function.
 *
 * @param a The first LiveData source.
 * @param b The second LiveData source.
 * @param combine A function that takes the latest values from both sources
 * and returns the combined result.
 *
 * @return A LiveData that updates with the combined output.
 */
fun <A, B, R> combineLiveData(
    a: LiveData<A>,
    b: LiveData<B>,
    combine: (A?, B?) -> R
): LiveData<R> {
    val result = MediatorLiveData<R>()

    val update = {
        result.value = combine(a.value, b.value)
    }

    result.addSource(a) { update() }
    result.addSource(b) { update() }

    return result
}

/**
 * Combines three LiveData sources into a single LiveData containing a Triple.
 *
 * The resulting LiveData emits a Triple containing the latest values from
 * all three inputs whenever any of the sources change.
 *
 * @param a The first LiveData source.
 * @param b The second LiveData source.
 * @param c The third LiveData source.
 *
 * @return A LiveData that emits a Triple with the latest values of a, b, and c.
 */
fun <A, B, C> combineLiveData(
    a: LiveData<A>,
    b: LiveData<B>,
    c: LiveData<C>
): LiveData<Triple<A?, B?, C?>> {

    val result = MediatorLiveData<Triple<A?, B?, C?>>()

    val update = {
        result.value = Triple(a.value, b.value, c.value)
    }

    result.addSource(a) { update() }
    result.addSource(b) { update() }
    result.addSource(c) { update() }

    return result
}