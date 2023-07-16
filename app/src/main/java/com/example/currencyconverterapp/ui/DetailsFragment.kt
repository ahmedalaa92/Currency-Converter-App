package com.example.currencyconverterapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.historicalRecyclerView.adapter = HistoricalRatesListAdapter()
        val fromCurrency = DetailsFragmentArgs.fromBundle(requireArguments()).fromCurrency
        val toCurrency = DetailsFragmentArgs.fromBundle(requireArguments()).toCurrency

        viewModel.retrieveHistoricalRates(fromCurrency, toCurrency)

        observeUI()
        return binding.root
    }

    private fun observeUI() {
        viewModel.uiStateLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoadingState -> {
                    binding.historicalRecyclerView.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                    binding.tvErrorMessage.visibility = View.GONE
                }

                is ContentState -> {
                    binding.historicalRecyclerView.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.tvErrorMessage.visibility = View.GONE
                }

                is ErrorState -> {
                    binding.historicalRecyclerView.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    binding.tvErrorMessage.visibility = View.VISIBLE
                    binding.tvErrorMessage.text = state.message
                }
            }
        }
    }

}