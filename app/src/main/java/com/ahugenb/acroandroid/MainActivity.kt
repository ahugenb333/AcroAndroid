package com.ahugenb.acroandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import com.ahugenb.acroandroid.api.AcroApi
import com.ahugenb.acroandroid.api.AcroRepo
import com.ahugenb.acroandroid.api.ApiHelper
import com.ahugenb.acroandroid.viewmodel.AcroViewModel

class MainActivity : AppCompatActivity() {

    //todo adapter
    //todo binding
    //todo detail
    //todo loading
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv = findViewById<TextView>(R.id.tv_acronym)

        val api = ApiHelper.getInstance().create(AcroApi::class.java)
        val acroRepo = AcroRepo(api)
        val viewModel = AcroViewModel(acroRepo)

        viewModel.acronymList.observe(this, Observer {
                it.forEach { str ->
                    tv.text = tv.text.toString().plus(str)
                }
        })

        viewModel.getAcronyms("lll")
    }
}