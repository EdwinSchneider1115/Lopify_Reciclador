package com.carlossaulvillabonapinilla.lopify.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carlossaulvillabonapinilla.lopify.R

// Importaciones de Mapbox
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState

import android.graphics.BitmapFactory
import androidx.compose.ui.platform.LocalContext
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.PolylineAnnotation

// ─── COLORES GLOBALES ─────────────────────────────────────────────────────────
private val IconGreen         = Color(0xFF0A1A1F)
private val BackgroundSurface = Color(0xFF72C8C0)
private val GlassSelector     = Color(0x33FFFFFF)
private val TextBlack         = Color(0xFF1A1A2E)
private val DarkGreenText     = Color(0xFF0D6E6E)
private val LightGrayText     = Color(0xFF607D8B)

// ─── DATA CLASSES ─────────────────────────────────────────────────────────────
data class DeliveryData(
    val name: String,
    val distance: String,
    val detail: String,
    val location: String,
    val weight: String,
    val status: String,
    val iconRes: Int
)

data class CategoryData(val name: String, val iconRes: Int)

// ─── PANTALLA PRINCIPAL ───────────────────────────────────────────────────────

@Composable
fun HomeScreen(
    selectedNavIndex: Int = 0,
    onNavItemSelected: (Int) -> Unit = {}
) {
    var isStreakActive by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF2EB8B0), Color(0xFF89D9C4), Color(0xFFB2EAE0)),
                    startY = 0f, endY = 1200f
                )
            )
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 120.dp)
        ) {
            item {
                HomeHeader(
                    userName = "Carlos Diaz",
                    streakCount = 3,
                    isStreakActive = isStreakActive,
                    onStreakClick = { isStreakActive = !isStreakActive },
                    modifier = Modifier.statusBarsPadding()
                )
            }
            item { ImpactCard() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item { CategorySection() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item { MapSection() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item { RecentDeliveriesSection() }
        }

        HomeNavBar(
            selectedIndex = selectedNavIndex,
            onItemSelected = onNavItemSelected,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

// ─── 1. HEADER ────────────────────────────────────────────────────────────────
@Composable
fun HomeHeader(
    userName: String, streakCount: Int, isStreakActive: Boolean,
    onStreakClick: () -> Unit, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onStreakClick() }
                    .alpha(if (isStreakActive) 1f else 0.4f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fuego_home),
                    contentDescription = "Racha",
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = streakCount.toString(), fontSize = 26.sp, fontWeight = FontWeight.Bold, color = TextBlack)
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Buenos Dias,", fontSize = 22.sp, color = TextBlack, fontWeight = FontWeight.Normal)
            Text(text = userName, fontSize = 34.sp, fontWeight = FontWeight.Bold, color = TextBlack, lineHeight = 38.sp)
        }

        Column(horizontalAlignment = Alignment.End) {
            Box(modifier = Modifier.padding(top = 4.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.bolso),
                    contentDescription = "Notificaciones",
                    modifier = Modifier.size(48.dp)
                )
                Surface(
                    color = Color.Red, shape = CircleShape,
                    modifier = Modifier.size(20.dp).align(Alignment.BottomEnd).offset(x = 2.dp, y = 2.dp),
                    border = BorderStroke(2.dp, Color.White)
                ) {
                    Text("1", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.wrapContentSize(Alignment.Center))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.size(90.dp).clip(CircleShape).background(Color(0xFFEBC1CC))) {
                Image(
                    painter = painterResource(id = R.drawable.avatar),
                    contentDescription = "Perfil", contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

// ─── 2. IMPACT CARD ───────────────────────────────────────────────────────────
@Composable
fun ImpactCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .background(Brush.linearGradient(listOf(Color(0xFF8EDF7C), Color(0xFF4CAF50))))
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Tu impacto este mes", color = Color.White, fontSize = 14.sp)
                    Text("23,5 kg recogidos", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color.White.copy(alpha = 0.3f),
                        modifier = Modifier.clickable { }
                    ) {
                        Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text("Ver mi huella verde", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("🌻", fontSize = 14.sp)
                        }
                    }
                }

                Box(
                    modifier = Modifier.size(75.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawArc(
                            color = Color.White.copy(alpha = 0.3f),
                            startAngle = -90f,
                            sweepAngle = 360f,
                            useCenter = false,
                            style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
                        )
                        drawArc(
                            color = Color.White,
                            startAngle = -90f,
                            sweepAngle = 360f * 0.45f,
                            useCenter = false,
                            style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("45%", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("meta", color = Color.White, fontSize = 10.sp)
                    }
                }
            }
        }

        Image(
            painter = painterResource(id = R.drawable.planta_flotante),
            contentDescription = null,
            modifier = Modifier
                .size(70.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 4.dp, y = 12.dp)
        )
    }
}

// ─── 3. CATEGORÍAS ────────────────────────────────────────────────────────────
@Composable
fun CategorySection() {
    val categories = listOf(
        CategoryData("Papel", R.drawable.icon_papel),
        CategoryData("Vidrio", R.drawable.icon_vidrio),
        CategoryData("Plastico", R.drawable.icon_plastico),
        CategoryData("Carton", R.drawable.icon_carton)
    )

    Column {
        Text("Preferencias de Recoleccion?", modifier = Modifier.padding(horizontal = 24.dp), fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextBlack)
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categories) { category ->
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White.copy(alpha = 0.6f),
                    modifier = Modifier.clickable { }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(painter = painterResource(id = category.iconRes), contentDescription = null, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(category.name, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = TextBlack)
                    }
                }
            }
        }
    }
}

// ─── 4. MAPA (MAPBOX) ─────────────────────────────────────────────────────────
@Composable
fun MapSection() {

    val context = LocalContext.current

    val markerReciclaje = remember(context) {
        BitmapFactory.decodeResource(context.resources, R.drawable.bowl_reciclaje)
    }
    val markerMaria = remember(context) {
        BitmapFactory.decodeResource(context.resources, R.drawable.avatar)
    }

    val puntoSanPio = Point.fromLngLat(-73.1158, 7.1189)
    val puntoEsquina = Point.fromLngLat(-73.1158, 7.1175)
    val puntoMaria = Point.fromLngLat(-73.1165, 7.1175)

    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            center(Point.fromLngLat(-73.1160, 7.1182))
            zoom(16.0)
            pitch(45.0)
        }
    }

    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Puntos de Recoleccion", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextBlack)
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = R.drawable.punto),
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(24.dp)),
            shadowElevation = 4.dp
        ) {
            MapboxMap(
                modifier = Modifier.fillMaxSize(),
                mapViewportState = mapViewportState
            ) {
                PolylineAnnotation(
                    points = listOf(puntoMaria, puntoEsquina, puntoSanPio),
                    lineColorString = "#2E7D32",
                    lineWidth = 6.0
                )
                PointAnnotation(
                    point = puntoMaria,
                    iconImageBitmap = markerMaria,
                    iconSize = 0.8
                )
                PointAnnotation(
                    point = puntoSanPio,
                    iconImageBitmap = markerReciclaje,
                    iconSize = 1.2
                )
            }
        }
    }
}

// ─── 5. ENTREGAS RECIENTES ────────────────────────────────────────────────────
@Composable
fun RecentDeliveriesSection() {
    val deliveries = listOf(
        DeliveryData("Punto Centrar San Francisco", "2.0 kg", "Mayormente: Papel / Carton", "3 bolsas", "2.0 kg", "Pendiente", R.drawable.icon_papel),
        DeliveryData("Punto Carrera 15", "5.0 kg", "Mayormente: Plastico", "7 bolsas", "5.0 kg", "Aceptado", R.drawable.icon_plastico),
        DeliveryData("Punto Parque San Francisco", "5.0 kg", "Plastico - hace 12 min", "Punto Carrera 15", "5.0 kg", "Pendiente", R.drawable.icon_vidrio)
    )

    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.bowl_reciclaje),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Capacidad Punto de recolección",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = TextBlack
                )
            }
            Text(
                "Ver todos",
                color = DarkGreenText,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                modifier = Modifier.clickable { }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        deliveries.forEach { delivery ->
            DeliveryItemCard(delivery)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun DeliveryItemCard(delivery: DeliveryData) {
    val badgeBackground = if (delivery.status == "Aceptado") Color(0xFFC8E6C9) else Color(0xFFF0F4C3)
    val badgeTextColor  = if (delivery.status == "Aceptado") Color(0xFF1B5E20) else Color(0xFF827717)

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color.White.copy(alpha = 0.7f)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(50.dp).clip(RoundedCornerShape(14.dp)).background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(painter = painterResource(id = delivery.iconRes), contentDescription = null, modifier = Modifier.size(35.dp))
            }

            Column(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
                Text("${delivery.name} - ${delivery.distance}", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextBlack)
                Text(delivery.detail, fontSize = 12.sp, color = LightGrayText)
                Text(delivery.location, fontSize = 12.sp, color = LightGrayText)
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(delivery.weight, fontWeight = FontWeight.Bold, color = DarkGreenText, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Surface(color = badgeBackground, shape = RoundedCornerShape(8.dp)) {
                    Text(delivery.status, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), fontSize = 10.sp, color = badgeTextColor, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

// ─── 6. BOTTOM NAV BAR ────────────────────────────────────────────────────────
@Composable
fun HomeNavBar(
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
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavBarItem(R.drawable.icon_home, "Inicio", selectedIndex == 0) { onItemSelected(0) }
            NavBarItem(R.drawable.historial, "Historial", selectedIndex == 1) { onItemSelected(1) }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.offset(y = (-15).dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .shadow(10.dp, CircleShape)
                        .clip(CircleShape)
                        .background(Brush.verticalGradient(listOf(Color(0xFF4DD9D0), Color(0xFF0D6E6E))))
                        .clickable { onItemSelected(2) },
                    contentAlignment = Alignment.Center
                ) {
                    Image(painter = painterResource(id = R.drawable.meta), contentDescription = "Enviar", modifier = Modifier.requiredSize(200.dp))
                }
                Text("Metas", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = IconGreen)
            }

            NavBarItem(R.drawable.mapa_home, "Mapa", selectedIndex == 3) { onItemSelected(3) }
            NavBarItem(R.drawable.perfil_home, "Perfil", selectedIndex == 4) { onItemSelected(4) }
        }
    }
}

@Composable
private fun NavBarItem(iconRes: Int, label: String, selected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (selected) GlassSelector else Color.Transparent)
            .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier.size(32.dp).alpha(if (selected) 1f else 0.6f)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = label, fontSize = 11.sp, color = IconGreen, fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium)
    }
}