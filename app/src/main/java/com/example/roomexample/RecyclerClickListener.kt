package com.example.roomexample
//Interface for handling click events in a recycler view.
interface RecyclerClickListener {
    fun onItemRemoveClick(position: Int)
    fun onItemClick(position: Int)
}