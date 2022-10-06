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
import urdu.sabirdanish.databinding.FragmentDashboardBinding
import java.io.File
import java.util.concurrent.Executor

class DashboardFragment() : Fragment() {
    private var _binding: FragmentDashboardBinding? = null

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
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val imageView: ImageView = binding.imageDashboard

        val (pageNumber, currentOpenBook) = CurrentOpenBook.getBookInfo()

//        imageView.setOnClickListener { initButtons(imageView, currentOpenBook) }
        val codeCacheDir = ContextCompat.getCodeCacheDir(this.requireContext())

        dashboardViewModel.text.observe(viewLifecycleOwner) {
            PdfResourceReader.openPdfWithAndroidSDK(imageView,resources, codeCacheDir , pageNumber, currentOpenBook)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}