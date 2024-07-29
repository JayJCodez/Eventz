package com.example.optionsmenupractice

import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import fragments.NewEvent
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewEventTest {
    private lateinit var scenario: FragmentScenario<NewEvent>

    @Before
    fun setup() {
        scenario = FragmentScenario.launchInContainer(NewEvent::class.java)
    }

    @Test
    fun testEventCreation() {
        scenario.onFragment { fragment ->
            // Set up UI elements
            val eventname = fragment.view!!.findViewById<EditText>(R.id.editTextText)
            val spinner = fragment.view!!.findViewById<Spinner>(R.id.event_type)
            val event_date = fragment.view!!.findViewById<TextView>(R.id.date_text)
            val setStart = fragment.view!!.findViewById<TextView>(R.id.set_start_time)
            val setEnd = fragment.view!!.findViewById<TextView>(R.id.set_end_time)
            val event_info = fragment.view!!.findViewById<EditText>(R.id.eventinformation)
            val createEventButton = fragment.view!!.findViewById<Button>(R.id.createneweventBTN)

            // Populate fields with test data
            eventname.setText("Test Event")
            event_date.text = "2024-05-17"
            setStart.text = "10:00"
            setEnd.text = "12:00"
            event_info.setText("This is a test event")

            // Set spinner value
            spinner.setSelection(0) // Assume the first item is the expected value

            // Simulate button click
            createEventButton.performClick()

            // Verify that the values were correctly set before the task execution
            Assert.assertEquals("Test Event", eventname.text.toString())
            Assert.assertEquals("2024-05-17", event_date.text.toString())
            Assert.assertEquals("10:00", setStart.text.toString())
            Assert.assertEquals("12:00", setEnd.text.toString())
            Assert.assertEquals("This is a test event", event_info.text.toString())

            // Optionally, verify the spinner selection
            Assert.assertEquals("Travel", spinner.selectedItem.toString())
        }
    }
}
