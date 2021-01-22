package com.example.network

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //뷰를 직접 찾아서 변수로 생성
        /*
        val btnTextDownload = findViewById<Button>(R.id.btnTextDownload)
         */
        //위의 작업을 하지 않고자 할 때는 module 수준의 build.gradle 파일의 plug-in에
        //id 'kotlin-android-extensions'를 추가
        btnTextDownload.setOnClickListener{
            startActivity(Intent(this, TextDownloadActivity::class.java))
        }

        btnImageDownload.setOnClickListener{
            startActivity(Intent(this, ImageDownloadActivity::class.java))
        }

        btnHtmlParsing.setOnClickListener{
            startActivity(Intent(this, HtmlActivity::class.java))
        }

        btnXMLParsing.setOnClickListener{
            startActivity(Intent(this, HaniDomActivity::class.java))
        }
    }
}