package uz.orifjon.dictionaryinrxkotlin.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.orifjon.dictionaryinrxkotlin.database.entity.Category
import uz.orifjon.dictionaryinrxkotlin.fragment.ViewPagerListFragment

class AdapterViewPager2(fragmentActivity: FragmentActivity,var list:List<Category>) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {
        return ViewPagerListFragment.newInstance(list[position].id,list[position].name)
    }

}