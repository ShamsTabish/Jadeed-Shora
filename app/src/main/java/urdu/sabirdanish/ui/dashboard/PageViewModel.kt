package urdu.sabirdanish.ui.dashboard

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PageViewModel : ViewModel() {

    private val _image = MutableLiveData<String>().apply {
        value = null
    }
    val imageSrc: LiveData<String> = _image
}