package com.vibhuti.notes.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vibhuti.notes.entity.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg note: Note)

    @Update
    suspend fun update(vararg note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM noteTable order by id ASC")
    fun getAllNotes(): LiveData<List<Note>>
//
//    @Query("SELECT * FROM noteTable WHERE id is :id")
//    suspend fun getNoteById(vararg id:Int): Flow<Note>
}