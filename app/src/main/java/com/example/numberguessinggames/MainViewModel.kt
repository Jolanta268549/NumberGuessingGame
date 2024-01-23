package com.example.numberguessinggames

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
// Klasa ViewModel reprezentująca logikę głównego widoku gry
class MainViewModel: ViewModel() {
    // Prywatne pole reprezentujące stan gry jako MutableStateFlow
    private val _state = MutableStateFlow(GuessingGameState())
    // Publiczne pole stanu gry jako StateFlow
    val state = _state.asStateFlow()
    // Funkcja aktualizująca pole tekstowe w grze na podstawie wprowadzonej liczby przez użytkownika
    fun updateTextField(userNo: String) {
        _state.update { it.copy(userNumber = userNo) }
    }
    // Funkcja resetująca grę do stanu początkowego
    fun resetGame() {
        _state.value = GuessingGameState()
    }
    // Funkcja obsługująca wprowadzoną liczbę przez użytkownika
    fun onUserInput(
        userNumber: String,
        context: Context
    ) { // Konwersja wprowadzonej liczby do typu Int
        val userNumberInInt = try {
            userNumber.toInt()
        } catch (e: Exception) {
            0
        }
        // Sprawdzanie, czy wprowadzona liczba mieści się w zakresie od 1 do 99
        if (userNumberInInt !in 1..99) {
            Toast.makeText(
                context,
                "Please enter a number between 0 and 100.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        // Pobranie aktualnego stanu gry
        val currentState = _state.value
        // Zaktualizowanie liczby pozostałych prób
        val newGuessLeft = currentState.noOfGuessLeft - 1
        // Określenie nowego etapu gry na podstawie wprowadzonej liczby
        val newGameStage = when {
            userNumberInInt == currentState.mysteryNumber -> GameStage.WON
            newGuessLeft == 0 -> GameStage.LOSE
            else -> GameStage.PLAYING
        }
        // Określenie nowego opisu wskazówki na podstawie wprowadzonej liczby
        val newHintDescription = when {
            userNumberInInt > currentState.mysteryNumber -> {
                "Hint\nYou are guessing bigger number than the mystery number."
            }
            userNumberInInt < currentState.mysteryNumber -> {
                "Hint\nYou are guessing smaller number than the mystery number."
            }
            else -> ""
        }
        // Zaktualizowanie stanu gry na podstawie nowych danych
        val newGuessNoList = currentState.guessedNumbersList.plus(userNumberInInt)

        _state.update {
            it.copy(
                userNumber = "",
                noOfGuessLeft = newGuessLeft,
                guessedNumbersList = newGuessNoList,
                hintDescription = newHintDescription,
                gameStage = newGameStage
            )
        }
    }
}