package com.gobinda.notepad.main.domain.util

sealed class NoteSortingOrder {
    object Ascending : NoteSortingOrder()
    object Descending : NoteSortingOrder()
}