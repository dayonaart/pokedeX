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

package id.dayona.pokedex.data.items

import com.google.gson.annotations.SerializedName

data class Items(

    @field:SerializedName("cost")
    val cost: Int? = null,

    @field:SerializedName("fling_power")
    val flingPower: Int? = null,

    @field:SerializedName("sprites")
    val sprites: Sprites? = null,

    @field:SerializedName("fling_effect")
    val flingEffect: FlingEffect? = null,

    @field:SerializedName("effect_entries")
    val effectEntries: List<EffectEntriesItem?>? = null,

    @field:SerializedName("game_indices")
    val gameIndices: List<GameIndicesItem?>? = null,

    @field:SerializedName("baby_trigger_for")
    val babyTriggerFor: Any? = null,

    @field:SerializedName("names")
    val names: List<NamesItem?>? = null,

    @field:SerializedName("flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntriesItem?>? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("attributes")
    val attributes: List<AttributesItem?>? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("machines")
    val machines: List<Any?>? = null,

    @field:SerializedName("category")
    val category: Category? = null,

    @field:SerializedName("held_by_pokemon")
    val heldByPokemon: List<HeldByPokemonItem?>? = null
)