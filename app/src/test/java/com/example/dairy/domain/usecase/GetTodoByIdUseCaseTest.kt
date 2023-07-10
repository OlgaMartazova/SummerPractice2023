package com.example.dairy.domain.usecase

import com.example.dairy.domain.entity.TodoEntity
import com.example.dairy.domain.repository.TodoRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class GetTodoByIdUseCaseTest {

    @MockK
    lateinit var todoRepository: TodoRepository

    private lateinit var useCase: GetTodoByIdUseCase

    private val testDispatcher: CoroutineDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetTodoByIdUseCase(todoRepository = todoRepository, testDispatcher)
    }


    @Test
    fun whenGetTodoByIdUseCaseExpectedSuccess() {
        //arrange
        val id = 3
        val expectedName = "Reading"
        val expectedData: TodoEntity = mockk {
            every { name } returns expectedName
        }
        coEvery {
            todoRepository.getTodoById(id)
        } returns expectedData

        //act
        runTest(testDispatcher) {
            val result = useCase.invoke(id = id)

            //assert
            assertEquals(expectedData, result)
            assertEquals(expectedName, result.name)
        }
    }

    @Test
    fun whenGetTodoByIdUseCaseExpectedError() {
        //arrange
        val id = 3
        coEvery {
            todoRepository.getTodoById(id)
        } throws IllegalStateException("Test")

        //act
        runTest(testDispatcher) {
            //assert
            assertFailsWith<IllegalStateException> {
                useCase.invoke(id = id)
            }
        }
    }
}