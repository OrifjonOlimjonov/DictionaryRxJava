package uz.orifjon.dictionaryinrxkotlin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.orifjon.dictionaryinrxkotlin.database.entity.Category
import uz.orifjon.dictionaryinrxkotlin.database.entity.Word
import uz.orifjon.dictionaryinrxkotlin.databinding.ItemRvBinding
import uz.orifjon.dictionaryinrxkotlin.databinding.ItemRvMenuBinding

class AdapterRecyclerViewCategory(var onItemClick: (Category,View) -> Unit) :
    ListAdapter<Category, AdapterRecyclerViewCategory.VH>(MyDiffUtils()) {


    inner class VH(var binding: ItemRvMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(category: Category) {
            binding.tvName.text = category.name
            binding.tvInfo.visibility= View.GONE
           itemView.setOnClickListener {
                onItemClick(category,binding.btnInfo)
            }
        }
    }

    class MyDiffUtils : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
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