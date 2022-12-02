package com.ahugenb.acroandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahugenb.acroandroid.R
import com.ahugenb.acroandroid.api.AcroRepo
import kotlinx.coroutines.*
import java.io.EOFException

class AcroViewModel(private val repo: AcroRepo) : ViewModel() {
    val acronym = MutableLiveData("")
    val error = MutableLiveData(0)

    private val exceptionHandler = CoroutineExceptionHandler{ _ , throwable ->
        if (throwable is EOFException) {
            error.postValue(R.string.tv_no_results)
        } else {
            acronym.postValue("")
            error.postValue(R.string.tv_network_failure)
        }
    }

    fun getAcronyms(shortForm: String) {
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repo.getAcronyms(shortForm)
            withContext(Dispatchers.Main) {
                var strs = ""
                if (response.isSuccessful) {
                    response.body()?.forEach {
                        it.lfs.forEach { longForm ->
                            strs = strs.plus(longForm.lf.toString().plus("\n"))
                        }
                    }
                    if (strs == "") {
                        error.postValue(R.string.tv_no_results)
                    } else {
                        error.postValue(0)
                    }
                } else {
                    error.postValue(R.string.tv_network_failure)
                }
                acronym.postValue(strs)
            }
        }
    }
}