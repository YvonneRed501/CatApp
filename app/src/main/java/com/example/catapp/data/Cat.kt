package com.example.catapp.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Cat(
    @StringRes val nameRes: Int,
    @StringRes val descriptionRes: Int,
    @DrawableRes val imageRes: Int
)
