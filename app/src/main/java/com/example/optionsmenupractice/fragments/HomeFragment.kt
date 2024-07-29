package com.example.optionsmenupractice.fragments

import adapters.HomeRecyclerAdapter
import models.GeneralEventModel
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
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.optionsmenupractice.R
import saved_instance_data.HomeViewModel
import saved_instance_data.SharedViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var generalEventsList: ArrayList<GeneralEventModel>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HomeRecyclerAdapter
    private val requestAllEvents: RequestAllEvents = RequestAllEvents()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()
    private var userId : Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home2, container, false)
        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        generalEventsList = ArrayList()

        initalizeEmptyAdapter()

        storeDataInArray()


        return view
    }

    private fun initalizeEmptyAdapter(){
        // Initialize the adapter with an empty list initially
        adapter = HomeRecyclerAdapter(generalEventsList, { clickedItem ->
            val eventId = clickedItem.getEventId()
            val eventname = clickedItem.getEventName()
            val eventtype = clickedItem.getEventType()
            val eventstart = clickedItem.getEventStartTime()
            val eventfinish = clickedItem.getEventFinishTime()
            val eventdescription = clickedItem.getEventDescription()
            val likecount = clickedItem.getLikeCount()

            val fragmentB : Fragment = EventDetails()

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
            if (likecount != null) {
                bundle.putInt("like_count", likecount)
            }

            displayEventDetailsFragment(fragmentB, bundle)

            Toast.makeText(
                context,
                "Clicked: ${clickedItem.getEventName()}",
                Toast.LENGTH_SHORT
            ).show()
        },
            { onSaveClick ->
                onSaveClick.getEventId()?.let { saveEvent(it) }
            }, { onLikeClicked ->
                onLikeClicked.getEventId()?.let { likeEvent(it) }
            })
        recyclerView.adapter = adapter

        // Observe the userId
        sharedViewModel.userId.observe(viewLifecycleOwner, Observer { userId ->
            this.userId = userId
            storeDataInArray()
        })

        // Observe changes in the event list
        homeViewModel.generalEventsList.observe(viewLifecycleOwner, Observer { events ->
            adapter.updateData(events)
        })

    }

    override fun onResume() {
        super.onResume()
//        fetchEvents()
    }

//    private fun fetchEvents() {
//        requestAllEvents.fetchEvents(
//            onSuccess = { events ->
//                storeDataInArray(events)
//            },
//            onFailure = { errorMessage ->
////                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
//            }
//        )
//    }

    private fun storeDataInArray() {
        val url = "http://10.0.2.2:8888/all_events.php"
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
                        val eventDate = jsonObject.getString("event_date")
                        val likeCount = jsonObject.getInt("like_count")

                        println("Event Date: $eventDate")

                        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        val outputFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())


                        var formattedDateString = ""

                        try {

                            if (!eventDate.isNullOrEmpty()) {
                                val date: Date = inputFormat.parse(eventDate)!!
                                formattedDateString = outputFormat.format(date)
                            }
                        } catch (e: Exception) {
                            // Handle parsing exceptions
                            e.printStackTrace()
                            Toast.makeText(requireContext(), "Error parsing date: ${e.message}", Toast.LENGTH_SHORT).show()
                        }

                        // Create and add the event object
                        val event = GeneralEventModel().apply {
                            setEventId(eventID.toInt())
                            setEventName(eventName)
                            setEventType(eventType)
                            setEventStartTime(eventStart)
                            setEventFinishTime(eventFinish)
                            setEventDescription(eventInfo)
                            setLikeCount(likeCount)
                            setEventDate(formattedDateString)
                        }
                        generalEventsList.add(event)
                    }
                    homeViewModel.generalEventsList.value = generalEventsList

                    // Initialize adapter if not already initialized
                    if (!::adapter.isInitialized) {
                        adapter = HomeRecyclerAdapter(generalEventsList, { clickedItem ->
                            val eventId = clickedItem.getEventId()
                            val eventname = clickedItem.getEventName()
                            val eventtype = clickedItem.getEventType()
                            val eventstart = clickedItem.getEventStartTime()
                            val eventfinish = clickedItem.getEventFinishTime()
                            val eventdescription = clickedItem.getEventDescription()
                            val likecount = clickedItem.getLikeCount()
                            val eventdate = clickedItem.getEventDate()

                            val fragmentB: Fragment = EventDetails()

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
                            bundle.putString("event_date", eventdate)
                            if (likecount != null) {
                                bundle.putInt("like_count", likecount)
                            }

                            displayEventDetailsFragment(fragmentB, bundle)

                            Toast.makeText(
                                context,
                                "Clicked: ${clickedItem.getEventName()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                            { onSaveClick ->
                                onSaveClick.getEventId()?.let { saveEvent(it) }
                            }, { onLikeClicked ->
                                onLikeClicked.getEventId()?.let { likeEvent(it) }
                            })
                        recyclerView.adapter = adapter
                    } else {
                        adapter.notifyDataSetChanged()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(
                        requireContext(),
                        "Error parsing JSON: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            { error ->
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(jsonArrayRequest)
    }


    private fun saveEvent(eventId : Int){
        val url = "http://10.0.2.2:8888/SaveItem.php"
        val requestQueue = Volley.newRequestQueue(requireContext())
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                // Handle the response
                Toast.makeText(context, response, Toast.LENGTH_LONG).show()
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

    private fun likeEvent(eventId : Int){
        val url = "http://10.0.2.2:8888/LikeEvent.php"
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

    private fun displayEventDetailsFragment(fragment: Fragment, bundle: Bundle) {
        fragment.arguments = bundle
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}