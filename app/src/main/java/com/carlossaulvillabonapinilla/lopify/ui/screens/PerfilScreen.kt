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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carlossaulvillabonapinilla.lopify.R

private val IconGreen         = Color(0xFF0A1A1F)
private val BackgroundSurface = Color(0xFF72C8C0)
private val GlassSelector     = Color(0x33FFFFFF)
private val TextBlack         = Color(0xFF1A1A2E)
private val DarkGreenText     = Color(0xFF0D6E6E)

@Composable
fun PerfilScreen(
    onNavigateToHome: () -> Unit,
    onNavItemSelected: (Int) -> Unit,
    selectedNavIndex: Int
) {
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

            // ─── HEADER CON AVATAR ────────────────────────────────────────────
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                ) {
                    // Fondo curvo superior
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color(0xFF2EB8B0), Color(0xFF54C9B8))
                                ),
                                shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)
                            )
                    )

                    // Botón atrás
                    Box(
                        modifier = Modifier
                            .statusBarsPadding()
                            .padding(16.dp)
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.6f))
                            .clickable { onNavigateToHome() }
                            .align(Alignment.TopStart),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("←", fontSize = 20.sp, color = TextBlack)
                    }

                    // Avatar centrado
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(110.dp)
                                .shadow(12.dp, CircleShape)
                                .clip(CircleShape)
                                .background(Color(0xFFEBC1CC))
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.avatar),
                                contentDescription = "Perfil",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Carlos Diaz",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextBlack
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = CircleShape,
                                color = Color(0xFF2EB8B0)
                            ) {
                                Text(
                                    "✓",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Reciclador Nivel ⭐ Plata",
                            fontSize = 13.sp,
                            color = DarkGreenText,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // ─── STATS RÁPIDAS ────────────────────────────────────────────────
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatChip(modifier = Modifier.weight(1f), valor = "23.5 kg", label = "Recogido\neste mes", emoji = "♻️")
                    StatChip(modifier = Modifier.weight(1f), valor = "47",      label = "Paradas\ntotales",   emoji = "📍")
                    StatChip(modifier = Modifier.weight(1f), valor = "11",      label = "Árboles\nplantados", emoji = "🌳")
                }
            }

            // ─── CARD INFORMACIÓN ─────────────────────────────────────────────
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(24.dp),
                    shadowElevation = 6.dp,
                    color = Color.White.copy(alpha = 0.9f)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            "Información",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = DarkGreenText
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        InfoRow(emoji = "🏆", label = "Racha actual", valor = "3 días seguidos")
                        Divider()
                        InfoRow(emoji = "📦", label = "Entregas completadas", valor = "12 entregas")
                        Divider()
                        InfoRow(emoji = "🥇", label = "Material favorito", valor = "Plástico")
                        Divider()
                        InfoRow(emoji = "📅", label = "Miembro desde", valor = "Enero 2025")
                        Divider()
                        InfoRow(emoji = "🌍", label = "CO₂ evitado", valor = "18.3 kg")
                    }
                }
            }

            // ─── PROGRESO MENSUAL ─────────────────────────────────────────────
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(24.dp),
                    shadowElevation = 6.dp
                ) {
                    Column(
                        modifier = Modifier
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFF2EB8B0), Color(0xFF0D6E6E))
                                )
                            )
                            .padding(20.dp)
                    ) {
                        Text(
                            "Meta mensual",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("23.5 / 50 kg", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(10.dp)
                                        .clip(RoundedCornerShape(5.dp))
                                        .background(Color.White.copy(alpha = 0.3f))
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(0.47f)
                                            .fillMaxHeight()
                                            .clip(RoundedCornerShape(5.dp))
                                            .background(Color.White)
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text("¡Vas al 47%! Sigue así 💪", color = Color.White.copy(alpha = 0.85f), fontSize = 12.sp)
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Box(
                                modifier = Modifier.size(70.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Canvas(modifier = Modifier.fillMaxSize()) {
                                    drawArc(
                                        color = Color.White.copy(alpha = 0.25f),
                                        startAngle = -90f,
                                        sweepAngle = 360f,
                                        useCenter = false,
                                        style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
                                    )
                                    drawArc(
                                        color = Color.White,
                                        startAngle = -90f,
                                        sweepAngle = 360f * 0.47f,
                                        useCenter = false,
                                        style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
                                    )
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("47%", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                    Text("meta", color = Color.White, fontSize = 9.sp)
                                }
                            }
                        }
                    }
                }
            }

            // ─── LOGROS ───────────────────────────────────────────────────────
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(24.dp),
                    shadowElevation = 6.dp,
                    color = Color.White.copy(alpha = 0.9f)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            "Logros desbloqueados",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = DarkGreenText
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            LogroItem(emoji = "🌱", label = "Primer\nReciclo")
                            LogroItem(emoji = "🔥", label = "Racha\n3 días")
                            LogroItem(emoji = "♻️", label = "10 kg\nRecogidos")
                            LogroItem(emoji = "🌍", label = "Eco\nWarrior")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // ─── NAVBAR ───────────────────────────────────────────────────────────
        PerfilNavBar(
            selectedIndex = selectedNavIndex,
            onItemSelected = onNavItemSelected,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun StatChip(modifier: Modifier = Modifier, valor: String, label: String, emoji: String) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        color = Color.White.copy(alpha = 0.85f),
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(emoji, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(valor, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = DarkGreenText)
            Text(label, fontSize = 10.sp, color = Color(0xFF607D8B), textAlign = TextAlign.Center, lineHeight = 13.sp)
        }
    }
}

@Composable
fun InfoRow(emoji: String, label: String, valor: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(emoji, fontSize = 20.sp)
        Spacer(modifier = Modifier.width(12.dp))
        Text(label, fontSize = 13.sp, color = Color(0xFF607D8B), modifier = Modifier.weight(1f))
        Text(valor, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextBlack)
    }
}

@Composable
fun Divider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color(0xFFEEEEEE))
    )
}

@Composable
fun LogroItem(emoji: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            shape = CircleShape,
            color = Color(0xFFE0F7F5),
            modifier = Modifier.size(54.dp)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Text(emoji, fontSize = 24.sp)
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(label, fontSize = 10.sp, color = Color(0xFF607D8B), textAlign = TextAlign.Center, lineHeight = 13.sp)
    }
}

// ─── NAV BAR ──────────────────────────────────────────────────────────────────
@Composable
fun PerfilNavBar(
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
            PerfilNavBarItem(R.drawable.icon_home, "Inicio", selectedIndex == 0) { onItemSelected(0) }
            PerfilNavBarItem(R.drawable.historial, "Historial", selectedIndex == 1) { onItemSelected(1) }

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

            PerfilNavBarItem(R.drawable.mapa_home, "Mapa", selectedIndex == 3) { onItemSelected(3) }
            PerfilNavBarItem(R.drawable.perfil_home, "Perfil", selectedIndex == 4) { onItemSelected(4) }
        }
    }
}

@Composable
private fun PerfilNavBarItem(iconRes: Int, label: String, selected: Boolean, onClick: () -> Unit) {
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