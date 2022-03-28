package com.example.githubuserapp.user_fav_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.data.UsersData
import com.example.githubuserapp.database.FavUser
import com.example.githubuserapp.databinding.ActivityUserFavoriteBinding
import com.example.githubuserapp.home_screen.AdapterUserList
import com.example.githubuserapp.user_detail_screen.UserDetailActivity

class UserFavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserFavoriteBinding
    private lateinit var viewModel: UserFavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[UserFavoriteViewModel::class.java]

        showFavUser()
    }

    private fun mapList(users: List<FavUser>): ArrayList<UsersData> {
        val listUser = ArrayList<UsersData>()
        for (e in users){
            val userMapped = UsersData(
                e.avatarUrl,
                e.id,
                e.login
            )
            listUser.add(userMapped)
        }
        return listUser
    }

    private fun showFavUser(){
        viewModel.getAllFavUser().observe(this) {
            if (it != null) {
                val userData = mapList(it)
                val layoutManager = LinearLayoutManager(this)
                binding.rvFavUsers.layoutManager = layoutManager
                val layoutAdapter = AdapterUserList(userData)
                binding.rvFavUsers.adapter = layoutAdapter

                binding.progressBar.visibility = View.INVISIBLE

                layoutAdapter.setOnItemClickCallback(object: AdapterUserList.OnItemClickCallback{
                    override fun onItemClicked(data: UsersData) {
                        val iUserDetailPage =
                            Intent(this@UserFavoriteActivity, UserDetailActivity::class.java)
                        iUserDetailPage.putExtra(UserDetailActivity.EXTRA_USERNAME, data.login)
                        startActivity(iUserDetailPage)
                    }
                })
            }
        }
    }
}