package com.gobinda.notepad.view.note_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.gobinda.notepad.R
import com.gobinda.notepad.domain.model.NoteAsListItem

class NoteListAdapter(
    private val callback: Callback?
) : ListAdapter<NoteAsListItem, NoteListAdapter.NoteViewHolder>(DiffUtilCallback) {

    interface Callback {
        fun onItemClick(noteItem: NoteAsListItem)
        fun onDeleteClick(noteItem: NoteAsListItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.layout_single_note_item, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = getItem(position)
        holder.bind(currentNote)
    }

    inner class NoteViewHolder(itemView: View) : ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.note_title)
        private val contentTextView: TextView = itemView.findViewById(R.id.note_content)
        private var currentNote: NoteAsListItem? = null

        init {
            itemView.setOnClickListener {
                currentNote?.let { callback?.onItemClick(it) }
            }
            itemView.findViewById<ImageView>(R.id.delete_button).setOnClickListener {
                currentNote?.let { callback?.onDeleteClick(it) }
            }
        }

        fun bind(note: NoteAsListItem) {
            currentNote = note
            titleTextView.text = note.title
            contentTextView.text = note.content
        }
    }

    object DiffUtilCallback : DiffUtil.ItemCallback<NoteAsListItem>() {
        override fun areItemsTheSame(oldItem: NoteAsListItem, newItem: NoteAsListItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NoteAsListItem, newItem: NoteAsListItem): Boolean {
            return oldItem.id == newItem.id
        }
    }
}