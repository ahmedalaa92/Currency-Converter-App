package com.example.currencyconverterapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverterapp.data.model.HistoricalRateModel
import com.example.currencyconverterapp.databinding.CurrencyConversionListItemBinding

class HistoricalRatesListAdapter :
    ListAdapter<HistoricalRateModel, HistoricalRatesListAdapter.HistoricalRateViewHolder>(
        DiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricalRateViewHolder {
        return HistoricalRateViewHolder(
            CurrencyConversionListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoricalRateViewHolder, position: Int) {
        val historicalRateModel = getItem(position)
        holder.bind(historicalRateModel)
    }

    class HistoricalRateViewHolder(private var binding: CurrencyConversionListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: HistoricalRateModel) {
            binding.model = model
            binding.executePendingBindings()
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