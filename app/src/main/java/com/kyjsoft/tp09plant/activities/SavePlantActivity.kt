package com.kyjsoft.tp09plant.activities

import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.PopupMenu
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kyjsoft.tp09plant.R
import com.kyjsoft.tp09plant.databinding.ActivitySavePlantBinding
import com.kyjsoft.tp09plant.model.DBHelper
import com.kyjsoft.tp09plant.model.PlantItem
import java.text.SimpleDateFormat
import java.util.*

class SavePlantActivity : AppCompatActivity() {

    val binding by lazy {ActivitySavePlantBinding.inflate(layoutInflater)}
    lateinit var myPlantName : String
    lateinit var plantUrl : String
    lateinit var date : String
    lateinit var memo : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Glide.with(this).load(intent.getStringExtra("plantImg")).into(binding.ivPlant)
        binding.ivAdd.setOnClickListener { clickAdd() }
        binding.tvPlantname.text = "이름 : " + intent.getStringExtra("plantName")
        binding.tvDateChoice.text = SimpleDateFormat("yyyy년 MM월 dd일").format(Date())

        binding.tvDateChoice.setOnClickListener {showBottomSheetDialogCalendar()}

        binding.btnSave.setOnClickListener { saveMyPlantInSqlite() }
    }

    override fun onRestart() {
        super.onRestart()
        // TODO 이거 테스트 해보기
        // 만약에 사진을 안찍고 돌아왔으면 갤러리앱으로 이동하면 안되잖아.
        val captureIntent =  Intent(Intent.ACTION_PICK)
        captureIntent.type = "image/*"
        resultLaucher.launch(captureIntent)
    }

    private fun clickAdd(){
        val popupmenu = PopupMenu(this, binding.ivPlant)
        menuInflater.inflate(R.menu.popup_iv, popupmenu.menu)
        popupmenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_pick_gallery -> {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    resultLaucher.launch(intent)
                }
                R.id.menu_take_picture -> {
                    val intent = Intent(this@SavePlantActivity, CameraActivity::class.java)
                    startActivity(intent)
                }
            }
            false
        }
        popupmenu.show()
    }

    var resultLaucher : ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode != RESULT_CANCELED){
                Glide.with(this).load(result.data?.data).into(binding.ivPlant)
            }
        }

    private fun saveMyPlantInSqlite() {
        myPlantName = binding.etMyPlantName.text.toString()
        plantUrl = intent.getStringExtra("plantImg").toString()
        date = SimpleDateFormat("yyyy년 MM월 dd일").format(Date())
        memo = binding.etMemo.text.toString()
        DBHelper(this).insertDB(PlantItem(myPlantName, plantUrl, date, memo))
        finish()
    }

    private fun showBottomSheetDialogCalendar(){

        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.bs_calendar)
        bottomSheetDialog.show()

        val calendarView = bottomSheetDialog.findViewById<CalendarView>(R.id.bsd_calendar)
        calendarView!!.setOnDateChangeListener { view: CalendarView?, year: Int, month: Int, day: Int ->

            val calendar = GregorianCalendar(year, month, day)
            val date = SimpleDateFormat("yyyy년 MM월 dd일").format(calendar.time)
            binding.tvDateChoice.text = date
            bottomSheetDialog.dismiss()
        }
    }
}