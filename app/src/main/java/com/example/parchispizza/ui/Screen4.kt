package com.example.parchispizza.ui

import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.parchispizza.R
import com.example.parchispizza.data.Pizza
import com.example.parchispizza.ui.theme.AppTheme
import com.example.parchispizza.data.pizzas


@Composable
fun Screen4(
    pizzas: List<Pizza>,
    // Comentado porque tenemos la flecha de atrás en la barra superior
//    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

//        Spacer(modifier = Modifier.height(20.dp))
        // Comentado porque tenemos la flecha de atrás en la barra superior
//        Button(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp),
//            onClick = onBack
//        ) {
//            Text("Ir al Menu")
//        }
        Spacer(modifier = Modifier.height(20.dp))

        PizzaList(pizzas)


    }
}


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

//Card de la pizza
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
    Card(
        modifier = modifier,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
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

//Icono de expansión para enseñar la descripcion de la pizza
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

//Descripción de la pizza
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


@Preview(showSystemUi = true, name = "PANTALLA 4", showBackground = false)
@Composable
fun PreviewScreen4() {
    AppTheme() {
        Screen4(
            pizzas = pizzas,
        )
    }
}