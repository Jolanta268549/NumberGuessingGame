package com.example.numberguessinggames
// Klasa danych reprezentująca stan gry w zgadywaniu liczby
data class GuessingGameState(
    val userNumber: String = "", // Aktualny wpis użytkownika
    val noOfGuessLeft: Int = 5, // Ilość pozostałych prób
    val guessedNumbersList: List<Int> = emptyList(), // Lista wcześniej zgadywanych liczb
    val mysteryNumber: Int = (1..99).random(), // Tajemnicza liczba do odgadnięcia
    val hintDescription: String = "Guess\nthe mystery number between\n0 and 100.", // Opis wskazówki
    val gameStage: GameStage = GameStage.PLAYING // Aktualny etap gry
)
// Enumeracja reprezentująca etapy gry
enum class GameStage {
    WON,
    LOSE,
    PLAYING
}
// Gracz wygrał/przegrał/jest w trakcie gry
