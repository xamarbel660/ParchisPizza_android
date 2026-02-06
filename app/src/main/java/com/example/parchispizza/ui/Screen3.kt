package com.example.parchispizza.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parchispizza.ui.theme.AppTheme


@Composable
fun Screen3(
    nombre: String,
    numPizzas: String,
    tipoPizza: String,
    conBorde: Boolean,
    onRestart: () -> Unit,
    onSendButtonClicked: (String, String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "¡Pedido Confirmado!", fontSize = 30.sp, color = Color(0xFF2E7D32)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "TICKET DE PARCHIS",
                    fontSize = 27.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(16.dp))

                FilaTicket("Jugador:", nombre)
                FilaTicket("Pizzas:", numPizzas)
                FilaTicket("Tipo de Pizza:", tipoPizza)
                FilaTicket("Extra:", if (conBorde) "Borde de Queso de Cabra" else "Sin extras")

            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onRestart, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("PEDIR OTRA PIZZA")
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {onSendButtonClicked(numPizzas, tipoPizza, conBorde)},
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("COMPARTIR")
        }
    }
}


/*Fila del ticket*/
@Composable
fun FilaTicket(titulo: String, valor: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = titulo,
        )
        Text(text = valor)
    }
}


@Preview(showSystemUi = true, name = "PANTALLA 3", showBackground = false)
@Composable
fun PreviewScreen3() {
    AppTheme() {
        Screen3(
            nombre = "Pepito Grillo",
            numPizzas = "3",
            tipoPizza = "Pizza Margarita",
            conBorde = true,
            onRestart = {},
            onSendButtonClicked={_,_,_->}
        )
    }
}