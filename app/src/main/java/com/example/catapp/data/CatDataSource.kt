package com.example.catapp.data

import com.example.catapp.R

object CatRepository{
    val cats = listOf (
        Cat(
            nameRes = R.string.teddy,
            descriptionRes = R.string.teddy_description,
            imageRes = R.drawable.teddy
        ),
        Cat(
            nameRes = R.string.poppy,
            descriptionRes = R.string.poppy_description,
            imageRes = R.drawable.poppy
        ),
        Cat(
            nameRes = R.string.tictac,
            descriptionRes = R.string.tictac_description,
            imageRes = R.drawable.tictac
        ),
        Cat(
            nameRes = R.string.vivi,
            descriptionRes = R.string.vivi_description,
            imageRes = R.drawable.vivi
        ),
        Cat(
            nameRes = R.string.babi,
            descriptionRes = R.string.babi,
            imageRes = R.drawable.babi
        ),
        Cat(
            nameRes = R.string.teddy,
            descriptionRes = R.string.teddy_description,
            imageRes = R.drawable.teddy
        )
    )
}