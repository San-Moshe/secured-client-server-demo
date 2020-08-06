package com.sl.il.src.ui.pic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sl.il.src.backend.api.PicApi
import com.sl.il.src.backend.model.MeiziPic
import com.sl.il.src.base.BaseViewModel
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PicViewModel @Inject constructor(
    private val picApi: PicApi
) : BaseViewModel() {
    val liveDataPic: LiveData<MeiziPic> = MutableLiveData()

    init {
        fetchOnePic()
    }

    fun fetchOnePic() {
        liveDataPic as MutableLiveData
        picApi.getMeiziPics(1, (1..10).random())
            .subscribeOn(Schedulers.io())
            .autoDisposable()
            .subscribe { response ->
                liveDataPic.postValue(response.results[0])
            }
    }
}
