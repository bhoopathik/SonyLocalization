package com.bhoopathi.sony.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.bhoopathi.sony.R
import com.bhoopathi.sony.model.LanguageDictionaryMap
import com.bhoopathi.sony.model.LocalizationItem
import com.bhoopathi.sony.model.LocalizationResponse
import com.bhoopathi.sony.repository.AppRepository
import com.bhoopathi.sony.util.*
import com.bhoopathi.sony.viewmodel.LocalizationViewModel
import com.bhoopathi.sony.viewmodel.ViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: LocalizationViewModel
    private lateinit var langButton : Button

    val langList = arrayListOf<String>()
    val localizationList : LocalizationResponse = LocalizationResponse()
    val languageDictionaryMap : LanguageDictionaryMap = LanguageDictionaryMap()

    private  lateinit var textView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        val context = this

        langButton = findViewById(R.id.lang_button)
        textView = findViewById(R.id.textView);
        langButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val builder = MaterialAlertDialogBuilder(context)

                builder.setTitle("Choose Language")

                // set single choice items
                builder.setSingleChoiceItems(
                    langList.toTypedArray(), // array
                    -1 // initial selection (-1 none)
                ) { dialog, i -> }

                // alert dialog positive button
                builder.setPositiveButton("Set") { dialog, which ->

                    val position = (dialog as AlertDialog).listView.checkedItemPosition
                    // if selected, then get item text
                    if (position != -1) {
                        val selectedLangItem = localizationList.get(position)
                        viewModel.getDictionary(selectedLangItem.path)
                        langButton.setText(selectedLangItem.language)
                    }
                }

                builder.setNeutralButton("Cancel", null)
                builder.setCancelable(false)
                val dialog = builder.create()
                dialog.show()
            }
        })

    }

    private fun init() {
        setupViewModel()
        registerLanguage()
        registerDictionaryMap()
    }

    private fun setupViewModel() {
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(application, repository)
        viewModel = ViewModelProvider(this, factory).get(LocalizationViewModel::class.java)
    }

    private fun registerLanguage() {
        viewModel.localizationData.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { picsResponse ->

                        val localizationResponse : ArrayList<LocalizationItem> = picsResponse;
                        Log.d("Harini", "localizationResponse:"+localizationResponse)
                        for(localizationItem in localizationResponse){
                            langList.add(localizationItem.language)
                            localizationList.add(localizationItem)
                        }
                    }
                }

                is Resource.Error -> {
                    response.message?.let { message ->
                    }
                }

                is Resource.Loading -> {
                }
            }
        })
    }

    private fun registerDictionaryMap() {
        viewModel.languageDictionary.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { picsResponse ->

                        val languageDictionary : Map<String, String> = picsResponse;

                        Log.d("Harini", "Response:"+languageDictionary)
                        textView.setText(languageDictionary.get("hello_world"))
                    }
                }

                is Resource.Error -> {
                    response.message?.let { message ->
                    }
                }

                is Resource.Loading -> {
                }
            }
        })
    }
}