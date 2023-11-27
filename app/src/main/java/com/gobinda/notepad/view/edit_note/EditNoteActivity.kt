package com.gobinda.notepad.view.edit_note

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.gobinda.notepad.R
import com.gobinda.notepad.common.PK_NOTE_ID
import com.gobinda.notepad.databinding.ActivityEditNoteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class EditNoteActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MyTestApp"
    }

    private lateinit var binding: ActivityEditNoteBinding

    @Inject
    lateinit var editNoteViewModelFactory: EditNoteViewModel.Factory
    private val viewModel: EditNoteViewModel by viewModels {
        EditNoteViewModel.getFactory(
            assistedFactory = editNoteViewModelFactory,
            noteId = intent.getLongExtra(PK_NOTE_ID, 0L)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setContentView(binding.root)
        setTitle(R.string.text_add_new_note)

        addHandlersAndObservers()
    }

    private fun addHandlersAndObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.toastMessage.collectLatest {
                val context = this@EditNoteActivity
                Toast.makeText(context, getString(it), Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.shouldCloseActivity.collectLatest { shouldWe ->
                takeIf { shouldWe }?.let { finish() }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.shouldEnableSaveMenu.collectLatest {
                Log.d(TAG, "combined result flow updated [$it]")
                invalidateOptionsMenu()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit_note, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val saveMenuItem = menu?.findItem(R.id.action_save)
        saveMenuItem?.isEnabled = viewModel.shouldEnableSaveMenu.value
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                viewModel.onSaveButtonClicked()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}