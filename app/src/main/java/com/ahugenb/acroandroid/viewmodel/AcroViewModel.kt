package com.ahugenb.acroandroid.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahugenb.acroandroid.api.AcroRepo
import kotlinx.coroutines.*
import java.io.EOFException

class AcroViewModel(val repo: AcroRepo) : ViewModel() {
    val errorMessage = MutableLiveData<ErrorState>()
    val acronymList = MutableLiveData<List<String>>()
    var job: Job? = null

    enum class ErrorState {
        ERROR_STATE_NO_RESULTS,
        ERROR_STATE_NETWORK_FAILURE,
        ERROR_STATE_NONE
    }

    val exceptionHandler = CoroutineExceptionHandler{ _ , throwable ->
        if (throwable is EOFException) {
            errorMessage.postValue(ErrorState.ERROR_STATE_NO_RESULTS)
        } else {
            Log.e("Error in $TAG", throwable.message!!)
        }
    }

    fun getAcronyms(shortForm: String) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repo.getAcronyms(shortForm)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val longForms = mutableListOf<String>()
                    response.body()?.forEach {
                        it.lfs.forEach { longForm ->
                            longForms.add(longForm.lf!!)
                        }
                    }
                    if (longForms.isEmpty()) {
                        errorMessage.postValue(ErrorState.ERROR_STATE_NO_RESULTS)
                    } else {
                        errorMessage.postValue(ErrorState.ERROR_STATE_NONE)
                    }
                    acronymList.postValue(longForms)
                } else {
                    errorMessage.postValue(ErrorState.ERROR_STATE_NETWORK_FAILURE)
                }
            }
        }
    }

    companion object {
        const val TAG = "AcroViewModel"
    }
}