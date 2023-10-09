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

package id.dayona.pokedex.ui.screens.childscreens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import id.dayona.pokedex.data.items.Items
import id.dayona.pokedex.data.pokemon.PokemonData
import id.dayona.pokedex.ui.uitools.UiTools
import id.dayona.pokedex.viewmodel.PokeDetailViewModel
import kotlinx.coroutines.launch
import java.util.Locale

interface DetailPokemonScreen : UiTools {
    val pokeDetailViewModel: PokeDetailViewModel

    @Composable
    fun DetailPokemon(id: Int?, nav: NavController) {
        val pokemonData = pokeDetailViewModel.pokemonDetail.find { it?.id == id }
        val animatedProgress = pokeDetailViewModel.animated
        val pokemonListItem by pokeDetailViewModel.pokemonItem.collectAsState()
        val coroutineScope = rememberCoroutineScope()
        BackHandler {
            coroutineScope.launch {
                pokeDetailViewModel.resetAnimating()
                nav.popBackStack()
            }
        }
        LaunchedEffect(true) {
            if (animatedProgress.value < 1) {
                pokeDetailViewModel.getPokemonItem(id = id)
                pokeDetailViewModel.pokemonCries(id).cancel()
                pokeDetailViewModel.pokemonCries(id).join()
                pokeDetailViewModel.startAnimating()
            }
        }
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        scaleY = animatedProgress.value
                        scaleX = animatedProgress.value
                    }
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.inversePrimary
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        AsyncImage(
                            model = pokemonData?.sprites?.other?.officialArtwork?.frontDefault,
                            contentDescription = "pokeImg",
                            modifier = Modifier.fillMaxSize()
                        )
                        Text(
                            text = "${pokemonData?.name}".uppercase(Locale.ROOT),
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            ItemsHeld(pokemonListItem = pokemonListItem)
            PokemonType(poke = pokemonData)
            EvolvePokemon(poke = pokemonData)
        }
    }


    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun ItemsHeld(pokemonListItem: List<Items?>?) {
        Column(
            modifier = Modifier.padding(top = 10.dp)
        ) {
            Text(text = "Held Items", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            FlowColumn(
                maxItemsInEachColumn = 3,
            ) {
                pokemonListItem?.forEach {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = it?.sprites?.jsonMemberDefault,
                            contentDescription = "",
                            modifier = Modifier.size(50.dp),
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = "${it?.name}".replace("-", " "), fontSize = 10.sp)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun PokemonType(poke: PokemonData?) {
        Column {
            Text(text = "Types", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            FlowColumn(maxItemsInEachColumn = 2) {
                poke?.types?.map {
                    Text(text = "${it?.type?.name}")
                }
            }
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun PokemonMoves(poke: PokemonData?) {
        FlowRow(maxItemsInEachRow = 3) {
            poke?.moves?.map {
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .border(
                            width = 1.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(30)
                        )
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = "${it?.move?.name}",
                        fontSize = 10.sp,
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun EvolvePokemon(poke: PokemonData?) {
        FlowColumn {
            AsyncImage(model = poke?.sprites?.other?.home?.frontShiny, contentDescription = "")
            AsyncImage(
                model = poke?.sprites?.other?.dreamWorld?.frontDefault,
                contentDescription = ""
            )
            AsyncImage(
                model = poke?.sprites?.other?.officialArtwork?.frontShiny,
                contentDescription = ""
            )
        }
    }
}