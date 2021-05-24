package com.mehyo.inmobilestest.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mehyo.inmobilestest.Item
import com.mehyo.inmobilestest.adapter.GitAdapter
import com.mehyo.inmobilestest.adapter.OnItemClickListener
import com.mehyo.inmobilestest.databinding.ActivityMainBinding
import com.mehyo.inmobilestest.viewmodel.GitViewModel
import java.io.Serializable

class MainActivity : AppCompatActivity() , OnItemClickListener {
    lateinit var mAdapter: GitAdapter
    lateinit var gitViewModel: GitViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView(binding.recyclerView)
        getData()
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
        gitViewModel.gitPagedList.observe(this, Observer {
            mAdapter.submitList(it)
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