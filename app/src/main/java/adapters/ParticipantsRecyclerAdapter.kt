package adapters

import models.ParticipantModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.optionsmenupractice.R

class ParticipantsRecyclerAdapter(
    private var participantList: ArrayList<ParticipantModel>
) : RecyclerView.Adapter<ParticipantsRecyclerAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ParticipantsRecyclerAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.participants_row, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ParticipantsRecyclerAdapter.MyViewHolder, position: Int) {
        val currentItem = participantList[position]
        holder.bind(currentItem)
    }

    fun updateData(myParticipants: ArrayList<ParticipantModel>) {
        participantList = myParticipants
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
      return participantList.size
    }

    inner class MyViewHolder(itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private lateinit var currentItem: ParticipantModel
        val username: TextView = itemView.findViewById(R.id.username)
        val ticketQuantity: TextView = itemView.findViewById(R.id.ticket_quantity)
        val userEmail : TextView = itemView.findViewById(R.id.email)
        val phoneNo : TextView = itemView.findViewById(R.id.phoneno)



        fun bind(item: ParticipantModel) {
            currentItem = item
            username.text = item.getName()
            ticketQuantity.text = "${item.getQuantity()}"
            val emailtxt = "Email: ${item.getEmail()}"
            userEmail.text = emailtxt
            val phonetxt = "Phone Number: ${item.getPhoneNo()}"
            phoneNo.text = phonetxt
        }
    }

}