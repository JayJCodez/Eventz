package saved_instance_data

import models.ParticipantModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ParticipantsViewModel : ViewModel() {

    val participantsList = MutableLiveData<ArrayList<ParticipantModel>>()

}