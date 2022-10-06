package urdu.sabirdanish

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import urdu.sabirdanish.databinding.ActivityBookReaderBinding


class BookReader : AppCompatActivity() {
    private lateinit var binding: ActivityBookReaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookReaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        CurrentOpenBook.screenWidth = windowManager.defaultDisplay.width
        CurrentOpenBook.screenHeight = windowManager.defaultDisplay.height
        println("The Width of screen is ${CurrentOpenBook.screenWidth}")
        println("The Height of screen is ${CurrentOpenBook.screenHeight}")
        val navController = findNavController(R.id.nav_host_fragment_activity_book_reader)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    //    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
//    private fun initButtons(imageView: ImageView, pdfResource: Int) {
//        val next = findViewById<Button>(R.id.button_first)
//        val previous = findViewById<Button>(R.id.button_second)
//
//        next.setOnClickListener {
//            openPdfWithAndroidSDK(imageView, pageNumber.incrementAndGet(), pdfResource)
//        }
//        previous.setOnClickListener {
//            openPdfWithAndroidSDK(imageView, pageNumber.decrementAndGet(), pdfResource)
//        }
//    }


}