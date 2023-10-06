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

package id.dayona.pokedex.repository

import android.content.Context
import id.dayona.pokedex.core.CoreHelper
import id.dayona.pokedex.dao.AppDatabaseDao
import id.dayona.pokedex.data.PokemonList
import id.dayona.pokedex.data.items.Items
import id.dayona.pokedex.data.pokemon.PokemonData
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getContext(): Context
    fun getAppDatabaseDao(): AppDatabaseDao
    suspend fun getPokemonList(limit: Int): Flow<CoreHelper<PokemonList?>>
    suspend fun getDetailPokemon(url: String): Flow<CoreHelper<PokemonData?>>
    suspend fun getPokemonItems(url: String): Flow<CoreHelper<Items?>>
}