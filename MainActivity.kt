package com.example.laoutll

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.example.laoutll.databinding.ActivityMainBinding
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(){
    lateinit var binding: ActivityMainBinding
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        //set content is first Array
        var firstArray = arrayListOf<String>("what", "is", "your", "name")

        binding.micBtn.setOnClickListener {

            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH)
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something....")
            try {
                activityResultLauncher.launch(intent)
            } catch (exp: ActivityNotFoundException) {
                Toast.makeText(
                    applicationContext,
                    "device does not support mic",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? ->
                if (result!!.resultCode == RESULT_OK && result!!.data != null) {

                    val speechText =
                        result!!.data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>


                        var delimiter = " "
                        var secondArray = speechText[0].split(delimiter)
                    var Arraysize = secondArray.size
                    binding.inputText.setText("$speechText")

                    binding.nextbtn.setOnClickListener {

                        val intent = Intent(this@MainActivity, score_Activity::class.java)

                        intent.putExtra("input", secondArray)
                        intent.putExtra("length",Arraysize)
                        startActivity(intent)

                    }

                }
            }
    }
}