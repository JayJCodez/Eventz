package com.example.optionsmenupractice.main.activities

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.optionsmenupractice.fragments.HomeFragment
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeActivityUnitTest {

    @Mock
    private lateinit var mockActivity: AppCompatActivity

    private lateinit var homeActivity: HomeActivity

    @Before
    fun setup() {
        homeActivity = HomeActivity()
    }

    @Test
    fun testOpenFragment() {
        // Given
        val expectedFragment = HomeFragment()

        // When
        homeActivity.openFragment(expectedFragment)

        // Then
        val fragment = homeActivity.supportFragmentManager.fragments.first() as Fragment
        assertNotNull(fragment)
        assert(fragment is HomeFragment)
    }

    // Add more unit tests for other methods as needed
}
