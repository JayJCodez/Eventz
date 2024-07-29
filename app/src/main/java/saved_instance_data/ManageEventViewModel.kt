package saved_instance_data

import models.MyEventsModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ManageEventViewModel : ViewModel() {

    val myEventsList = MutableLiveData<ArrayList<MyEventsModel>>()

}