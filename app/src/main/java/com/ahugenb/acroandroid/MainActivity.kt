package com.ahugenb.acroandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
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
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val tvAcronym = binding.tvAcronym
        val tvError = binding.tvError
        val et = binding.etAcronym

        val api = ApiHelper.getInstance().create(AcroApi::class.java)
        val acroRepo = AcroRepo(api)
        val viewModel = AcroViewModel(acroRepo)

        viewModel.acronymList.observe(this, Observer {
                var strs = ""
                it.forEach { str ->
                    strs = strs.plus(str).plus("\n")
                }
                tvAcronym.text = strs
        })

        viewModel.errorMessage.observe(this, Observer {
            when(it) {
                AcroViewModel.ErrorState.ERROR_STATE_NONE -> {
                    tvAcronym.visibility = View.VISIBLE
                    tvError.visibility = View.GONE
                }
                AcroViewModel.ErrorState.ERROR_STATE_NO_RESULTS -> {
                    tvError.text = resources.getString(R.string.tv_no_results)
                    tvError.visibility = View.VISIBLE
                    tvAcronym.visibility = View.GONE
                }
                AcroViewModel.ErrorState.ERROR_STATE_NETWORK_FAILURE -> {
                    tvError.text = resources.getString(R.string.tv_network_failure)
                    tvError.visibility = View.VISIBLE
                    tvAcronym.visibility = View.GONE
                }
                else -> {}
            }
        })

        et.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val str = s.toString()
                if (str.length < 2) {
                    tvAcronym.text = resources.getString(R.string.tv_type_more)
                } else {
                    viewModel.getAcronyms(s.toString())
                }
            }
        })
    }
}