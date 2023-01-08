package urdu.sabirdanish.ui.notifications

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import urdu.sabirdanish.CurrentOpenBook
import urdu.sabirdanish.PdfResourceReader
import urdu.sabirdanish.databinding.FragmentNextBinding

class NextFragment : Fragment() {

    private var _binding: FragmentNextBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNextBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val imageView: ImageView = binding.imageNext

        val (pageNumber, currentOpenBook) = CurrentOpenBook.getBookInfo()

//        imageView.setOnClickListener { initButtons(imageView, currentOpenBook) }
        val codeCacheDir = ContextCompat.getCodeCacheDir(this.requireContext())

//        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
            val nextPageNumber = CurrentOpenBook.getNextPageNumber()
            val bitmap = PdfResourceReader.openPdfWithAndroidSDK(
                imageView.height,
                imageView.width,
                imageView.scaleX,
                imageView.scaleY,
                resources,
                codeCacheDir,
                nextPageNumber,
                currentOpenBook
            )

//            dashboardViewModel.imageSrc=bitmap
            imageView.setImageBitmap(bitmap)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}