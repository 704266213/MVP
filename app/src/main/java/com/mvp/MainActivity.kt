package com.mvp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mvp.activity.FilmEntryActivity
import com.mvp.activity.UserInfoActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRequest.setOnClickListener({
            val intent = Intent(this, UserInfoActivity::class.java)
            startActivity(intent)
        })

        loadMoreRequest.setOnClickListener({
            val intent = Intent(this, FilmEntryActivity::class.java)
            startActivity(intent)
        })

        loadMoreAndFresh.setOnClickListener({
            val intent = Intent(this, FilmEntryActivity::class.java)
            startActivity(intent)
        })


    }


}
