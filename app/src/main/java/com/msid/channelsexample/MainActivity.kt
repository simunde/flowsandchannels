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
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
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

            getNotes().map {
                FormattedNote(it.isActive,it.title.uppercase(),it.description)
            }
                .filter {
                    it.isActive
                }
                .collect{
                    Log.d("Formatted",it.toString())
                }
//            val data: Flow<Int> = producer()
//            data
//                .onStart {
//                    Log.d("Siddhesh","Started out")
//                }
//                .onCompletion {
//                    Log.d("Siddhesh","Completed")
//                }
//                .map {
//
//                }
//                .onEach {
//                    Log.d("Siddhesh","Received - $it")
//                }
//
//                .collect{
//                Log.d("Flows - 1: ", it.toString())
//            }
        }

//        GlobalScope.launch {
//            delay(2500L)
//            val data: Flow<Int> = producer()
//            data.collect{
//                Log.d("Flows - 2: ", it.toString())
//            }
//        }

    }

    private fun producer()= flow<Int> {

        val list = listOf(1,2,3,4,5,6)
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

    private fun getNotes(): Flow<Note>{
        val list = listOf(
            Note(1,true,"First", "First Description"),
            Note(2,false,"Second", "Second Description"),
            Note(3,true,"Third", "Third Description")

        )

        return list.asFlow()
    }

    data class Note(val id: Int, val isActive: Boolean, val title: String, val description: String)

    data class FormattedNote(val isActive: Boolean, val title: String, val description: String)




}