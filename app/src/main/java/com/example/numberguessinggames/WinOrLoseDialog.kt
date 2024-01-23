package com.example.numberguessinggames

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.numberguessinggames.ui.theme.YellowDark
// Komponent Compose reprezentujący dialog wygranej lub przegranej w grze
@Composable
fun WinOrLoseDialog(
    text: String,
    buttonText: String,
    mysteryNumber: Int,
    resetGame: () -> Unit
) { // Deklaracja Dialogu z możliwością wywołania funkcji resetującej grę po zamknięciu dialogu
    Dialog(onDismissRequest = resetGame) {
        Column(
            modifier = Modifier
                .size(300.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(YellowDark),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text( // Tekst informacyjny o rezultacie gry
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp)) // Odstęp pionowy między tekstami
            Text( // Tekst z informacją o  tajemniczej liczbie
                text = "The Mystery Number is $mysteryNumber",
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Cursive
            )

            Button( // Przycisk służący do resetowania gry
                onClick = resetGame,
                colors = ButtonDefaults.buttonColors(
                    //backgroundColor = BlueDark,
                    contentColor = Color.White
                )
            ) {
                Text(text = buttonText, fontSize = 18.sp)
            }
        }
    }
}
// Podgląd komponentu WinOrLoseDialog z rezultatem wygranej
@Preview
@Composable
fun WinDialogPrev() {
    WinOrLoseDialog(
        text = "Congratulations\nYou won",
        buttonText = "Play Again",
        mysteryNumber = 32,
    ) {}
}
// Podgląd komponentu WinOrLoseDialog z rezultatem przegranej
@Preview
@Composable
fun LoseDialogPrev() {
    WinOrLoseDialog(
        text = "Better Luck next time",
        buttonText = "Try Again",
        mysteryNumber = 32,
    ) {}
}