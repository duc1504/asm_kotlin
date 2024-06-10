package com.example.ps34810_asm.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ps34810_asm.R

data class TabBarItem(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
)
class Home {
    @Composable
    fun Container(saveUser:(String) -> Unit,goToScreen: (String) -> Unit){

        Surface(
            modifier = Modifier
                .fillMaxSize() ,
            color = MaterialTheme.colorScheme.background,

            ) {
            MainTabs(saveUserInfo = {saveUser(it)}, goToScreen = {goToScreen(it)})
        }

    }
    @Composable
    fun TabView(tabBarItems: List<TabBarItem>, navController: NavController) {
        var selectedTabIndex by rememberSaveable {
            mutableIntStateOf(0)
        }
        NavigationBar {

            tabBarItems.forEachIndexed { index, tabBarItem ->
                NavigationBarItem(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        navController.navigate(tabBarItem.title)
                    },
                    icon = {
                        TabBarIconView(
                            isSelected = selectedTabIndex == index,
                            selectedIcon = tabBarItem.selectedIcon,
                            unselectedIcon = tabBarItem.unselectedIcon,
                            title = tabBarItem.title,
                        )
                    },
                    label = { Text(tabBarItem.title) })
            }
        }
    }
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun MainTabs( saveUserInfo: (String) -> Unit,goToScreen: (String) -> Unit) {
        val HomeSc = HomeSc()
        // setting up the individual tabs
        val homeTab = TabBarItem(
            title = "Home",
            selectedIcon = R.drawable.home_selected,
            unselectedIcon = R.drawable.home
        )
        val alertsTab = TabBarItem(
            title = "Alerts",
            selectedIcon = R.drawable.bell_selected,
            unselectedIcon = R.drawable.bell
        )
        val settingsTab = TabBarItem(
            title = "Settings",
            selectedIcon = R.drawable.bell_selected,
            unselectedIcon = R.drawable.bell
        )
        val profileTab = TabBarItem(
            title = "Profle",
            selectedIcon = R.drawable.profile_selected,
            unselectedIcon = R.drawable.profile
        )
        // creating a list of all the tabs
        val tabBarItems = listOf(homeTab, alertsTab, settingsTab, profileTab)
        // creating our navController
        val navController = rememberNavController()
        Scaffold(bottomBar = { TabView(tabBarItems, navController) }) {
            NavHost(navController = navController, startDestination = homeTab.title) {
                composable(homeTab.title) {
                    HomeSc.Container(gotoSceen = {goToScreen(it)})

                }
                composable(alertsTab.title) {
                    Text(alertsTab.title)
                }
                composable(settingsTab.title) {
                    Text(settingsTab.title)
                }
                composable(profileTab.title) {
                    Button(onClick = {
                        saveUserInfo("")
                    }) {
                        Text(text = "Đăng xuất")
                    }
                }
            }
        }
    }

    @Composable
    fun TabBarIconView(
        isSelected: Boolean,
        selectedIcon: Int,
        unselectedIcon: Int,
        title: String,
    ) {
        Image(
            painter = painterResource(id = if (isSelected) selectedIcon else unselectedIcon),
            modifier = Modifier
                .width(24.dp)
                .height(24.dp),
            contentDescription = title
        )
    }
}