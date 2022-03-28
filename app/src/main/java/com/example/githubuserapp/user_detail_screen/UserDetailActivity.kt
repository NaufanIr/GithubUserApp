package com.example.githubuserapp.user_detail_screen

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.database.FavUser
import com.example.githubuserapp.databinding.ActivityUserDetailsBinding
import com.example.githubuserapp.user_detail_screen.tabs.SectionsPagerAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailsBinding
    private lateinit var viewModel: UserDetailViewModel
    private lateinit var favUser: FavUser
    private lateinit var username: String
    private var liked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra(EXTRA_USERNAME).toString()

        viewModel = ViewModelProvider(this)[UserDetailViewModel::class.java]

        showLayout()
    }

    private fun showLayout() {
        binding.itemUserDetails.progressBar.visibility = View.VISIBLE

        if (username != null) {
            viewModel.setData(username)
        } else {
            binding.itemUserDetails.progressBar.visibility = View.VISIBLE
        }

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        val viewPager = binding.viewPager
        val tabs = binding.tabs
        viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        viewModel.getData().observe(this) {
            if (it != null) {
                val item = binding.itemUserDetails

                item.apply {
                    favUser = FavUser(it.id, it.login, it.avatarUrl)

                    supportActionBar?.title = it.login.lowercase()

                    Glide.with(this@UserDetailActivity)
                        .load(it.avatarUrl)
                        .circleCrop()
                        .placeholder(R.drawable.user)
                        .error(R.drawable.user)
                        .into(circleImageView)

                    tvName.text = it.name?.uppercase() ?: "-"
                    tvFollowersCount.text = it.followers.toString()
                    tvRepositoriesCount.text = it.publicRepos.toString()
                    tvFollowingCount.text = it.following.toString()
                    tvCompany.text = it.company ?: "-"
                    tvLoc.text = it.location ?: "-"

                    binding.itemUserDetails.progressBar.visibility = View.INVISIBLE
                    binding.fabFavorite.visibility = View.VISIBLE

                    fabActionFavorite(it.id, favUser)
                }
            }
        }
    }

    private fun fabActionFavorite(id: Int, favUser: FavUser) {
        viewModel.checkUser(id).observe(this@UserDetailActivity){
            if (it != null) {
                if (it > 0) {
                    liked = true
                    binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_favorited))
                } else {
                    binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_favorite))
                    liked = false
                }
            }
        }

        binding.fabFavorite.setOnClickListener {
            when(liked){
                false -> {
                    viewModel.addToFav(favUser)
                    liked = true
                    binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_favorited))
                    Snackbar.make(it, "Added to your favorite list", Snackbar.LENGTH_SHORT).show()
                }
                true -> {
                    viewModel.removeFromFav(id)
                    liked = false
                    binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_favorite))
                    Snackbar.make(it, "Removed from your favorite list", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    companion object {
        const val EXTRA_USERNAME = "username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
    }
}