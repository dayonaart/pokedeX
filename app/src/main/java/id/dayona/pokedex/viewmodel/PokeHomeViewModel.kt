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

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import id.dayona.pokedex.BuildConfig
import id.dayona.pokedex.core.CoreError
import id.dayona.pokedex.core.CoreException
import id.dayona.pokedex.core.CoreLoading
import id.dayona.pokedex.core.CoreSuccess
import id.dayona.pokedex.dao.AppDatabaseEntity
import id.dayona.pokedex.data.pokemon.PokemonData
import id.dayona.pokedex.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds


@HiltViewModel
class PokeHomeViewModel @Inject constructor(repository: Lazy<Repository>) :
    ViewModel() {
    private val repoInstance = repository.get()
    var pokemonListDetail = MutableStateFlow<List<PokemonData?>>(listOf())
    var initSuccessPokemon = MutableStateFlow(false)
    var pokemonCounter = MutableStateFlow(0)
    private val limitPokemonCount = 50

    init {
        val readPokemon = repoInstance.getAppDatabaseDao().getAll().find { it.id == 1 }
        if (readPokemon != null) {
            val itemType = object : TypeToken<List<PokemonData?>>() {}.type
            pokemonListDetail.update {
                Gson().fromJson(readPokemon.pokemonDataList, itemType)
            }
            viewModelScope.launch(Dispatchers.Default) {
                delay(2.seconds)
                initSuccessPokemon.update { true }
            }
        } else {
            viewModelScope.launch(Dispatchers.Default) {
                initPokemon().join()
                initSuccessPokemon.update { true }
                updateDatabase().join()
            }
        }
    }

    private fun updateDatabase() = viewModelScope.launch(Dispatchers.IO) {
        pokemonListDetail.collectLatest {
            repoInstance.getAppDatabaseDao()
                .insert(
                    AppDatabaseEntity(
                        id = 1,
                        pokemonDataList = Gson().toJson(it),
                    )
                )
        }
    }

    private fun initPokemon() =
        viewModelScope.launch(Dispatchers.IO) {
            repoInstance.getPokemonList(limitPokemonCount).collectLatest {
                when (it) {
                    is CoreError -> Toast.makeText(
                        repoInstance.getContext(),
                        "${it.code} :: ${it.message}",
                        Toast.LENGTH_LONG
                    ).show()

                    is CoreException -> Toast.makeText(
                        repoInstance.getContext(),
                        it.e,
                        Toast.LENGTH_LONG
                    ).show()

                    CoreLoading -> {

                    }

                    is CoreSuccess -> {
                        val job = launch {
                            repeat(it.data?.results?.size ?: 0) { i ->
                                getDetailPokemon(
                                    it.data?.results?.get(i)?.url!!,
                                    onError = { err ->
                                        if (err) this.cancel()
                                    })
                                pokemonCounter.update { ((i + 1) * 100) / limitPokemonCount }
                            }
                        }
                        job.join()
                    }
                }
            }
        }

    private fun getDetailPokemon(url: String, onError: (Boolean) -> Unit) =
        viewModelScope.launch(Dispatchers.Default) {
            repoInstance.getDetailPokemon(url.replace(BuildConfig.BASE_URL, "")).collectLatest {
                when (it) {
                    is CoreError -> {
                        launch(Dispatchers.Main) {
                            Toast.makeText(
                                repoInstance.getContext(),
                                "${it.code} :: ${it.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        onError(true)
                        this.cancel()
                    }

                    is CoreException -> {
                        launch(Dispatchers.Main) {
                            Toast.makeText(
                                repoInstance.getContext(),
                                it.e,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        onError(true)
                        this.cancel()
                    }

                    CoreLoading -> {
                    }

                    is CoreSuccess -> {
                        pokemonListDetail.update { p ->
                            p.plus(it.data)
                        }

                    }
                }
            }
        }


    suspend fun testAsync(context: Context) {
        val job = viewModelScope.launch {
            repeat(1000) { i ->
                Toast.makeText(context, "job: I'm sleeping $i ...", Toast.LENGTH_SHORT).show()
                delay(2000L)
            }
        }
//        delay(1300L) // delay a bit
//        Toast.makeText(context, "main: I'm tired of waiting!", Toast.LENGTH_SHORT).show()
//        job.cancel() // cancels the job
//        job.join() // waits for job's completion
        Toast.makeText(context, "main: Now I can quit.", Toast.LENGTH_SHORT).show()
    }
}