package com.ahugenb.acroandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahugenb.acroandroid.api.AcroRepo
import kotlinx.coroutines.*

class AcroViewModel(val repo: AcroRepo) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val acronymList = MutableLiveData<List<String>>()
    val loading = MutableLiveData<Boolean>()
    var job: Job? = null

    val exceptionHandler = CoroutineExceptionHandler{ _ , throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    private fun onError(msg: String) {
        errorMessage.value = msg
        loading.value = false
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
                    acronymList.postValue(longForms)
                    loading.value = false
                } else {
                    onError("Error: ${response.message()}")
                }
            }
        }
    }


}