package com.kyjsoft.tp09plant.model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VER) {

    companion object{
        private const val DATABASE_VER = 1
        private const val DATABASE_NAME = "plant.db"
        private const val TABLE_NAME = "plant"
        private const val COL_PLANT_NUM = "num"
        const val COL_PLANT_NAME = "plantName"
        private const val COL_PLANT_IMG = "plantImg"
        private const val COL_PLANT_DATE = "date"
        private const val COL_PLANT_MEMO = "memo"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE IF NOT EXISTS $TABLE_NAME($COL_PLANT_NUM INTEGER PRIMARY KEY AUTOINCREMENT, $COL_PLANT_NAME TEXT, $COL_PLANT_IMG TEXT, $COL_PLANT_DATE TEXT, $COL_PLANT_MEMO TEXT)"
        p0?.execSQL(createTableQuery)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val deleteTableQuery ="DROP TABLE IF EXISTS $TABLE_NAME"
        p0?.execSQL(deleteTableQuery)
        onCreate(p0)
    }

    lateinit var oldName : String
    fun insertDB(item: PlantItem){
        val db = writableDatabase

        val contentValue = ContentValues().apply {
            put(COL_PLANT_NAME, item.plantName)
            put(COL_PLANT_IMG, item.plantUrl)
            put(COL_PLANT_DATE, item.date)
            put(COL_PLANT_MEMO, item.memo)
        }

        db.insert("$TABLE_NAME", null, contentValue)
        oldName = item.plantName
    }

    fun selectDB(): MutableList<PlantItem>{
        val db = readableDatabase
        var item = mutableListOf<PlantItem>()

        val projection = arrayOf(COL_PLANT_NAME, COL_PLANT_IMG, COL_PLANT_DATE, COL_PLANT_MEMO)

        val sortOrder = "${COL_PLANT_NUM} DESC"

        val cursor = db.query(
            TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        )

        with(cursor) {
            while (moveToNext()) {
                val plantName = getString(getColumnIndexOrThrow(COL_PLANT_NAME))
                val plantUrl = getString(getColumnIndexOrThrow(COL_PLANT_IMG))
                val date = getString(getColumnIndexOrThrow(COL_PLANT_DATE))
                val memo = getString(getColumnIndexOrThrow(COL_PLANT_MEMO))

                item.add(PlantItem(plantName, plantUrl, date, memo))
            }
        }
        cursor.close()
        db.close()

        return item
    }

    fun deleteDB(plantName : String){
        val db = writableDatabase

        val selection = "${COL_PLANT_NAME} LIKE ?"  // Define 'where' part of query.
        val selectionArgs = arrayOf(plantName)  // Specify arguments in placeholder order.
        db.delete(TABLE_NAME, selection, selectionArgs)   // Issue SQL statement.

        db.close()

    }

    fun updateDB(plantName : String, item : PlantItem){
        val db = writableDatabase

        val contentValue = ContentValues().apply {
            put(COL_PLANT_NAME, item.plantName)
            put(COL_PLANT_IMG, item.plantUrl)
            put(COL_PLANT_DATE, item.date)
            put(COL_PLANT_MEMO, item.memo)
        }

        // Which row to update, based on the title
        val selection = "$COL_PLANT_NAME LIKE ?"
        val selectionArgs = arrayOf(plantName)

        db.update(
            TABLE_NAME,
            contentValue,
            selection,
            selectionArgs
        )

        db.close()
    }
}