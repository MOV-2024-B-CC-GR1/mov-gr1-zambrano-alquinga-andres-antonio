package com.example.examen_2b_aaza

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class GMaps : AppCompatActivity() {
    private lateinit var mapa: GoogleMap
    var permisos = false
    val nombrePermisoFine = android.Manifest.permission.ACCESS_FINE_LOCATION
    val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            Log.d("GGoogleMaps", "Iniciando onCreate")

            setContentView(R.layout.activity_gmaps)

            Log.d("GGoogleMaps", "Layout establecido")

            solicitarPermisos()
            inicializarLogicaMapa()

            val botonUbi = findViewById<Button>(R.id.btn_ubi)
            botonUbi.setOnClickListener {
                try {
                    val ubi = LatLng(-0.21021860341498336, -78.48897235393967)
                    moverCamaraConZoom(ubi)
                } catch (e: Exception) {
                    Log.e("GGoogleMaps", "Error en botón", e)
                    Toast.makeText(this, "Error al mover la cámara", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            Log.e("GGoogleMaps", "Error en onCreate", e)
            Toast.makeText(this, "Error al iniciar el mapa", Toast.LENGTH_LONG).show()
        }
    }

    private fun solicitarPermisos() {
        try {
            if (!tengoPermisos()) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(nombrePermisoFine, nombrePermisoCoarse),
                    1
                )
            }
        } catch (e: Exception) {
            Log.e("GGoogleMaps", "Error en solicitarPermisos", e)
        }
    }

    private fun tengoPermisos(): Boolean {
        val permisoFine = ContextCompat.checkSelfPermission(this, nombrePermisoFine)
        val permisoCoarse = ContextCompat.checkSelfPermission(this, nombrePermisoCoarse)
        permisos = permisoFine == PackageManager.PERMISSION_GRANTED &&
                permisoCoarse == PackageManager.PERMISSION_GRANTED
        return permisos
    }

    private fun inicializarLogicaMapa() {
        try {
            val fragmentoMapa = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment

            fragmentoMapa.getMapAsync { googleMap ->
                try {
                    mapa = googleMap
                    establecerConfiguracionMapa()
                    moverUbi()
                } catch (e: Exception) {
                    Log.e("GGoogleMaps", "Error al configurar mapa", e)
                }
            }
        } catch (e: Exception) {
            Log.e("GGoogleMaps", "Error en inicializarLogicaMapa", e)
        }
    }

    private fun moverUbi() {
        try {
            val uni = LatLng(-0.21021860341498336, -78.48897235393967)
            val titulo = "Universidad"
            val marcador = anadirMarcador(uni, titulo)
            marcador.tag = titulo
            moverCamaraConZoom(uni)
        } catch (e: Exception) {
            Log.e("GGoogleMaps", "Error en mover", e)
        }
    }

    private fun moverCamaraConZoom(latLng: LatLng, zoom: Float = 17f) {
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
    }

    private fun anadirMarcador(latLng: LatLng, title: String): Marker {
        return mapa.addMarker(MarkerOptions().position(latLng).title(title))!!
    }

    @SuppressLint("MissingPermission")
    private fun establecerConfiguracionMapa() {
        try {
            with(mapa) {
                if (tengoPermisos()) {
                    isMyLocationEnabled = true
                    uiSettings.isMyLocationButtonEnabled = true
                }
                uiSettings.isZoomControlsEnabled = true
            }
        } catch (e: Exception) {
            Log.e("GGoogleMaps", "Error en establecerConfiguracionMapa", e)
        }
    }
}