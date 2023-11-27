package com.gobinda.notepad.domain.util

sealed class NoteSortingOrder {
    object Ascending : NoteSortingOrder()
    object Descending : NoteSortingOrder()
}