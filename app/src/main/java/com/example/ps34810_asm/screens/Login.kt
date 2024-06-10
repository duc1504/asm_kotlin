package com.example.ps34810_asm.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ps34810_asm.R
import com.example.ps34810_asm.helpers.RetrofitAPI
import com.example.ps34810_asm.httpmodels.LoginRequestModel
import com.example.ps34810_asm.ui.theme.BlackLitle

class Login {
    @Composable
    fun Container(goToScreen: (String) -> Unit, saveUser: (String) -> Unit) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background,

            ) {
            Body(goToScreen = { goToScreen(it) }, saveUser = { saveUser(it) })
        }
    }

    val gelasio = Font(R.font.gelasio_medium);
    val nunitosans = Font(R.font.nunitosans);

    @Composable
    fun Body(goToScreen: (String) -> Unit, saveUser: (String) -> Unit) {
        val context = LocalContext.current;
        var email by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }

        fun handleLogin() {
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return
            }
            val retrofitAPI = RetrofitAPI()
            var body = LoginRequestModel(email = email, password = password)
            retrofitAPI.login(body) { response ->
                if (response?.status == true) {

                    var emailuser = response.email
                    var id = response.id
                    // lưu email vào SharedPreferences
                    val sharePref = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
                    with(sharePref.edit()) {
                        putString("userId", id)
                        apply()
                    }
                    // Xử lý phản hồi thành công
                    Toast.makeText(
                        context,
                        "Đăng nhập thành công",
                        Toast.LENGTH_SHORT
                    ).show()
                    saveUser(emailuser)
                    goToScreen("home")
                } else {
                    // Xử lý lỗi
                    Toast.makeText(
                        context,
                        "Đăng nhập thất bại",
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
                        text = "Hello !",
                        fontFamily = FontFamily(gelasio),
                        color = Color.Gray,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .padding(top = 28.dp)
                    )
                    Text(
                        text = "WELLCOME BACK",
                        fontFamily = FontFamily(gelasio),
                        color = Color.Black,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(bottom = 30.dp)
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
                            Spacer(modifier = Modifier.height(40.dp))
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Forgot Password",
                                    fontSize = 18.sp,
                                    fontFamily = FontFamily(nunitosans)
                                )
                                Button(
                                    onClick = {
//
                                        handleLogin()
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()

                                        .align(Alignment.CenterHorizontally)
                                        .padding(vertical = 30.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = BlackLitle),
                                    shape = RoundedCornerShape(6.dp),

                                    ) {
                                    Text(
                                        text = "Login",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .padding(vertical = 6.dp)
                                    )
                                }

                                Text(text = "SIGN UP",
                                    fontSize = 18.sp,
                                    fontFamily = FontFamily(nunitosans),
                                    modifier = Modifier.clickable {
                                        goToScreen("signup")
                                    })

                            }
                        }

                    }


                }
            }


        }


    }
}