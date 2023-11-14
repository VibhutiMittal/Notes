package com.vibhuti.notes.repository

import androidx.lifecycle.LiveData
import com.vibhuti.notes.dao.NoteDao
import com.vibhuti.notes.entity.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NoteRepository(private val noteDao: NoteDao) {

    val getAllNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun addNote(note: Note) {
        noteDao.insert(note)
    }

    suspend fun updateNote(note: Note) {
        noteDao.update(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.delete(note)
    }

//    suspend fun deleteNote(note: Note): Flow<Boolean> {
//        return flow {
//            emit(value = false)
//            noteDao.delete(note)
//            emit(value = true)
//        }
//
//    }
}