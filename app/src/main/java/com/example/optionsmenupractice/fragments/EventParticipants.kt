package com.example.optionsmenupractice.fragments

import adapters.ParticipantsRecyclerAdapter
import models.ParticipantModel
import requests.RequestAllEvents
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
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.optionsmenupractice.R
import saved_instance_data.ParticipantsViewModel
import saved_instance_data.SharedViewModel


class EventParticipants : Fragment() {

    private lateinit var participantList : ArrayList<ParticipantModel>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ParticipantsRecyclerAdapter
    private val requestAllEvents: RequestAllEvents = RequestAllEvents()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val participantsViewModel: ParticipantsViewModel by activityViewModels()
    private var userId : Int = 0
    private var eventid : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_event_participants, container, false)
        // Inflate the layout for this fragment
        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        participantList = ArrayList()

        val bundle = arguments

         eventid = bundle?.getInt("event_id")!!

        initalizeEmptyAdapter()

        storeDataInArray()


        return view
    }

    private fun storeDataInArray() {
        val url = "http://10.0.2.2:8888/MyParticipants.php?eventid=$eventid"
        val requestQueue = Volley.newRequestQueue(requireContext())

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                try {
                    participantList.clear()
                    for (i in 0 until response.length()) {
                        val jsonObject = response.getJSONObject(i)
                        val name = jsonObject.getString("participant_name")
                        val email = jsonObject.getString("participant_email")
                        val quantity = jsonObject.getString("ticket_quantity")
                        val phoneno = jsonObject.getInt("participant_phone")

                        val event = ParticipantModel().apply {
                            setName(name)
                            setEmail(email)
                            setQuantity(quantity.toInt())
                            setPhoneNo(phoneno)
                        }
                        participantList.add(event)
                    }
                    participantsViewModel.participantsList.value = participantList

                    if (!::adapter.isInitialized) {
                        adapter = ParticipantsRecyclerAdapter(participantList)
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

    private fun initalizeEmptyAdapter() {
        // Initialize the adapter with an empty list initially
        adapter = ParticipantsRecyclerAdapter(participantList)
        recyclerView.adapter = adapter

        // Observe the userId
        sharedViewModel.userId.observe(viewLifecycleOwner, Observer { userId ->
            this.userId = userId
            storeDataInArray()
        })

        // Observe changes in the event list
        participantsViewModel.participantsList.observe(viewLifecycleOwner, Observer { events ->
            adapter.updateData(events)
        })

    }


}