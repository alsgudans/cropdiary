package com.example.mycrodiary.cropdiaryutils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.mycrodiary.R

class Adapter(val context: Context, private val cropList: ArrayList<Cropinfo>): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // 뷰홀더 패턴을 적용하기 위해 ViewHolder 클래스 정의
        class ViewHolder(view: View) {
            val nameTextView: TextView = view.findViewById(R.id.crop_name)
            val nicknameTextView: TextView = view.findViewById(R.id.crop_nickname)
            val dateTextView: TextView = view.findViewById(R.id.add_date)
        }

        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            // XML 레이아웃 파일을 인플레이션하여 뷰 생성
            view = LayoutInflater.from(context).inflate(R.layout.adddiary, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        // 해당 위치의 데이터를 가져와서 각각의 텍스트뷰에 설정
        val cropInfo = cropList[position]
        viewHolder.nameTextView.text = cropInfo.cropname
        viewHolder.nicknameTextView.text = cropInfo.nickname
        viewHolder.dateTextView.text = cropInfo.date

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
