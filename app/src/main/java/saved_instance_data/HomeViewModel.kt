package saved_instance_data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import models.GeneralEventModel

class HomeViewModel : ViewModel() {
    val generalEventsList = MutableLiveData<ArrayList<GeneralEventModel>>()
}