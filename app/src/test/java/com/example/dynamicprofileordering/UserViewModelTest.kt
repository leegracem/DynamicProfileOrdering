package com.example.dynamicprofileordering

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.dynamicprofileordering.model.Config
import com.example.dynamicprofileordering.model.User
import com.example.dynamicprofileordering.repository.UserRepository
import com.example.dynamicprofileordering.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when` as whenever
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class UserViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var userRepository: UserRepository
    private lateinit var userViewModel: UserViewModel

    private val testScope = TestCoroutineScope()

    private val mockUsers = listOf(
        User(
            id = 1,
            name = "User 1",
            gender = "Male",
            photo = "https://tinyurl.com/mpckmdss",
            about = "About User 1",
            school = "School 1",
            hobbies = listOf("Running", "Art")
        ),
        User(
            id = 2,
            name = "User 2",
            gender = "Female",
            photo = "https://tinyurl.com/3vpr35zj",
            about = "About User 2",
            school = "School 2",
            hobbies = listOf("Music", "Swimming")
        ),
        User(
            id = 3,
            name = "User 3",
            gender = "Female",
            photo = "https://tinyurl.com/3vpr35zj",
            about = "About User 3",
            school = "School 3",
            hobbies = listOf("Hiking", "Swimming")
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        userRepository = mock(UserRepository::class.java)
        userViewModel = UserViewModel(userRepository)
    }


    @Test
    fun `test fetchUsers`() = testScope.runTest {
        whenever(userRepository.getUsers()).thenReturn(mockUsers)
        userViewModel.fetchUsers()
        assertEquals(mockUsers, userViewModel.users.value)
    }

    @Test
    fun `test fetchConfig`() = testScope.runTest {
        val mockConfig = Config(listOf("name", "photo", "gender", "about", "school", "hobbies"))
        whenever(userRepository.getConfig()).thenReturn(mockConfig)
        userViewModel.fetchConfig()
        assertEquals(mockConfig, userViewModel.config.value)
    }

    @Test
    fun `test fetchUsers with empty list`() = testScope.runTest {
        whenever(userRepository.getUsers()).thenReturn(emptyList())
        userViewModel.fetchUsers()
        assertEquals(emptyList<User>(), userViewModel.users.value)
    }

    @Test
    fun `test fetchConfig with empty list`() = testScope.runTest {
        val mockConfig = Config(emptyList())
        whenever(userRepository.getConfig()).thenReturn(mockConfig)
        userViewModel.fetchConfig()
        assertEquals(mockConfig, userViewModel.config.value)
    }

    @Test
    fun `test nextUser`() = testScope.runTest {
        whenever(userRepository.getUsers()).thenReturn(mockUsers)
        userViewModel.fetchUsers()
        userViewModel.nextUser()
        assertEquals(1, userViewModel.currentIndex.value)
    }



    @Test
    fun `test nextUser out of bounds condition`() = testScope.runTest {
        whenever(userRepository.getUsers()).thenReturn(mockUsers)
        userViewModel.fetchUsers()
        userViewModel.nextUser()
        userViewModel.nextUser()
        userViewModel.nextUser()
        assertEquals(2, userViewModel.currentIndex.value)
    }
}
