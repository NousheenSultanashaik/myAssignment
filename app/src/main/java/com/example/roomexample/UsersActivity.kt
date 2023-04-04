package com.example.roomexample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import java.util.*

class UsersActivity : AppCompatActivity() {

    private lateinit var adapter: UsersRVAdapter

    private val userDatabase by lazy { NoteDatabase.getDatabase(this).noteDao() }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        setRecyclerView()

        observeNotes()
        // Set up event listeners
        setUpListeners()

    }

    private fun setUpListeners() {
        //getting the reference of the add button and the edit text in the recyclerview page
        val addBtn = findViewById<FloatingActionButton>(R.id.button_add_note)
        val editBtn = findViewById<TextView>(R.id.edit_txt)
      //when we click on the addbtn the it will go to the next page to fill the details there
        addBtn.setOnClickListener {
            val intent = Intent(this, AddUsersActivity::class.java)
            newNoteResultLauncher.launch(intent)
        }
  // when we click on editbtn then an '-' will be appear on the side to delete the field
        editBtn.setOnClickListener {
            adapter.activateButtons(true);
        }
    }
   //Sets up the RecyclerView with the adapter and layout manager.
    //Sets a listener on the adapter that will trigger when the remove button is clicked for an item in the list.
    private fun setRecyclerView() {
        val notesRecyclerview = findViewById<RecyclerView>(R.id.notes_recyclerview)
        notesRecyclerview.layoutManager = LinearLayoutManager(this)
        notesRecyclerview.setHasFixedSize(true)
        adapter = UsersRVAdapter()
        adapter.setItemListener(object : RecyclerClickListener {
   // Called when the remove button is clicked for an item in the list.
   // Removes the selected item from the RecyclerView and submits the updated list to the adapter.
   //  Deletes the same item from the database.

            override fun onItemRemoveClick(position: Int) {
                val notesList = adapter.currentList.toMutableList()
                val noteText = notesList[position].noteText
                val emailText = notesList[position].emailText
                val genderText = notesList[position].genderText
                val passyearText = notesList[position].passyearText
                val dobdateText = notesList[position].dobdateText
                val datetimeText = notesList[position].datetimeText
                val noteDateAdded = notesList[position].dateAdded
                val starText = notesList[position].starText
                val removeUser = User(noteDateAdded, noteText,emailText,genderText,passyearText,dobdateText,datetimeText,starText)
                notesList.removeAt(position)
                // This ensures that the note is removed both from the UI and the database.
                adapter.submitList(notesList)
                lifecycleScope.launch {
                    userDatabase.deleteNote(removeUser)
                }
            }

            override fun onItemClick(position: Int) {
                // Create an intent to launch the AddUsersActivity
                val intent = Intent(this@UsersActivity, AddUsersActivity::class.java)
                // Get the notes list from the adapter and retrieve the details of the selected note
                val notesList = adapter.currentList.toMutableList()
                intent.putExtra("note_date_added", notesList[position].dateAdded)
                intent.putExtra("note_text", notesList[position].noteText)
                intent.putExtra("email_text", notesList[position].emailText)
                intent.putExtra("gender_text", notesList[position].genderText)
                intent.putExtra("passyear_text", notesList[position].passyearText)
                intent.putExtra("dobdate_text", notesList[position].dobdateText)
                intent.putExtra("datetime_text", notesList[position].datetimeText)
                intent.putExtra("star_text", notesList[position].starText)
                //this is for handling the result
                editNoteResultLauncher.launch(intent)
            }
        })
        notesRecyclerview.adapter = adapter
    }

    private fun observeNotes() {
        lifecycleScope.launch {
            userDatabase.getNotes().collect { notesList ->
                if (notesList.isNotEmpty()) {
                    adapter.submitList(notesList)
                }
            }
        }
    }
// This is a  launcher for starting the activity to add a new note. It registers a callback function that is called
// when the AddUsersActivity finishes and returns a result.
    private val newNoteResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                // Extract the data from the intent extras returned by the activity
                val noteDateAdded = Date()
                val noteText = result.data?.getStringExtra("note_text")
                val emailText = result.data?.getStringExtra("email_text")
                val genderText = result.data?.getStringExtra("gender_text")
                val passyearText = result.data?.getStringExtra("passyear_text")
                val dobdateText = result.data?.getStringExtra("dobdate_text")
                val datetime_Text = result.data?.getStringExtra("datetime_text")
                val starText = result.data?.getStringExtra("star_text")


                // Create a new user object with the  details

                val newUser = User(noteDateAdded, noteText ?: "",emailText?:"",
                    genderText?:"",passyearText?:"",dobdateText?:"",datetime_Text?:"",starText?:"")
                // Add the new user to the database

                lifecycleScope.launch {
                    userDatabase.addNote(newUser)
                }
            }
        }

    val editNoteResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Get the edited note from the AddNoteActivity
                val noteDateAdded = result.data?.getSerializableExtra("note_date_added") as Date
                val noteText = result.data?.getStringExtra("note_text")
                val emailText = result.data?.getStringExtra("email_text")
                val genderText = result.data?.getStringExtra("gender_text")
                val passyearText = result.data?.getStringExtra("passyear_text")
                val dobdateText = result.data?.getStringExtra("dobdate_text")
                val datetime_Text = result.data?.getStringExtra("datetime_text")
                val star_Text = result.data?.getStringExtra("star_text")
                // Create a new User object with the edited note details
                val editedUser = User(noteDateAdded, noteText ?: "",emailText?:""
                , genderText?:"",passyearText?:"",dobdateText?:"",datetime_Text?:"",star_Text?:"")
                // Update the note in the database
                lifecycleScope.launch {
                    userDatabase.updateNote(editedUser)
                }
            }
        }



}