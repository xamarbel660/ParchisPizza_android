package com.example.parchispizza.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.parchispizza.R
import com.example.parchispizza.ui.theme.AppTheme


@Composable
fun Screen1(
    nombre: String,
    numPizzas: String,
    onValueChangeNombreUsuario: (String) -> Unit,
    onValueChangeNumeroPizza: (String) -> Unit,
    onNext: () -> Unit,
    onCatalogo: () -> Unit,
    onCarrea: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)), // Margen alrededor de toda la pantalla
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.bienvenida),
            style = MaterialTheme.typography.displayLarge,
        )

        Text(
            text = stringResource(R.string.introducirInformacion),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium))
        )

        Spacer(modifier = Modifier.height(24.dp))

        //Nombre del usuario
        TextField(
            value = nombre,
            onValueChange = onValueChangeNombreUsuario,
            singleLine = true, //sirve para que el campo de texto no se extienda a más de una línea
            label = { Text(stringResource(R.string.labelNombre)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.account_circle_24px),
                    "Nombre del Usuario"
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
            ),
            placeholder = { Text("Ej: Pepe") },
            modifier = Modifier.fillMaxWidth() //ocupa el ancho de la columna
        )

        Spacer(modifier = Modifier.height(15.dp))

        //Cantidad de pizzas que quiere pedir el usuario

        TextField(
            value = numPizzas,
            onValueChange = onValueChangeNumeroPizza,
            singleLine = true,
            label = { Text(stringResource(R.string.labelPizzas)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.local_pizza_24px),
                    contentDescription = "Cantidad de pizzas"
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
            ),
            placeholder = { Text("Ej: 4") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = nombre.isNotEmpty() && numPizzas.isNotEmpty(), // El botón solo funciona si has escrito algo en name
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("SIGUIENTE")
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

        Button(
            onClick = onCatalogo,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("CATÁLOGO")
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

        Button(
            onClick = onCarrea,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("CARRERA REPARTIDORES")
        }

    }
}

@Preview(showSystemUi = true, name = "PANTALLA 1")
@Composable
fun PreviewScreen1() {
    AppTheme() {
        var testName by remember { mutableStateOf("Pepito Grillo") }
        Screen1(
            nombre = testName,
            numPizzas = "3",
            onValueChangeNombreUsuario = { testName = it },
            onValueChangeNumeroPizza = {},
            onNext = {},
            onCatalogo = {},
            onCarrea = {}
        )
    }
}