package com.example.livedata_guesstheword.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.livedata_guesstheword.R
import com.example.livedata_guesstheword.databinding.FragmentGameBinding
import java.util.Objects


class GameFragment : Fragment() {

private lateinit var binding: FragmentGameBinding
private lateinit var viewModel: GameViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)

        viewModel  = ViewModelProvider(this).get(GameViewModel::class.java)

//        viewModel.word.observe(viewLifecycleOwner, Observer { newWord ->          //  // No need since Now, we are directly using LiveData Obj in xml.
//            binding.wordText.text = newWord.toString()
//        })

        /* it variable is an implicit parameter in lambda.
         if a function literal has only one parameter, its declaration may be omitted ,
          and its name will be it:
        */
        viewModel.score.observe(viewLifecycleOwner, Observer {
            binding.scoreText.text = it.toString()
        })

//        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer {
//            if (it) {
//                gameFinished()
//            }
//        })

                /* NO Need Now, since using DATA Binding */
//        binding.correctButton.setOnClickListener { onCorrect() }
//        binding.skipButton.setOnClickListener { onSkip() }
//        binding.endGameButton.setOnClickListener { onEndGame() }


        binding.gameViewModel = viewModel       //  passing the GameViewModel into the data binding , so now UI can directly control the Actions
/*
* Data binding creates a listener and sets the listener on the view.
*   When the listened-for event happens, the listener evaluates the lambda expression
* */

        binding.lifecycleOwner = viewLifecycleOwner     // for binding word & score directly in  LiveData Object in xml directly


        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it)
                gameFinished()
        })

        return binding.root
    }


    private fun onSkip() {
        viewModel.onSkip()
    }

    private fun onCorrect() {
        viewModel.onCorrect()
    }

    private fun onEndGame() {
        gameFinished()
    }

    private fun gameFinished() {
        Toast.makeText(activity, "Game Finished", Toast.LENGTH_LONG).show()
      val action = GameFragmentDirections.actionGameFragmentToScoreFragment()
        action.score = viewModel.score.value?:0

        findNavController().navigate(action)

//        findNavController().navigate(GameFragmentDirections.actionGameFragmentToScoreFragment())
        viewModel.onGameFinishComplete()
    }

}