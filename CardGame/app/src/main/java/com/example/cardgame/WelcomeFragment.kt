package com.example.cardgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.cardgame.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentWelcomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_welcome, container, false)

        val cardNumbers = resources.getStringArray(R.array.card_numbers)
        val spinnerItemRes = R.layout.simple_spinner_item

        val adapter =
            ArrayAdapter(requireContext(),spinnerItemRes , cardNumbers)
        adapter.setDropDownViewResource(spinnerItemRes)
        binding.fragmentGameCardsSpinner.adapter = adapter

        binding.fragmentGameCardsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    val selectedCardCount = cardNumbers[position]
                    navigateToGameFragment(selectedCardCount.toInt())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //not used
            }
        }

        return binding.root
    }

    private fun navigateToGameFragment(cardCount: Int) {
        Navigation.findNavController(requireView()).navigate(
            R.id.action_welcomeFragment_to_gameFragment, bundleOf(CARD_KEY to cardCount)
        )
    }
}
