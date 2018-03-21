package com.example.davegilbier.finalexam

import com.google.gson.annotations.SerializedName

/**
 * Created by Dave Gilbier on 21/03/2018.
 */
data class Album (
        var name: String,
        var artist: String,
        var image: String
)

//data class text (@SerializedName("#text") val text: String)