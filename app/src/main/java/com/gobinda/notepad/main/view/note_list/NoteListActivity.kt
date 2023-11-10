package com.gobinda.notepad.main.view.note_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.gobinda.notepad.R
import com.gobinda.notepad.main.view.add_note.AddNoteActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)
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
}