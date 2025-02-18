package com.jay.mywatchlist.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun String.toImageUrl() = "https://www.themoviedb.org/t/p/original/$this"

fun View.shortSnackbar(message: String) = Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()