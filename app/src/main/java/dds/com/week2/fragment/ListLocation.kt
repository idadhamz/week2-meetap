package dds.com.week2.fragment

import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dds.com.week2.GlobalVars
import dds.com.week2.R
import dds.com.week2.adapter.PlaceAdapter
import dds.com.week2.model.PlaceModel
import kotlinx.android.synthetic.main.fragment_list_location.view.*

class ListLocation : Fragment() {

    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_list_location, container, false)
        mHandler = Handler()

        // thread di background
        view.swipeRefreshLayout.setOnRefreshListener {
            mRunnable = Runnable {
                view.swipeRefreshLayout.isRefreshing = false
            }

            mHandler.postDelayed(
                mRunnable,
                (2000).toLong()
            )
        }

        val listascyn: ListLocationAsyncTask = ListLocationAsyncTask(view)
        listascyn.execute()

        val newList: List<PlaceModel> = GlobalVars.placeModels
        view.rvItemPlace.adapter = PlaceAdapter(newList)

        view.btnFindPlace.setOnClickListener {
            val listascyn: ListLocationAsyncTask = ListLocationAsyncTask(view)
            listascyn.execute()

            if(view.editFindPlace.text.toString() != ""){
                println(view.editFindPlace.text)

                view.rvItemPlace.adapter = PlaceAdapter(
                    newList.filter {it.name.toLowerCase().contains(view.editFindPlace.text.toString().toLowerCase())}
                )

                newList.forEach{println("ListLocation: " + it.name)}

            }else{
                view.rvItemPlace.adapter = PlaceAdapter(newList)
            }
        }
        return view
    }

    class ListLocationAsyncTask(view: View) : AsyncTask<Void, Void, Void>() {
        val this_view: View = view

        override fun onPreExecute() {
            this_view.progress_utama.visibility = View.VISIBLE
            this_view.rvItemPlace.visibility = View.GONE
        }

        override fun doInBackground(vararg params: Void?): Void? {
            try {
                Thread.sleep(2000)
            } catch (e: Exception) {
                println("Main Activity: " + e.message)
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            this_view.progress_utama.visibility = View.GONE
            this_view.rvItemPlace.visibility = View.VISIBLE
        }
    }

}