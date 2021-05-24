package com.mehyo.inmobilestest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mehyo.inmobilestest.Item
import com.mehyo.inmobilestest.repo.GitDataSource
import com.mehyo.inmobilestest.repo.GitDataSourceFactory

class GitViewModel: ViewModel() {
    //PagedList liveData variable
    val gitPagedList: LiveData<PagedList<Item>>
    //DataSource liveData variable
    private val liveDataSource: LiveData<GitDataSource>

    init {
        //DataSource Factory variable
        val mGitDataSourceFactory= GitDataSourceFactory()
        //initialize the dataSource liveData using DataSourceFactory
        liveDataSource=mGitDataSourceFactory.itemLiveDataSource
        //PagedList Config variable
        val config=
            PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(GitDataSource.PAGE_SIZE)
                .build()
        //initialize the PagedList using the builder
        gitPagedList= LivePagedListBuilder(mGitDataSourceFactory,config).build()
    }
}