package com.example.kinopedia.network

import android.view.inspector.IntFlagMapping

data class Spouses(
    val personId: Int,
    val name: String,
    val divorced: Boolean,
    val divorcedReason: String,
    val sex: String,
    val children: Int,
    val webUrl: String,
    val relation: String
)
