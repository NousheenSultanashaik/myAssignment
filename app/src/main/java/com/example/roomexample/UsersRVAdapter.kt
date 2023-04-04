package com.example.roomexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.awaitCancellation

class UsersRVAdapter : ListAdapter<User, UsersRVAdapter.NoteHolder>(DiffCallback()) {

    class NoteHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var listener: RecyclerClickListener
    // this to indicate if the adapter is activated
    var activated = false

    fun setItemListener(listener: RecyclerClickListener) {
        this.listener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.users_row, parent, false)
        val noteHolder = NoteHolder(v)
       // Set a click listener for the note delete button
        val noteDelete = noteHolder.itemView.findViewById<ImageView>(R.id.note_delete)
        noteDelete.setOnClickListener {
            // Call the onItemRemoveClick method of the listener with the clicked item's position
            listener.onItemRemoveClick(noteHolder.adapterPosition)
        }

        val note = noteHolder.itemView.findViewById<CardView>(R.id.user)
        note.setOnClickListener {
            listener.onItemClick(noteHolder.adapterPosition)
        }

        return noteHolder
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentItem = getItem(position)
        val noteText = holder.itemView.findViewById<TextView>(R.id.note_text)
        val emailText = holder.itemView.findViewById<TextView>(R.id.email_text)
        val genderText = holder.itemView.findViewById<TextView>(R.id.gender_text)
        val btech_text = holder.itemView.findViewById<TextView>(R.id.btech_text)
        val dob_text = holder.itemView.findViewById<TextView>(R.id.dob_text)
        val datetime_text = holder.itemView.findViewById<TextView>(R.id.datetime_text)
        val user_img = holder.itemView.findViewById<ImageView>(R.id.user_img)
   //this i have writen so that it can display the text in the recyclerview and when the user add the details it display in the recycler view
        noteText.text = "Name : "+currentItem.noteText
        emailText.text = "Email : "+currentItem.emailText
        genderText.text = "Gender : "+currentItem.genderText
        btech_text.text = "Btech passed out year : "+currentItem.passyearText
        dob_text.text = "DOB : "+currentItem.dobdateText
        datetime_text.text = "Date and Time : "+currentItem.datetimeText
        val star1 =  holder.itemView.findViewById<ImageView>(R.id.star1)
        val star2 = holder.itemView.findViewById<ImageView>(R.id.star2)
        val star3 = holder.itemView.findViewById<ImageView>(R.id.star3)
        val star4 = holder.itemView.findViewById<ImageView>(R.id.star4)
        val star5 = holder.itemView.findViewById<ImageView>(R.id.star5)
        if(currentItem.starText.equals("1")){
            star1.setImageResource(R.drawable.ic_star_filled)
            star2.setImageResource(R.drawable.ic_star_empty)
            star3.setImageResource(R.drawable.ic_star_empty)
            star4.setImageResource(R.drawable.ic_star_empty)
            star5.setImageResource(R.drawable.ic_star_empty)
        }else  if(currentItem.starText.equals("2")){
            star1.setImageResource(R.drawable.ic_star_filled)
            star2.setImageResource(R.drawable.ic_star_filled)
            star3.setImageResource(R.drawable.ic_star_empty)
            star4.setImageResource(R.drawable.ic_star_empty)
            star5.setImageResource(R.drawable.ic_star_empty)
        }else  if(currentItem.starText.equals("3")){
            star1.setImageResource(R.drawable.ic_star_filled)
            star2.setImageResource(R.drawable.ic_star_filled)
            star3.setImageResource(R.drawable.ic_star_filled)
            star4.setImageResource(R.drawable.ic_star_empty)
            star5.setImageResource(R.drawable.ic_star_empty)
        } else  if(currentItem.starText.equals("4")){
            star1.setImageResource(R.drawable.ic_star_filled)
            star2.setImageResource(R.drawable.ic_star_filled)
            star3.setImageResource(R.drawable.ic_star_filled)
            star4.setImageResource(R.drawable.ic_star_filled)
            star5.setImageResource(R.drawable.ic_star_empty)
        } else  if(currentItem.starText.equals("5")){
            star1.setImageResource(R.drawable.ic_star_filled)
            star2.setImageResource(R.drawable.ic_star_filled)
            star3.setImageResource(R.drawable.ic_star_filled)
            star4.setImageResource(R.drawable.ic_star_filled)
            star5.setImageResource(R.drawable.ic_star_filled)
        }
        val noteDelete = holder.itemView.findViewById<ImageView>(R.id.note_delete)
        if (activated) {
            noteDelete.setVisibility(View.VISIBLE);
        } else {
            noteDelete.setVisibility(View.GONE);
        }
        if(currentItem.genderText.equals("Male")){
            user_img.setImageResource(R.drawable.user_image)
        }else{
            user_img.setImageResource(R.drawable.user_female)
        }
    }
    fun activateButtons(activate: Boolean) {
        activated = activate
        notifyDataSetChanged() //need to call it for the child views to be re-created with buttons.
    }
    class DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User) =
            oldItem.dateAdded == newItem.dateAdded

        override fun areContentsTheSame(oldItem: User, newItem: User) =
            oldItem == newItem
    }
}