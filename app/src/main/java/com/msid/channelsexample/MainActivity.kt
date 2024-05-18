package com.msid.channelsexample

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val channel = Channel<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        producer()
//        consumer()
        GlobalScope.launch {
            val data: Flow<Int> = producer()
            data.collect{
                Log.d("Flows: ", it.toString())
            }
        }

    }

    private fun producer()= flow<Int> {

        val list = listOf(1,2,3,4,5)
        list.forEach {
            delay(1000L)
            emit(it)
        }
    }

//    private fun producer() {
//        CoroutineScope(Dispatchers.Main).launch {
//            channel.send(1)
//            channel.send(2)
//
//        }
//    }
//
//    private fun consumer() {
//        CoroutineScope(Dispatchers.Main).launch {
//            Log.d("Channel Received: ",channel.receive().toString())
//            Log.d("Channel Received: ",channel.receive().toString())
//        }
//    }




}