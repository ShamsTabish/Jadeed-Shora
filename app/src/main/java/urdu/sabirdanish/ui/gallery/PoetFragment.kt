package urdu.sabirdanish.ui.gallery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import urdu.sabirdanish.BookReader
import urdu.sabirdanish.CurrentOpenBook
import urdu.sabirdanish.R
import urdu.sabirdanish.databinding.FragmentPoetBinding

class PoetFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel
    private var _binding: FragmentPoetBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentPoetBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val textView: TextView = binding.poetName
//        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        val izterab: Button = binding.izteraab



        izterab.setOnClickListener { view ->
            println("Worksxxx.....")
            CurrentOpenBook.openBook(R.raw.taskeen,67)
            CurrentOpenBook.goToPageNo(24)
            val pdfIntent = Intent(view?.context, BookReader::class.java)
            startActivity(pdfIntent)
            Snackbar.make(view, "Izteraab", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        };
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}