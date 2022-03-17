package com.example.githubuserapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val users = ArrayList<DataUsers>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        users.addAll(listUsers)
        showRecyleView()
    }

    private val listUsers: ArrayList<DataUsers>
    get() {
        val listUser = ArrayList<DataUsers>()
        for (i in resources.getStringArray(R.array.name).indices){
            val user = DataUsers(
                name = resources.getStringArray(R.array.name)[i],
                username = resources.getStringArray(R.array.username)[i],
                following = resources.getStringArray(R.array.following)[i],
                followers = resources.getStringArray(R.array.followers)[i],
                repository = resources.getStringArray(R.array.repository)[i],
                company = resources.getStringArray(R.array.company)[i],
                location = resources.getStringArray(R.array.location)[i],
                avatar = resources.obtainTypedArray(R.array.avatar).getResourceId(i, -1)
            )
            listUser.add(user)
        }
        return listUser
    }

    private fun showRecyleView() {
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        val listUsersAdapter = AdapterUserList(users)
        binding.rvUsers.adapter = listUsersAdapter

        listUsersAdapter.setOnItemClickCallback(object : AdapterUserList.OnItemClickCallback{
            override fun onItemClicked(data: DataUsers) {
                val iUserDetailPage = Intent(this@MainActivity, UserDetailActivity::class.java)
                iUserDetailPage.putExtra("DATA", data)
                startActivity(iUserDetailPage)
            }
        })
    }

}