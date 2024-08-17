package com.example.castalknote.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.castalknote.databinding.ItemNoteBinding
import com.example.castalknote.model.NoteLocalModel

class NoteAdapter(
    private val notes: ArrayList<NoteLocalModel>,
    private val listener: OnNoteItemClickListener,
) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        with(holder) {
            binding.textNoteName.apply {
                if (note.noteName.isNotEmpty()) {
                    text = note.noteName
                    visibility = View.VISIBLE
                } else visibility = View.GONE
            }
            binding.textNoteContent.apply {
                if (note.noteContent.isNotEmpty()) {
                    text = note.noteContent
                    visibility = View.VISIBLE
                } else visibility = View.GONE
            }
            binding.buttonDelete.setOnClickListener {
                listener.onDeleteClick(note)
            }
            itemView.setOnClickListener {
                listener.onItemClick(note)
            }
        }
    }

    fun updateNotes(notes: ArrayList<NoteLocalModel>) {
        this.notes.apply {
            clear()
            addAll(notes)
        }
    }
}

interface OnNoteItemClickListener {
    fun onItemClick(note: NoteLocalModel)
    fun onDeleteClick(note: NoteLocalModel)
}
