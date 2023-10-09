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

package id.dayona.pokedex.viewmodel

import android.media.MediaPlayer
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import id.dayona.pokedex.BuildConfig
import id.dayona.pokedex.R
import id.dayona.pokedex.core.CoreError
import id.dayona.pokedex.core.CoreException
import id.dayona.pokedex.core.CoreLoading
import id.dayona.pokedex.core.CoreSuccess
import id.dayona.pokedex.data.items.Items
import id.dayona.pokedex.data.pokemon.PokemonData
import id.dayona.pokedex.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokeDetailViewModel @Inject constructor(repository: Lazy<Repository>) : ViewModel() {
    private var mediaPlayer: MediaPlayer? = null
    private val repoInstance = repository.get()
    private val pokemonDatabase =
        repoInstance.getAppDatabaseDao().getAll().find { it.id == 1 }?.pokemonDataList
    private val itemType = object : TypeToken<List<PokemonData?>>() {}.type
    val pokemonDetail: List<PokemonData?> =
        Gson().fromJson(pokemonDatabase, itemType)

    val pokemonItem = MutableStateFlow<List<Items?>>(listOf())
    val animated = Animatable(0f)
    suspend fun getPokemonItem(id: Int?) {
        pokemonItem.update { listOf() }
        val heldItems = pokemonDetail.find { f -> f?.id == id }?.heldItems
        repeat(heldItems?.size ?: 0) {
            val heldItemsUrl = heldItems?.get(it)?.item?.url?.replace(
                BuildConfig.BASE_URL, ""
            )
            viewModelScope.async(Dispatchers.IO) {
                repoInstance.getPokemonItems("$heldItemsUrl").collectLatest { res ->
                    when (res) {
                        is CoreError -> {}
                        is CoreException -> {}
                        CoreLoading -> {}
                        is CoreSuccess -> {
                            pokemonItem.update { p -> p.plus(res.data) }
                        }
                    }
                }
            }.join()
        }
    }

    suspend fun startAnimating() {
        animated.animateTo(
            1f,
            animationSpec = tween(
                durationMillis = 2000,
                delayMillis = 100
            )
        )
    }

    suspend fun resetAnimating() {
        animated.animateTo(
            0f,
            animationSpec = tween(
                durationMillis = 0,
                delayMillis = 0
            )
        )
    }

    fun pokemonCries(id: Int?) = viewModelScope.launch(Dispatchers.Default) {
        val field = R.raw::class.java.getDeclaredField("pokemon$id")
        field.isAccessible = true
        val cries = field.get(field)
        mediaPlayer = MediaPlayer.create(repoInstance.getContext(), cries as Int)
        mediaPlayer?.start()
    }
}