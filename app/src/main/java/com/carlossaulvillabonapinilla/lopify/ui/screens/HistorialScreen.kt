package com.carlossaulvillabonapinilla.lopify.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carlossaulvillabonapinilla.lopify.R

private val IconGreenH         = Color(0xFF0A1A1F)
private val BackgroundSurfaceH = Color(0xFF72C8C0)
private val GlassSelectorH     = Color(0x33FFFFFF)
private val TextBlackH         = Color(0xFF1A1A2E)
private val DarkGreenTextH     = Color(0xFF0D6E6E)

// ─── DATA ─────────────────────────────────────────────────────────────────────
data class EntregaHistorial(
    val id: Int,
    val hora: String,
    val material: String,
    val peso: String,
    val punto: String,
    val iconRes: Int,
    val color: Color
)

data class DiaHistorial(
    val numero: Int,
    val nombreDia: String,
    val entregas: List<EntregaHistorial>
)

// ─── DATOS DE EJEMPLO ─────────────────────────────────────────────────────────
val historialMayo = listOf(
    DiaHistorial(1, "Jue", listOf(
        EntregaHistorial(1, "09:15", "Plástico", "3.2 kg", "Punto Carrera 15", R.drawable.icon_plastico, Color(0xFF29B6F6)),
        EntregaHistorial(2, "14:30", "Papel", "1.8 kg", "Punto Centrar San Francisco", R.drawable.icon_papel, Color(0xFFAB47BC))
    )),
    DiaHistorial(2, "Vie", listOf(
        EntregaHistorial(3, "10:00", "Vidrio", "4.5 kg", "Punto Parque San Francisco", R.drawable.icon_vidrio, Color(0xFF26A69A))
    )),
    DiaHistorial(3, "Sáb", emptyList()),
    DiaHistorial(4, "Dom", emptyList()),
    DiaHistorial(5, "Lun", listOf(
        EntregaHistorial(4, "08:45", "Cartón", "6.1 kg", "Punto Carrera 15", R.drawable.icon_carton, Color(0xFFFF8A65)),
        EntregaHistorial(5, "16:00", "Plástico", "2.3 kg", "Punto Centrar San Francisco", R.drawable.icon_plastico, Color(0xFF29B6F6)),
        EntregaHistorial(6, "18:20", "Papel", "0.9 kg", "Punto Parque San Francisco", R.drawable.icon_papel, Color(0xFFAB47BC))
    )),
    DiaHistorial(6, "Mar", listOf(
        EntregaHistorial(7, "11:30", "Vidrio", "3.7 kg", "Punto Carrera 15", R.drawable.icon_vidrio, Color(0xFF26A69A))
    )),
    DiaHistorial(7, "Mié", listOf(
        EntregaHistorial(8, "09:00", "Plástico", "5.0 kg", "Punto Centrar San Francisco", R.drawable.icon_plastico, Color(0xFF29B6F6)),
        EntregaHistorial(9, "13:15", "Cartón", "2.8 kg", "Punto Parque San Francisco", R.drawable.icon_carton, Color(0xFFFF8A65))
    )),
    DiaHistorial(8, "Jue", listOf(
        EntregaHistorial(10, "10:45", "Papel", "1.5 kg", "Punto Carrera 15", R.drawable.icon_papel, Color(0xFFAB47BC))
    )),
    DiaHistorial(9, "Vie", emptyList()),
    DiaHistorial(10, "Sáb", listOf(
        EntregaHistorial(11, "12:00", "Vidrio", "4.2 kg", "Punto Centrar San Francisco", R.drawable.icon_vidrio, Color(0xFF26A69A)),
        EntregaHistorial(12, "15:30", "Plástico", "3.1 kg", "Punto Parque San Francisco", R.drawable.icon_plastico, Color(0xFF29B6F6))
    ))
)

// ─── PANTALLA PRINCIPAL ───────────────────────────────────────────────────────
@Composable
fun HistorialScreen(
    onNavigateToHome: () -> Unit,
    onNavItemSelected: (Int) -> Unit,
    selectedNavIndex: Int
) {
    var diaSeleccionado by remember { mutableStateOf(10) }
    val diasConEntregas = historialMayo.filter { it.entregas.isNotEmpty() }.map { it.numero }.toSet()
    val diaActual = historialMayo.find { it.numero == diaSeleccionado }

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

            // ─── HEADER ───────────────────────────────────────────────────────
            item {
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
                        Text("←", fontSize = 20.sp, color = TextBlackH)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "Historial",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextBlackH
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.bowl_reciclaje),
                        contentDescription = null,
                        modifier = Modifier.size(34.dp)
                    )
                }
            }

            // ─── MES Y RESUMEN ────────────────────────────────────────────────
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(24.dp),
                    shadowElevation = 6.dp
                ) {
                    Row(
                        modifier = Modifier
                            .background(Brush.linearGradient(listOf(Color(0xFF2EB8B0), Color(0xFF0D6E6E))))
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Mayo 2025", color = Color.White, fontSize = 13.sp)
                            Text(
                                "12 entregas",
                                color = Color.White,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("37.1 kg recogidos este mes", color = Color.White.copy(alpha = 0.85f), fontSize = 12.sp)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("🗓️", fontSize = 36.sp)
                            Text("10 días", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // ─── CALENDARIO DÍAS ──────────────────────────────────────────────
            item {
                Text(
                    "  Selecciona un día",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = DarkGreenTextH
                )
                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(historialMayo) { dia ->
                        val seleccionado = dia.numero == diaSeleccionado
                        val tieneEntregas = dia.entregas.isNotEmpty()

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { diaSeleccionado = dia.numero }
                        ) {
                            Text(
                                dia.nombreDia,
                                fontSize = 11.sp,
                                color = if (seleccionado) DarkGreenTextH else TextBlackH.copy(alpha = 0.5f),
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .shadow(if (seleccionado) 6.dp else 0.dp, CircleShape)
                                    .clip(CircleShape)
                                    .background(
                                        when {
                                            seleccionado -> Color(0xFF0D6E6E)
                                            tieneEntregas -> Color.White.copy(alpha = 0.8f)
                                            else -> Color.White.copy(alpha = 0.3f)
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    dia.numero.toString(),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = when {
                                        seleccionado -> Color.White
                                        tieneEntregas -> DarkGreenTextH
                                        else -> TextBlackH.copy(alpha = 0.4f)
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            // Punto indicador de entregas
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (tieneEntregas) Color(0xFF0D6E6E)
                                        else Color.Transparent
                                    )
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // ─── ENTREGAS DEL DÍA ─────────────────────────────────────────────
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White.copy(alpha = 0.85f),
                    shadowElevation = 4.dp
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "Día ${diaSeleccionado} de Mayo",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = DarkGreenTextH
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if ((diaActual?.entregas?.size ?: 0) > 0)
                                    Color(0xFFE0F7F5) else Color(0xFFF5F5F5)
                            ) {
                                Text(
                                    "${diaActual?.entregas?.size ?: 0} entregas",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if ((diaActual?.entregas?.size ?: 0) > 0)
                                        DarkGreenTextH else Color(0xFF9E9E9E),
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        if (diaActual?.entregas.isNullOrEmpty()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("😴", fontSize = 36.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "Sin entregas este día",
                                    color = Color(0xFF9E9E9E),
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        } else {
                            diaActual?.entregas?.forEach { entrega ->
                                EntregaItem(entrega = entrega)
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }
                }
            }
        }

        // ─── NAVBAR ───────────────────────────────────────────────────────────
        HistorialNavBar(
            selectedIndex = selectedNavIndex,
            onItemSelected = onNavItemSelected,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

// ─── TARJETA ENTREGA EXPANDIBLE ───────────────────────────────────────────────
@Composable
fun EntregaItem(entrega: EntregaHistorial) {
    var expandida by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { expandida = !expandida },
        shape = RoundedCornerShape(16.dp),
        color = entrega.color.copy(alpha = 0.12f)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            // ─── FILA PRINCIPAL (siempre visible) ─────────────────────────────
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(entrega.color.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = entrega.iconRes),
                        contentDescription = null,
                        modifier = Modifier.size(26.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        entrega.material,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = TextBlackH
                    )
                    Text(
                        entrega.hora,
                        fontSize = 12.sp,
                        color = Color(0xFF607D8B)
                    )
                }

                Text(
                    entrega.peso,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = entrega.color
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    if (expandida) "▲" else "▼",
                    fontSize = 11.sp,
                    color = Color(0xFF9E9E9E)
                )
            }

            // ─── DETALLE EXPANDIBLE ───────────────────────────────────────────
            AnimatedVisibility(
                visible = expandida,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(entrega.color.copy(alpha = 0.3f))
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    DetalleRow(label = "🕐 Hora", valor = entrega.hora)
                    Spacer(modifier = Modifier.height(6.dp))
                    DetalleRow(label = "♻️ Material", valor = entrega.material)
                    Spacer(modifier = Modifier.height(6.dp))
                    DetalleRow(label = "⚖️ Peso", valor = entrega.peso)
                    Spacer(modifier = Modifier.height(6.dp))
                    DetalleRow(label = "📍 Punto", valor = entrega.punto)
                }
            }
        }
    }
}

@Composable
fun DetalleRow(label: String, valor: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 12.sp, color = Color(0xFF607D8B))
        Text(valor, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextBlackH)
    }
}

// ─── NAV BAR ──────────────────────────────────────────────────────────────────
@Composable
fun HistorialNavBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shadowElevation = 24.dp,
        color = BackgroundSurfaceH,
        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HistorialNavBarItem(R.drawable.icon_home, "Inicio", selectedIndex == 0) { onItemSelected(0) }
            HistorialNavBarItem(R.drawable.historial, "Historial", selectedIndex == 1) { onItemSelected(1) }

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
                    Image(
                        painter = painterResource(id = R.drawable.meta),
                        contentDescription = "Metas",
                        modifier = Modifier.requiredSize(200.dp)
                    )
                }
                Text("Metas", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = IconGreenH)
            }

            HistorialNavBarItem(R.drawable.mapa_home, "Mapa", selectedIndex == 3) { onItemSelected(3) }
            HistorialNavBarItem(R.drawable.perfil_home, "Perfil", selectedIndex == 4) { onItemSelected(4) }
        }
    }
}

@Composable
private fun HistorialNavBarItem(iconRes: Int, label: String, selected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (selected) GlassSelectorH else Color.Transparent)
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
            color = IconGreenH,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
        )
    }
}