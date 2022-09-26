package com.example.livedata_guesstheword.score

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.livedata_guesstheword.R
import com.example.livedata_guesstheword.databinding.FragmentScoreBinding


class ScoreFragment : Fragment() {

    private lateinit var viewModel: ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentScoreBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_score,
            container,
            false
        )

        viewModelFactory = ScoreViewModelFactory(ScoreFragmentArgs.fromBundle(requireArguments()).score)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ScoreViewModel::class.java)

        binding.scoreViewModel = viewModel

        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.eventPlayAgain.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(ScoreFragmentDirections.actionScoreFragmentToGameFragment())
                viewModel.onPlayAgainComplete()
            }
        })

        // Inflate the layout for this fragment
        return binding.root
    }


}