package com.example.optionsmenupractice.fragments

import ManageEventsAdapter
import models.MyEventsModel
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import saved_instance_data.ManageEventViewModel
import saved_instance_data.SharedViewModel

class ManageEventsTest {

    // Mock ViewModel instances
    @Mock
    private lateinit var sharedViewModel: SharedViewModel

    @Mock
    private lateinit var manageEventViewModel: ManageEventViewModel

    // Mock ErrorHandler
    @Mock
    private lateinit var errorHandler: ManageEvents.ErrorHandler

    // Initialize your Fragment
    private lateinit var fragment: ManageEvents

    // Mock adapter
    @Mock
    private lateinit var adapter: ManageEventsAdapter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        // Initialize your Fragment
        fragment = ManageEvents()

        // Set the error handler
        fragment.setErrorHandler(errorHandler)

        // Mock adapter
        fragment.adapter = adapter
    }

    @Test
    fun testFragmentNavigation() {
        // Test fragment navigation behavior
        val mockClickedItem = MyEventsModel()

        // Trigger onItemClick callback
        fragment.adapter.onItemClick(mockClickedItem)

        // Verify if displayEventDetailsFragment is called
        verify(fragment).displayEventDetailsFragment(any(), any())
    }
}
