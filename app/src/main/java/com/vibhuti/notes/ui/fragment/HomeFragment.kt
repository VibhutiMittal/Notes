package com.vibhuti.notes.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.vibhuti.notes.R
import com.vibhuti.notes.databinding.FragmentHomeBinding
import com.vibhuti.notes.entity.Note
import com.vibhuti.notes.ui.fragment.adapter.ViewNotesAdapter
import com.vibhuti.notes.utils.Constants
import com.vibhuti.notes.viewmodels.NoteViewModel

class HomeFragment : Fragment() {

    private val list = ArrayList<Note>()
    lateinit var viewModel: NoteViewModel
    var adapter: ViewNotesAdapter? = null
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[NoteViewModel::class.java]

        binding.viewModel = viewModel
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.allNotes.observe(viewLifecycleOwner) {
            it.forEach { note ->
                Log.d("Notes", note.title)
                Log.d("Notes", note.description)
                Log.d("Notes", note.createdAt)
                Log.d("Notes", note.updatedAt)
            }
            list.clear()
            list.addAll(it)
            adapter?.notifyDataSetChanged()
        }
        setAdapter()

        binding.fbAdd.setOnClickListener {
            it.findNavController().navigate(R.id.add_note_fragment)
        }

    }

    private fun setAdapter() {
        adapter = ViewNotesAdapter(list)
        binding.rvNotes.adapter = adapter

        adapter?.apply {

            // Redirect to note fragment for updating the note
            clickHandler = { note, _, view ->
                val bundle = Bundle().apply {
                    putBoolean(Constants.ARE_WE_UPDATING, true)
                    putSerializable(Constants.NOTE_DATA, note)
                }
                view.findNavController().navigate(R.id.add_note_fragment, bundle)
            }

            // Show a popup with delete note option
            longClickHandler = { note, position, view ->

                shopDeletePopup(view, note, position)
            }
        }
    }

    private fun ViewNotesAdapter.shopDeletePopup(
        view: View,
        note: Note,
        position: Int
    ) {
        val popup = PopupMenu(requireContext(), view, Gravity.END)
        popup.menu.add(
            0,
            0,
            Menu.NONE,
            "Delete Note"
        ) // first 0 is groupId, second 0 is itemId

        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                0 -> {
                    viewModel.deleteNote(note)
                    list.removeAt(position)
                    adapter?.notifyItemRemoved(position)
                }
            }
            true
        }

        popup.show()
    }
}