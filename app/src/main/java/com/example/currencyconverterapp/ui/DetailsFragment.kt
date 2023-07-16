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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        val fromCurrency = DetailsFragmentArgs.fromBundle(requireArguments()).fromCurrency
        val toCurrency = DetailsFragmentArgs.fromBundle(requireArguments()).toCurrency
        val viewModel: DetailsViewModel by viewModels {
            DetailsViewModelFactory(
                fromCurrency, toCurrency
            )
        }

        binding.viewModel = viewModel
        return binding.root
    }

}