package com.swirl.pocketarcade.utils.components

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

class CombineLiveData<Input1, Input2, Output>(
    source1: LiveData<Input1>,
    source2: LiveData<Input2>,
    private val combine: (Input1?, Input2?) -> Output
) : MediatorLiveData<Output>() {

    private var data1: Input1? = null
    private var data2: Input2? = null

    init {
        addSource(source1) { value ->
            data1 = value
            this.value = combine(data1, data2)
        }
        addSource(source2) { value ->
            data2 = value
            this.value = combine(data1, data2)
        }
    }
}
