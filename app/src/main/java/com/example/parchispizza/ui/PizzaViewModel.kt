package com.example.parchispizza.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.parchispizza.RaceParticipant
import com.example.parchispizza.data.PizzaUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PizzaViewModel : ViewModel() {

    // ESTADO la caja privada y la pública
    private val _uiState = MutableStateFlow(PizzaUiState())
    val uiState: StateFlow<PizzaUiState> = _uiState.asStateFlow()

    // FUNCIONES PARA MODIFICAR DATOS
    // Cuando escriben el nombre en Screen 1
    fun setNombre(nombre: String) {
        _uiState.update { it.copy(nombreUsuario = nombre) }
    }

    // Cuando eligen pizza
    fun setTipoPizza(pizza: String) {
        _uiState.update { it.copy(tipoPizza = pizza) }
    }

    // Cuando activan el switch de borde con queso
    fun setConBorde(borde: Boolean) {
        _uiState.update { it.copy(conBorde = borde) }
    }

    // Cuando escriben el número de pizzas
    fun setNumPizzas(numPizzas: String) {
        _uiState.update { it.copy(numPizzas = numPizzas) }
    }

    // Para reiniciar todo cuando terminan (Screen 3 -> Inicio)
    fun reiniciarPedido() {
        _uiState.value = PizzaUiState() // Reseteamos a valores por defecto
    }


    // Variables de dos repartidores predefinidos para la carrera
    val repartidor1 = RaceParticipant(name = "Repartidor Rojo", progressIncrement = 1,)
    val repartidor2 = RaceParticipant(name = "Repartidor Azul", progressIncrement = 1,)

    // Para saber si la carrera está en marcha
    // Es el que contrala si se para la carrera o no (Start/Pause)
    var carreraEnMarcha by mutableStateOf(false)
        private set

    fun finalizarCarrera() {
        carreraEnMarcha = false
    }

    // Para pasar de Start a Pause y viceversa
    fun toggleCarrera() {
        carreraEnMarcha = !carreraEnMarcha
    }

    // Para el reinicio de la carrera
    fun reiniciarCarrera() {
        repartidor1.reset()
        repartidor2.reset()
        carreraEnMarcha = false
    }
}