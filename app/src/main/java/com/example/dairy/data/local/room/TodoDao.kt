package com.example.dairy.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dairy.data.model.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo_db WHERE date_start BETWEEN :dayStart AND :dayEnd ORDER BY date_start")
    suspend fun getTodoByDay(dayStart: Long, dayEnd: Long): List<Todo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<Todo>)

    @Query("SELECT * FROM todo_db WHERE id = :id")
    suspend fun getTodoById(id: Int): Todo

    @Query("DELETE FROM todo_db WHERE id = :id")
    suspend fun deleteTodoById(id: Int)
}