package com.example.mycrodiary.cropdiarypages

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.addcropdiarypage.AddcropdiarypageActivity
import com.example.mycrodiary.cropdiaryutils.Adapter
import com.example.mycrodiary.cropdiaryutils.Cropinfo
import com.example.mycrodiary.databinding.ActivityCropdiarypageBinding

@Suppress("DEPRECATION")
class CropdiarypageActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var itemList: ArrayList<Cropinfo>
    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val addSubActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val newItem = data?.getParcelableExtra<Cropinfo>("newItem")
                newItem?.let {
                    itemList.add(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        val binding = ActivityCropdiarypageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        listView = binding.cropDiaryListview

        itemList = ArrayList()

        if (savedInstanceState != null) {
            val savedData = savedInstanceState.getParcelableArrayList<Cropinfo>("dataList")
            savedData?.let { itemList.addAll(it) }
        }

        adapter = Adapter(this, itemList) // Adapter 객체 초기화
        listView.adapter = adapter

        binding.addDiaryBtn.setOnClickListener{
            val intent = Intent(this, AddcropdiarypageActivity::class.java)
            addSubActivityLauncher.launch(intent)
        }


    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("dataList", itemList)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        itemList = savedInstanceState.getParcelableArrayList("dataList") ?: ArrayList()
        adapter.notifyDataSetChanged()
    }
}

