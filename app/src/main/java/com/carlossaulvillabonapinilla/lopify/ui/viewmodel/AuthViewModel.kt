package com.carlossaulvillabonapinilla.lopify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

// ─── Estados UI ───────────────────────────────────────────────────────────────
sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}

// ─── ViewModel LOGIN ──────────────────────────────────────────────────────────
class AuthViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName

    val isLoggedIn: Boolean
        get() = auth.currentUser != null

    // ─── LOGIN ────────────────────────────────────────────────────────────────
    fun login(email: String, password: String) {

        if (email.isBlank() || password.isBlank()) {
            _authState.value = AuthState.Error("Completa todos los campos")
            return
        }

        viewModelScope.launch {

            _authState.value = AuthState.Loading

            try {

                auth.signInWithEmailAndPassword(email, password).await()

                loadUserData()

                _authState.value = AuthState.Success

            } catch (e: Exception) {

                _authState.value = AuthState.Error(
                    when {
                        e.message?.contains("password") == true ->
                            "Contraseña incorrecta"

                        e.message?.contains("no user") == true ->
                            "No existe esta cuenta"

                        e.message?.contains("network") == true ->
                            "Sin conexión a internet"

                        else ->
                            "Error al iniciar sesión"
                    }
                )
            }
        }
    }

    // ─── CARGAR DATOS DEL USUARIO ─────────────────────────────────────────────
    fun loadUserData() {

        val uid = auth.currentUser?.uid ?: return

        viewModelScope.launch {

            try {

                val document = db.collection("usuarios")
                    .document(uid)
                    .get()
                    .await()

                val nombre = document.getString("nombre") ?: ""
                val apellido = document.getString("apellido") ?: ""

                _userName.value = "$nombre $apellido"

            } catch (_: Exception) { }
        }
    }

    // ─── LOGOUT ───────────────────────────────────────────────────────────────
    fun logout() {
        auth.signOut()
        _authState.value = AuthState.Idle
        _userName.value = ""
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}

// ─── ViewModel REGISTER (aislado, sin interferir con Login) ───────────────────
class RegisterViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName

    fun register(
        nombre: String,
        apellido: String,
        email: String,
        password: String,
        telefono: String,
        fechaNacimiento: String
    ) {

        if (nombre.isBlank() || apellido.isBlank() || email.isBlank() || password.isBlank()) {
            _authState.value = AuthState.Error("Completa todos los campos obligatorios")
            return
        }

        if (password.length < 6) {
            _authState.value = AuthState.Error("La contraseña debe tener al menos 6 caracteres")
            return
        }

        viewModelScope.launch {

            _authState.value = AuthState.Loading

            try {

                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val uid = result.user?.uid ?: throw Exception("Error al obtener UID")

                val userData = hashMapOf(
                    "nombre" to nombre,
                    "apellido" to apellido,
                    "telefono" to telefono,
                    "fechaNacimiento" to fechaNacimiento,
                    "kgReciclados" to 0.0,
                    "racha" to 0,
                    "createdAt" to System.currentTimeMillis()
                )

                db.collection("usuarios").document(uid).set(userData).await()

                _userName.value = "$nombre $apellido"
                _authState.value = AuthState.Success

            } catch (e: Exception) {

                _authState.value = AuthState.Error(
                    when {
                        e.message?.contains("email") == true ->
                            "Este correo ya está registrado"

                        e.message?.contains("network") == true ->
                            "Sin conexión a internet"

                        else ->
                            "Error al registrar: ${e.message}"
                    }
                )
            }
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}