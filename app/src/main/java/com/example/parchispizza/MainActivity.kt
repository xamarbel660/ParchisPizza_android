package com.example.parchispizza

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

/* ---------- ESTADO DE PANTALLA ---------- */
//esta comentado porque lo tengo lo mismo en una dataClass llamado ParchisScreen
/*enum class Screen {
    SCREEN_1,
    SCREEN_2,
    SCREEN_3
}*/

/*Poner el barra de navegacion que vimos el ultimo dia*//*Hacer pantalla para ver las pizzas disponibles usar lazyColumn y cards*//*Agregar colores (lo que vimos casi los ultimos dias), tipografias nuevas*/

@Composable
fun App() {
    // Estado de la pantalla
    var currentScreen by remember { mutableStateOf(ParchisScreen.SCREEN_1) }

    var nombreUsuarioInput by remember { mutableStateOf("") }
    var numeroDePizzasInput by remember { mutableStateOf("") }

//    var numeroDePizzas = numeroDePizzasInput.toIntOrNull() ?: 1

    var pizzaInput by remember { mutableStateOf("") }
    var hasCheeseBorder by remember { mutableStateOf(false) }


    when (currentScreen) {
        ParchisScreen.SCREEN_1 -> Screen1(
            nombre = nombreUsuarioInput,
            numPizzas = numeroDePizzasInput,
            onValueChangeNombreUsuario = { nombreUsuarioInput = it },
            onValueChangeNumeroPizza = { numeroDePizzasInput = it },

            onNext = { currentScreen = ParchisScreen.SCREEN_2 })

        ParchisScreen.SCREEN_2 -> {
            Screen2(
                tipoPizza = pizzaInput,
                numPizzas = numeroDePizzasInput,
                onPizzaChange = { pizzaInput = it },
                tieneBorde = hasCheeseBorder,
                onBordeChange = { hasCheeseBorder = it },

                onNext = { currentScreen = ParchisScreen.SCREEN_3 },
                onBack = { currentScreen = ParchisScreen.SCREEN_1 })
        }

        ParchisScreen.SCREEN_3 -> Screen3(
            nombre = nombreUsuarioInput,
            numPizzas = numeroDePizzasInput,
            tipoPizza = pizzaInput,
            conBorde = hasCheeseBorder,
            onRestart = {
                nombreUsuarioInput = ""
                numeroDePizzasInput = ""
                pizzaInput = ""
                hasCheeseBorder = false
                currentScreen = ParchisScreen.SCREEN_1
            })

        else -> print("High-priority handling")
    }
}

/* ---------- PANTALLA 1 ---------- */
@Composable
fun Screen1(
    nombre: String,
    numPizzas: String,
    onValueChangeNombreUsuario: (String) -> Unit,
    onValueChangeNumeroPizza: (String) -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp), // Margen alrededor de toda la pantalla
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(R.string.despImagen),
            modifier = Modifier.size(250.dp)
        )
        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = stringResource(R.string.bienvenida), fontSize = 24.sp
        )

        Text(
            text = stringResource(R.string.introducirInformacion),
            fontSize = 15.sp,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = nombre,
            onValueChange = onValueChangeNombreUsuario,
            singleLine = true, //sirve para que el campo de texto no se extienda a más de una línea
            //tambien lo pongo en el archivo de textos??
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
            //tambien lo pongo en el archivo de textos??
            placeholder = { Text("Ej: Pepe") },
            modifier = Modifier.fillMaxWidth() //ocupa el ancho de la columna
        )
        Spacer(modifier = Modifier.height(15.dp))

        /*Poner la cantidad de pizzas que quiere pedir el usuario con otro textField*/
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
            enabled = nombre.isNotEmpty() && numPizzas.isNotEmpty() // El botón solo funciona si has escrito algo en name
        ) {
            //tambien lo pongo en el archivo de textos??
            Text("SIGUIENTE")
        }
    }
}

/* ---------- PANTALLA 2 ---------- */
@Composable
fun Screen2(
    tipoPizza: String,
    numPizzas: String,
    onPizzaChange: (String) -> Unit,
    tieneBorde: Boolean,
    onBordeChange: (Boolean) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(R.string.despImagen),
            modifier = Modifier.size(250.dp)
        )
        Spacer(modifier = Modifier.height(35.dp))

        Text(
            text = "Ponga el tipo de pizza para tus $numPizzas pizzas:", fontSize = 20.sp
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
        )
        Spacer(modifier = Modifier.height(38.dp))

        /*Switch*/
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFF3E0))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "¿Borde de Queso de Cabra?")
            Switch(
                checked = tieneBorde, onCheckedChange = onBordeChange
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

/* ---------- PANTALLA 3 ---------- */
@Composable
fun Screen3(
    nombre: String, numPizzas: String, tipoPizza: String, conBorde: Boolean, onRestart: () -> Unit
) {
    Column(
        modifier = Modifier
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
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDE7))
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("TICKET DE PARCHIS", fontSize = 27.sp)
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
    }
}


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

/* ---------- PREVIEWS ---------- */

//showSystemUi sirve para que el preview se vea como una pantalla de móvil
//name para ponerle un nombre al preview
@Preview(showSystemUi = true, name = "PANTALLA 1")
@Composable
fun PreviewScreen1() {
    var testName by remember { mutableStateOf("Pepito Grillo") }
    Screen1(
        nombre = testName,
        numPizzas = "3",
        onValueChangeNombreUsuario = { testName = it },
        onValueChangeNumeroPizza = {},
        onNext = {}
    )
}

@Preview(showSystemUi = true, name = "PANTALLA 2")
@Composable
fun PreviewScreen2() {
    var pizza by remember { mutableStateOf("Pizza Margarita") }
    var testBorde by remember { mutableStateOf(true) }

    Screen2(
        tipoPizza = pizza,
        numPizzas = "3",
        onPizzaChange = { pizza = it },
        tieneBorde = testBorde,
        onBordeChange = { testBorde = it },
        onNext = {},
        onBack = {})
}

@Preview(showSystemUi = true, name = "PANTALLA 3", showBackground = false)
@Composable
fun PreviewScreen3() {
    Screen3(
        nombre = "Pepito Grillo",
        numPizzas = "3",
        tipoPizza = "Pizza Margarita",
        conBorde = true,
        onRestart = {})
}

