package com.example.optionsmenupractice.fragments


import ManageEventsAdapter
import models.MyEventsModel
import requests.RequestAllEvents
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.optionsmenupractice.R
import saved_instance_data.ManageEventViewModel
import saved_instance_data.SharedViewModel

class ManageEvents : Fragment() {

    private lateinit var myEventsList: ArrayList<MyEventsModel>
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ManageEventsAdapter
    private val requestAllEvents: RequestAllEvents = RequestAllEvents()
    val sharedViewModel: SharedViewModel by activityViewModels()
    val manageEventViewModel: ManageEventViewModel by activityViewModels()
    private var userId : Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_manage_events, container, false)
        // Inflate the layout for this fragment

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        myEventsList = ArrayList()

        val bundle = arguments

        userId = bundle?.getInt("user_id")!!

        initalizeEmptyAdapter()

        storeDataInArray()

        return view
    }


    private lateinit var errorHandler: ErrorHandler

    interface ErrorHandler {
        fun showError(message: String)
    }

    fun setErrorHandler(handler: ErrorHandler) {
        errorHandler = handler
    }

    fun showError(error: String) : String{
      return error
    }

    fun storeDataInArray() {
        val url = "http://10.0.2.2:8888/MyEvents.php?user_id=$userId"
        val requestQueue = Volley.newRequestQueue(requireContext())

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                try {
                    myEventsList.clear()
                    for (i in 0 until response.length()) {
                        val jsonObject = response.getJSONObject(i)
                        val event_id = jsonObject.getString("event_id")
                        val eventName = jsonObject.getString("event_name")
                        val eventType = jsonObject.getString("event_type")
                        val eventStart = jsonObject.getString("event_start")
                        val eventFinish = jsonObject.getString("event_finish")
                        val eventInfo = jsonObject.getString("event_info")

                        val event = MyEventsModel().apply {
                            setEventId(event_id.toInt())
                            setEventName(eventName)
                            setEventType(eventType)
                            setEventStartTime(eventStart)
                            setEventFinishTime(eventFinish)
                            setEventDescription(eventInfo)
                        }
                        myEventsList.add(event)
                    }
                    manageEventViewModel.myEventsList.value = myEventsList

                    if (!::adapter.isInitialized) {

//                        adapter = MyEventsAdapter(myEventsList)

                        adapter = ManageEventsAdapter(myEventsList) { clickedItem ->


                            // Handle item click here
                            val eventId = clickedItem.getEventId()
                            val eventname = clickedItem.getEventName()
                            val eventtype = clickedItem.getEventType()
                            val eventstart = clickedItem.getEventStartTime()
                            val eventfinish = clickedItem.getEventFinishTime()
                            val eventdescription = clickedItem.getEventDescription()

                            val fragmentB = EventParticipants()

                            val bundle = Bundle()

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
                    showError("Error parsing JSON: ${e.message}")
                }
            },
            { error ->
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        )

        requestQueue.add(jsonArrayRequest)
    }

    private fun initalizeEmptyAdapter() {
        // Initialize the adapter with an empty list initially
        adapter = ManageEventsAdapter(myEventsList) { clickedItem ->
            val eventId = clickedItem.getEventId()
            val eventname = clickedItem.getEventName()
            val eventtype = clickedItem.getEventType()
            val eventstart = clickedItem.getEventStartTime()
            val eventfinish = clickedItem.getEventFinishTime()
            val eventdescription = clickedItem.getEventDescription()


            val fragmentB: Fragment = EventParticipants()

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
        }
        recyclerView.adapter = adapter

        // Observe the userId
        sharedViewModel.userId.observe(viewLifecycleOwner, Observer { userId ->
            this.userId = userId
            storeDataInArray()
        })

        // Observe changes in the event list
        manageEventViewModel.myEventsList.observe(viewLifecycleOwner, Observer { events ->
            adapter.updateData(events)
        })

    }

    fun displayEventDetailsFragment(fragmentB: Fragment, bundle: Bundle) {
        fragmentB.arguments = bundle
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragmentB)
            .addToBackStack(null)
            .commit()

    }

}


