package dds.com.week2.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import dds.com.week2.GlobalVars
import dds.com.week2.R
import dds.com.week2.fragment.ListLocation
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_list -> {
                changeFragment("listLocation")
            }
            R.id.navigation_map -> {

            }
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (!GlobalVars.isHaveFragment) {
            val fragmentManager: FragmentManager = supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            val listLocationFragment: ListLocation = ListLocation()
            fragmentTransaction.add(R.id.mainFrame, listLocationFragment, listLocationFragment.javaClass.simpleName)
            GlobalVars.isHaveFragment = true
            fragmentTransaction.commit()
        } else {
            changeFragment("listLocation")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun changeFragment(fragment: String) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        if (fragment.equals("listLocation")) {
            val listLocationFragment: ListLocation = ListLocation()
            fragmentTransaction.replace(
                R.id.mainFrame, listLocationFragment,
                listLocationFragment.javaClass.simpleName
            )
        } else {
            // empty code
        }
        GlobalVars.isHaveFragment = true
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.changeLanguage -> {
                if (GlobalVars.nameLanguage.equals("default")) {
                    GlobalVars.nameLanguage = "in"
                    setLocale("in")
                } else {
                    GlobalVars.nameLanguage = "default"
                    setLocale("default")
                }
                this.recreate()
                return true;
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @Suppress("DEPRECATION")
    fun setLocale(lang: String?) {
        var myLocale: Locale?
        if (lang != "in") {
            myLocale = Locale.getDefault()
        } else {
            myLocale = Locale(lang)
        }
        val res = resources
        val conf = res.configuration
        conf.setLocale(myLocale)
        println("MainActivity: " + myLocale.toString())
        res.updateConfiguration(conf, res.displayMetrics)
    }
}
