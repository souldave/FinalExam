package com.example.davegilbier.finalexam

import com.google.gson.annotations.SerializedName

/**
 * Created by Dave Gilbier on 21/03/2018.
 */
data class Album (
        val name: String,
        val artist: String,
        val image: text

)

data class text (@SerializedName("#text") val text: String)