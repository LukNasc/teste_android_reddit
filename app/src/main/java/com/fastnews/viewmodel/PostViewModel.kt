package com.fastnews.viewmodel

import android.app.Application
import androidx.annotation.UiThread
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fastnews.mechanism.Coroutines
import com.fastnews.repository.PostRepository
import com.fastnews.service.model.PostData
import com.fastnews.service.model.PostDataChild

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var data: MutableLiveData<PostDataChild>

    @UiThread
    fun getPosts(after: String, limit: Int): LiveData<PostDataChild> {
            if (!::data.isInitialized) {
                data = MutableLiveData()

                Coroutines.ioThenMain({
                    PostRepository.getPosts(after, limit)
                }) {
                    data.postValue(it)
                }
            }
        return data
    }

}