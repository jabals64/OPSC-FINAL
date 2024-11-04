package com.example.carspotteropsc7312poe.location

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.example.carspotteropsc7312poe.DashboardActivity
import com.example.carspotteropsc7312poe.R
import com.example.carspotteropsc7312poe.dataclass.CarObservation
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.gestures.gestures

class MapboxActivity : AppCompatActivity() {

    private lateinit var mapView: MapView

    private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().bearing(it).build())
    }

    private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(it).build())
        mapView.gestures.focalPoint = mapView.getMapboxMap().pixelForCoordinate(it)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapbox)
        mapView = findViewById(R.id.mapView)

        onMapReady()

        val zoomInButton = findViewById<Button>(R.id.zoomInButton)
        val zoomOutButton = findViewById<Button>(R.id.zoomOutButton)
        val btnDashBoard = findViewById<Button>(R.id.dashboardButton)

        zoomInButton.setOnClickListener { zoomIn() }
        zoomOutButton.setOnClickListener { zoomOut() }

        btnDashBoard.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
    }

    private fun zoomIn() {
        val currentZoom = mapView.getMapboxMap().cameraState.zoom
        mapView.getMapboxMap().setCamera(
            CameraOptions.Builder()
                .zoom(currentZoom + 1.0)
                .build()
        )
    }

    private fun zoomOut() {
        val currentZoom = mapView.getMapboxMap().cameraState.zoom
        mapView.getMapboxMap().setCamera(
            CameraOptions.Builder()
                .zoom(currentZoom - 1.0)
                .build()
        )
    }

    private fun onMapReady() {
        mapView.getMapboxMap().setCamera(
            CameraOptions.Builder()
                .zoom(10.0) // Adjust initial zoom level as needed
                .build()
        )
        mapView.getMapboxMap().loadStyleUri(
            Style.MAPBOX_STREETS
        ) {
            initLocationComponent()

            // Add tap listener to add marker when the map is clicked
            mapView.gestures.addOnMapClickListener { point ->
                addMapIcon(point.latitude(), point.longitude())
                true
            }
        }
    }

    private fun initLocationComponent() {
        val locationComponentPlugin = mapView.location
        locationComponentPlugin.updateSettings {
            this.enabled = true
            this.locationPuck = LocationPuck2D(
                bearingImage = AppCompatResources.getDrawable(
                    this@MapboxActivity,
                    R.drawable.mapbox_mylocation_icon_default,
                ),
                scaleExpression = com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(0.0)
                        literal(0.6)
                    }
                    stop {
                        literal(20.0)
                        literal(1.0)
                    }
                }.toJson()
            )
        }
        locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
    }

    // This function adds a map icon at a specific latitude and longitude
    private fun addMapIcon(latitude: Double, longitude: Double) {
        bitmapFromDrawableRes(this@MapboxActivity, R.drawable.blue_marker)?.let {
            val annotationApi = mapView.annotations
            val pointAnnotationManager = annotationApi.createPointAnnotationManager(mapView)
            val pointAnnotationOptions = PointAnnotationOptions()
                .withPoint(Point.fromLngLat(longitude, latitude))
                .withIconImage(it)
            pointAnnotationManager.create(pointAnnotationOptions)
        }
    }

    // Helper function to convert drawable resource to bitmap
    private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int): Bitmap? {
        val drawable = AppCompatResources.getDrawable(context, resourceId)
        return drawable?.let { convertDrawableToBitmap(it) }
    }

    private fun convertDrawableToBitmap(sourceDrawable: Drawable): Bitmap {
        if (sourceDrawable is BitmapDrawable) return sourceDrawable.bitmap
        val bitmap: Bitmap = Bitmap.createBitmap(
            sourceDrawable.intrinsicWidth,
            sourceDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        sourceDrawable.setBounds(0, 0, canvas.width, canvas.height)
        sourceDrawable.draw(canvas)
        return bitmap
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
    }
}
