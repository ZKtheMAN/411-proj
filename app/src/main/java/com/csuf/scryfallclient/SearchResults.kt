package com.csuf.scryfallclient

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result

class SearchResults : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        val query = intent.getStringExtra(SEARCH_QUERY)
        if (query != null) {
            Fuel.get("https://api.scryfall.com/cards/search?q=$query")
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            println("Couldn't get search results!")
                            println(result.error)
                            noCardsFound()
                        }
                        is Result.Success -> {
                            cardsFound(result.value)
                        }
                    }
                }
        }
    }

    fun cardsFound(responseString: String) {
        val cardList = Klaxon().parse<CardList>(responseString)

        if (cardList != null) {
            println("Got card list length ${cardList.total_cards}")
            if (cardList.total_cards <= 0) noCardsFound()
            else {
            }
        }
    }

    fun noCardsFound() {
        println("Showing that no cards were found...")
        val searchResultsLayout = findViewById<LinearLayout>(R.id.search_results_layout)
        val textView = TextView(this)
        textView.text = "Your search returned no results."
        textView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        textView.textSize = 20.0f
        searchResultsLayout.addView(textView)
        println(searchResultsLayout.childCount)
    }
}