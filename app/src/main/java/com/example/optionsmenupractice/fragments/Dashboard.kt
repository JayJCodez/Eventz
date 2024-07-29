package com.example.optionsmenupractice.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.optionsmenupractice.R
import saved_instance_data.SharedViewModel

class Dashboard : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val search : CardView = view.findViewById(R.id.search)
        val popular : CardView = view.findViewById(R.id.popular)
        val profile : CardView = view.findViewById(R.id.profile)
        val tickets : CardView = view.findViewById(R.id.tickets)
        val manageevent : CardView = view.findViewById(R.id.manageevent)
        val myevent : CardView = view.findViewById(R.id.myevents)

        val bundle = arguments

        val user_id : Int? = bundle?.getString("user_id")?.toInt()


        user_id?.let {
            sharedViewModel.userId.value = it
        }


        val bundle_forward = Bundle()

        sharedViewModel.userId.value?.let {
            bundle_forward.putInt("user_id", it)
        }

        if (user_id != null) {
            bundle_forward.putInt("user_id", user_id)
        }

        search.setOnClickListener{


            dashboard_navigation(HomeFragment(), bundle_forward)

        }

        popular.setOnClickListener{

            dashboard_navigation(PopularFragment(), bundle_forward)

        }


        profile.setOnClickListener{

            dashboard_navigation(ProfileFragment(), bundle_forward)

        }


        tickets.setOnClickListener{

            dashboard_navigation(Tickets(), bundle_forward)

        }

        manageevent.setOnClickListener{


            dashboard_navigation(ManageEvents(), bundle_forward)

        }

        myevent.setOnClickListener{

            dashboard_navigation(MyEvents(), bundle_forward)

        }



        // Inflate the layout for this fragment
        return view
    }


    fun dashboard_navigation(fragment : Fragment, bundle : Bundle){

        fragment.arguments = bundle
        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, fragment)
            ?.addToBackStack("null")
            ?.commit()

    }

}