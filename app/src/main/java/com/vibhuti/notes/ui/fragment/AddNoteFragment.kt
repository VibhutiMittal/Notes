package com.vibhuti.notes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.vibhuti.notes.R
import com.vibhuti.notes.databinding.FragmentAddNoteBinding
import com.vibhuti.notes.entity.Note
import com.vibhuti.notes.utils.Constants
import com.vibhuti.notes.utils.serializable
import com.vibhuti.notes.viewmodels.NoteViewModel

class AddNoteFragment : Fragment() {

    lateinit var binding: FragmentAddNoteBinding
    lateinit var viewModel: NoteViewModel
    var areWeUpdatingNote = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_note, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[NoteViewModel::class.java]
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.apply {
            areWeUpdatingNote = getBoolean(Constants.ARE_WE_UPDATING, false)
            viewModel.note.value = serializable(Constants.NOTE_DATA) ?: Note("", "", "", "")

            binding.areWeUpdatingNote = areWeUpdatingNote
        }

        binding.tvAddNote.setOnClickListener {
            if (isDataValid()) {
                viewModel.note.value?.let { note ->
                    addUpdateNote(note, it)
                } ?: kotlin.run {
                    showMessage("Something went wrong while adding note please try again after sometime.")
                }
            } else {
                showMessage("Please Fill Required Data.")
            }
        }
    }

    private fun addUpdateNote(note: Note, it: View) {
        if (areWeUpdatingNote) {
            viewModel.updateNote(note)
            showMessage("Note Updated.")
        } else {
            viewModel.insertNote(note)
            showMessage("Note Added.")
        }
        it.findNavController().popBackStack()
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun isDataValid(): Boolean {
        return if (viewModel.note.value?.title.isNullOrEmpty()) false
        else !viewModel.note.value?.description.isNullOrEmpty()
    }
}