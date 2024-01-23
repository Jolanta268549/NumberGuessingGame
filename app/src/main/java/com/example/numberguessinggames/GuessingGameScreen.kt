package com.example.numberguessinggames

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.numberguessinggames.ui.theme.BlueDark
import com.example.numberguessinggames.ui.theme.YellowDark
import kotlinx.coroutines.delay

//Jolanta Mlynczak 268549 gra w zgadywanie liczby calkowitej z zakresu od 0 do 100.

// Adnotacja o eksperymentalnej funkcjonalności Material3
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuessingGameScreen(
    viewModel: MainViewModel
){
    // Pobranie kontekstu
    val context = LocalContext.current
    // Pobranie stanu z ViewModel za pomocą Flow
    val state by viewModel.state.collectAsState()

    // Warunek określający etap gry
    when(state.gameStage) {
        // Obsługa stanu gry "PLAYING" - stan zdefiniowany jest w GuessingGameState.kt
        GameStage.PLAYING -> {
            ScreenContent(
                state = state,
                onValueChange = { viewModel.updateTextField(userNo = it) },
                onEnterButtonClicked = {
                    viewModel.onUserInput(
                        userNumber = state.userNumber,
                        context = context
                    )
                }
            )
        }
        GameStage.WON -> {
            // Obsługa stanu gry "WON"
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BlueDark)
            ) {
                WinOrLoseDialog(
                    text = "Congratulations\nYou won",
                    buttonText = "Play Again",
                    mysteryNumber = state.mysteryNumber
                ) { viewModel.resetGame() }
            }
        }
        GameStage.LOSE -> {
            // Obsługa stanu gry "LOSE"
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BlueDark)
            ) {
                WinOrLoseDialog(
                    text = "Better Luck next time",
                    buttonText = "Try Again",
                    mysteryNumber = state.mysteryNumber
                ) { viewModel.resetGame() }
            }
        }
    }

    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(
    state: GuessingGameState,
    onValueChange: (String) -> Unit,
    onEnterButtonClicked: () -> Unit
) {
    // Utworzenie obiektu FocusRequester do zarządzania fokusem
    val focusRequester = remember { FocusRequester() }
    // Opóźnienie ustawienia fokusa przy uruchomieniu komponentu
    LaunchedEffect(key1 = Unit) {
        delay(500)
        focusRequester.requestFocus()
    }

    Column (
        // Komponent kolumny, reprezentujący zawartość ekranu gry
        modifier = Modifier
            .fillMaxSize()
            .background(BlueDark)
            .padding(20.dp)
    ){
        Text(
            // Wyświetlanie liczby pozostałych prób
            text = buildAnnotatedString {
                append("No of guess left: ")
                withStyle(style = SpanStyle(color = Color.White)) {
                    append("${state.noOfGuessLeft}")
                }
            } ,
            color = YellowDark,
            fontSize = 18.sp
        )
        Row(
            // Komponent wiersza, reprezentujący historię zgadywanych liczb
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            state.guessedNumbersList.forEach { number ->
                Text(
                    text = "$number",
                    color = YellowDark,
                    fontSize = 42.sp,
                    modifier = Modifier.padding(end = 20.dp)
                )
            }
        }
        Text(
            // Komponent wyświetlający opis wskazówki
            text = state.hintDescription,
            color = Color.Green,
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            lineHeight = 30.sp,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.fillMaxWidth()
        )
        // Komponent odstępu
        Spacer(modifier = Modifier.height(40.dp))
        // Komponent pola tekstowego (dletego wyżej eksperymentalne)
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .focusRequester(focusRequester),
            value = state.userNumber,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 48.sp
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                //backgroundColor = Color.White,
                focusedBorderColor = Color.Yellow
            ),
            //ustawienia klawiatury
            keyboardOptions = KeyboardOptions(
                //ma się wyświetlać tylko klawiatura numeryczna
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { onEnterButtonClicked() }
            )
        )
        // Komponent odstępu
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            // Komponent przycisku "Enter"
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 40.dp),
            onClick = onEnterButtonClicked,
            colors = ButtonDefaults.buttonColors(
                //backgroundColor = YellowDark,
                contentColor = Color.Black
            )
        ) {
        Text(text = "Enter", fontSize = 18.sp)
    }
    }

    }
// Wyświetlanie podglądu komponentu `GuessingGameScreen`
@Preview
@Composable
fun Prev() {
    GuessingGameScreen(viewModel = viewModel())
}