package com.example.kinopedia.ui

interface BaseMapper<A, B> {
    fun map(item: A): B
}