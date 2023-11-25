package com.gobinda.notepad.main.view.note_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.gobinda.notepad.R
import com.gobinda.notepad.databinding.ActivityNoteListBinding
import com.gobinda.notepad.main.common.PK_NOTE_ID
import com.gobinda.notepad.main.domain.model.NoteAsListItem
import com.gobinda.notepad.main.view.add_note.AddNoteActivity
import com.gobinda.notepad.main.view.show_note.ShowNoteActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class NoteListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteListBinding
    private lateinit var noteListAdapter: NoteListAdapter

    private val viewModel: NoteListViewModel by viewModels()

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
        noteListAdapter = NoteListAdapter { note -> handleAdapterCallback(note) }
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

    private fun handleAdapterCallback(note: NoteAsListItem) {
        startActivity(Intent(this, ShowNoteActivity::class.java).apply {
            putExtra(PK_NOTE_ID, note.id)
        })
    }
}