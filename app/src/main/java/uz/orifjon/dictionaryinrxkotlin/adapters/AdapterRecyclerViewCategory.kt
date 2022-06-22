package uz.orifjon.dictionaryinrxkotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.orifjon.dictionaryinrxkotlin.database.entity.Word
import uz.orifjon.dictionaryinrxkotlin.databinding.ItemRvBinding
import uz.orifjon.dictionaryinrxkotlin.databinding.ItemRvMenuBinding

class AdapterRecyclerViewCategory(var onItemClick: (Word) -> Unit) :
    ListAdapter<Word, AdapterRecyclerViewCategory.VH>(MyDiffUtils()) {


    inner class VH(var binding: ItemRvMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(word: Word) {
            binding.tvName.text = word.name
            binding.tvInfo.text = word.translate
            binding.btnInfo.setOnClickListener {
                onItemClick(word)
            }
        }
    }

    class MyDiffUtils : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemRvMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(getItem(position))
    }

}