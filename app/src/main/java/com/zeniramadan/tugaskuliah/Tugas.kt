package com.zeniramadan.tugaskuliah

data class Tugas(
    val id: Int,
    val tugas: String,
    val matakuliah: String,
    val namadosen: String,
    val deadline: String,
    val deskripsi: String,
    val status: Boolean = false
)
