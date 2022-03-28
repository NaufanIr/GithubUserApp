package com.example.githubuserapp.home_screen

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.SettingPreferences
import com.example.githubuserapp.ViewModelFactory
import com.example.githubuserapp.data.UsersData
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.example.githubuserapp.user_detail_screen.UserDetailActivity
import com.example.githubuserapp.user_fav_screen.UserFavoriteActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var theme = true
    private var initUser: Boolean? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)

        viewModel = ViewModelProvider(this, ViewModelFactory(pref))[MainViewModel::class.java]

        getThemePref()

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
                if (newText.isEmpty()) {
                    binding.progressBar.visibility = View.INVISIBLE
                } else binding.progressBar.visibility = View.VISIBLE
                viewModel.searchUsers(newText)
                return true
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.item_favorite -> {
                val i = Intent(this, UserFavoriteActivity::class.java)
                startActivity(i)
                return true
            }

            R.id.item_theme -> {
                when (theme) {

                    true -> {
                        theme = false
                        viewModel.saveThemeSetting(theme)
                    }

                    false -> {
                        theme = true
                        viewModel.saveThemeSetting(theme)
                    }

                }
                return true
            }
            else -> return true
        }
    }

    private fun showUserList() {
        initUser = viewModel.isInitUser

        if (initUser == true) {
            viewModel.searchUsers("a")
            viewModel.setIsInitUser(false)
            initUser = viewModel.isInitUser
        }

        viewModel.getSearchUsers().observe(this@MainActivity) {
            if (it != null) {
                val layoutManager = LinearLayoutManager(this@MainActivity)
                binding.rvUsers.layoutManager = layoutManager
                val layoutAdapter = AdapterUserList(it)
                binding.rvUsers.adapter = layoutAdapter

                binding.progressBar.visibility = View.INVISIBLE

                layoutAdapter.setOnItemClickCallback(object : AdapterUserList.OnItemClickCallback {
                    override fun onItemClicked(data: UsersData) {
                        val iUserDetailPage =
                            Intent(this@MainActivity, UserDetailActivity::class.java)
                        iUserDetailPage.putExtra(UserDetailActivity.EXTRA_USERNAME, data.login)
                        startActivity(iUserDetailPage)
                    }
                })
            }
        }
    }

    private fun getThemePref() {
        viewModel.getThemeSettings().observe(this) {
            if (it) {
                theme = true
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                theme = false
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}