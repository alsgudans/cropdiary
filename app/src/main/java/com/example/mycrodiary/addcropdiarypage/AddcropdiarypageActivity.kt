package com.example.mycrodiary.addcropdiarypage

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.R
import com.example.mycrodiary.accountutil.FirebaseRef
import com.example.mycrodiary.cropdiaryutils.Cropinfo
import com.example.mycrodiary.databinding.ActivityAddcropdiarypageBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.Calendar

class AddcropdiarypageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddcropdiarypageBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddcropdiarypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val selectcropData = resources.getStringArray(R.array.crop_array)
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, selectcropData)
        binding.selectCrop.adapter = spinnerAdapter

        binding.newAddDate.setOnClickListener {
            showDatePicker()
        }

        binding.addCropButton.setOnClickListener {
            val newCropName = binding.selectCrop.selectedItem.toString()
            val newAddDate = binding.newAddDate.text.toString()
            val newCropNickname = binding.newCropNickname.text.toString()

            val currentUser = auth.currentUser
            val uid = currentUser?.uid

            if (uid != null) {
                val takeCropinfo = Cropinfo(newCropName, newCropNickname, newAddDate) // 닉네임 대신 UID 사용
                FirebaseRef.cropInfo.child(uid).push().setValue(takeCropinfo)
            }
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // DatePickerDialog를 생성하고 표시
        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
            binding.newAddDate.setText(selectedDate)
        }, year, month, day)
        datePickerDialog.show()
    }
}
