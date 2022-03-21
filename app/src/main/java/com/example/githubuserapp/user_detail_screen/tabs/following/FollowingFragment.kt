package com.example.githubuserapp.user_detail_screen.tabs.following

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.data.UsersData
import com.example.githubuserapp.databinding.FragmentFollowingBinding
import com.example.githubuserapp.home_screen.AdapterUserList
import com.example.githubuserapp.user_detail_screen.UserDetailActivity

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowingViewModel
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        username = arguments?.getString(UserDetailActivity.EXTRA_USERNAME).toString()

        _binding = FragmentFollowingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollowing.layoutManager = layoutManager

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(FollowingViewModel::class.java)

        getListOfFollowing()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getListOfFollowing() {
        binding.progressBar.visibility = View.VISIBLE

        viewModel.setData(username)

        viewModel.getData().observe(viewLifecycleOwner, {
            if (it != null) {
                val layoutAdapter = AdapterUserList(it)
                binding.rvFollowing.adapter = layoutAdapter
                binding.progressBar.visibility = View.INVISIBLE

                layoutAdapter.setOnItemClickCallback(object : AdapterUserList.OnItemClickCallback{
                    override fun onItemClicked(data: UsersData) {
                        val iUserDetailPage = Intent(requireContext(), UserDetailActivity::class.java)
                        iUserDetailPage.putExtra(UserDetailActivity.EXTRA_USERNAME, data.login)
                        startActivity(iUserDetailPage)
                    }
                })
            }
        })

    }
}