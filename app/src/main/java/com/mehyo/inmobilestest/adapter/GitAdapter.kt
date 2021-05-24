package com.mehyo.inmobilestest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.mehyo.inmobilestest.Item
import com.mehyo.inmobilestest.R
import com.mehyo.inmobilestest.databinding.ItemRowBinding
import java.util.*

interface OnItemClickListener{
    fun onItemClicked(item: Item)
}

class GitAdapter(val itemClickListener: OnItemClickListener): PagedListAdapter<Item, GitAdapter.GitViewHolder>(ITEM_COMPARATOR) {

    //CreateViewHolder using view binding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitViewHolder {
        val binding=ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GitViewHolder(binding)
    }

    //onBindViewHolder binding the data from the data to the row
    override fun onBindViewHolder(holder: GitViewHolder, position: Int) {
        val item=getItem(position)
        item?.let {
            holder.bind(item,itemClickListener)
        }
    }

    //custom view holder with the bind function to bind the data to the item row views
    class GitViewHolder(val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root){
        private val repoID=binding.tvRepoID
        private val repoName=binding.tvRepoName
        private val repoFullName=binding.tvRepoFullName
        private val avatarImage=binding.ivAvatar
        private val repoOwnerName=binding.tvRepoOwner
        private val cardView=binding.cardview

        fun bind(data: Item,clickListener: OnItemClickListener){
            repoID.text = "[Repo ID = ${data.id}]"
            repoName.text=data.name
            repoFullName.text=data.full_name
            repoOwnerName.text="[Submitted by ${data.owner.login} ID = ${data.owner.id}]"
            val url:String=data.owner.avatar_url
            avatarImage.load(url){
                crossfade(true)
                placeholder(R.drawable.ic_image_place_holder)
                transformations(CircleCropTransformation())
            }
            cardView.setOnClickListener {
                clickListener.onItemClicked(data)
            }
        }
    }
    //checking for data change
    companion object{
        private val ITEM_COMPARATOR= object : DiffUtil.ItemCallback<Item>(){
            override fun areItemsTheSame(oldItem: Item, newItem: Item):
                    Boolean = oldItem.id==newItem.id
            override fun areContentsTheSame(oldItem: Item, newItem: Item):
                    Boolean = oldItem==newItem
        }
    }
}