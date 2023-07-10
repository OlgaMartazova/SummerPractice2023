package com.example.dairy.data.impl

import android.database.sqlite.SQLiteConstraintException
import com.example.dairy.data.local.room.TodoDao
import com.example.dairy.data.model.Todo
import com.example.dairy.domain.entity.TodoEntity
import com.example.dairy.domain.repository.TodoRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class TodoRepositoryImplTest {

    @MockK
    lateinit var dao: TodoDao

    private lateinit var repository: TodoRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = TodoRepositoryImpl(dao)
    }

    @Test
    fun whenTodoRepositoryImplExpectedSuccess() = runTest {
        //arrange
        val expectedId = 3
        val expectedData = mockk<Todo> {
            every { dateStart } returns LocalDateTime.of(2023, 7, 10, 17, 30)
            every { dateFinish } returns LocalDateTime.of(2023, 7, 10, 18, 30)
            every { name } returns "Test"
            every { description } returns "Test"
            every { id } returns 3
        }
        val expectedResult = TodoEntity(
            id = 3,
            dateStart = LocalDateTime.of(2023, 7, 10, 17, 30),
            dateFinish = LocalDateTime.of(2023, 7, 10, 18, 30),
            name = "Test",
            description = "Test"
        )
        coEvery {
            dao.getTodoById(expectedId)
        } returns expectedData

        //act
        val result = repository.getTodoById(expectedId)

        //assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun whenTodoRepositoryImplExpectedError() = runTest {
        //arrange
        val expectedId = 3

        coEvery {
            dao.getTodoById(expectedId)
        } throws SQLiteConstraintException("Test")

        //assert
        assertFailsWith<SQLiteConstraintException> {
            //act
            repository.getTodoById(expectedId)
        }
    }
}