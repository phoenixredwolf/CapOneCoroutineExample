package com.example.caponecoroutineexample.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caponecoroutineexample.data.repository.DataRepository
import com.example.caponecoroutineexample.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(repo: DataRepository) : ViewModel() {

    var tag= ""
    var imageFilter= ""
    var imageText= ""
    var textColor= ""
    var textSize = ""
    val _cats = MutableLiveData<Resource<List<String>>>()
    val cats: LiveData<Resource<List<String>>> get() = _cats
    val dataRepo = repo

    fun getCats() {
        if (tag.isEmpty() && imageText.isEmpty()) {
            getCat()
        } else if (imageText.isEmpty()) {
            getCatWithTag()
        } else if (tag.isEmpty()) {
            getCatWithText()
        } else {
            getCatWithAll()
        }
    }

    private fun getCat() {
        _cats.value = Resource.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val catResource = dataRepo.getCat(true, imageFilter.lowercase(Locale.getDefault()))
            _cats.postValue(catResource)
        }
    }

    private fun getCatWithText() {
        _cats.value = Resource.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val catResource = dataRepo.getCatWithText(imageText, true, textSize, textColor, imageFilter.lowercase(
                Locale.getDefault()))
            _cats.postValue(catResource)
        }
    }

    private fun getCatWithAll() {
        _cats.value = Resource.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val catResource = dataRepo.getCatWithAll(tag, imageText, true, textColor, textSize, imageFilter.lowercase(
                Locale.getDefault()))
            _cats.postValue(catResource)
        }
    }

    private fun getCatWithTag() {
        _cats.value = Resource.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val catResource = dataRepo.getCatWithTag(tag, true, imageFilter.lowercase(Locale.getDefault()))
            _cats.postValue(catResource)
        }
    }

}