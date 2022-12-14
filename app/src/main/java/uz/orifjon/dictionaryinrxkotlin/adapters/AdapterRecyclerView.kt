package uz.orifjon.dictionaryinrxkotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.orifjon.dictionaryinrxkotlin.database.entity.Word
import uz.orifjon.dictionaryinrxkotlin.databinding.ItemRvBinding

class AdapterRecyclerView(var onItemClick: (Word) -> Unit) :
    ListAdapter<Word, AdapterRecyclerView.VH>(MyDiffUtils()) {


    inner class VH(var binding: ItemRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(word: Word) {
            binding.tvName.text = word.name
            binding.tvInfo.text = word.translate
           itemView.setOnClickListener {
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
        return VH(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(getItem(position))
    }

}