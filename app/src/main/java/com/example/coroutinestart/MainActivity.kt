package com.example.coroutinestart

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.coroutinestart.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonLoad.setOnClickListener {
            //Use coroutine, prevent memory leak
            lifecycleScope.launch(){
                loadData()
            }
        }
    }

    //Asynchronous run of the function
    private suspend fun loadData() {
        Log.d("MyLog", "Load started: $this")
        binding.progress.isVisible = true
        binding.buttonLoad.isEnabled = false

        val city = loadCity()
        binding.tvCity.text = city
        val temp = loadTemperature(city)
        binding.tvTemp.text = temp.toString()
        binding.progress.isVisible = false
        binding.buttonLoad.isEnabled = true
        Log.d("MyLog", "Load finished: $this")
    }

    private suspend fun loadCity(): String {
        //Thread.sleep(5000)
        delay(5000)
        return "Moscow"
    }

    private suspend fun loadTemperature(city: String): Int {
        Toast.makeText(
            this,
            getString(R.string.loading_temperature_toast) + city,
            Toast.LENGTH_SHORT
        ).show()
        //Thread.sleep(5000)
        delay(5000)
        return 17
    }
}