package com.example.numberguessinggames

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.numberguessinggames.ui.theme.NumberGuessingGamesTheme


// Główna klasa reprezentująca główną aktywność aplikacji
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { // Ustawienie zawartości aktywności
            NumberGuessingGamesTheme {
                val viewModel = viewModel<MainViewModel>() // Inicjalizacja ViewModel za pomocą viewModel
                // Wywołanie GuessingGameScreen z użyciem ViewModel przez androidx.lifecycle.viewmodel.compose.viewModel()
                GuessingGameScreen(viewModel = androidx.lifecycle.viewmodel.compose.viewModel()) // jak to idzie jako lazy to wtedy wysypuje się screen
            }}}}
