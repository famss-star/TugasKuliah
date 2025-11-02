package com.zeniramadan.tugaskuliah

import android.content.Context
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "tugas.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "tugas"
        private const val COLUMN_TUGAS = "tugas"
        private const val COLUMN_MATAKULIAH = "matakuliah"
        private const val COLUMN_NAMADOSEN = "namadosen"
        private const val COLUMN_DEADLINE = "deadline"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val QueryTable = "CREATE TABLE $TABLE_NAME ($COLUMN_TUGAS TEXT, $COLUMN_MATAKULIAH TEXT, $COLUMN_NAMADOSEN TEXT, $COLUMN_DEADLINE TEXT)"
        db?.execSQL(QueryTable)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        val QueryCheck = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(QueryCheck)
        db?.close()
    }

    fun GetTugas(): List<Tugas> {
        val tugasList = mutableListOf<Tugas>()
        val db = this.readableDatabase

        val QGetData = "SELECT * FROM $TABLE_NAME"
        val pointer = db.rawQuery(QGetData, null)

        while (pointer.moveToNext()){
            val tugas = pointer.getString(pointer.getColumnIndexOrThrow(COLUMN_TUGAS))
            val matakuliah = pointer.getString(pointer.getColumnIndexOrThrow(COLUMN_MATAKULIAH))
            val namadosen = pointer.getString(pointer.getColumnIndexOrThrow(COLUMN_NAMADOSEN))
            val deadline = pointer.getString(pointer.getColumnIndexOrThrow(COLUMN_DEADLINE))
            val dataTugas = Tugas(tugas, matakuliah, namadosen, deadline)
            tugasList.add(dataTugas)
        }
        pointer.close()
        db.close()
        return tugasList
    }


    fun InsertTugas(tugas: Tugas) {
        val db = this.writableDatabase
        val dataTugas = ContentValues().apply {
            put(COLUMN_TUGAS, tugas.tugas)
            put(COLUMN_MATAKULIAH, tugas.matakuliah)
            put(COLUMN_NAMADOSEN, tugas.namadosen)
            put(COLUMN_DEADLINE, tugas.deadline)
        }
        db.insert(TABLE_NAME, null, dataTugas)
        db.close()

    }

    fun findDatabyTugas(tugas: String): Tugas? {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_TUGAS = ?"

        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val tugas = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TUGAS))
        val matakuliah = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MATAKULIAH))
        val namadosen = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMADOSEN))
        val deadline = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEADLINE))

        cursor.close()
        db.close()
        return Tugas(tugas, matakuliah, namadosen, deadline)
    }

    fun editDataTugas(oldTugas: String, tugas: Tugas){
        val db = this.writableDatabase
        val newData = ContentValues().apply {
            put(COLUMN_TUGAS, tugas.tugas)
            put(COLUMN_MATAKULIAH, tugas.matakuliah)
            put(COLUMN_NAMADOSEN, tugas.namadosen)
            put(COLUMN_DEADLINE, tugas.deadline)
        }
        val where = "$COLUMN_TUGAS = ?"
        val arg = arrayOf(oldTugas)
        db.update(TABLE_NAME, newData, where, arg)
        db.close()
    }

    fun deleteDataTugas(tugas: String){
        val db = this.writableDatabase
        val where = "$COLUMN_TUGAS = ?"
        val arg = arrayOf(tugas)
        db.delete(TABLE_NAME, where, arg)
        db.close()
    }
}