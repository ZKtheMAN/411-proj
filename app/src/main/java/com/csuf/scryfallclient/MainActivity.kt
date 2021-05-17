package com.csuf.scryfallclient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import kotlinx.coroutines.delay

const val SEARCH_QUERY = "com.csuf.scryfallclient.SEARCH_QUERY"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val searchButton: Button = findViewById(R.id.searchButton)
    }

    fun searchButtonCallback(view: View) {
        val searchBox: TextView = findViewById(R.id.searchBox)
        val query = searchBox.text.toString()
        val request = Fuel.get("https://api.scryfall.com/cards/named?exact=$query")
            .responseString { request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        Thread.sleep(100) // so as not to flood Scryfall with requests
                        startActivity(Intent(this, SearchResults::class.java).apply {
                            putExtra(SEARCH_QUERY, query)
                        })
                    }
                    is Result.Success -> {
                        Thread.sleep(100)
                        startActivity(Intent(this, CardView::class.java).apply {
                            putExtra(SEARCH_QUERY, query)
                        })
                    }
                }
            }
        request.join()
    }
}