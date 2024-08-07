package adapters

import models.GeneralEventModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.optionsmenupractice.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeRecyclerAdapter(
    private var generalList: ArrayList<GeneralEventModel>,
    private val onItemClick: ((GeneralEventModel) -> Unit),
    private val onSaveClick: ((GeneralEventModel) -> Unit),
    private val onLikeClicked: ((GeneralEventModel) -> Unit)

) : RecyclerView.Adapter<HomeRecyclerAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.modern_row, parent, false)
        return MyViewHolder(itemView, onItemClick)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val context = holder.itemView.context
        val currentItem = generalList[position]

        holder.eventname.text = currentItem.getEventName()
//        holder.eventid.text = currentItem.getEventId().toString()
        holder.eventtype.text = currentItem.getEventType()
        holder.eventinfo.text = currentItem.getEventDescription()
        holder.bind(currentItem) // Bind data to the ViewHolder
        holder.like_event.setOnClickListener{

            onLikeClicked(currentItem)

        }
//        val imageUriString = currentItem.getImageURI()
//        val imageUri = Uri.parse(imageUriString)

//        if (context != null) {
//            Glide.with(context)
//                .load("https://cdn.pixabay.com/photo/2013/02/01/18/14/url-77169_1280.jpg")
//                .apply(RequestOptions().override(100, 150))
//                .error(R.drawable.baseline_error_24)
//                .placeholder(R.drawable.baseline_downloading_24)
//                .into(holder.imgView)
//        }

        holder.saveforLater.setOnClickListener{

            onSaveClick(currentItem)

        }
    }

    fun updateData(newEvents: ArrayList<GeneralEventModel>) {
        generalList = newEvents
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return generalList.size
    }



    class MyViewHolder(itemView: View, onItemClick: (GeneralEventModel) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private lateinit var currentItem: GeneralEventModel
//        val eventid: TextView = itemView.findViewById(R.id.eventid)
        val eventname: TextView = itemView.findViewById(R.id.eventname)
        val eventtype: TextView = itemView.findViewById(R.id.eventtype)
//        public val imgView: ImageView = itemView.findViewById(R.id.imageView)
        val eventinfo: TextView = itemView.findViewById(R.id.event_info)
        val saveforLater: Button = itemView.findViewById(R.id.save)
        val like_count : TextView = itemView.findViewById(R.id.like_count)
        val like_event : FloatingActionButton = itemView.findViewById(R.id.fab1)

        init {
            itemView.setOnClickListener {
                onItemClick.invoke(currentItem)
            }
        }

        fun bind(item: GeneralEventModel) {
            currentItem = item
//            eventid.text = item.getEventId().toString()
            eventname.text = item.getEventName()
            eventtype.text = item.getEventType()
            val count = "Likes:  ${item.getLikeCount().toString()}"
            like_count.text = count
        }
    }
}
