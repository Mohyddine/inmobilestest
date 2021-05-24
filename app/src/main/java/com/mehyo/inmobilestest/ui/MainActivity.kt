package com.mehyo.inmobilestest.ui

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Chronometer
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mehyo.inmobilestest.Item
import com.mehyo.inmobilestest.adapter.GitAdapter
import com.mehyo.inmobilestest.adapter.OnItemClickListener
import com.mehyo.inmobilestest.databinding.ActivityMainBinding
import com.mehyo.inmobilestest.viewmodel.GitViewModel


class MainActivity : AppCompatActivity() , OnItemClickListener {
    lateinit var mAdapter: GitAdapter
    lateinit var gitViewModel: GitViewModel
    lateinit var chronometer: Chronometer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chronometer=binding.Chronometer
        val settings = getPreferences(0)
        val savedBase = settings.getLong("base", SystemClock.elapsedRealtime() - (17 * 1000))
        chronometer.base=savedBase
        chronometer.start()

        initRecyclerView(binding.recyclerView)
        getData()

        Log.d("onCreate base",chronometer.base.toString())
    }

    override fun onResume() {
        super.onResume()
        Log.d("onResume base",chronometer.base.toString())
    }

    override fun onPause() {
        super.onPause()
        val settings = getPreferences(0)
        val editor = settings.edit()
        editor.putLong("base",chronometer.base)
        editor.apply()
        Log.d("onPause base",chronometer.base.toString())
    }

    override fun onStop() {
        super.onStop()
        chronometer.stop()
        Log.d("onStop base",chronometer.base.toString())
    }


    //RecyclerView initialization with dividerItemDecoration and setting the Adapter
    fun initRecyclerView(recyclerView: RecyclerView){
        recyclerView.apply {
            layoutManager= LinearLayoutManager(applicationContext)
            mAdapter=GitAdapter(this@MainActivity)
            adapter=mAdapter
        }
    }
    //get the data using the viewModel then submit inside the adapter
    fun getData(){
        gitViewModel= ViewModelProvider(this).get(GitViewModel::class.java)
        gitViewModel.gitPagedList.observe(this, Observer {data->
            mAdapter.submitList(data)
            mAdapter.notifyDataSetChanged()
        })
    }

    override fun onItemClicked(item: Item) {
        val intent= Intent(this@MainActivity,
            DetailsActivity::class.java).apply {
                putExtra("item object", item)
        }
        startActivity(intent)
    }
}