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

package id.dayona.pokedex.repositoryImpl

import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import id.dayona.pokedex.core.CoreError
import id.dayona.pokedex.core.CoreException
import id.dayona.pokedex.core.CoreHelper
import id.dayona.pokedex.core.CoreLoading
import id.dayona.pokedex.core.CoreSuccess
import id.dayona.pokedex.core.HTTP_LOGGER
import id.dayona.pokedex.dao.AppDatabaseDao
import id.dayona.pokedex.data.PokemonList
import id.dayona.pokedex.data.items.Items
import id.dayona.pokedex.data.pokemon.PokemonData
import id.dayona.pokedex.network.NetworkInterface
import id.dayona.pokedex.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val app: Application,
    private val network: NetworkInterface,
    private val appDatabaseDao: AppDatabaseDao
) : Repository {
    override fun getContext(): Context = app.applicationContext

    override fun getAppDatabaseDao() = appDatabaseDao

    override suspend fun getPokemonList(limit: Int): Flow<CoreHelper<PokemonList?>> {
        return flow {
            emit(CoreLoading)
            try {
                val res = network.getPokemonList("$limit")
                if (res.isSuccessful) {
                    emit(CoreSuccess(data = res.body()))
                } else {
                    Log.d(HTTP_LOGGER, "getPokemonList: ${res.errorBody()?.string()}")
                    emit(CoreError(code = "${res.code()}", message = res.errorBody()?.string()))
                }
            } catch (e: IOException) {
                Log.d(HTTP_LOGGER, "getPokemonList: ${e.message}")
                emit(CoreException(e = "${e.message}"))
            } catch (e: HttpException) {
                Log.d(HTTP_LOGGER, "getPokemonList: ${e.message}")
                emit(CoreException(e = "${e.message}"))
            } catch (e: Exception) {
                Log.d(HTTP_LOGGER, "getPokemonList: ${e.message}")
                emit(CoreException(e = "${e.message}"))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getDetailPokemon(url: String): Flow<CoreHelper<PokemonData?>> {
        return flow {
            emit(CoreLoading)
            try {
                val res = network.getDetailPokemon(url)
                if (res.isSuccessful) {
                    emit(CoreSuccess(data = res.body()))
                } else {
                    Log.d(HTTP_LOGGER, "getDetailPokemon: ${res.errorBody()?.string()}")
                    emit(CoreError(code = "${res.code()}", message = res.errorBody()?.string()))
                }
            } catch (e: IOException) {
                Log.d(HTTP_LOGGER, "getDetailPokemon: ${e.message}")
                emit(CoreException(e = "${e.message}"))
            } catch (e: HttpException) {
                Log.d(HTTP_LOGGER, "getDetailPokemon: ${e.message}")
                emit(CoreException(e = "${e.message}"))
            } catch (e: Exception) {
                Log.d(HTTP_LOGGER, "getDetailPokemon: ${e.message}")
                emit(CoreException(e = "${e.message}"))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getPokemonItems(url: String): Flow<CoreHelper<Items?>> {
        return flow {
            emit(CoreLoading)
            try {
                val res = network.getPokemonItems(url)
                if (res.isSuccessful) {
                    emit(CoreSuccess(data = res.body()))
                } else {
                    Log.d(HTTP_LOGGER, "getPokemonItems: ${res.errorBody()?.string()}")
                    emit(CoreError(code = "${res.code()}", message = res.errorBody()?.string()))
                }
            } catch (e: IOException) {
                Log.d(HTTP_LOGGER, "getPokemonItems: ${e.message}")
                emit(CoreException(e = "${e.message}"))
            } catch (e: HttpException) {
                Log.d(HTTP_LOGGER, "getPokemonItems: ${e.message}")
                emit(CoreException(e = "${e.message}"))
            } catch (e: Exception) {
                Log.d(HTTP_LOGGER, "getPokemonItems: ${e.message}")
                emit(CoreException(e = "${e.message}"))
            }
        }.flowOn(Dispatchers.IO)
    }
}