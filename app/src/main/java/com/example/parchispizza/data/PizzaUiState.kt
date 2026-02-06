package com.example.parchispizza.data

data class PizzaUiState(
    // Datos del usuario y pedido
    val nombreUsuario: String = "",
    val numPizzas:String = "",
    val tipoPizza: String = "",
    val conBorde: Boolean = false,
)
