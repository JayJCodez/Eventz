package com.example.optionsmenupractice.fragments

import adapters.MyEventsAdapter
import models.MyEventsModel
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
import saved_instance_data.MyEventViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [MyEvents.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyEvents : Fragment() {

    private var userid : Int = 0
    private lateinit var myEventsList: ArrayList<MyEventsModel>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyEventsAdapter
    private val myEventViewModel : MyEventViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_my_events, container, false)


        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        myEventsList = ArrayList()
        initializeEmptyAdapter()

        val bundle = arguments
        if (bundle != null) {
            userid = bundle.getInt("user_id") // Change getString to getInt
            storeDataInArray()
        }


        // Observe changes in the event list
        myEventViewModel.myEventsList.observe(viewLifecycleOwner, Observer { events ->
            adapter.updateData(events)
        })




        return view
    }

    private fun initializeEmptyAdapter(){

        adapter = MyEventsAdapter(myEventsList,
            // onItemClick function
            { clickedItem ->
                // Handle item click here
                val eventId = clickedItem.getEventId()
                val eventname = clickedItem.getEventName()
                val eventtype = clickedItem.getEventType()
                val eventstart = clickedItem.getEventStartTime()
                val eventfinish = clickedItem.getEventFinishTime()
                val eventdescription = clickedItem.getEventDescription()

                val fragmentB = EventDetails()

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
            },
            // onEditClick function
            { editClicked ->
                // Handle edit click here
                // For example, you can open an edit fragment or perform any other action
                // Handle edit click here
                // For example, you can open an edit fragment or perform any other action
                val eventId = editClicked.getEventId()
                val eventname = editClicked.getEventName()
                val eventtype = editClicked.getEventType()
                val eventstart = editClicked.getEventStartTime()
                val eventfinish = editClicked.getEventFinishTime()
                val eventdescription = editClicked.getEventDescription()

                val fragmentB = EventDetails()

                val bundle = Bundle()
                bundle.putString("event_id", eventId.toString())
                bundle.putString("event_name", eventname)
                bundle.putString("event_type", eventtype)
                bundle.putString("event_start", eventstart)
                bundle.putString("event_finish", eventfinish)
                bundle.putString("event_info", eventdescription)

                Toast.makeText(
                    context,
                    "Clicked: ${editClicked.getEventName()}",
                    Toast.LENGTH_SHORT
                ).show()

                displayEventDetailsFragment(EditMyEventFragment(), bundle)
            },
            { viewClicked ->

                // Handle item click here
                val eventId = viewClicked.getEventId()
                val eventname = viewClicked.getEventName()
                val eventtype = viewClicked.getEventType()
                val eventstart = viewClicked.getEventStartTime()
                val eventfinish = viewClicked.getEventFinishTime()
                val eventdescription = viewClicked.getEventDescription()

                val fragmentB = EventDetails()

                val bundle = Bundle()
                if (eventId != null) {
                    bundle.putInt("event_id", eventId)
                }
                bundle.putString("event_name", eventname)
                bundle.putString("event_type", eventtype)
                bundle.putString("event_start", eventstart)
                bundle.putString("event_finish", eventfinish)
                bundle.putString("event_info", eventdescription)

                displayEventDetailsFragment(fragmentB, bundle)


            }
        )

        recyclerView.adapter = adapter



    }

    private fun storeDataInArray() {
        val url = "http://10.0.2.2:8888/MyEvents.php?user_id=$userid"
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

                    if (!::adapter.isInitialized) {

//                        adapter = MyEventsAdapter(myEventsList)

                        adapter = MyEventsAdapter(myEventsList,
                            // onItemClick function
                            { clickedItem ->
                                // Handle item click here
                                val eventId = clickedItem.getEventId()
                                val eventname = clickedItem.getEventName()
                                val eventtype = clickedItem.getEventType()
                                val eventstart = clickedItem.getEventStartTime()
                                val eventfinish = clickedItem.getEventFinishTime()
                                val eventdescription = clickedItem.getEventDescription()

                                val fragmentB = EventDetails()

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
                            },
                            // onEditClick function
                            { editClicked ->
                                // Handle edit click here
                                // For example, you can open an edit fragment or perform any other action
                                // Handle edit click here
                                // For example, you can open an edit fragment or perform any other action
                                val eventId = editClicked.getEventId()
                                val eventname = editClicked.getEventName()
                                val eventtype = editClicked.getEventType()
                                val eventstart = editClicked.getEventStartTime()
                                val eventfinish = editClicked.getEventFinishTime()
                                val eventdescription = editClicked.getEventDescription()

                                val fragmentB = EventDetails()

                                val bundle = Bundle()
                                bundle.putString("event_id", eventId.toString())
                                bundle.putString("event_name", eventname)
                                bundle.putString("event_type", eventtype)
                                bundle.putString("event_start", eventstart)
                                bundle.putString("event_finish", eventfinish)
                                bundle.putString("event_info", eventdescription)

                                Toast.makeText(
                                    context,
                                    "Clicked: ${editClicked.getEventName()}",
                                    Toast.LENGTH_SHORT
                                ).show()

                                displayEventDetailsFragment(EditMyEventFragment(), bundle)
                            },
                            { viewClicked ->

                                // Handle item click here
                                val eventId = viewClicked.getEventId()
                                val eventname = viewClicked.getEventName()
                                val eventtype = viewClicked.getEventType()
                                val eventstart = viewClicked.getEventStartTime()
                                val eventfinish = viewClicked.getEventFinishTime()
                                val eventdescription = viewClicked.getEventDescription()

                                val fragmentB = EventDetails()

                                val bundle = Bundle()
                                if (eventId != null) {
                                    bundle.putInt("event_id", eventId)
                                }
                                bundle.putString("event_name", eventname)
                                bundle.putString("event_type", eventtype)
                                bundle.putString("event_start", eventstart)
                                bundle.putString("event_finish", eventfinish)
                                bundle.putString("event_info", eventdescription)

                                displayEventDetailsFragment(fragmentB, bundle)


                            }
                        )

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
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        )

        requestQueue.add(jsonArrayRequest)
    }

    private fun displayEventDetailsFragment(fragment: Fragment, bundle: Bundle) {
        fragment.arguments = bundle
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun displayFragment(fragment: Fragment) {
//        fragment.arguments = bundle
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

}