package com.kyjsoft.tp09plant.activities

import android.os.Bundle
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kyjsoft.tp09plant.R
import com.kyjsoft.tp09plant.databinding.ActivitySavePlantBinding
import java.text.SimpleDateFormat
import java.util.*

class SavePlantActivity : AppCompatActivity() {

    val binding by lazy {ActivitySavePlantBinding.inflate(layoutInflater)}
    lateinit var myPlant : String
    // todo멤버변수 만들기


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Glide.with(this).load(intent.getStringExtra("plantImg")).into(binding.ivPlant)
        binding.tvPlantname.text = "이름 :" + intent.getStringExtra("plantName")

        binding.tvDateChoice.setOnClickListener {showBottomSheetDialogCalendar()}

        binding.btnSave.setOnClickListener { saveMyPlantInSqlite() }
    }

    private fun saveMyPlantInSqlite() {

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