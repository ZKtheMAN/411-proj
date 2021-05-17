package com.csuf.scryfallclient

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executor

class CardView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_view)

        val query = intent.getStringExtra(SEARCH_QUERY)

        if (query != null) {
            val r = Fuel.get("https://api.scryfall.com/cards/named?exact=$query")
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            println("Couldn't search! ${result.getException()}")
                        }
                        is Result.Success -> {
                            val json = result.value
                            val parsed = Klaxon().parse<Card>(json)
                            if (parsed != null) {
                                setCard(parsed)
                            }
                        }
                    }
                }

            r.join()
        }

    }

    private fun setCard(card: Card) {
        findViewById<TextView>(R.id.card_name_text).text = card.name
        findViewById<TextView>(R.id.card_mana_text).text = card.mana_cost
        findViewById<TextView>(R.id.card_type_text).text = card.type_line
        findViewById<TextView>(R.id.card_oracle_text).text = card.oracle_text

        val imageView = findViewById<ImageView>(R.id.imageView)
        var bitmap : Bitmap? = null
        try {
            val input = URL(card.image_uris.png).openStream()
            bitmap = BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (bitmap != null) imageView.setImageBitmap(bitmap)
    }
}