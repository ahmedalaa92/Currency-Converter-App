package com.example.currencyconverterapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.data.local.models.CurrencyNamesEntity
import com.example.currencyconverterapp.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var currencyEntities: MutableList<CurrencyNamesEntity> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        observeUI()
        addListeners()
        return binding.root
    }

    private fun addListeners() {
        binding.spinnerFromCurrency.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                binding.spinnerFromCurrency.setSelection(pos)
                viewModel.currencyFromSelection = currencyEntities[pos].currencyName
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        binding.spinnerToCurrency.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                binding.spinnerToCurrency.setSelection(pos)
                viewModel.currencyToSelection = currencyEntities[pos].currencyName
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        binding.edtAmountFrom.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty().not()) {
                    viewModel.fromAmountValue = s.toString().toDouble()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.edtAmountTo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty().not()) {
                    viewModel.toAmountValue = s.toString().toDouble()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun observeUI() {

        viewModel.currenciesLiveData.observe(viewLifecycleOwner) { response ->
            response?.let {
                currencyEntities = response.toMutableList()
                val adapter = context?.let { context ->
                    ArrayAdapter(
                        context,
                        android.R.layout.simple_spinner_dropdown_item,
                        response.map { it.currencyName }.sorted()
                    )
                }
                binding.spinnerFromCurrency.adapter = adapter
                binding.spinnerToCurrency.adapter = adapter
            }
        }

        viewModel.uiStateLiveData.observe(viewLifecycleOwner) { state ->

            when (state) {
                is LoadingState -> {
                    binding.content.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                    binding.tvErrorMessage.visibility = View.GONE
                }

                is ContentState -> {
                    binding.content.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.tvErrorMessage.visibility = View.GONE
                }

                is ErrorState -> {
                    binding.content.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    binding.tvErrorMessage.visibility = View.VISIBLE
                    binding.tvErrorMessage.text = state.message
                }
            }
        }

        viewModel.fromAmountValueLiveData.observe(viewLifecycleOwner) { amount ->
            if (binding.edtAmountFrom.text.toString()
                    .isEmpty() || amount != binding.edtAmountFrom.text.toString().toDouble()
            ) {
                binding.edtAmountFrom.setText(amount.toString())
            }
        }
        viewModel.toAmountValueLiveData.observe(viewLifecycleOwner) { amount ->
            if (binding.edtAmountTo.text.toString()
                    .isEmpty() || amount != binding.edtAmountTo.text.toString().toDouble()
            ) {
                binding.edtAmountTo.setText(amount.toString())
            }
        }

        viewModel.navigationToDetailsScreen.observe(viewLifecycleOwner) { shouldNavigate ->
            if (shouldNavigate) {
                val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(
                    binding.spinnerFromCurrency.selectedItem.toString(),
                    binding.spinnerToCurrency.selectedItem.toString()
                )
                findNavController().navigate(action)
                viewModel.onNavigationToDetailsDone()
            }
        }

        viewModel.ratesLiveData.observe(viewLifecycleOwner) { rates ->
            if (rates.isNullOrEmpty().not()) {
                binding.edtAmountFrom.setText("1.0")
            }
        }

        viewModel.swapCurrencies.observe(viewLifecycleOwner) {
            val selectedItemFromPos = binding.spinnerFromCurrency.selectedItemPosition
            val selectedItemToPos = binding.spinnerToCurrency.selectedItemPosition
            binding.spinnerFromCurrency.setSelection(selectedItemToPos)
            binding.spinnerToCurrency.setSelection(selectedItemFromPos)
        }
    }
}