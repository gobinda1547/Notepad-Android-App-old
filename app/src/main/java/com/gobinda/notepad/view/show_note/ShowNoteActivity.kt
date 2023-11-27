package com.gobinda.notepad.view.show_note

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.gobinda.notepad.R
import com.gobinda.notepad.common.PK_NOTE_ID
import com.gobinda.notepad.databinding.ActivityShowNoteBinding
import com.gobinda.notepad.view.edit_note.EditNoteActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class ShowNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowNoteBinding

    @Inject
    lateinit var viewModelFactory: ShowNoteViewModel.ShowNoteViewModelFactory

    private val viewModel: ShowNoteViewModel by viewModels {
        ShowNoteViewModel.providesFactory(
            assistedFactory = viewModelFactory,
            noteId = intent.getLongExtra(PK_NOTE_ID, 0)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowNoteBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        setContentView(binding.root)
        addHandlersAndListeners()
    }

    private fun addHandlersAndListeners() {
        lifecycleScope.launchWhenStarted {
            viewModel.singleNote.collectLatest {
                supportActionBar?.title = it?.title
                binding.contentTextView.text = it?.content
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_show_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> {
                startActivity(
                    Intent(this, EditNoteActivity::class.java).apply {
                        putExtra(PK_NOTE_ID, viewModel.noteId)
                    }
                )
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}