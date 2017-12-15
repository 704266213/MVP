package com.mvp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mvp.activity.FilmEntryActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test.setOnClickListener({
            val intent = Intent(this, FilmEntryActivity::class.java)
            startActivity(intent)
        })
    }


}
