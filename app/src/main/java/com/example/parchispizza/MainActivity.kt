package com.example.parchispizza

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parchispizza.ui.theme.AppTheme
import com.example.parchispizza.ui.theme.primaryLight
import com.example.woof.data.Pizza
import com.example.woof.data.pizzas


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    App()
                }
            }

        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun App() {
    Scaffold(
        topBar = {
            TopAppBar()
        }
    ) {
        // Estado de la pantalla
        var currentScreen by remember { mutableStateOf(ParchisScreen.SCREEN_1) }

        var nombreUsuarioInput by remember { mutableStateOf("") }
        var numeroDePizzasInput by remember { mutableStateOf("") }


        var pizzaInput by remember { mutableStateOf("") }
        var hasCheeseBorder by remember { mutableStateOf(false) }

        val pizzas = pizzas

        when (currentScreen) {
            ParchisScreen.SCREEN_1 -> Screen1(
                nombre = nombreUsuarioInput,
                numPizzas = numeroDePizzasInput,
                onValueChangeNombreUsuario = { nombreUsuarioInput = it },
                onValueChangeNumeroPizza = { numeroDePizzasInput = it },
                onNext = { currentScreen = ParchisScreen.SCREEN_2 },
                onCatalogo = { currentScreen = ParchisScreen.SCREEN_4 },
            )

            ParchisScreen.SCREEN_2 -> {
                Screen2(
                    tipoPizza = pizzaInput,
                    numPizzas = numeroDePizzasInput,
                    onPizzaChange = { pizzaInput = it },
                    tieneBorde = hasCheeseBorder,
                    onBordeChange = { hasCheeseBorder = it },

                    onNext = { currentScreen = ParchisScreen.SCREEN_3 },
                    onBack = { currentScreen = ParchisScreen.SCREEN_1 },
                )
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
                },
            )

            ParchisScreen.SCREEN_4 -> {
                Screen4(
                    pizzas = pizzas,
                    onBack = { currentScreen = ParchisScreen.SCREEN_1 },
                )
            }

            else -> print("Error")
        }
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
    onCatalogo: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)), // Margen alrededor de toda la pantalla
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(150.dp))

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

        /*Cantidad de pizzas que quiere pedir el usuario*/
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
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(150.dp))

        Text(
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
        )

        Spacer(modifier = Modifier.height(38.dp))

        /*Switch*/
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.tertiaryContainer,
                    shape = MaterialTheme.shapes.medium
                )
                /*.background(MaterialTheme.colorScheme.tertiary)*/
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

/* ---------- PANTALLA 3 ---------- */
@Composable
fun Screen3(
    nombre: String,
    numPizzas: String,
    tipoPizza: String,
    conBorde: Boolean,
    onRestart: () -> Unit,
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
                Text("TICKET DE PARCHIS", fontSize = 27.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
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

/* ---------- PANTALLA 4 ---------- */
@Composable
fun Screen4(
    pizzas: List<Pizza>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(110.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = onBack
        ) {
            Text("Ir al Menu")
        }
        Spacer(modifier = Modifier.height(20.dp))

        PizzaList(pizzas)


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

/*Barra superior*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size))
                        .padding(dimensionResource(id = R.dimen.padding_small)),
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = stringResource(R.string.despImagen),
                )
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        //Color del topAppBar
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        modifier = modifier
    )
}

/*Card de la pizza*/
@Composable
fun PizzaCard(
    pizza: Pizza,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val color by animateColorAsState(
        targetValue = if (expanded)
            MaterialTheme.colorScheme.primaryContainer
        else
            MaterialTheme.colorScheme.surface,
    )
    Card(modifier = modifier,border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
                .background(color = color)
        ) {
            Image(
                painter = painterResource(pizza.imagen),
                contentDescription = stringResource(pizza.description),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
            Row() {
                Text(
                    text = LocalContext.current.getString(pizza.nombre),
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(Modifier.weight(1f))
                ItemButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded },
                )
            }
            if (expanded) {
                PizzaDescrip(
                    pizza.description, modifier = Modifier.padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        top = dimensionResource(R.dimen.padding_small),
                        end = dimensionResource(R.dimen.padding_medium),
                        bottom = dimensionResource(R.dimen.padding_medium)
                    )
                )
            }

        }
    }
}

/*Icono de expansión para enseñar la descripcion de la pizza*/
@Composable
private fun ItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(R.string.expand_button_content_description),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

/*Descripción de la pizza*/
@Composable
fun PizzaDescrip(
    @StringRes pizzaDescrip: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(pizzaDescrip),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

/*Lista de pizzas*/
@Composable
fun PizzaList(
    pizzaList: List<Pizza>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.padding(bottom = 50.dp)) {
        items(pizzaList) { pizza ->
            PizzaCard(
                pizza = pizza,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


/* ---------- PREVIEWS ---------- */

//showSystemUi sirve para que el preview se vea como una pantalla de móvil
//name para ponerle un nombre al preview
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
            onCatalogo = {}
        )
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

@Preview(showSystemUi = true, name = "PANTALLA 3", showBackground = false)
@Composable
fun PreviewScreen3() {
    AppTheme() {
        Screen3(
            nombre = "Pepito Grillo",
            numPizzas = "3",
            tipoPizza = "Pizza Margarita",
            conBorde = true,
            onRestart = {}
        )
    }
}

@Preview(showSystemUi = true, name = "PANTALLA 4", showBackground = false)
@Composable
fun PreviewScreen4() {
    AppTheme() {
        Screen4(
            pizzas = pizzas,
            onBack = {}
        )
    }
}

/*Este es el unico que se ve el topBar y el modo oscuro*/
@Preview(showSystemUi = true, name = "TopAppBar")
@Composable
fun PreviewApp() {
    AppTheme(darkTheme = true) {
        App()
    }
}
