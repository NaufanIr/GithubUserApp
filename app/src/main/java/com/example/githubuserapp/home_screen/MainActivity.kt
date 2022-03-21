package com.example.githubuserapp.home_screen

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.data.UsersData
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.example.githubuserapp.user_detail_screen.UserDetailActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this@MainActivity, ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)

        supportActionBar?.title = "Github User's"

        val layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvUsers.layoutManager = layoutManager

        showUserList()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.appbar_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.item_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Find Some Users..."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()){
                    binding.progressBar.visibility = View.INVISIBLE
                } else binding.progressBar.visibility = View.VISIBLE

                viewModel.searchUsers(newText)
                return true
            }

        })
        return true
    }

    private fun showUserList() {
        viewModel.getSearchUsers().observe(this@MainActivity, {
            if (it != null) {
                val layoutAdapter = AdapterUserList(it)
                binding.rvUsers.adapter = layoutAdapter

                binding.progressBar.visibility = View.INVISIBLE

                layoutAdapter.setOnItemClickCallback(object : AdapterUserList.OnItemClickCallback{
                    override fun onItemClicked(data: UsersData) {
                        val iUserDetailPage = Intent(this@MainActivity, UserDetailActivity::class.java)
                        iUserDetailPage.putExtra(UserDetailActivity.EXTRA_USERNAME, data.login)
                        startActivity(iUserDetailPage)
                    }
                })
            }
        })
    }

}