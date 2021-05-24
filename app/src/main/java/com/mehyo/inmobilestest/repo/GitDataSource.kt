package com.mehyo.inmobilestest.repo

import android.util.Log
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.mehyo.inmobilestest.Item
import com.mehyo.inmobilestest.network.RetrofitInstanceBuilder
import okio.IOException
import java.util.*

//DataSource that gets the Data sets using a PageKeyedDataSource
class GitDataSource: PageKeyedDataSource<Int, Item>() {

    companion object{
        const val PAGE_SIZE=50
        const val FIRST_PAGE_Number=1
        const val sort:String="stars"
        const val order:String="desc"
        const val time:String="created:>2019-11-01"
    }
    // function to load the Initial data
    // using the retrofit network call with Initial page number
    override fun loadInitial(params: LoadInitialParams<Int>,
                             callback: LoadInitialCallback<Int, Item>
    ) {
        GlobalScope.launch(Dispatchers.IO){

            try {
                val response =RetrofitInstanceBuilder.apiService.getAPIResult(time,sort,order,
                    FIRST_PAGE_Number, PAGE_SIZE)
                if(response.isSuccessful){
                    val apiResponse=response.body()!!
                    val responseItems=apiResponse.items
                    Log.d("DataSource Good",response.toString())

                    responseItems?.let {
                        callback.onResult(responseItems,null, FIRST_PAGE_Number+1)
                    }

                }
                else{
                    Log.d("DataSource Bad",response.toString())
                }
            }catch (e:IOException){
                Log.d("DataSource:loadInitial","IOException "+e.message)
            }
        }
    }

    // function to load the past data
    // using the retrofit network call with decreased page number
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        GlobalScope.launch(Dispatchers.IO){

            try {
                val response =RetrofitInstanceBuilder.apiService.getAPIResult(time,sort,order,
                    params.key, PAGE_SIZE)
                if(response.isSuccessful){
                    val apiResponse=response.body()!!
                    val responseItems=apiResponse.items
                    val key= if(params.key>1)params.key - 1 else 0

                    responseItems?.let {
                        callback.onResult(responseItems,key)
                    }
                }
            }catch (e:IOException){
                Log.d("DataSource:loadBefore","IOException "+e.message)
            }
        }
    }

    // function to load the new data
    // using the retrofit network call with increased page number
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        GlobalScope.launch(Dispatchers.IO){

            try {
                val response =RetrofitInstanceBuilder.apiService.getAPIResult(time,sort,order,
                    params.key, PAGE_SIZE)
                if(response.isSuccessful){
                    val apiResponse=response.body()!!
                    val responseItems=apiResponse.items
                    val key=params.key + 1

                    responseItems?.let {
                        callback.onResult(responseItems,key)
                    }
                }
            }catch (e:IOException){
                Log.d("DataSource:loadAfter","IOException "+e.message)
            }
        }
    }
}