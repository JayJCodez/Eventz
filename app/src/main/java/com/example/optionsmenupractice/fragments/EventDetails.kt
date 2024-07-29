package com.example.optionsmenupractice.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.optionsmenupractice.R


class EventDetails : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_details, container, false)


    }

    override fun onStart() {

        // Retrieve the integer from the arguments bundle
        val eventId = arguments?.getInt("event_id")// Replace -1 with a default value if eventId is not found
        val eventname = arguments?.getString("event_name")
        val eventtype = arguments?.getString("event_type")
        val start = arguments?.getString("event_start")
        val end = arguments?.getString("event_finish")
        val info = arguments?.getString("event_info")
        val userid = arguments?.getInt("user_id")
        val eventdate = arguments?.getString("event_date")

        val event_id : TextView = requireView().findViewById<TextView>(R.id.eventid)
        val event_name : TextView = requireView().findViewById<TextView>(R.id.event_name)
        val event_type : TextView = requireView().findViewById<TextView>(R.id.eventtype)
        val event_start : TextView = requireView().findViewById<TextView>(R.id.start_time)
        val event_finish : TextView = requireView().findViewById<TextView>(R.id.finish_time)
        val event_info : TextView = requireView().findViewById<TextView>(R.id.event_info)
        val getTickets : Button = requireView().findViewById<Button>(R.id.gettickets)
        val date : TextView = requireView().findViewById<TextView>(R.id.date)



        event_id.text = eventId.toString()
        event_name.text = eventname.toString()
        event_type.text = eventtype.toString()
        event_start.text = start.toString()
        event_finish.text = end.toString()
        event_info.text = info.toString()
        date.text = eventdate.toString()

        val ticket_bundle = Bundle()
        if (eventId != null) {
            ticket_bundle.putInt("event_id", eventId)

            if (userid != null) {
                ticket_bundle.putInt("user_id", userid)
            }
            ticket_bundle.putString("event_name", eventname)
            ticket_bundle.putString("event_type", eventtype)


        }

        getTickets.setOnClickListener{

            openFragment(GetTickets(), ticket_bundle)

        }


        super.onStart()
        // Called once the fragment is visible.
    }

    private fun openFragment(fragment: Fragment, bundle : Bundle) {


        fragment.arguments = bundle
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack("null")
            .commit()
    }

    override fun onResume() {
        super.onResume()
        // Called when the fragment gains focus and is visible.
    }

    override fun onPause() {
        super.onPause()
        // Called when the fragment is no longer resumed.
    }

    override fun onStop() {
        super.onStop()
        // Called when the fragment is no longer visible.
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Called when the view hierarchy associated with the fragment is being removed.
    }

    override fun onDestroy() {
        super.onDestroy()
        // Called when the fragment is no longer in use.
    }

    override fun onDetach() {
        super.onDetach()
        // Called when the fragment is no longer attached to its activity.
    }


}