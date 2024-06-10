package com.example.ps34810_asm.screens

import android.widget.Toast



import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ps34810_asm.R
import com.example.ps34810_asm.helpers.RetrofitAPI
import com.example.ps34810_asm.httpmodels.RegisterRequestModel
import com.example.ps34810_asm.ui.theme.BlackLitle


class SignUp {
    @Composable
    fun Container(goToScreen: (String) -> Unit) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background,

            ) {
            Body(goToScreen = { goToScreen(it) })
        }

    }


    val gelasio = Font(R.font.gelasio_medium);
    val nunitosans = Font(R.font.nunitosans);

    @Composable
    fun Body(goToScreen: (String) -> Unit) {
        val context = LocalContext.current
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        var name by remember { mutableStateOf("") }

        fun handleSignUp() {
            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || name.isEmpty()) {
                Toast.makeText(
                   context,
                    "Vui lòng điền đầy đủ thông tin",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            if (password != confirmPassword) {
                Toast.makeText(
                    context,
                    "Mật khẩu không khớp",
                    Toast.LENGTH_SHORT
                    ).show()
                return
            }
            val retrofitAPI = RetrofitAPI()
            var body = RegisterRequestModel(name = name, email= email, password= password)
            retrofitAPI.register(body) { response ->
                if (response != null) {
                    // Xử lý phản hồi thành công
                    Toast.makeText(
                        context,
                        "Thành công",
                        Toast.LENGTH_SHORT
                    ).show()
                    goToScreen("login")
                } else {
                    // Xử lý lỗi
                    Toast.makeText(
                        context,
                        "Thất bại",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }

        Column(
            modifier = Modifier
                .fillMaxSize(),

            )

        {
            Column() {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "logo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .padding(top = 28.dp),
                    )

                    Text(
                        text = "WELLCOME",
                        fontFamily = FontFamily(gelasio),
                        color = Color.Black,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(vertical = 30.dp)
                    )
                }


                Box(
                    modifier = Modifier
                        .padding(end = 22.dp)
                        .fillMaxWidth()

                        .shadow(
                            elevation = 3.dp, shape = RoundedCornerShape(5.dp),
                            ambientColor = Color.Black.copy(alpha = 0.5f),
                            spotColor = Color.Black.copy(alpha = 0.4f)
                        )
                        .padding(20.dp)
                )
                {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 40.dp)

                    )
                    {
                        Column {

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                contentAlignment = Alignment.BottomStart
                            ) {
                                TextField(
                                    value = name,
                                    onValueChange = { name = it },
                                    label = { Text(text = "Name") },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 8.dp),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedIndicatorColor = Color.Black
                                    )
                                )

                            }
                            Spacer(modifier = Modifier.height(30.dp))


                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                contentAlignment = Alignment.BottomStart
                            ) {
                                TextField(
                                    value = email,
                                    onValueChange = { email = it },
                                    label = { Text(text = "Email") },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 8.dp),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedIndicatorColor = Color.Black
                                    )
                                )

                            }
                            Spacer(modifier = Modifier.height(30.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                contentAlignment = Alignment.BottomStart
                            ) {
                                TextField(
                                    value = password,
                                    onValueChange = { password = it },
                                    label = { Text(text = "Password") },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 8.dp),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedIndicatorColor = Color.Black
                                    ),
                                    trailingIcon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.eye),
                                            contentDescription = "eye",
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                )

                            }
                            Spacer(modifier = Modifier.height(30.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                contentAlignment = Alignment.BottomStart
                            ) {
                                TextField(
                                    value = confirmPassword,
                                    onValueChange = {confirmPassword = it },
                                    label = { Text(text = "Confirm Password") },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 8.dp),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedIndicatorColor = Color.Black
                                    ),
                                    trailingIcon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.eye),
                                            contentDescription = "eye",
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                )

                            }

                            Spacer(modifier = Modifier.height(30.dp))
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Button(
                                    onClick = {
                                       handleSignUp()
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.CenterHorizontally)
                                        .padding(vertical = 20.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = BlackLitle),
                                    shape = RoundedCornerShape(6.dp),

                                    ) {
                                    Text(
                                        text = "Sign up",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .padding(vertical = 6.dp)
                                    )
                                }

                            }

                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),

                            horizontalArrangement = Arrangement.Center

                        ) {
                            Text(
                                text = "Already have account? ",
                                fontSize = 14.sp,
                                fontFamily = FontFamily(nunitosans),
                                color = Color.Gray
                            )
                            Text(text = "SIGN IN",
                                fontSize = 14.sp,
                                fontFamily = FontFamily(nunitosans),
                                fontWeight = FontWeight(600),
                                modifier = Modifier.clickable {
                                    goToScreen("login")
                                }
                            )
                        }

                    }


                }
            }


        }


    }
}