package com.example.cardgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.cardgame.databinding.FragmentEndGameBinding

class EndGameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding: FragmentEndGameBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_end_game, container, false)
        binding.fragmentEndGamePlayAgainButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_endGameFragment_to_welcomeFragment)
        )
        return binding.root
    }
}
