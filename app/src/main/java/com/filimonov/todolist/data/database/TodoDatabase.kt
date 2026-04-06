package com.filimonov.todolist.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.filimonov.todolist.data.dao.TodoDao
import com.filimonov.todolist.data.model.TodoDbModel

@Database(entities = [TodoDbModel::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {

    companion object {

        private const val DB_NAME = "todo.db"
        private val LOCK = Any()
        private var instance: TodoDatabase? = null

        fun getInstance(context: Context): TodoDatabase {
            instance?.let { return it }
            synchronized(LOCK) {
                instance?.let { return it }
                return Room.databaseBuilder(
                    context,
                    TodoDatabase::class.java,
                    DB_NAME
                ).build().also { instance = it }
            }
        }
    }

    abstract fun todoDao(): TodoDao
}