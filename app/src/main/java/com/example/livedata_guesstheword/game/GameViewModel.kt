package com.example.livedata_guesstheword.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private lateinit var wordList: MutableList<String>

    private val _eventGameFinish = MutableLiveData<Boolean>()

    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar"
        )
        wordList.shuffle()
    }

    init {
        Log.d("GameViewModel", "GameViewModel created!")
        _word.value = ""
        _score.value = 0

        resetList()
        nextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GameViewModel", "GameViewModel destroyed!")
    }

    fun onSkip() {
        _score.value = (score.value)?.minus(1)
         nextWord()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextWord()
    }


    private fun nextWord() {
        if (wordList.isEmpty()) {
            onGameFinish()
        } else {
            _word.value = wordList.removeAt(0)
        }
    }

    private fun onGameFinish() {
        _eventGameFinish.value = true
    }

     fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }


}













































