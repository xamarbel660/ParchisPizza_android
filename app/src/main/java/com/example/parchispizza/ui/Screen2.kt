package com.example.parchispizza.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.parchispizza.data.pizzas
import com.example.parchispizza.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class) // Necesario para el menú desplegable
@Composable
fun Screen2(
    tipoPizza: String,
    numPizzas: String,
    onPizzaChange: (String) -> Unit,
    tieneBorde: Boolean,
    onBordeChange: (Boolean) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {

    // Variable local para controlar si el menú está abierto o cerrado
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        //Esto es sin el select, se usa un TextField
        /*Text(
            text = "Ponga el tipo de pizza para tus $numPizzas pizzas:",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        TextField(
            value = tipoPizza,
            onValueChange = onPizzaChange,
            singleLine = true, //sirve para que el campo de texto no se extienda a más de una línea
            label = { Text("Tipo Pizza") },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.local_pizza_24px), "Pizza"
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
            ),
            placeholder = { Text("Ej: Pizza Margarita") },
            modifier = Modifier.fillMaxWidth() //ocupa el ancho de la columna
        )*/

        Text(
            text = "Ponga el tipo de pizza para tus $numPizzas pizzas:",
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Cambio de textFiled por un menú desplegable (ExposedDropdownMenuBox)
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded } // Al pulsar, se abre/cierra
        ) {
            // Caja de texto que muestra lo seleccionado
            TextField(
                value = tipoPizza,
                onValueChange = {}, // No dejamos escribir manual, solo seleccionar
                readOnly = true,
                label = { Text("Selecciona tu Pizza") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }, // La flechita
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier
                    .menuAnchor(
                        MenuAnchorType.PrimaryNotEditable,
                        true
                    ) // Ancla el menú a este campo
                    .fillMaxWidth()
            )

            // Lista de opciones que se despliega
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false } // Si tocas fuera, se cierra
            ) {
                // Recorremos las pizzas
                pizzas.forEach { pizza ->
                    // Obtenemos el nombre real usando stringResource
                    val nombrePizza = stringResource(id = pizza.nombre)

                    DropdownMenuItem(
                        text = { Text(text = nombrePizza) },
                        onClick = {
                            onPizzaChange(nombrePizza) // ¡Guardamos en el ViewModel!
                            expanded = false // Cerramos el menú
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(38.dp))

        //Switch

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.tertiaryContainer,
                    shape = MaterialTheme.shapes.medium
                )
                .background(MaterialTheme.colorScheme.tertiary)

                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "¿Borde de Queso de Cabra?",
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                style = MaterialTheme.typography.bodyLarge
            )
            Switch(
                checked = tieneBorde, onCheckedChange = onBordeChange,
                colors = (SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colorScheme.surface)),
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onBack) { Text("Ir al Menu") }
            Button(onClick = onNext, enabled = tipoPizza.isNotEmpty()) { Text("Ver Ticket") }
        }
    }
}

@Preview(showSystemUi = true, name = "PANTALLA 2")
@Composable
fun PreviewScreen2() {
    var pizza by remember { mutableStateOf("Pizza Margarita") }
    var testBorde by remember { mutableStateOf(true) }

    AppTheme() {
        Screen2(
            tipoPizza = pizza,
            numPizzas = "3",
            onPizzaChange = { pizza = it },
            tieneBorde = testBorde,
            onBordeChange = { testBorde = it },
            onNext = {},
            onBack = {}
        )
    }
}