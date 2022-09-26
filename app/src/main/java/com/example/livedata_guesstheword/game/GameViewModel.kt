package com.example.livedata_guesstheword.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    /* MutableLiveData is commonly used since it provides the postValue(), setValue() methods publicly,
    * that LiveData class doesn’t provide
    * It can be changed , & Inside the ViewModel, the data should be editable, so we uses MutableLiveData.
    * */

    /*                   LIVE-DATA
* Data in a LiveData object can be read, but not changed. From outside the ViewModel,
* data should be readable, but not editable, so the data should be exposed as LiveData.
*/

    //    To encapsulate our app's data, you use both MutableLiveData and LiveData objects

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
        //  MutableList class is used to create mutable lists in which the elements can be added or removed.
            //  https://www.geeksforgeeks.org/kotlin-mutablelistof/

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
        /*
        * shuffle does shuffle into original list, shuffled do and return new list.
            And same behavior is for sort & sorted, sortBy & sortedBy, reverse & asReversed:
        * */
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
        _score.value = (score.value)?.minus(1)      //
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

    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }

    // When the End button in Game Fragment is clicked , so Now The liveData in GameFragment can execute
     fun onGameFinish() {
        _eventGameFinish.value = true
    }




}













































