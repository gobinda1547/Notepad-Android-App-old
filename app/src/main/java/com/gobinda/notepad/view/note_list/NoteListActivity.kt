package com.gobinda.notepad.view.note_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.gobinda.notepad.R
import com.gobinda.notepad.databinding.ActivityNoteListBinding
import com.gobinda.notepad.domain.model.NoteAsListItem
import com.gobinda.notepad.view.add_note.AddNoteActivity
import com.gobinda.notepad.view.show_note.ShowNoteActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class NoteListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteListBinding
    private lateinit var noteListAdapter: NoteListAdapter

    private val viewModel: NoteListViewModel by viewModels()

    private val adapterCallback = object : NoteListAdapter.Callback {
        override fun onItemClick(noteItem: NoteAsListItem) {
            handleOnListItemClick(noteItem)
        }

        override fun onDeleteClick(noteItem: NoteAsListItem) {
            handleOnListItemDeleteClick(noteItem)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteListBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        initializeUiComponents()
        addHandlersAndListeners()
    }

    private fun addHandlersAndListeners() {
        lifecycleScope.launchWhenStarted {
            viewModel.noteList.collectLatest {
                noteListAdapter.submitList(it)
            }
        }
    }

    private fun initializeUiComponents() {
        setTitle(R.string.text_note_list)

        noteListAdapter = NoteListAdapter(adapterCallback)
        binding.noteListRecyclerView.adapter = noteListAdapter
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.noteListRecyclerView.addItemDecoration(decoration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_note_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                startActivity(Intent(this, AddNoteActivity::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun handleOnListItemClick(note: NoteAsListItem) {
        startActivity(Intent(this, ShowNoteActivity::class.java).apply {
            putExtra(com.gobinda.notepad.common.PK_NOTE_ID, note.id)
        })
    }

    private fun handleOnListItemDeleteClick(note: NoteAsListItem) {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.delete_dialog_title))
            setMessage(getString(R.string.delete_dialog_content))
            setPositiveButton(getString(R.string.text_OK)) { dialog, _ ->
                viewModel.deleteNoteFromList(note.id)
                dialog.dismiss()
            }
            setNegativeButton(getString(R.string.text_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
        }.create().show()
    }
}