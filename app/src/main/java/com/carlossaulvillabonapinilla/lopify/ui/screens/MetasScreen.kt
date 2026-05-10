package com.carlossaulvillabonapinilla.lopify.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carlossaulvillabonapinilla.lopify.R

private val IconGreen         = Color(0xFF0A1A1F)
private val BackgroundSurface = Color(0xFF72C8C0)
private val GlassSelector     = Color(0x33FFFFFF)
private val TextBlack         = Color(0xFF1A1A2E)
private val DarkGreenText     = Color(0xFF0D6E6E)

data class MetaData(
    val titulo: String,
    val actual: Float,
    val objetivo: Float,
    val color: Color,
    val iconRes: Int
)

@Composable
fun MetasScreen(
    onNavigateToHome: () -> Unit,
    onNavItemSelected: (Int) -> Unit,
    selectedNavIndex: Int
) {
    val metas = listOf(
        MetaData("Plástico",   23.5f, 50.0f, Color(0xFF29B6F6), R.drawable.icon_plastico),
        MetaData("Cartón",     15.2f, 50.0f, Color(0xFFFF8A65), R.drawable.icon_carton),
        MetaData("Papel",      30.0f, 50.0f, Color(0xFFAB47BC), R.drawable.icon_papel),
        MetaData("Vidrio",      8.7f, 50.0f, Color(0xFF26A69A), R.drawable.icon_vidrio),
        MetaData("Total",      77.4f, 200.0f, Color(0xFF66BB6A), R.drawable.bowl_reciclaje)
    )

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
                        Text("←", fontSize = 20.sp, color = TextBlack)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Metas",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextBlack
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.bowl_reciclaje),
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            // ─── RESUMEN GENERAL ──────────────────────────────────────────────
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
                            Text("Progreso general", color = Color.White, fontSize = 13.sp)
                            Text(
                                "77,4 / 200 kg",
                                color = Color.White,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Este mes vas muy bien 🌱", color = Color.White.copy(alpha = 0.85f), fontSize = 12.sp)
                        }

                        Box(
                            modifier = Modifier.size(80.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                drawArc(
                                    color = Color.White.copy(alpha = 0.25f),
                                    startAngle = -90f,
                                    sweepAngle = 360f,
                                    useCenter = false,
                                    style = Stroke(width = 9.dp.toPx(), cap = StrokeCap.Round)
                                )
                                drawArc(
                                    color = Color.White,
                                    startAngle = -90f,
                                    sweepAngle = 360f * (77.4f / 200f),
                                    useCenter = false,
                                    style = Stroke(width = 9.dp.toPx(), cap = StrokeCap.Round)
                                )
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    "39%",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Text("meta", color = Color.White, fontSize = 10.sp)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // ─── TÍTULO SECCIÓN ───────────────────────────────────────────────
            item {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Informacion",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = DarkGreenText
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.bowl_reciclaje),
                        contentDescription = null,
                        modifier = Modifier.size(26.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // ─── TARJETAS DE METAS ────────────────────────────────────────────
            items(metas.size) { index ->
                MetaCard(meta = metas[index])
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        // ─── NAVBAR ───────────────────────────────────────────────────────────
        MetasNavBar(
            selectedIndex = selectedNavIndex,
            onItemSelected = onNavItemSelected,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun MetaCard(meta: MetaData) {
    val progreso = meta.actual / meta.objetivo

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(20.dp),
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        listOf(
                            meta.color.copy(alpha = 0.85f),
                            meta.color
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.White.copy(alpha = 0.25f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = meta.iconRes),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Cantidad de ${meta.titulo}",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
                Text(
                    text = "${(progreso * 100).toInt()}%",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Barra de progreso
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White.copy(alpha = 0.3f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progreso.coerceIn(0f, 1f))
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${meta.actual} / ${meta.objetivo} kg recogidos",
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

// ─── NAV BAR ──────────────────────────────────────────────────────────────────
@Composable
fun MetasNavBar(
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
            MetasNavBarItem(R.drawable.icon_home, "Inicio", selectedIndex == 0) { onItemSelected(0) }
            MetasNavBarItem(R.drawable.historial, "Historial", selectedIndex == 1) { onItemSelected(1) }

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
                Text("Metas", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = IconGreen)
            }

            MetasNavBarItem(R.drawable.mapa_home, "Mapa", selectedIndex == 3) { onItemSelected(3) }
            MetasNavBarItem(R.drawable.perfil_home, "Perfil", selectedIndex == 4) { onItemSelected(4) }
        }
    }
}

@Composable
private fun MetasNavBarItem(iconRes: Int, label: String, selected: Boolean, onClick: () -> Unit) {
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