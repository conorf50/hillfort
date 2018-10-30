package cfarrell.hillfortexplorer.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import cfarrell.hillfortexplorer.R
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import cfarrell.hillfortexplorer.models.HillfortModel
class PlacemarkActivity : AppCompatActivity(), AnkoLogger {

    var hillfort = HillfortModel()
    val hillforts= ArrayList<HillfortModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)

        btnAdd.setOnClickListener() {
            hillfort.title = hillfortTitle.text.toString()
            if (hillfort.title.isNotEmpty()) {
                info("add Button Pressed: $hillfortTitle")
                hillforts.forEach { info("add Button Pressed: ${it.title}")}
            }
            else {
                toast ("Please Enter a title")
            }
        }
    }
}
