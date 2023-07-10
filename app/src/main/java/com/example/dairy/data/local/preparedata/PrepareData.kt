package com.example.dairy.data.local.preparedata

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.dairy.R
import com.example.dairy.data.local.converter.Converters
import com.example.dairy.data.local.room.TodoDatabase
import com.example.dairy.data.model.Todo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class PrepareData(
    private val context: Context,
) : RoomDatabase.Callback() {

    private val converters by lazy {
        Converters()
    }

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            populateDatabase(context)
        }
    }

    private suspend fun populateDatabase(context: Context) {
        val todoDao = TodoDatabase.getInstance(context).todoDao()
        val todoList = loadJSONArray()
        List(todoList.length()) {
            mapTodo(todoList.getJSONObject(it))
        }.also { list ->
            todoDao.insert(list)
        }
    }

    private fun mapTodo(item: JSONObject): Todo {
        return Todo(
            dateStart = converters.fromTimestamp(
                item.getString(context.getString(R.string.raw_date_start)).toLong()
            )!!,
            dateFinish = converters.fromTimestamp(
                item.getString(context.getString(R.string.raw_date_finish)).toLong()
            )!!,
            name = item.getString(context.getString(R.string.raw_name)),
            description = item.getString(context.getString(R.string.raw_description))
        )
    }

    private fun loadJSONArray(): JSONArray {
        val inputStream = context.resources.openRawResource(R.raw.todo_list)
        inputStream.bufferedReader().use {
            return JSONArray(it.readText())
        }
    }
}