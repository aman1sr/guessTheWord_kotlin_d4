package com.example.livedata_guesstheword.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
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



    private val _currentTime = MutableLiveData<Long>()      //  Mutable variable, to store countdown of the timer
    val currentTime: LiveData<Long>
    get() = _currentTime

    private val timer: CountDownTimer       // initialzed in init block

    companion object{
        private const val DONE = 0L // game over time
        private const val ONE_SECOND = 1000L    // countdown time interval
        private const val COUNTDOWN_TIME = 60000L  // total time for the game
    }
/*
* we use Transformations.map() to define currentTimeString.
* Pass in the currentTime and a lambda function to format the time
* */
    val currentTimeString = Transformations.map(currentTime){ time ->
        DateUtils.formatElapsedTime(time)

    }


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

        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            /*
            *  onTick() is a callback method,  is called on every tick.
            * */
            override fun onTick(p0: Long) {
                _currentTime.value = p0/ ONE_SECOND
            }

            override fun onFinish() {
                _currentTime.value = DONE
                onGameFinish()              // finish the Game once, countdown is finished
            }
        }
        timer.start()

    }


    override fun onCleared() {
        super.onCleared()
        timer.cancel()      // cancel the timer to avoid memory leaks
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
//            onGameFinish()
            resetList()             // so Now the game Will be finished based on timer, not because the list gets Empty
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













































