package com.gobinda.notepad.view.show_note

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.gobinda.notepad.databinding.ActivityShowNoteBinding
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
            noteId = intent.getLongExtra(com.gobinda.notepad.common.PK_NOTE_ID, 0)
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
}