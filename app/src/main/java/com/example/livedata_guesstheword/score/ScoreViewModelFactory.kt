package com.example.livedata_guesstheword.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ScoreViewModelFactory(private val finalScore: Int): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            return ScoreViewModel(finalScore) as T
        }
        throw IllegalAccessException("Unknown ViewModal class")
    }

}