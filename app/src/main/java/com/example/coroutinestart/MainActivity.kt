package com.example.coroutinestart

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.coroutinestart.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

//    private val handler = object: Handler(){
//        override fun handleMessage(msg: Message) {
//            super.handleMessage(msg)
//            println("HANDLE_MSG $msg")
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonLoad.setOnClickListener {
            loadData()
        }
        //handler.sendMessage(Message.obtain(handler, 0, 17))
    }

    //Asynchronous run of the function
    private fun loadData() {
        Log.d("MyLog", "Load started: $this")
        binding.progress.isVisible = true
        binding.buttonLoad.isEnabled = false

        loadCity {
            binding.tvCity.text = it
            loadTemperature(it) {
                binding.tvTemp.text = it.toString()
                binding.progress.isVisible = false
                binding.buttonLoad.isEnabled = true
                Log.d("MyLog", "Load finished: $this")
            }
        }
    }

    private fun loadCity(callback: (String) -> Unit) {
        //Create new flow
        thread {
            Thread.sleep(5000)
            runOnUiThread {
                callback.invoke("Moscow")
            }
        }
    }

    private fun loadTemperature(city: String, callback: (Int) -> Unit) {
        thread {
            //Looper.prepare()
            //Handler()
            //Handler(Looper.getMainLooper()).post {
            runOnUiThread {
                Toast.makeText(
                    this,
                    "Loading temperature for city: $city",
                    Toast.LENGTH_SHORT
                ).show()
            }
            Thread.sleep(5000)
            runOnUiThread {
                callback.invoke(17)
            }
        }
    }
}