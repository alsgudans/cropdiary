package com.example.mycrodiary.editcroplist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.mycrodiary.databinding.AdddiaryBinding

class Adapter(val context: Context, val cropList: ArrayList<Cropinfo>): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = AdddiaryBinding.inflate(LayoutInflater.from(context))

        val profile = binding.cropProfile
        val name = binding.cropName
        val nickname = binding.cropNickname
        val date = binding.addDay

        val croplist = cropList[position]

        val resourceId = context.resources.getIdentifier(croplist.profile, "drawable", context.packageName)
        profile.setImageResource(resourceId)

        name.text = croplist.name
        nickname.text = croplist.nickname
        date.text = croplist.date

        return binding.root
    }

    override fun getCount(): Int {
        return cropList.size
    }

    override fun getItem(position: Int): Any {
        return cropList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }
}