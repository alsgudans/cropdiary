package com.example.mycrodiary.addcropdiarypage



import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.R
import com.example.mycrodiary.accountutil.FirebaseRef
import com.example.mycrodiary.cropdiaryutils.Cropinfo
import com.example.mycrodiary.databinding.ActivityAddcropdiarypageBinding


class AddcropdiarypageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAddcropdiarypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectcropData = resources.getStringArray(R.array.crop_array)
        val spinnerAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,selectcropData)
        binding.selectCrop.adapter = spinnerAdapter

        binding.addCropButton.setOnClickListener {
            val newCropName= binding.selectCrop.selectedItem.toString()
            val newCropNickname = binding.newCropNickname.text.toString()
            val newAddDate = binding.newAddDate.text.toString()

            val takeCropinfo = Cropinfo(newCropName,newCropNickname,newAddDate)
            FirebaseRef.cropInfo.child(newCropNickname).setValue(takeCropinfo)

        }
    }
}