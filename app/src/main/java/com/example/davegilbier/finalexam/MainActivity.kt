package com.example.davegilbier.finalexam

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private var albumSearch: String? = null
    private var counter = 0

    private lateinit var mAdapter: Adapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mAdapter = Adapter(this, ArrayList())

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        button.setOnClickListener({
            albumSearch = etext.text.toString()
            progressBar.visibility = View.VISIBLE
            textView4.text = "Searching for $albumSearch..."


            val url = "http://ws.audioscrobbler.com/2.0/?method=album.search&album=$albumSearch&api_key=7ae483dae4b8aba5e56f65c156a51f8c&format=json"

            val request = Request.Builder().url(url).build()
            val client = OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call?, e: IOException?) {
                    Log.e("Album API", "Failed to get album", e)
                    progressBar.visibility = View.GONE
                    textView3.text = getString(R.string.album_not_found)
                }

                override fun onResponse(call: Call?, response: Response?) {
                    if (response != null && response.isSuccessful) {
                        val json = response.body()?.string()
                        displayAlbum(json)

                    }
                }
            })
        }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    private fun displayAlbum(json: String?) {

        runOnUiThread {
            var lengthalbum = JSONObject(json).getJSONObject("results").getJSONObject("albummatches").getJSONArray("album").length()
            Log.e("Final Exam", "$lengthalbum")
            val albumlist = ArrayList<Album>()
            for (i in 1..lengthalbum) {
                Log.e("Final Exam", "$counter")
                textView3.text = getString(R.string.results_below)
                var albumtext = JSONObject(json).getJSONObject("results").getJSONObject("albummatches").getJSONArray("album").getJSONObject(counter).getString("name")
                var artisttext = JSONObject(json).getJSONObject("results").getJSONObject("albummatches").getJSONArray("album").getJSONObject(counter).getString("artist")
                var imgName = JSONObject(json).getJSONObject("results").getJSONObject("albummatches").getJSONArray("album").getJSONObject(counter).getJSONArray("image").getJSONObject(2).getString("#text")
                recyclerView.adapter = Adapter(this@MainActivity, albumlist)
                albumlist.add(Album(albumtext, artisttext, imgName))
                counter++
                Log.e("Final Exam", "$counter")
            }

            recyclerView.layoutManager = LinearLayoutManager(this)


            // if (albumSearch == album.name || albumSearch == album.artist){
            //   album_txt.text = album.name
            // artist_txt.text= album.artist
            //Glide.with(this@MainActivity).load(album.image.text).into(album_img)
            //progressBar.visibility = View.GONE
            //textView4.text= getString(R.string.ALBUM_FOUND)
            //val adapter = Adapter(abilitylist)
            //recyclerView.adapter = adapter

            //} else {
            //    textView4.text = getString(R.string.album_nfound)
            //}

        }
    }

}

