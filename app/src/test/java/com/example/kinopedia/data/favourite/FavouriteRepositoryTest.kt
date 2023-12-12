package com.example.kinopedia.data.favourite

import com.example.kinopedia.BaseTest
import com.example.kinopedia.data.dao.FavouriteDao
import com.example.kinopedia.data.favourite.dto.FavouriteEntity
import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.any

@OptIn(ExperimentalCoroutinesApi::class)
class FavouriteRepositoryTest : BaseTest() {

    @MockK
    lateinit var favouriteDao: FavouriteDao

    private lateinit var repo: FavouriteRepositoryImpl

    private lateinit var flow: Flow<List<FavouriteEntity>>

    override fun setUp() {
        super.setUp()
        flow = flowOf<List<FavouriteEntity>>()
        coEvery { favouriteDao.getLatestItem() }.returns(flow)
        repo = FavouriteRepositoryImpl(favouriteDao)
    }

    @Test
    fun `repo latestFilms returns correct flow`() {
        every { favouriteDao.getLatestItem() }.returns(flow)

        val result = repo.latestFilms

        assertThat(result).isEqualTo(flow)
    }

    @Test
    fun `repo insert calls favouriteDao insert`() = runBlocking {
        coEvery { favouriteDao.insert(any()) } just Runs
        repo.insert(any())
        coVerify { favouriteDao.insert(any()) }
    }

    @Test
    fun `repo checkId calls favouriteDao checkId`() = runBlocking {

        coEvery { favouriteDao.checkId(1) }.returns(flowOf(1))

        val result = repo.checkId(1)

        assertThat(result).isEqualTo(1)
        coVerify { favouriteDao.checkId(1) }
    }

    @Test
    fun `repo delete calls favouriteDao deleteById`() = runBlocking {

        coEvery { favouriteDao.deleteById(any()) } just Runs

        repo.delete(any())

        coVerify { favouriteDao.deleteById(any()) }
    }
}