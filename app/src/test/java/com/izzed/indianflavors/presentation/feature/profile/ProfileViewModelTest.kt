package com.izzed.indianflavors.presentation.feature.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.izzed.indianflavors.data.repository.UserRepository
import com.izzed.indianflavors.model.User
import com.izzed.indianflavors.tools.MainCoroutineRule
import com.izzed.indianflavors.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ProfileViewModelTest {

    @MockK
    lateinit var repository: UserRepository

    private lateinit var viewModel: ProfileViewModel

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(ProfileViewModel(repository))
        val updateResult = flow {
            emit(ResultWrapper.Success(true))
        }
        coEvery { repository.getCurrentUser() } returns User("fullname", "url", "email")
        coEvery { repository.sendChangePasswordRequestByEmail() } returns true
        coEvery { repository.doLogout() } returns true
        coEvery { repository.updateProfile(any(), any()) } returns updateResult
    }

    @Test
    fun `get current user`() {
        viewModel.getCurrentUser()
        coVerify { repository.getCurrentUser() }
    }

    @Test
    fun `create change password request`() {
        viewModel.createChangePwdRequest()
        coVerify { repository.sendChangePasswordRequestByEmail() }
    }

    @Test
    fun `do logout`() {
        viewModel.doLogout()
        coVerify { repository.doLogout() }
    }

    @Test
    fun `update profile`() {
        viewModel.updateFullName("full name")
        coVerify { repository.updateProfile(any()) }
    }
}
