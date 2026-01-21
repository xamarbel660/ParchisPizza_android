/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.woof.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.parchispizza.R

/**
 * A data class to represent the information presented in the dog card
 */
data class Pizza(
    @DrawableRes val imagen: Int,
    @StringRes val nombre: Int,
    @StringRes val description: Int
)

val pizzas = listOf(
    Pizza(R.drawable.pizza_bao_de_cerdo, R.string.nombrePizza1, R.string.descPizza1),
    Pizza(R.drawable.pizza_barbacoa_texana, R.string.nombrePizza2, R.string.descPizza2),
    Pizza(R.drawable.pizza_ciberpunk, R.string.nombrePizza3, R.string.descPizza3),
    Pizza(R.drawable.pizza_cochinita_pibil, R.string.nombrePizza4, R.string.descPizza4),
    Pizza(R.drawable.pizza_curry_verde_tailandes, R.string.nombrePizza5, R.string.descPizza5),
    Pizza(R.drawable.pizza_disney, R.string.nombrePizza6, R.string.descPizza6),
    Pizza(R.drawable.pizza_el_senyor_de_los_anillos, R.string.nombrePizza7, R.string.descPizza7),
    Pizza(R.drawable.pizza_macarrones_con_queso, R.string.nombrePizza8, R.string.descPizza8),
    Pizza(R.drawable.pizza_molletes_espanyoles, R.string.nombrePizza9, R.string.descPizza9),
    Pizza(R.drawable.pizza_nintendo, R.string.nombrePizza10, R.string.descPizza10),
    Pizza(R.drawable.pizza_paella_de_mariscos, R.string.nombrePizza11, R.string.descPizza11),
    Pizza(R.drawable.pizza_shawarma_de_cordero, R.string.nombrePizza12, R.string.descPizza12),

)
