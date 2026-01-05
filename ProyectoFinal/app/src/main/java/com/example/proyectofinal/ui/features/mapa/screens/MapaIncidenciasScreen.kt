package com.example.proyectofinal.ui.features.mapa.screens

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.maplibre.android.maps.MapView
import org.maplibre.android.style.layers.FillLayer
import org.maplibre.android.style.layers.PropertyFactory.*
import org.maplibre.android.style.layers.SymbolLayer
import org.maplibre.android.style.sources.GeoJsonSource
import org.maplibre.geojson.Feature
import org.maplibre.geojson.FeatureCollection
import org.maplibre.geojson.Point
import com.example.proyectofinal.ui.features.mapa.utils.GeoJsonLoader
import com.example.proyectofinal.ui.features.mapa.utils.updateSource
import com.example.proyectofinal.ui.features.reportes.viewmodel.ReportesViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.maplibre.android.style.sources.Source
import org.maplibre.android.maps.Style
import androidx.compose.runtime.remember
import com.example.proyectofinal.ui.features.mapa.viewmodel.MapaViewModel

@Composable
fun MapaIncidenciasScreen(
    reportesVM: ReportesViewModel,
    mapaVM: MapaViewModel,
    modifier: Modifier = Modifier
){
    val reportes by reportesVM.reportes.collectAsStateWithLifecycle()

    AndroidView(
        modifier = modifier,
        factory = {ctx ->
            MapView(ctx).apply{
                getMapAsync { map ->
                    map.setStyle(Style.MAPBOX_STREETS){ style ->
                        // Source y capa de reportes
                        style.addSource(GeoJsonSource("reportes-source", FeatureCollection.fromFeatures(emptyArray())))
                        style.addLayer(
                            SymbolLayer("reportes-layer", "reportes-source").withProperties(
                                iconImage("marker-15"),
                                iconAllowOverlap(true),
                                textField("{categoria}"),
                                textOffset(arrayOf(0f, 1.5f))
                            )
                        )
                        // Source y FillLayer de alcaldía
                        val fc = FeatureCollection.fromJson(GeoJsonLoader.load(ctx, "alcaldias.geojson"))
                        style.addSource(GeoJsonSource("alc-source", fc))
                        style.addLayer(
                            
                        )
                    }
            }
        }
    )
}