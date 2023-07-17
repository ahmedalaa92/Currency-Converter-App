package com.example.currencyconverterapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverterapp.data.model.HistoricalRateModel
import com.example.currencyconverterapp.databinding.CurrencyConversionListItemBinding

class HistoricalRatesListAdapter(fromCurrency: String, toCurrency: String) :
    ListAdapter<HistoricalRateModel, HistoricalRatesListAdapter.HistoricalRateViewHolder>(
        DiffCallback
    ) {

    private val currencyFrom = fromCurrency
    private val currencyTo = toCurrency

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricalRateViewHolder {
        return HistoricalRateViewHolder(
            CurrencyConversionListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoricalRateViewHolder, position: Int) {
        val historicalRateModel = getItem(position)
        holder.bind(historicalRateModel, currencyFrom, currencyTo)
    }

    class HistoricalRateViewHolder(
        private var binding: CurrencyConversionListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            model: HistoricalRateModel, fromCurrency: String, toCurrency: String
        ) {
            binding.model = model
            binding.fromCurrency = fromCurrency
            binding.toCurrency = toCurrency
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<HistoricalRateModel>() {
        override fun areItemsTheSame(
            oldItem: HistoricalRateModel, newItem: HistoricalRateModel
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: HistoricalRateModel, newItem: HistoricalRateModel
        ): Boolean {
            return oldItem.date == newItem.date
        }
    }
}