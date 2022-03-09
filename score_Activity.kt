package com.example.laoutll

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.laoutll.databinding.ScoreLayoutBinding
import java.util.*
import kotlin.collections.ArrayList

class score_Activity:AppCompatActivity() {
    var i =0
    var j =0
    var k=0
    lateinit var tts:TextToSpeech
    lateinit var binding: ScoreLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
binding = DataBindingUtil.setContentView(this,R.layout.score_layout)


        // text to speak
        binding.speakbtn.setOnClickListener {
            tts = TextToSpeech(applicationContext, TextToSpeech.OnInitListener {
                if(it==TextToSpeech.SUCCESS){
                  tts.language = Locale.getDefault()
                  tts.setSpeechRate(1.0f)
                  tts.speak(binding.etScore.text.toString(), TextToSpeech.QUEUE_ADD,null)
                }
            })
        }

        var errroArray= arrayListOf<String>()  // to store incorrect words

        //set content is first Array
        var firstArray = ArrayList<String>(4)

        firstArray.add("what")
        firstArray.add("is")
        firstArray.add("your")
        firstArray.add("name")




        val inputList:Array<Serializable>  = arrayOf(intent.getSerializableExtra("input")).sure()
        val inputSize = getIntent().getExtras()!!.getInt("length")

        if(firstArray.size>inputSize){
            binding.incorrectText.setText("you've missed some words")
        }
       else if(firstArray.size<inputSize){
            binding.incorrectText.setText("you've read some extra words")
        }

        else{
           while(i<4){
               if(firstArray[i]!= inputList!!.get(j)){
                   errroArray.add(firstArray[i])
                   i++
                   j++
               }
               else{
                   i++
                   j++
                   continue
               }

           }
            while(k<errroArray.size){
                binding.incorrectText.setText(errroArray.get(k))
                k++
            }
        }
    }
}