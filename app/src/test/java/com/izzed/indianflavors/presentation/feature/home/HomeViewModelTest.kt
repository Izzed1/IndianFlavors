package com.izzed.indianflavors.presentation.feature.home

import org.junit.Assert.*

// class HomeViewModelTest {
//
//    @get:Rule
//    val testRule = InstantTaskExecutorRule()
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @get:Rule
//    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())
//
//    @MockK
//    private lateinit var repository: ProductRepository
//
//    private lateinit var viewModel: HomeViewModel
//
//    @Before
//    fun setUp() {
//        MockKAnnotations.init(this)
//
//        coEvery { repository.getMenus(any()) } returns flow {
//            emit(
//                ResultWrapper.Success(
//                    listOf(
//                        mockk(relaxed = true),
//                        mockk(relaxed = true),
//                        mockk(relaxed = true)
//
//                    )
//                )
//            )
//        }
//
//        // category
//        coEvery { repository.getCategories() } returns flow {
//            emit(
//                ResultWrapper.Success(
//                    listOf(
//                        mockk(relaxed = true),
//                        mockk(relaxed = true),
//                        mockk(relaxed = true),
//                        mockk(relaxed = true)
//
//                    )
//                )
//            )
//        }
//
//        viewModel = spyk(
//            HomeViewModel(
//                repository = ProductRepository
//            )
//        )
//    }
//
//    @Test
//    fun getCategories() {
//        val result = viewModel.categories.getOrAwaitValue()
//        assertEquals(result.payload?.size, 4)
//        coEvery { repository.getCategories() }
//    }
// }
