package urdu.sabirdanish.ui.dashboard

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import urdu.sabirdanish.CurrentOpenBook
import urdu.sabirdanish.PdfResourceReader
import urdu.sabirdanish.databinding.FragmentPreviousBinding

class PreviousFragment() : Fragment() {
    private var _binding: FragmentPreviousBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(PageViewModel::class.java)

        _binding = FragmentPreviousBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val imageView: ImageView = binding.imageDashboard

        val (pageNumber, currentOpenBook) = CurrentOpenBook.getBookInfo()

//        imageView.setOnClickListener { initButtons(imageView, currentOpenBook) }
        val codeCacheDir = ContextCompat.getCodeCacheDir(this.requireContext())

        //Try this : https://www.geeksforgeeks.org/how-to-load-pdf-from-url-in-android/

        dashboardViewModel.imageSrc.observe(viewLifecycleOwner) {
            val previousPageNumber = CurrentOpenBook.getPreviousPageNumber()
            val bitmap = PdfResourceReader.openPdfWithAndroidSDK(
                imageView.height,
                imageView.width,
                imageView.scaleX,
                imageView.scaleY,
                resources,
                codeCacheDir,
                previousPageNumber,
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