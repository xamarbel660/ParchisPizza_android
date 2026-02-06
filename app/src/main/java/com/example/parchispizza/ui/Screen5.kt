package com.example.parchispizza.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.parchispizza.R
import com.example.parchispizza.RaceParticipant
import com.example.parchispizza.progressFactor
import com.example.parchispizza.ui.theme.AppTheme
import kotlinx.coroutines.launch


@Composable
fun Screen5(
    playerOne: RaceParticipant,
    playerTwo: RaceParticipant,
    isRunning: Boolean,
    onRunStateChange: () -> Unit, // Para iniciar/pausar
    onRaceFinished: () -> Unit,   // Para que el VM sepa que acabaron
    onReset: () -> Unit,          // Para reiniciar
    onBack: () -> Unit,           // Para volver atrás
    modifier: Modifier = Modifier
) {
    if (isRunning) {
        LaunchedEffect(playerOne, playerTwo) {
            // Esto arranca cuando isRunning = true
            kotlinx.coroutines.coroutineScope {
                launch { playerOne.run() }
                launch { playerTwo.run() }
            }
            // Si llega aquí, es que ambos han terminado la carrera
            onRaceFinished()
        }
        // Si isRunning cambia a false desde fuera (botón),
        // este bloque se cancela y los corredores se pausan.
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título
        Text(
            text = "Carrera de Repartidores",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Icon(
            painter = painterResource(R.drawable.moped_package_48px),
            contentDescription = stringResource(R.string.tituloIconoMoto),
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
        )

        Spacer(modifier = Modifier.height(24.dp))

        // BARRA DE PROGRESO 1 (ROJO)
        StatusIndicator(
            participantName = playerOne.name,
            currentProgress = playerOne.currentProgress,
            maxProgress = playerOne.maxProgress,
            progressFactor = playerOne.progressFactor,
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(24.dp))

        // BARRA DE PROGRESO 2 (AZUL)
        StatusIndicator(
            participantName = playerTwo.name,
            currentProgress = playerTwo.currentProgress,
            maxProgress = playerTwo.maxProgress,
            progressFactor = playerTwo.progressFactor,
            color = Color.Blue
        )

        Spacer(modifier = Modifier.height(48.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Botón Iniciar/Pausar
            Button(
                onClick = onRunStateChange, // Alterna true/false
                modifier = Modifier.weight(1f)
            ) {
                // El texto cambia según el estado
                Text(if (isRunning) "PAUSAR" else "ARRANCAR")
            }

            // Botón Reiniciar
            OutlinedButton(
                onClick = onReset,
                modifier = Modifier.weight(1f)
            ) {
                Text("REINICIAR")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón volver al menu
        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("VOLVER AL MENÚ")
        }
    }
}

// Componente para mostrar la barra de progreso
@Composable
private fun StatusIndicator(
    participantName: String,
    currentProgress: Int,
    maxProgress: Int,
    progressFactor: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = participantName,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$currentProgress / $maxProgress"
            )
        }

        // La barra de progreso
        LinearProgressIndicator(
            progress = { progressFactor }, // Forma moderna de llamar al progreso
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .padding(top = 8.dp)
                .clip(RoundedCornerShape(8.dp)),
            color = color
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen5() {
    val playerOne = remember {
        RaceParticipant(name = "Repartidor 1", progressIncrement = 5)
    }
    val playerTwo = remember {
        RaceParticipant(name = "Repartidor 2", progressIncrement = 5)
    }
    AppTheme() {
        Screen5(
            onBack = {},
            playerOne = playerOne,
            playerTwo = playerTwo,
            isRunning = true,
            onRunStateChange = {},
            onReset = {},
            onRaceFinished = {}
        )
    }
}