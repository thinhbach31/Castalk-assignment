package com.example.castalknote.view.main

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.castalknote.base.BaseFragment
import com.example.castalknote.base.NavigationHost
import com.example.castalknote.databinding.FragmentMainBinding
import com.example.castalknote.model.NoteLocalModel
import com.example.castalknote.utils.Func.hide
import com.example.castalknote.utils.Func.show
import com.example.castalknote.view.detail.NoteDetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var noteAdapter: NoteAdapter
    override fun observeData() {
        viewModel.noteLiveData.observe(this) {
            noteAdapter.apply {
                if (it.isEmpty()) {
                    binding.recyclerNote.hide()
                    binding.textEmpty.show()
                } else {
                    binding.recyclerNote.show()
                    binding.textEmpty.hide()
                }
                updateNotes(it)
                notifyDataSetChanged()
            }
        }
        viewModel.isDeleted.observe(this) {
            viewModel.getAllNote()
        }
    }

    override fun requestData() {
    }

    override fun initUIComponents() {
        noteAdapter = NoteAdapter(arrayListOf(), object : OnNoteItemClickListener {
            override fun onItemClick(note: NoteLocalModel) {
                (activity as NavigationHost).addFragment(
                    NoteDetailFragment.newInstance(note.noteId)
                )
            }

            override fun onDeleteClick(note: NoteLocalModel) {
                viewModel.deleteNote(note)
            }
        })
        binding.recyclerNote.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = noteAdapter
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
        }

        binding.textNewNote.setOnClickListener {
            (activity as NavigationHost).addFragment(
                NoteDetailFragment.newNoteInstance()
            )
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllNote()
    }
}
