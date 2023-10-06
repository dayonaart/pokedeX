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

package id.dayona.pokedex.data.pokemon

import com.google.gson.annotations.SerializedName

data class PokemonData(

    @field:SerializedName("location_area_encounters")
    val locationAreaEncounters: String? = null,

    @field:SerializedName("types")
    val types: List<TypesItem?>? = null,

    @field:SerializedName("base_experience")
    val baseExperience: Int? = null,

    @field:SerializedName("held_items")
    val heldItems: List<HeldItemsItem?>? = null,

    @field:SerializedName("weight")
    val weight: Int? = null,

    @field:SerializedName("is_default")
    val isDefault: Boolean? = null,

    @field:SerializedName("past_types")
    val pastTypes: List<Any?>? = null,

    @field:SerializedName("sprites")
    val sprites: Sprites? = null,

    @field:SerializedName("abilities")
    val abilities: List<AbilitiesItem?>? = null,

    @field:SerializedName("game_indices")
    val gameIndices: List<GameIndicesItem?>? = null,

    @field:SerializedName("species")
    val species: Species? = null,

    @field:SerializedName("stats")
    val stats: List<StatsItem?>? = null,

    @field:SerializedName("moves")
    val moves: List<MovesItem?>? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("forms")
    val forms: List<FormsItem?>? = null,

    @field:SerializedName("height")
    val height: Int? = null,

    @field:SerializedName("order")
    val order: Int? = null
)