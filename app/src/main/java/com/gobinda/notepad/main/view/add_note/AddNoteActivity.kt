package com.gobinda.notepad.main.view.add_note

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.gobinda.notepad.R
import com.gobinda.notepad.databinding.ActivityAddNoteBinding
import com.gobinda.notepad.main.view.common.util.AfterTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private val viewModel: AddNoteViewModel by viewModels()

    private val titleChangeListener = object : AfterTextChangedListener() {
        override fun onChange(text: String) {
            viewModel.onTitleChanged(text)
        }
    }

    private val contentChangeListener = object : AfterTextChangedListener() {
        override fun onChange(text: String) {
            viewModel.onContentChanged(text)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setContentView(binding.root)
        initializeUI()

        addHandlersAndObservers()
    }

    private fun addHandlersAndObservers() {
        binding.textInputTitle.addTextChangedListener(titleChangeListener)
        binding.textInputContent.addTextChangedListener(contentChangeListener)

        lifecycleScope.launchWhenStarted {
            viewModel.toastMessage.collectLatest {
                val context = this@AddNoteActivity
                Toast.makeText(context, getString(it), Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.shouldCloseActivity.collectLatest { shouldWe ->
                takeIf { shouldWe }?.let { finish() }
            }
        }
    }

    private fun initializeUI() {
        binding.textInputTitle.setText(viewModel.noteTitle.value)
        binding.textInputContent.setText(viewModel.noteContent.value)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_note, menu)
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