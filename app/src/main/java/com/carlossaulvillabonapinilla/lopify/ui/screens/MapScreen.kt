package com.carlossaulvillabonapinilla.lopify.ui.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carlossaulvillabonapinilla.lopify.R
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.PolylineAnnotation

private val IconGreen         = Color(0xFF0A1A05)
private val BackgroundSurface = Color(0xFF93E575)
private val GlassSelector     = Color(0x33FFFFFF)
private val TextBlack         = Color(0xFF1A1A1A)

@Composable
fun MapScreen(
    onNavigateToHome: () -> Unit,
    onNavItemSelected: (Int) -> Unit,
    selectedNavIndex: Int
) {
    val context = LocalContext.current

    val markerReciclaje = remember(context) {
        BitmapFactory.decodeResource(context.resources, R.drawable.bowl_reciclaje)
    }
    val markerMaria = remember(context) {
        BitmapFactory.decodeResource(context.resources, R.drawable.avatar)
    }

    // ─── Ruta principal ───────────────────────────────────────────────────────
    val puntoSanPio  = Point.fromLngLat(-73.1158, 7.1189)
    val puntoEsquina = Point.fromLngLat(-73.1158, 7.1175)
    val puntoMaria   = Point.fromLngLat(-73.1165, 7.1175)

    // ─── Puntos de reciclaje dispersos en Bucaramanga ─────────────────────────
    val puntosReciclaje = listOf(
        Point.fromLngLat(-73.1172, 7.1205),
        Point.fromLngLat(-73.1143, 7.1210),
        Point.fromLngLat(-73.1128, 7.1195),
        Point.fromLngLat(-73.1135, 7.1178),
        Point.fromLngLat(-73.1148, 7.1162),
        Point.fromLngLat(-73.1170, 7.1158),
        Point.fromLngLat(-73.1155, 7.1148),
        Point.fromLngLat(-73.1120, 7.1168),
        Point.fromLngLat(-73.1115, 7.1185),
        Point.fromLngLat(-73.1180, 7.1185)
    )

    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            center(Point.fromLngLat(-73.1160, 7.1182))
            zoom(16.0)
            pitch(45.0)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF2ECC40), Color(0xFFA4DCA4), Color(0xFFBBEABB)),
                    startY = 0f, endY = 1200f
                )
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // ─── HEADER ───────────────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.6f))
                        .clickable { onNavigateToHome() },
                    contentAlignment = Alignment.Center
                ) {
                    Text("←", fontSize = 20.sp, color = TextBlack)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Puntos cercanos",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextBlack
                )
            }

            // ─── FILTRO "Todos" ───────────────────────────────────────────────
            Row(modifier = Modifier.padding(horizontal = 20.dp)) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color(0xFF2ECC40)
                ) {
                    Text(
                        text = "Todos",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ─── MAPA ─────────────────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                MapboxMap(
                    modifier = Modifier.fillMaxSize(),
                    mapViewportState = mapViewportState
                ) {
                    // Ruta
                    PolylineAnnotation(
                        points = listOf(puntoMaria, puntoEsquina, puntoSanPio),
                        lineColorString = "#2E7D32",
                        lineWidth = 6.0
                    )

                    // Marcador origen (usuario)
                    PointAnnotation(
                        point = puntoMaria,
                        iconImageBitmap = markerMaria,
                        iconSize = 0.8
                    )

                    // Marcador destino principal
                    PointAnnotation(
                        point = puntoSanPio,
                        iconImageBitmap = markerReciclaje,
                        iconSize = 1.2
                    )

                    // Puntos de reciclaje dispersos
                    puntosReciclaje.forEach { punto ->
                        PointAnnotation(
                            point = punto,
                            iconImageBitmap = markerReciclaje,
                            iconSize = 1.0
                        )
                    }
                }

                // Etiqueta sobre el mapa
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF2ECC40),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Reciclaje\ncerca de ti",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 6.dp, bottom = 10.dp)
                    )
                }
            }

            // ─── CARD INFERIOR ────────────────────────────────────────────────
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White.copy(alpha = 0.95f),
                shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bowl_reciclaje),
                        contentDescription = null,
                        modifier = Modifier.size(52.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Reciclaje cerca de ti",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = TextBlack
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = Color(0xFF81C784)
                            ) {
                                Text(
                                    "Aceptar",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp)
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = Color(0xFFEF5350)
                            ) {
                                Text(
                                    "Rechazar",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFEBC1CC))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.avatar),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }

        // ─── NAVBAR ───────────────────────────────────────────────────────────
        MapNavBar(
            selectedIndex = selectedNavIndex,
            onItemSelected = onNavItemSelected,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun MapNavBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shadowElevation = 24.dp,
        color = BackgroundSurface,
        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MapNavBarItem(R.drawable.icon_home, "Inicio", selectedIndex == 0) { onItemSelected(0) }
            MapNavBarItem(R.drawable.historial, "Historial", selectedIndex == 1) { onItemSelected(1) }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.offset(y = (-15).dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .shadow(10.dp, CircleShape)
                        .clip(CircleShape)
                        .background(Brush.verticalGradient(listOf(Color(0xFF8EDF7C), Color(0xFF2D741C))))
                        .clickable { onItemSelected(2) },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.meta),
                        contentDescription = "Metas",
                        modifier = Modifier.requiredSize(200.dp)
                    )
                }
                Text("Metas", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = IconGreen)
            }

            MapNavBarItem(R.drawable.mapa_home, "Mapa", selectedIndex == 3) { onItemSelected(3) }
            MapNavBarItem(R.drawable.perfil_home, "Perfil", selectedIndex == 4) { onItemSelected(4) }
        }
    }
}
//barrita navegaci
@Composable
private fun MapNavBarItem(iconRes: Int, label: String, selected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (selected) GlassSelector else Color.Transparent)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier
                .size(32.dp)
                .alpha(if (selected) 1f else 0.6f)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            color = IconGreen,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
        )
    }
}