package com.zeniramadan.tugaskuliah

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "tugas.db"
        private const val DATABASE_VERSION = 4 // Incremented version
        private const val TABLE_NAME = "tugas"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TUGAS = "tugas"
        private const val COLUMN_MATAKULIAH = "matakuliah"
        private const val COLUMN_NAMADOSEN = "namadosen"
        private const val COLUMN_DEADLINE = "deadline"
        private const val COLUMN_DESKRIPSI = "deskripsi"
        private const val COLUMN_STATUS = "status"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_TUGAS TEXT, $COLUMN_MATAKULIAH TEXT, $COLUMN_NAMADOSEN TEXT, $COLUMN_DEADLINE TEXT, $COLUMN_DESKRIPSI TEXT, $COLUMN_STATUS INTEGER DEFAULT 0)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 4) {
            db?.execSQL("ALTER TABLE $TABLE_NAME ADD COLUMN $COLUMN_DESKRIPSI TEXT")
        }
    }

    fun getAllTugas(): List<Tugas> {
        val tugasList = mutableListOf<Tugas>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val tugas = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TUGAS))
            val matakuliah = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MATAKULIAH))
            val namadosen = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMADOSEN))
            val deadline = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEADLINE))
            val deskripsi = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESKRIPSI))
            val status = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STATUS)) == 1
            val dataTugas = Tugas(id, tugas, matakuliah, namadosen, deadline, deskripsi, status)
            tugasList.add(dataTugas)
        }
        cursor.close()
        db.close()
        return tugasList
    }

    fun InsertTugas(tugas: Tugas) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_TUGAS, tugas.tugas)
            put(COLUMN_MATAKULIAH, tugas.matakuliah)
            put(COLUMN_NAMADOSEN, tugas.namadosen)
            put(COLUMN_DEADLINE, tugas.deadline)
            put(COLUMN_DESKRIPSI, tugas.deskripsi)
            put(COLUMN_STATUS, if (tugas.status) 1 else 0)
        }
        db.insert(TABLE_NAME, null, contentValues)
        db.close()
    }

    fun getTugasByID(tugasId: Int): Tugas? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(tugasId.toString()))
        var tugas: Tugas? = null

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val namaTugas = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TUGAS))
            val mataKuliah = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MATAKULIAH))
            val namaDosen = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMADOSEN))
            val deadline = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEADLINE))
            val deskripsi = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESKRIPSI))
            val status = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STATUS)) == 1
            tugas = Tugas(id, namaTugas, mataKuliah, namaDosen, deadline, deskripsi, status)
        }

        cursor.close()
        db.close()
        return tugas
    }

    fun editDataTugas(tugas: Tugas) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_TUGAS, tugas.tugas)
            put(COLUMN_MATAKULIAH, tugas.matakuliah)
            put(COLUMN_NAMADOSEN, tugas.namadosen)
            put(COLUMN_DEADLINE, tugas.deadline)
            put(COLUMN_DESKRIPSI, tugas.deskripsi)
            put(COLUMN_STATUS, if (tugas.status) 1 else 0)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(tugas.id.toString())
        db.update(TABLE_NAME, contentValues, whereClause, whereArgs)
        db.close()
    }

    fun deleteDataTugas(tugasId: Int) {
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(tugasId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }

    fun updateStatus(tugasId: Int, status: Boolean) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_STATUS, if (status) 1 else 0)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(tugasId.toString())
        db.update(TABLE_NAME, contentValues, whereClause, whereArgs)
        db.close()
    }
}
