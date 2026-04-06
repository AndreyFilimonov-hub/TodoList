package com.filimonov.todolist.di

import android.content.Context
import com.filimonov.todolist.data.dao.TodoDao
import com.filimonov.todolist.data.database.TodoDatabase
import com.filimonov.todolist.data.repository.TodoRepositoryImpl
import com.filimonov.todolist.domain.repository.TodoRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindTodoRepository(impl: TodoRepositoryImpl): TodoRepository

    companion object {

        @Singleton
        @Provides
        fun provideDatabase(@ApplicationContext context: Context): TodoDatabase {
            return TodoDatabase.getInstance(context)
        }

        @Singleton
        @Provides
        fun provideTodoDao(db: TodoDatabase): TodoDao {
            return db.todoDao()
        }
    }
}