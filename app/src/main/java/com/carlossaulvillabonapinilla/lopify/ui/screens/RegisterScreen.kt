package com.carlossaulvillabonapinilla.lopify.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carlossaulvillabonapinilla.lopify.R
import com.carlossaulvillabonapinilla.lopify.viewmodel.AuthState
import com.carlossaulvillabonapinilla.lopify.viewmodel.RegisterViewModel

// ─── Colores ──────────────────────────────────────────────────────────────────
private val BackgroundColor = Color(0xFFF0F5F0)
private val GreenPrimary = Color(0xFF4CAF50)
private val GreenSec = Color(0xFF6EC979)
private val GreenText = Color(0xFF4CAF50)
private val TitleColor = Color(0xFF1A1A1A)
private val SubtitleColor = Color(0xFF888888)
private val FieldBackground = Color(0xFFFFFFFF)
private val FieldBorder = Color(0xFFE8E8E8)

// ─── Fuente ───────────────────────────────────────────────────────────────────
private val GoogleSansSemiBold = FontFamily(
    Font(R.font.googlesans_semibold, FontWeight.SemiBold)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    onBackClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {

    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    val authState by viewModel.authState.collectAsState()

    val navigateToLogin = authState is AuthState.Success

    LaunchedEffect(navigateToLogin) {
        if (navigateToLogin) {
            viewModel.resetState()
            onLoginClick()
        }
    }

    // ─── Animaciones ──────────────────────────────────────────────────────────
    val infiniteTransition = rememberInfiniteTransition(label = "plantBob")

    val plantOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -10f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "plantOffset"
    )

    val plantRotation by infiniteTransition.animateFloat(
        initialValue = -2f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "plantRotation"
    )

    val bulbScale = remember { Animatable(1f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF3DD92A), Color(0xFFF0F5F0))
                        )
                    )
            ) {

                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .padding(start = 8.dp, top = 16.dp)
                        .align(Alignment.TopStart)
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.flecha),
                        contentDescription = "Volver",
                        tint = TitleColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column(modifier = Modifier.weight(1f)) {

                        Text(
                            text = "Nuevo Reciclador",
                            fontFamily = GoogleSansSemiBold,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 30.sp,
                            color = TitleColor
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Crea una cuenta Reciclador para continuar!",
                            fontSize = 13.sp,
                            color = SubtitleColor
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.planta),
                        contentDescription = null,
                        modifier = Modifier
                            .size(90.dp)
                            .padding(start = 8.dp)
                            .offset(y = plantOffset.dp)
                            .graphicsLayer { rotationZ = plantRotation }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                RegisterTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    placeholder = "Nombre"
                )

                Spacer(modifier = Modifier.height(12.dp))

                RegisterTextField(
                    value = apellido,
                    onValueChange = { apellido = it },
                    placeholder = "Apellido"
                )

                Spacer(modifier = Modifier.height(12.dp))

                RegisterTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "Email",
                    keyboardType = KeyboardType.Email
                )

                Spacer(modifier = Modifier.height(12.dp))

                RegisterTextField(
                    value = fechaNacimiento,
                    onValueChange = { fechaNacimiento = it },
                    placeholder = "DD-MM-YYYY",
                    keyboardType = KeyboardType.Number,
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.calendar),
                                contentDescription = "Calendario",
                                tint = SubtitleColor,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                )

                if (showDatePicker) {

                    val datePickerState = rememberDatePickerState()

                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    datePickerState.selectedDateMillis?.let { millis ->
                                        val sdf = java.text.SimpleDateFormat(
                                            "dd-MM-yyyy",
                                            java.util.Locale.getDefault()
                                        )
                                        fechaNacimiento = sdf.format(java.util.Date(millis))
                                    }
                                    showDatePicker = false
                                }
                            ) { Text("Aceptar", color = GreenPrimary) }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDatePicker = false }) {
                                Text("Cancelar", color = SubtitleColor)
                            }
                        }
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                RegisterTextField(
                    value = telefono,
                    onValueChange = { telefono = it },
                    placeholder = "Telefono",
                    keyboardType = KeyboardType.Phone
                )

                Spacer(modifier = Modifier.height(12.dp))

                RegisterTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "Contraseña",
                    isPassword = !passwordVisible,
                    trailingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.bombillo),
                            contentDescription = null,
                            modifier = Modifier
                                .size(28.dp)
                                .scale(bulbScale.value)
                                .alpha(if (passwordVisible) 1f else 0.35f)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) { passwordVisible = !passwordVisible },
                            colorFilter = if (passwordVisible) null
                            else ColorFilter.colorMatrix(
                                ColorMatrix().apply { setToSaturation(0f) }
                            )
                        )
                    }
                )

                Spacer(modifier = Modifier.height(28.dp))

                // ─── Mensaje de error ──────────────────────────────────────────
                if (authState is AuthState.Error) {
                    Text(
                        text = (authState as AuthState.Error).message,
                        color = Color.Red,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                // ─── Botón Verificación (obligatorio) ─────────────────────────
                Button(
                    onClick = { /* TODO: lógica de verificación */ },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
                ) {
                    Text(
                        text = "Verificacion (obligatorio)",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // ─── Botón Register ────────────────────────────────────────────
                Button(
                    onClick = {
                        viewModel.register(
                            nombre = nombre,
                            apellido = apellido,
                            email = email,
                            password = password,
                            telefono = telefono,
                            fechaNacimiento = fechaNacimiento
                        )
                    },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GreenSec)
                ) {
                    if (authState is AuthState.Loading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(22.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = "Register",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(35.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Ya tienes una cuenta? ", fontSize = 13.sp, color = SubtitleColor)
                    TextButton(onClick = onLoginClick, contentPadding = PaddingValues(0.dp)) {
                        Text(
                            text = "Ingresa sesión",
                            fontSize = 13.sp,
                            color = GreenText,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

// ─── TextField reutilizable ───────────────────────────────────────────────────
@Composable
private fun RegisterTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = placeholder, color = Color(0xFFBBBBBB), fontSize = 15.sp)
        },
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        trailingIcon = trailingIcon,
        shape = RoundedCornerShape(14.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = FieldBackground,
            focusedContainerColor = FieldBackground,
            unfocusedBorderColor = FieldBorder,
            focusedBorderColor = GreenPrimary
        ),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(14.dp))
    )
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() { }