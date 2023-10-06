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

package id.dayona.pokedex.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.dayona.pokedex.data.PokemonList
import id.dayona.pokedex.data.ResultsItem
import java.util.Date

@Entity(tableName = "app_database")
data class AppDatabaseEntity(
    @PrimaryKey
    var id: Int = 0,
    var pokemonDataList: String?
) {
    @ColumnInfo(name = "create_at", defaultValue = "CURRENT_TIMESTAMP")
    var createdAt: Date? = EntityConverter.toDate(System.currentTimeMillis())
}

object EntityConverter {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun pokemonListString(typeItems: PokemonList): String? {
        return Gson().toJson(typeItems)
    }

    @TypeConverter
    fun pokemonListJson(json: String): PokemonList? {
        val listType = object : TypeToken<PokemonList?>() {}.type
        return Gson().fromJson(json, listType)
    }

    @TypeConverter
    fun resultsItemString(typeItems: List<ResultsItem?>?): String? {
        return Gson().toJson(typeItems)
    }

    @TypeConverter
    fun resultsItemJson(json: String): List<ResultsItem?>? {
        val listType = object : TypeToken<PokemonList?>() {}.type
        return Gson().fromJson(json, listType)
    }
}