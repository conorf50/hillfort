package org.cfarrell.hillfort.activities

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import org.cfarrell.hillfort.R

import kotlinx.android.synthetic.main.activity_hillfort_maps.*

class HillfortMapsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_maps)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
