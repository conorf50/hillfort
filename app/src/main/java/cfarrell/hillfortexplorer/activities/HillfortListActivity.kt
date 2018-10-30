package cfarrell.hillfortexplorer.activities


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import cfarrell.hillfortexplorer.R
import cfarrell.hillfortexplorer.main.MainApp

class HillfortListActivity : AppCompatActivity() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_list)
        app = application as MainApp
    }
}