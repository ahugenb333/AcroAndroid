package com.ahugenb.acroandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ahugenb.acroandroid.api.AcroApi
import com.ahugenb.acroandroid.api.ApiHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val api = ApiHelper.getInstance().create(AcroApi::class.java)
        GlobalScope.launch {
            val result = api.getAcronyms("lll")
            Log.d("asdf: ", result.body().toString())
        }
    }
}