package com.example.ps34810_asm.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ps34810_asm.R
import com.example.ps34810_asm.ui.theme.BlackLitle
import com.example.ps34810_asm.ui.theme.Color3


class Broading {
    
    @Composable
    fun Container(goToScreen: (String) -> Unit){
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background,

            ) {
            Body(goToScreen={goToScreen(it)})
        }
    }
    val gelasio = Font(R.font.gelasio_medium);
    val nunitosans = Font(R.font.nunitosans);
    @Composable
    fun Body(goToScreen: (String) -> Unit) {
        val context = LocalContext.current;
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.background),
                    contentScale = ContentScale.Crop
                ),
            verticalArrangement = Arrangement.Center
        )

        {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    text = "MAKE YOUR",
                    fontFamily = FontFamily(gelasio),
                    color = Color3,
                    fontSize = 24.sp
                )
                Text(
                    text = "HOME BEAUTIFUL",
                    fontFamily = FontFamily(gelasio),
                    color = Color.Black,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 15.dp)
                )
                Text(
                    text = "The best simple place where you discover most wonderful furnitures and make your home beautiful",
                    fontFamily = FontFamily(nunitosans),
                    color = Color3, fontSize = 18.sp, modifier = Modifier.padding(start = 50.dp)
                )
                Spacer(modifier = Modifier.height(60.dp))
                Button(
                    onClick = {
                        goToScreen("login")
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 100.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BlackLitle),
                    shape = RoundedCornerShape(6.dp),

                    ) {
                    Text(text = "Get started", textAlign = TextAlign.Center)
                }
            }
        }

    }


}