package org.cfarrell.hillfort.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.GoogleMap
import org.cfarrell.hillfort.R

import kotlinx.android.synthetic.main.content_hillfort_maps.*

class HillfortMapsActivity : AppCompatActivity() {

    lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_maps)
        mapView.onCreate(savedInstanceState);
    }

}
