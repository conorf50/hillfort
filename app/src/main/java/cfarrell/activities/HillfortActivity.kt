package cfarrell.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import cfarrell.hillfortexplorer.R
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class HillfortActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)
        info("Placemark Activity started..")
        btnAdd.setOnClickListener() {
            val placemarkTitle = placemarkTitle.text.toString()
            if (placemarkTitle.isNotEmpty()) {
                info("add Button Pressed: $placemarkTitle")
            }
            else {
                toast ("Please Enter a title")
            }
        }

    }
}
