package com.syed.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.common_action_bar_white_background.*


class MapActivity : BaseActivity() {

    lateinit var smf:SupportMapFragment
    lateinit var client:FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        tv_map.visibility=View.GONE
        iv_back.setOnClickListener({
            finish()
        })
        smf=supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        client=LocationServices.getFusedLocationProviderClient(this)
        Dexter.withContext(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        getmyLocation()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) { /* ... */
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                    token?.continuePermissionRequest()
                    }
                }).check()
    }
fun getmyLocation(){
     if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return
    }
    var tast= client.lastLocation
    tast.addOnSuccessListener(object: OnSuccessListener<Location>{
        override fun onSuccess(location: Location?) {
            smf.getMapAsync( object : OnMapReadyCallback{
                override fun onMapReady(googleMap: GoogleMap) {
                    var latLng= location?.let { LatLng(it?.latitude,location?.longitude) }
                    var markerOptions=MarkerOptions().position(latLng).title("You are here ......!!")
                 googleMap.addMarker(markerOptions)
                 googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15F))
                }

            })

        }

    })
}
}