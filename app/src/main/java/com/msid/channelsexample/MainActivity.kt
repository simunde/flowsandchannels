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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime

class MainActivity : AppCompatActivity() {

    val channel = Channel<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        producer()
//        consumer()
//        GlobalScope.launch {

//            val time = measureTimeMillis {
//                producer()
//                    .buffer(5)
//                    .collect{
//                        delay(1500L)
//                        Log.d("Siddhesh--->",it.toString())
//                    }
//
//
//            }
//            Log.d("Siddhesh--->",time.toString())

//            getNotes().map {
//                FormattedNote(it.isActive,it.title.uppercase(),it.description)
//            }
//                .filter {
//                    it.isActive
//                }
//                .collect{
//                    Log.d("Formatted",it.toString())
//                }
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
        //}

//        GlobalScope.launch {
//            delay(2500L)
//            val data: Flow<Int> = producer()
//            data.collect{
//                Log.d("Flows - 2: ", it.toString())
//            }
//        }


        GlobalScope.launch(Dispatchers.Main) {
           val fl1 = producer()
            fl1.collect{
                Log.d("Flows-1: ","Item -$it")

            }
        }

        GlobalScope.launch(Dispatchers.Main) {
            val fl2 = producer()
            delay(2500L)


            fl2.collect{
                Log.d("Flows-2: ","Item -$it")

            }
        }
    }

    private fun producer(): Flow<Int> {
        val mutableSharedFlow = MutableSharedFlow<Int>(2)

        GlobalScope.launch {

            val list = listOf(1,2,3,4,5)
            list.forEach {
                mutableSharedFlow.emit(it)
                delay(1000L)
            }
        }

        return  mutableSharedFlow


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