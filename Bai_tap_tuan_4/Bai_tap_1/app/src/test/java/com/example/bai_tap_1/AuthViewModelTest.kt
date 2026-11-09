package com.example.bai_tap_1

import com.example.bai_tap_1.viewmodel.AuthViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AuthViewModelTest {

    private lateinit var viewModel: AuthViewModel

    @Before
    fun setUp() {
        viewModel = AuthViewModel()
    }

    @Test
    fun initialTasksState_isCorrect() {
        assertEquals(4, viewModel.totalTasksCount)
        assertEquals(2, viewModel.completedTasksCount)
        assertEquals(2, viewModel.pendingTasksCount)
        assertEquals(50, viewModel.completionPercentage)
    }

    @Test
    fun toggleTaskCompletion_updatesCountsAndPercentage() {
        // Uncheck task "1" (currently completed)
        viewModel.toggleTaskCompletion("1")

        assertEquals(1, viewModel.completedTasksCount)
        assertEquals(3, viewModel.pendingTasksCount)
        assertEquals(25, viewModel.completionPercentage)

        // Check task "3" (currently uncompleted)
        viewModel.toggleTaskCompletion("3")

        assertEquals(2, viewModel.completedTasksCount)
        assertEquals(50, viewModel.completionPercentage)
    }

    @Test
    fun addNewTask_increasesTotalTasks() {
        viewModel.addNewTask("Làm bài tập Android", "Học tập", "20:00")

        assertEquals(5, viewModel.totalTasksCount)
        assertEquals(3, viewModel.pendingTasksCount)
    }

    @Test
    fun loginAsGuest_updatesUserProfile() {
        viewModel.loginAsGuest {}

        assertTrue(viewModel.isLoggedIn)
        assertTrue(viewModel.userProfile.isGuest)
        assertEquals("Khách", viewModel.userProfile.name)
    }

    @Test
    fun logout_resetsUserAndLoginState() {
        viewModel.loginAsGuest {}
        assertTrue(viewModel.isLoggedIn)

        viewModel.logout {}

        assertFalse(viewModel.isLoggedIn)
        assertEquals("Minh Anh", viewModel.userProfile.name)
    }
}
