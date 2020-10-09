package com.fatdino.blabrrr.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import com.fatdino.blabrrr.configs.Configs
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainActivityViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var lifecycleOwner: LifecycleOwner

    lateinit var viewModel: MainActivityViewModel

    @Before
    fun setup() {
        viewModel = MainActivityViewModel()
    }

    @Test
    fun start() {
        viewModel.start(lifecycleOwner)
        assertEquals(null, viewModel.callbackMoveAppToBg.value)
    }

    @Test
    fun backPressed_showToast() {
        viewModel.backPressed()
        assertEquals(false, viewModel.callbackMoveAppToBg.value)
    }

    @Test
    fun backPressed_shouldGoToBg() {
        viewModel.backPressed()
        assertEquals(false, viewModel.callbackMoveAppToBg.value)

        Thread.sleep(Configs.DIFF_TIME_BACK_PRESS_TO_BG.toLong() - 500)

        viewModel.backPressed()
        assertEquals(true, viewModel.callbackMoveAppToBg.value)
    }
}