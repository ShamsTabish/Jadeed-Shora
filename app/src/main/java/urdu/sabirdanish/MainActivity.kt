package urdu.sabirdanish

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import urdu.sabirdanish.databinding.ActivityMainBinding
import java.security.InvalidParameterException


fun timeConversion(s: String): String {
    // Write your code here
    fun handleAM(hour: String, minute: String, seconds: String): String {
        if (hour.equals("12"))
            return "00:$minute:$seconds"
        return "$hour:$minute:$seconds"

    }

    fun handlePM(hour: String, minute: String, seconds: String): String {
        if (hour.equals("12"))
            return "12:$minute:$seconds"
        else return "${(hour.toInt() + 12).toString()}:$minute:$seconds"
    }

    val (hour, minute, secondsWithMeridiem) = s.split(":")
    val seconds = secondsWithMeridiem.substring(0, 2)
    val meridiem = secondsWithMeridiem.substring(2)
    println("$hour: $minute: $seconds : $meridiem")
    when (meridiem.uppercase().trim()) {
        "PM" -> return handlePM(hour, minute, seconds)
        "AM" -> return handleAM(hour, minute, seconds)
        else -> throw InvalidParameterException()
    }

}

fun mains(args: Array<String>) {
    println(timeConversion("07:05:45PM"))
    println(timeConversion("12:05:45AM"))
    println(timeConversion("12:05:45PM"))
    println(timeConversion("11:05:45PM"))

}

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_about_app, R.id.nav_sabir_danish, R.id.nav_poet1
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}