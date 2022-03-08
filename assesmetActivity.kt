package com.example.voicetotext

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech

import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.example.voicetotext.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

class assesmetActivity : AppCompatActivity() {

    private var tts: TextToSpeech? = null
    lateinit var binding: ActivityMainBinding
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.assesment_layout)



        var count = 0
        var score = 0




        binding.stTbtn.setOnClickListener {

            count = count + 1

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

                    if (count == 1) {

                        var quesOne = arrayListOf<String>("know")
                        if (speechText[0] == quesOne[0]) {
                            binding.Resultmsg.visibility = View.VISIBLE
                            binding.Resultmsg.setText("$speechText", TextView.BufferType.EDITABLE)
                            binding.firstWord.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark))
                            Toast.makeText(this, "Correct ans !! ", Toast.LENGTH_SHORT).show()
                            score = score + 1
                        } else {
                            binding.Resultmsg.visibility = View.VISIBLE
                            binding.Resultmsg.setText("$speechText")
                            Toast.makeText(this, "incorrect ans !!", Toast.LENGTH_LONG).show()
                            binding.firstWord.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark))
                        }

                    } else if (count == 2) {
                        var quesTwo = arrayListOf<String>("make")
                        if (speechText[0] == quesTwo[0]) {
                            binding.Resultmsg.visibility = View.VISIBLE
                            binding.Resultmsg.setText("$speechText", TextView.BufferType.EDITABLE)
                            binding.secondWord.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark))
                            Toast.makeText(this, "Correct ans !! ", Toast.LENGTH_SHORT).show()
                            score = score + 1
                        } else {
                            binding.Resultmsg.visibility = View.VISIBLE
                            binding.Resultmsg.setText("$speechText")
                            Toast.makeText(this, "incorrect ans !!", Toast.LENGTH_LONG).show()
                            binding.secondWord.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark))
                        }
                    } else if (count == 3) {
                        var quesThree = arrayListOf<String>("athlete")
                        if (speechText[0] == quesThree[0]) {
                            binding.Resultmsg.visibility = View.VISIBLE
                            binding.Resultmsg.setText("$speechText", TextView.BufferType.EDITABLE)
                            binding.ThirdWord.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark))
                            Toast.makeText(this, "Correct ans !! ", Toast.LENGTH_SHORT).show()
                            score = score + 1
                        } else {
                            binding.Resultmsg.visibility = View.VISIBLE
                            binding.Resultmsg.setText("$speechText")
                            Toast.makeText(this, "incorrect ans !!", Toast.LENGTH_LONG).show()
                            binding.ThirdWord.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark))
                        }

                    } else if (count == 4) {
                        var quesfour = arrayListOf<String>("lecture")
                        if (speechText[0] == quesfour[0]) {
                            binding.Resultmsg.visibility = View.VISIBLE
                            binding.Resultmsg.setText("$speechText", TextView.BufferType.EDITABLE)
                            binding.fourthWord.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark))
                            Toast.makeText(this, "Correct ans !! ", Toast.LENGTH_SHORT).show()
                            score = score + 1
                        } else {
                            binding.Resultmsg.visibility = View.VISIBLE
                            binding.Resultmsg.setText("$speechText")
                            Toast.makeText(this, "incorrect ans !!", Toast.LENGTH_LONG).show()
                            binding.fourthWord.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark))
                        }

                        binding.Resultmsg.text = "You've answered $score question correctly! \n score is $score out of 4"



                    }

                }
            }
    }




    }







//String languagePref = "hi";
//Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, languagePref);
//intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, languagePref);
//intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, languagePref)