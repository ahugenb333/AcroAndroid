package com.ahugenb.acroandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.ahugenb.acroandroid.api.AcroApi
import com.ahugenb.acroandroid.api.AcroRepo
import com.ahugenb.acroandroid.api.ApiHelper
import com.ahugenb.acroandroid.databinding.ActivityMainBinding
import com.ahugenb.acroandroid.viewmodel.AcroViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    //todo testing
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        val api = ApiHelper.getInstance().create(AcroApi::class.java)
        val acroRepo = AcroRepo(api)
        val viewModel = AcroViewModel(acroRepo)

        binding.acroViewModel = viewModel

        val et = binding.etAcronym


        et.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.getAcronyms(s.toString())

            }
        })
    }
}