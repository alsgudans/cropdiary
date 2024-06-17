package com.example.mycrodiary.Cropdiary_Pages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.databinding.ActivityCropdiaryinfopageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class CropdiaryinfopageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCropdiaryinfopageBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCropdiaryinfopageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Firebase 초기화
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val uid = currentUser?.uid.toString()

        // Intent로 전달된 데이터 받기
        val day = intent.getStringExtra("day").toString()
        val nickname = intent.getStringExtra("nickname").toString()

        // 데이터베이스 참조 설정
        databaseReference = FirebaseDatabase.getInstance("https://project-my-crop-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("cropInfo")
            .child(uid)
            .child(nickname)
            .child(day)

        // 데이터베이스에서 데이터 불러오기
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val day = dataSnapshot.child("day").getValue(String::class.java).toString()
                val weight = dataSnapshot.child("weight").getValue(String::class.java)
                val temperature = dataSnapshot.child("temperature").getValue(String::class.java)
                val humidity = dataSnapshot.child("humidity").getValue(String::class.java)
                val illumination = dataSnapshot.child("illumination").getValue(String::class.java)
                val group1 = dataSnapshot.child("group1").getValue(Int::class.java) ?: 0
                val group2 = dataSnapshot.child("group2").getValue(Int::class.java) ?: 0
                val group3 = dataSnapshot.child("group3").getValue(Int::class.java) ?: 0
                val group4 = dataSnapshot.child("group4").getValue(Int::class.java) ?: 0

                // 텍스트뷰에 데이터 설정
                binding.day.text = "${day} day"
                binding.flow.text = weight
                binding.temperature.text = temperature
                binding.humidity.text = humidity
                binding.illumination.text = illumination

                if (group1 == 11){
                    binding.leafStatus.text = "첫 본잎이 나오고 있어요! 아직 성장 초기이니 잘 지켜봐주세요!"
                }
                else if (group1 == 12){
                    binding.leafStatus.text = "첫번째 진잎이에요! 이제 본격적으로 폭발적인 성장에 들어가기 시작했어요!"
                }
                else if (group1 == 13){
                    binding.leafStatus.text = "두번째 진잎이 나오기 시작했어요! 꽃눈이 생겼을수도 있겠군요?"
                }
                else if (group1 == 14){
                    binding.leafStatus.text = "세번째 진잎이 나오기 시작했습니다! 이제 본격적으로 꽃이 피고 열매가 열릴꺼에요!"
                }
                else{
                    binding.leafStatus.text = "아직 발아하지 않았어요!"
                }

                if (group2 == 21){
                    binding.flowerStatus.text = "꽃눈이 형성되었군요! 첫 꽃눈은 때주는게 더 맛있는 열매를 맺히게 한답니다!"
                }
                else if (group2 == 22){
                    binding.flowerStatus.text = "노란색 꽃이 활짝 폈군요! 꽃이 지고 나면 열매가 열리기 시작할거에요!"
                }
                else if (group2 == 23){
                    binding.flowerStatus.text = "이제 꽃이 지고 열매가 나올 준비를 하고 있어요!"
                }
                else if (group2 == 24){
                    binding.flowerStatus.text = "꽃이 완전히 지고 열매가 열립니다! 열매의 성장을 잘 관찰해주세요!"
                }
                else {
                    binding.flowerStatus.text = "아직 꽃이 피지 않았어요!"
                }

                if(group3 == 31){
                    binding.bugStatus.text = "드디어 열매가 열리기 시작했어요! 아직은 쓰고 신 작은 열매지만, 빨간 열매로 잘 성장시켜봐요."
                }
                else if (group3 == 32){
                    binding.bugStatus.text = "본격적인 열매 팽창기에 들어섰어요! 토마토의 폭발적인 성장을 지켜봐주세요!"
                }
                else if (group3 == 33){
                    binding.bugStatus.text = "열매 성숙기에 들어서며, 열매의 색이 변화하기 시작했군요! 열매가 빨간색으로 바뀔때까지 잘 보듬어주세요!"
                }
                else if (group3 == 34){
                    binding.bugStatus.text = "이제는 열매를 수확할 시기가 되었습니다! 그동안 토마토를 돌보느라 고생한 자신에게 칭찬해주세요!"
                }
                else{
                    binding.bugStatus.text = "아직 열매가 열리지 않았군요!"
                }

                if (group4 == 41){
                    binding.fruitStatus.text = "잿빛곰팡이병이 발병했어요! 통풍을 개선하고, 습기를 줄이고, 감염된 부위를 제거하세요!"
                }
                else if (group4 == 42){
                    binding.fruitStatus.text = "역병이 발병했어요! 감염된 식물을 즉시 제거하고, 습기가 많은 환경을 피하고, 방제하세요!"
                }
                else if (group4 == 43){
                    binding.fruitStatus.text = "잎곰팡이병이 발병했어요! 질소 과다 사용을 피하고, 통풍을 개선하세요!"
                }
                else if (group4 == 44){
                    binding.fruitStatus.text = "세균성 반점병이 발병했어요! 감염된 잎과 열매를 제거하고, 구리기반 살균제를 사용하세요!"
                }
                else if (group4 == 45){
                    binding.fruitStatus.text = "세균성 위축병이 발병했어요! 감염된 식물을 제거하고, 방제하세요!"
                }
                else if (group4 == 46){
                    binding.fruitStatus.text = "토마토 황화 잎말림 바이러스에 감염됐어요! 감염된 식물을 제거하세요!"
                }
                else if (group4 == 47){
                    binding.fruitStatus.text = "모자이크 바이러스에 감염됐어요! 감염된 식물을 제거하고, 병원균이 전파되는 손과 도구를 소독하세요!"
                }
                else if (group4 == 48){
                    binding.fruitStatus.text = "해충이 들러붙었군요! 살충제나 방제제를 이용하여 해충을 제거하세요!"
                }
                else{
                    binding.fruitStatus.text = "식물이 아주 건강합니다!"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 에러 처리
            }
        })
    }
}
