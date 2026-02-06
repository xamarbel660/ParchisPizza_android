package com.example.parchispizza

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.parchispizza.data.pizzas
import com.example.parchispizza.ui.PizzaViewModel
import com.example.parchispizza.ui.Screen1
import com.example.parchispizza.ui.Screen2
import com.example.parchispizza.ui.Screen3
import com.example.parchispizza.ui.Screen4
import com.example.parchispizza.ui.Screen5

// Pantallas
enum class ParchisScreen(@StringRes val title: Int) {
    SCREEN_1(title = R.string.screen1Title),
    SCREEN_2(title = R.string.screen2Title),
    SCREEN_3(title = R.string.screen3Title),
    SCREEN_4(title = R.string.screen4Title),
    SCREEN_5(title = R.string.screen5Title),
}

// LA BARRA SUPERIOR
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParchisTopAppBar(
    currentScreen: ParchisScreen,
    canNavigateBack: Boolean, // ¿Debo mostrar la flecha?
    navigateUp: () -> Unit,   // ¿Qué hago si la pulsan?
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
//                    text = stringResource(R.string.app_name),
                    text = stringResource(currentScreen.title),
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button),
                        tint = MaterialTheme.colorScheme.onPrimary // Para que se vea blanca sobre tu fondo primary
                    )
                }
            }
        }
    )
}

// LA APP PRINCIPAL
@Composable
fun ParchisApp(
    viewModel: PizzaViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    // Obtenemos la pila de navegación para saber en qué pantalla estamos
    val backStackEntry by navController.currentBackStackEntryAsState()

    // Calculamos la pantalla actual para poner el título correcto
    val currentScreen = ParchisScreen.valueOf(
        backStackEntry?.destination?.route ?: ParchisScreen.SCREEN_1.name
    )

    Scaffold(
        topBar = {
            ParchisTopAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->

        // Leemos el estado del ViewModel
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = ParchisScreen.SCREEN_1.name,
            modifier = Modifier.padding(innerPadding)
        ) {

            // --- PANTALLA 1: INICIO ---
            composable(route = ParchisScreen.SCREEN_1.name) {
                Screen1(
                    nombre = uiState.nombreUsuario,
                    numPizzas = uiState.numPizzas,
                    onValueChangeNombreUsuario = { viewModel.setNombre(it) },
                    onValueChangeNumeroPizza = { viewModel.setNumPizzas(it) },
                    onNext = { navController.navigate(ParchisScreen.SCREEN_2.name) },
                    onCatalogo = { navController.navigate(ParchisScreen.SCREEN_4.name) }, // Botón extra para ir al catálogo
                    onCarrea = { navController.navigate(ParchisScreen.SCREEN_5.name) } // Botón extra
                )
            }

            // --- PANTALLA 2: MENÚ (SELECT) ---
            composable(route = ParchisScreen.SCREEN_2.name) {
                Screen2(
                    tipoPizza = uiState.tipoPizza,
                    numPizzas = uiState.numPizzas,
                    onPizzaChange = { viewModel.setTipoPizza(it) },
                    tieneBorde = uiState.conBorde,
                    onBordeChange = { viewModel.setConBorde(it) },
                    onNext = { navController.navigate(ParchisScreen.SCREEN_3.name) },
                    onBack = { navController.popBackStack() } // O navigateUp()
                )
            }

            // --- PANTALLA 3: RESUMEN ---
            composable(route = ParchisScreen.SCREEN_3.name) {
                val context = LocalContext.current
                Screen3(
                    nombre = uiState.nombreUsuario,
                    numPizzas = uiState.numPizzas,
                    tipoPizza = uiState.tipoPizza,
                    conBorde = uiState.conBorde,
                    onSendButtonClicked = { numPizzas: String, tipoPizza: String, conBorde: Boolean ->
                        com.example.parchispizza.shareOrder(
                            context,
                            numPizzas = numPizzas,
                            tipoPizza = tipoPizza,
                            conBorde = conBorde
                        )
                    },
                    onRestart = {
                        viewModel.reiniciarPedido()
                        // Vuelve al inicio y borra la historia para no poder volver atrás al resumen
                        navController.popBackStack(ParchisScreen.SCREEN_1.name, inclusive = false)
                    }
                )
            }

            // --- PANTALLA 4: CATÁLOGO DE FOTOS ---
            composable(route = ParchisScreen.SCREEN_4.name) {
                Screen4(
                    pizzas = pizzas,
//                    Comentado porque tenemos la flecha de atrás en la barra superior
//                    onBack = { navController.popBackStack() }
                )
            }

            // --- PANTALLA 5: CARRERA DE REPARTIDORES ---
            composable(route = ParchisScreen.SCREEN_5.name) {
                Screen5(
                    // Le pasamos los corredores
                    playerOne = viewModel.repartidor1,
                    playerTwo = viewModel.repartidor2,

                    // Le pasamos el estado (si está corriendo o no)
                    isRunning = viewModel.carreraEnMarcha,

                    onRunStateChange = { viewModel.toggleCarrera() },
                    onRaceFinished = { viewModel.finalizarCarrera() },
                    onReset = { viewModel.reiniciarCarrera() },
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

// Función para compartir el pedido
private fun shareOrder(context: Context, numPizzas: String, tipoPizza: String, conBorde: Boolean) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Nuevo Pedido Parchís Pizza")
        putExtra(
            Intent.EXTRA_TEXT, """
        Pedido:
        Cantidad: $numPizzas
        Tipo: $tipoPizza
        Borde de queso: ${if (conBorde) "Sí" else "No"}"""
        )
    }

    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.pedidoPizza)
        )
    )
}