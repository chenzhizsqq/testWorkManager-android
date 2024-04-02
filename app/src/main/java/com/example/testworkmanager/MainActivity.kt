package com.example.testworkmanager

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.testworkmanager.databinding.ActivityMainBinding
import java.time.Duration

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding


    private val manager = WorkManager.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.buttonStart.setOnClickListener {

            //每十五分钟一次发信息给android机上
            val periodicWork = PeriodicWorkRequest.Builder(
                MyWorker::class.java,
                Duration.ofMinutes(15)
            ).build()

            //val operation = manager.enqueue(periodicWork)

            //使用 WorkManager#enqueueUniquePeriodicWork() 将您的 WorkRequest 作为独特任务 (unique Work) 加入队列：
            WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "MyUniqueWorkName",
            ExistingPeriodicWorkPolicy.KEEP,
                periodicWork)
        }
    }
}