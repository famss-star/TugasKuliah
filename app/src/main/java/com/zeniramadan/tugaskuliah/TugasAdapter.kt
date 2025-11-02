package com.zeniramadan.tugaskuliah

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class TugasAdapter(private var tugas: List<Tugas>, context: Context) : RecyclerView.Adapter<TugasAdapter.TugasViewHolder>() {
    class TugasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val editButton: ImageView = itemView.findViewById(R.id.editButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
        val namaTugasTextView: TextView = itemView.findViewById(R.id.namaTugasTextView)
        val mataKuliahTextView: TextView = itemView.findViewById(R.id.mataKuliahTextView)
        val namaDosenTextView: TextView = itemView.findViewById(R.id.namaDosenTextView)
        val deadlineTextView: TextView = itemView.findViewById(R.id.deadlineTextView)
    }

    private val db : DatabaseHelper = DatabaseHelper(context)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TugasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item_tugas, parent, false)
        return TugasViewHolder(view)
    }

    override fun onBindViewHolder(holder: TugasAdapter.TugasViewHolder, position: Int) {
        val tugas = tugas[position]
        holder.namaTugasTextView.text = tugas.tugas
        holder.mataKuliahTextView.text = tugas.matakuliah
        holder.namaDosenTextView.text = tugas.namadosen
        holder.deadlineTextView.text = tugas.deadline

        holder.editButton.setOnClickListener {
            val edit = Intent(holder.itemView.context, EditTugasActivity::class.java).apply {putExtra("tugas", tugas.tugas)}
            holder.itemView.context.startActivity(edit)
        }

        holder.deleteButton.setOnClickListener {
            db.deleteDataTugas(tugas.tugas)
            refreshData(db.GetTugas())
            Toast.makeText(holder.itemView.context, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newData: List<Tugas>) {
        tugas = newData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = tugas.size
}