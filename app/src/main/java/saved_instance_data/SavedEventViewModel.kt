package saved_instance_data

import models.GeneralEventModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SavedEventViewModel : ViewModel() {

    val generalEventsList = MutableLiveData<ArrayList<GeneralEventModel>>()

}