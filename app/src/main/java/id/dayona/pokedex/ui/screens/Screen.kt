/*
 * Copyright (c) 2023 (  Dayona )
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package id.dayona.pokedex.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import id.dayona.pokedex.R
import id.dayona.pokedex.ui.screens.childscreens.DetailPokemonScreen
import id.dayona.pokedex.ui.screens.tabscreens.GpsScreen
import id.dayona.pokedex.ui.screens.tabscreens.HomeScreen
import id.dayona.pokedex.ui.screens.tabscreens.PdaScreen
import id.dayona.pokedex.ui.uitools.UiTools
import id.dayona.pokedex.viewmodel.PokeDetailViewModel
import id.dayona.pokedex.viewmodel.PokeHomeViewModel
import kotlinx.coroutines.flow.collectLatest

interface Screen : HomeScreen, GpsScreen, PdaScreen, SplashScreen, DetailPokemonScreen, UiTools {
    override val pokeHomeViewModel: PokeHomeViewModel
    override val pokeDetailViewModel: PokeDetailViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen(navController: NavHostController) {
        LaunchedEffect(key1 = true, block = {
            pokeHomeViewModel.initSuccessPokemon.collectLatest {
                if (it) {
                    navController.navigate("main") {
                        popUpTo(navController.graph.id)
                        launchSingleTop = true
                    }
                }
            }
        })
        NavHost(navController = navController, startDestination = "splash") {
            composable("splash") {
                Splash()
            }
            composable("main") {
                val navTab = rememberNavController()
                Scaffold(
                    topBar = { TopAppBar() },
                    bottomBar = { BottomNavigator(navController = navTab) }
                ) {
                    NavigationGraph(navController = navTab, paddingValues = it)
                }
            }
        }
    }


    @Composable
    fun TopAppBar() {
        Box(
            modifier = Modifier
                .padding(top = 10.dp)
                .clip(CircleShape.copy(all = CornerSize(10)))
                .fillMaxWidth()
                .border(shape = RoundedCornerShape(20), width = 1.dp, color = Color.Blue)
                .padding(vertical = 10.dp, horizontal = 20.dp),
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.pokeball),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .rotate(infinityRotationAnimated())
                        .size(30.dp),
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "PokedeX",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                )

            }
        }
    }

    @Composable
    fun NavigationGraph(navController: NavHostController, paddingValues: PaddingValues) {
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = BottomNavItem.PokeHome.screenRoute,
        ) {
            composable(BottomNavItem.PokeHome.screenRoute) {
                PokeHome(navController)
            }
            composable(BottomNavItem.PokeGps.screenRoute) {
                PokeGps()
            }
            composable(BottomNavItem.PokePda.screenRoute) {
                PokePda()
            }
            composable(
                ScreenItem.PokeDetail.screenRoute + "{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) {
                val id = it.arguments?.getString("id")?.toInt()
                DetailPokemon(id = id, navController)
            }
            dialog(ScreenItem.SplashScreen.screenRoute) {
                Splash()
            }
        }
    }
}
