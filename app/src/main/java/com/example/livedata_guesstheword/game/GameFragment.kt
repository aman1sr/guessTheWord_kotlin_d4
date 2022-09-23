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
import com.example.livedata_guesstheword.R
import com.example.livedata_guesstheword.databinding.FragmentGameBinding
import com.example.livedata_guesstheword.databinding.GameFragmentBinding


class GameFragment : Fragment() {

private lateinit var binding: GameFragmentBinding
private lateinit var viewModel: GameViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)

        viewModel  = ViewModelProvider(this).get(GameViewModel::class.java)

        viewModel.word.observe(viewLifecycleOwner, Observer { newWord ->
            binding.wordText.text = newWord.toString()
        })

        viewModel.score.observe(viewLifecycleOwner, Observer {
            binding.scoreText.text = it.toString()
        })

        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer {
            if (it) {
                gameFinished()
            }
        })

        binding.correctButton.setOnClickListener { onCorrect() }
        binding.skipButton.setOnClickListener { onSkip() }
        binding.endGameButton.setOnClickListener { onEndGame() }



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
        viewModel.onGameFinishComplete()
    }

}