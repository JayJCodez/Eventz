package com.example.optionsmenupractice.fragments

import adapters.SavedEventsAdapter
import models.GeneralEventModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.optionsmenupractice.R
import saved_instance_data.SavedEventViewModel
import saved_instance_data.SharedViewModel

class SavedEvents : Fragment() {

    private lateinit var generalEventsList: ArrayList<GeneralEventModel>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SavedEventsAdapter
    private var userId: Int = 0
    private val savedEventViewModel : SavedEventViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_saved_events, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        generalEventsList = ArrayList()
        initializeEmptyAdapter()

        // Get the user ID from sharedViewModel
        sharedViewModel.userId.observe(viewLifecycleOwner, Observer { userId ->
            this.userId = userId
            storeDataInArray()
        })

        // Observe changes in the event list
        savedEventViewModel.generalEventsList.observe(viewLifecycleOwner, Observer { events ->
            adapter.updateData(events)
        })

        return view
    }

    private fun initializeEmptyAdapter() {
        adapter = SavedEventsAdapter(generalEventsList, { clickedItem ->
            // Handle item click here
            val eventId = clickedItem.getEventId()
            val eventname = clickedItem.getEventName()
            val eventtype = clickedItem.getEventType()
            val eventstart = clickedItem.getEventStartTime()
            val eventfinish = clickedItem.getEventFinishTime()
            val eventdescription = clickedItem.getEventDescription()

            val fragmentB = EventDetails()

            val bundle = Bundle()
            if (eventId != null) {
                bundle.putInt("event_id", eventId.toInt())
            }

            bundle.putInt("user_id", userId)
            bundle.putString("event_name", eventname)
            bundle.putString("event_type", eventtype)
            bundle.putString("event_start", eventstart)
            bundle.putString("event_finish", eventfinish)
            bundle.putString("event_info", eventdescription)

            displayEventDetailsFragment(fragmentB, bundle)

            Toast.makeText(
                context,
                "Clicked: ${clickedItem.getEventName()}",
                Toast.LENGTH_SHORT
            ).show()
        }) { onRemoveClick ->
            removeEvent(onRemoveClick.getEventId()!!)
            Toast.makeText(
                requireContext(),
                "Removed ${onRemoveClick.getEventName()}",
                Toast.LENGTH_SHORT
            ).show()
        }
        recyclerView.adapter = adapter
    }

    private fun storeDataInArray() {

        val url = "http://10.0.2.2:8888/SavedEvents.php?userid=$userId"
        val requestQueue = Volley.newRequestQueue(requireContext())

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                try {
                    generalEventsList.clear()

                        for (i in 0 until response.length()) {
                            val jsonObject = response.getJSONObject(i)
                            val eventID = jsonObject.getString("event_id")
                            val eventName = jsonObject.getString("event_name")
                            val eventType = jsonObject.getString("event_type")
                            val eventStart = jsonObject.getString("event_start")
                            val eventFinish = jsonObject.getString("event_finish")
                            val eventInfo = jsonObject.getString("event_info")

                            val event = GeneralEventModel().apply {
                                setEventId(eventID.toInt())
                                setEventName(eventName)
                                setEventType(eventType)
                                setEventStartTime(eventStart)
                                setEventFinishTime(eventFinish)
                                setEventDescription(eventInfo)
                            }
                            generalEventsList.add(event)
                        }

                    savedEventViewModel.generalEventsList.value = generalEventsList


                    if (!::adapter.isInitialized) {
                        adapter = SavedEventsAdapter(generalEventsList, { clickedItem ->
                            // Handle item click here
                            val eventId = clickedItem.getEventId()
                            val eventname = clickedItem.getEventName()
                            val eventtype = clickedItem.getEventType()
                            val eventstart = clickedItem.getEventStartTime()
                            val eventfinish = clickedItem.getEventFinishTime()
                            val eventdescription = clickedItem.getEventDescription()

                            val fragmentB = EventDetails()

                            val bundle = Bundle()
                            if (eventId != null) {
                                bundle.putInt("event_id", eventId.toInt())
                            }

                            bundle.putInt("user_id", userId)
                            bundle.putString("event_name", eventname)
                            bundle.putString("event_type", eventtype)
                            bundle.putString("event_start", eventstart)
                            bundle.putString("event_finish", eventfinish)
                            bundle.putString("event_info", eventdescription)

                            displayEventDetailsFragment(fragmentB, bundle)

                            Toast.makeText(
                                context,
                                "Clicked: ${clickedItem.getEventName()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }) { onRemoveClick ->
                            removeEvent(onRemoveClick.getEventId()!!)
                            Toast.makeText(
                                requireContext(),
                                "Removed ${onRemoveClick.getEventName()}",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                        recyclerView.adapter = adapter
                    } else {
                        adapter.notifyDataSetChanged()
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        requireContext(),
                        "Error parsing JSON: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            { error ->
                // No events found, create an empty adapter
                generalEventsList.clear()
                adapter = SavedEventsAdapter(generalEventsList,
                    { clickedItem -> },
                    { onRemoveClick -> }
                )
                recyclerView.adapter = adapter

                Toast.makeText(requireContext(), "Error: No saved events found", Toast.LENGTH_SHORT)
                    .show()
            }
        )

        requestQueue.add(jsonArrayRequest)
    }

    private fun removeEvent(eventId : Int){
        val url = "http://10.0.2.2:8888/UnsaveItem.php"
        val requestQueue = Volley.newRequestQueue(requireContext())
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                // Handle the response
                Toast.makeText(context, response, Toast.LENGTH_LONG).show()
                storeDataInArray()

            },
            Response.ErrorListener { error ->
                // Handle the error
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                return hashMapOf("userid" to userId.toString(), "eventid" to eventId.toString())
            }
        }

        requestQueue.add(stringRequest)

    }

    private fun displayEventDetailsFragment(fragmentB: EventDetails, bundle: Bundle) {
        fragmentB.arguments = bundle
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragmentB)
            .addToBackStack(null)
            .commit()
    }
}
