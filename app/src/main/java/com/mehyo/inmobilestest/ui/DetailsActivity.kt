package com.mehyo.inmobilestest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.load
import coil.transform.CircleCropTransformation
import com.mehyo.inmobilestest.Item
import com.mehyo.inmobilestest.R
import com.mehyo.inmobilestest.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getSerializableExtra("item object") as? Item

        val repoID=binding.tvRepoID
        val repoName=binding.tvRepoName
        val repoFullName=binding.tvRepoFullName
        val avatarImage=binding.ivAvatar
        val repoOwnerName=binding.tvRepoOwner
        val repoOwnerID=binding.tvRepoOwnerID

        repoOwnerID.text="[Owner ID = ${data?.owner?.id}]"
        repoID.text = "[Repo ID = ${data?.id}]"
        repoName.text="[Repo Name = ${data?.name}]"
        repoFullName.text="[Repo Full Name = ${data?.full_name}]"
        repoOwnerName.text="[Submitted by ${data?.owner?.login}]"
        val url: String? =data?.owner?.avatar_url
        avatarImage.load(url){
            crossfade(true)
            placeholder(R.drawable.ic_image_place_holder)
            transformations(CircleCropTransformation())
        }

    }
}