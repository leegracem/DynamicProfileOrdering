package com.example.dynamicprofileordering.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dynamicprofileordering.R
import com.example.dynamicprofileordering.databinding.ActivityMainBinding
import com.example.dynamicprofileordering.model.Config
import com.example.dynamicprofileordering.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val userViewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = userViewModel

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter(null, Config(emptyList()))
        binding.recyclerView.adapter = userAdapter

        setUpObservers()
        setUpNextButton()
    }

    private fun setUpObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                userViewModel.users.combine(userViewModel.currentIndex) { users, index ->
                    Pair(users, index)
                }.collect { (users, index) ->
                    if (users.isNotEmpty() && index < users.size) {
                        val currentUser = users[index]
                        userAdapter.updateUser(currentUser)
                    }
                }
            }
        }
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                userViewModel.config.collect { fetchedConfig ->
                    userAdapter.updateConfig(fetchedConfig)

                }
            }
        }
    }

    private fun setUpNextButton() {
        binding.nextButton.setOnClickListener {
            userViewModel.nextUser()
        }
    }
}