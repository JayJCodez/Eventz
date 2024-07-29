
package saved_instance_data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

public class SharedViewModel : ViewModel() {
    val userId: MutableLiveData<Int> = MutableLiveData()

}
