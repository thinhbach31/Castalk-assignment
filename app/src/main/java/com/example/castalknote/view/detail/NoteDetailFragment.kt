package com.example.castalknote.view.detail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.castalknote.base.BaseFragment
import com.example.castalknote.databinding.FragmentNoteDetailBinding
import com.example.castalknote.utils.Const
import com.example.castalknote.utils.Func.hide
import com.example.castalknote.utils.Func.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoteDetailFragment() :
    BaseFragment<FragmentNoteDetailBinding>(FragmentNoteDetailBinding::inflate) {

    private val viewModel: NoteDetailViewModel by viewModels()
    private var savingJob: Job? = null

    override fun observeData() {
        viewModel.noteDetail.observe(this) {
            binding.textTitle.setText(it.noteName)
            binding.textContent.setText(it.noteContent)
            viewModel.setNote(it)
        }
        viewModel.isSaving.observe(this) {
            binding.progressSaving.apply {
                if (it) show() else hide()
            }
        }
        viewModel.isAdded.observe(this) {
            binding.buttonRemove.apply {
                if (it) show() else hide()
            }
        }
        viewModel.isDeleted.observe(this) {
            if (it) activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    override fun requestData() {

    }

    override fun initUIComponents() {
        arguments?.getBoolean(Const.IS_NEW_NOTE)?.apply {
            viewModel.isNewNote = this
            if (!this) {
                arguments?.getInt(Const.NOTE_ID)?.let {
                    viewModel.setNote(it, "", "")
                    viewModel.getNoteDetailByID()
                }
            } else {
                viewModel.setNote(0, "", "")
                binding.buttonRemove.visibility = View.GONE
                binding.progressSaving.visibility = View.GONE
            }
        }
        binding.buttonBack.setOnClickListener {
            viewModel.saveNote()
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
        binding.buttonRemove.setOnClickListener {
            viewModel.deleteNote()
        }

        binding.textTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.setNote(title = p0.toString())
                saveNote()
            }
        })
        binding.textContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.setNote(content = p0.toString())
                saveNote()
            }
        })
    }

    private fun saveNote() {
        savingJob?.cancel()
        savingJob = lifecycleScope.launch {
            delay(Const.AUTO_SAVE_DURATION)
            viewModel.saveNote()
        }
    }

    companion object {
        fun newInstance(id: Int) = NoteDetailFragment().apply {
            arguments = Bundle().apply {
                putBoolean(Const.IS_NEW_NOTE, false)
                putInt(Const.NOTE_ID, id)
            }
        }

        fun newNoteInstance() = NoteDetailFragment().apply {
            arguments = Bundle().apply {
                putBoolean(Const.IS_NEW_NOTE, true)
            }
        }
    }
}
