package com.example.cardgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cardgame.databinding.FragmentGameBinding

const val CARD_KEY = "card_key"
private const val CARD_COUNT = 2

class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private lateinit var gameAdapter: GameAdapter

    private val allImages: MutableList<Int> = mutableListOf(
        R.drawable.card_one,
        R.drawable.card_one,
        R.drawable.card_two,
        R.drawable.card_two,
        R.drawable.card_three,
        R.drawable.card_three,
        R.drawable.card_four,
        R.drawable.card_four,
        R.drawable.card_five,
        R.drawable.card_five,
        R.drawable.card_six,
        R.drawable.card_six,
        R.drawable.card_seven,
        R.drawable.card_seven,
        R.drawable.card_eight,
        R.drawable.card_eight,
        R.drawable.card_nine,
        R.drawable.card_nine,
        R.drawable.card_ten,
        R.drawable.card_ten
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val cardsCount = arguments?.getInt(CARD_KEY) ?: 0
        val cardImageList = allImages.subList(0, cardsCount)
        cardImageList.shuffle()
        val binding: FragmentGameBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        binding.gameViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val itemDecoration = GridSpacingItemDecoration(
			spanCount = CARD_COUNT, spacing = resources.getDimensionPixelSize(R.dimen.grid_spacing), includeEdge = true
		)
        binding.fragmentGameRv.apply {
            layoutManager = GridLayoutManager(requireContext(), CARD_COUNT)
            addItemDecoration(itemDecoration)


            gameAdapter = GameAdapter(itemClickListener = object : IOnCardClickListener {
                override fun onCardClick(position: Int) {
                    viewModel.onCardClicked(position)
                }
            })
            binding.fragmentGameRv.adapter = gameAdapter

            viewModel.setListOfCards(cardImageList)
            setObservers()
            return binding.root
        }
    }

    private fun setObservers() {
        viewModel.listOFCard.observe(viewLifecycleOwner) { cardList ->
            gameAdapter.updateData(cardList)
        }
        viewModel.eventGameFinished.observe(viewLifecycleOwner) { hasFinished ->
            if (hasFinished == true) {
                gameFinished()
            }
        }
    }

    private fun gameFinished() {
        findNavController().navigate(R.id.action_gameFragment_to_endGameFragment)
    }
}
