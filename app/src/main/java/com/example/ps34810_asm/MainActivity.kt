package com.example.ps34810_asm

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ps34810_asm.screens.Broading
import com.example.ps34810_asm.screens.Cart
import com.example.ps34810_asm.screens.Detail
import com.example.ps34810_asm.screens.Home
import com.example.ps34810_asm.screens.Login
import com.example.ps34810_asm.screens.SignUp
import com.example.ps34810_asm.ui.theme.Ps34810_AsmTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ps34810_AsmTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize().statusBarsPadding(),
                    color = MaterialTheme.colorScheme.background,

                    ) {
                    Body()
                }

            }
        }
    }

    @Composable
    fun Body() {
        // khai báo các màn hình
        val Broading = Broading()
        val Login = Login()
        val SignUp = SignUp()
        val Home = Home()
        val Detail = Detail()
        val Cart = Cart()

        // hàm đọc thông tin user từ shared preference
        fun readUserInfo(): String {
            val sharePref = getPreferences(Context.MODE_PRIVATE)
            return (sharePref.getString("email", null) ?: "")
        }

        // hàm kiểm tra lần đầu vào app
        fun isFirstLaunch(): Boolean {
            val sharePref = getPreferences(Context.MODE_PRIVATE)
            return sharePref.getBoolean("isFirstLaunch", true)
        }

        // hàm cập nhật trạng thái lần đầu vào app
        fun setFirstLaunchCompleted() {
            val sharePref = getPreferences(Context.MODE_PRIVATE)
            with(sharePref.edit()) {
                putBoolean("isFirstLaunch", false)
                apply()
            }
        }

        // thông tin người dùng
        var userInfo by remember { mutableStateOf(readUserInfo()) }
        var firstLaunch by remember { mutableStateOf(isFirstLaunch()) }

        // hàm lưu thông tin user vào shared preference
        fun saveUserInfo(email: String) {
            val sharePref = getPreferences(Context.MODE_PRIVATE)
            with(sharePref.edit()) {
                putString("email", email)

                apply()
            }
            // cập nhật thông tin người dùng
            userInfo = email
        }

        // khai báo biến để chuyển giữa cac màn hình
        val navController = rememberNavController()

        fun goToScreen(screen: String) {
            navController.navigate(screen)
        }

        NavHost(
            navController = navController,
            startDestination = if (firstLaunch) "broading" else if (userInfo.isNullOrEmpty()) "login" else "home"
        ) {
            composable("broading") {
                Broading.Container(goToScreen = {
                    setFirstLaunchCompleted()
                    firstLaunch = false
                    goToScreen("login")
                })
            }
            composable("login") {
                Login.Container(
                    goToScreen = { goToScreen(it) },
                    saveUser = { saveUserInfo(it) }
                )
            }
            composable("signup") { SignUp.Container(goToScreen = { goToScreen(it) }) }
//            composable("detail") { Detail.Container() }
            composable("home") {
                Home.Container(saveUser = { saveUserInfo(it) }, goToScreen = { goToScreen(it) })
            }
            composable("detail/{value}") { backStackEntry ->
                val value = backStackEntry.arguments?.getString("value") ?: ""
                Detail.Container(value, goToScreen = { goToScreen(it) })
            }
            composable("cart") { Cart.Container() }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        Ps34810_AsmTheme {
            Body()
        }
    }
}
