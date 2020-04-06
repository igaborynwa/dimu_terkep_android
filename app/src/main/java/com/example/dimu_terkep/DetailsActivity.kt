package com.example.dimu_terkep

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.dimu_terkep.data.Institution

import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.content_details.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)

        val inst :Institution? = intent.getSerializableExtra("isntitution") as? Institution
        title = inst?.getName()
        tv_addr.text=inst?.getAddress()
        tv_head.text=inst?.getHead()
        tv_desc.text=inst?.getDesc()






    }

}
