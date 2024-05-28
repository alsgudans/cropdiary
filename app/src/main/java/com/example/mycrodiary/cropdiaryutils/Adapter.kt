package com.example.mycrodiary.cropdiaryutils

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.mycrodiary.R

class Adapter(val context: Context, private val cropList: ArrayList<Cropinfo>): BaseAdapter() {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // XML 레이아웃 파일을 인플레이션하여 뷰 생성
        val view = LayoutInflater.from(context).inflate(R.layout.adddiary, parent, false)

        // 뷰에서 각각의 텍스트뷰를 찾음
        val nameTextView = view.findViewById<TextView>(R.id.crop_name)
        val nicknameTextView = view.findViewById<TextView>(R.id.crop_nickname)
        val dateTextView = view.findViewById<TextView>(R.id.add_date)

        // 해당 위치의 데이터를 가져와서 각각의 텍스트뷰에 설정
        val cropInfo = cropList[position]
        nameTextView.text = cropInfo.cropname
        nicknameTextView.text = cropInfo.nickname
        dateTextView.text = cropInfo.date

        return view
    }

    override fun getCount(): Int {
        return cropList.size
    }

    override fun getItem(position: Int): Any {
        return cropList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}
