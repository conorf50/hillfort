package cfarrell.hillfortexplorer.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import cfarrell.hillfortexplorer.R
import cfarrell.hillfortexplorer.main.MainApp
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import cfarrell.hillfortexplorer.models.HillfortModel
class HillfortActivity : AppCompatActivity(), AnkoLogger {

    var hillfort = HillfortModel()
    lateinit var app : MainApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)
        app = application as MainApp


        btnAdd.setOnClickListener() {
            hillfort.title = hillfortTitle.text.toString()
            hillfort.description = description.text.toString()

            if (hillfort.title.isNotEmpty()) {
                app.hillforts.add(hillfort.copy())
                info("add Button Pressed: $hillfortTitle")
                app.hillforts.forEach { info("add Button Pressed: ${it.title}")}
            }
            else {
                toast ("Please Enter a title")
            }
        }
    }
}
