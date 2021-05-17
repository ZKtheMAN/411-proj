package com.csuf.scryfallclient

import java.util.*

data class ImageUris(
    val small: String?,
    val normal: String?,
    val large: String?,
    val png: String?,
    val art_crop: String?,
    val border_crop: String?
)

data class Card(
    val name: String,
    val layout: String,
    val cmc: java.lang.Double, // ignore compiler warning here, it's just salty because Klaxon uses a Java double for these
    val mana_cost: String?,
    val color_identity: ArrayList<String>,
    val type_line: String,
    val oracle_text: String?,
    val image_uris: ImageUris
)

data class CardJustName(
    val name: String
)

data class CardList(
    val total_cards: Int,
    val has_more: Boolean,
    val data: ArrayList<CardJustName>
)