package com.example.roomexample

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    // Insert a new note into the database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(user: User)
  // Retrieve all notes from the database
    @Query("SELECT * FROM notes ORDER BY dateAdded DESC")
    fun getNotes(): Flow<List<User>>
   // Update an existing note in the database
    @Update
    suspend fun updateNote(user: User)
    // Delete a note from the database
    @Delete
    suspend fun deleteNote(user: User)

}