package com.example.storyapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.R
import com.example.storyapp.data.remote.response.ListStoryItem
import com.example.storyapp.databinding.ActivityMainBinding
import com.example.storyapp.ui.create.CreateStoryActivity
import com.example.storyapp.ui.detail.DetailActivity
import com.example.storyapp.ui.login.LoginActivity
import com.example.storyapp.ui.maps.MapsActivity
import com.example.storyapp.utils.Preferences
import com.example.storyapp.utils.UserModel
import com.example.storyapp.utils.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var preferences: Preferences
    private var token: String = ""
    private lateinit var userModel: UserModel
    private lateinit var layoutManager: LinearLayoutManager
    private var isFirstLoad = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = ViewModelFactory.getInstance(this)
        mainViewModel = ViewModelProvider(
            this@MainActivity,
            viewModelFactory
        )[MainViewModel::class.java]
        layoutManager = LinearLayoutManager(this@MainActivity)
        binding.listStory.layoutManager = layoutManager
        binding.listStory.setHasFixedSize(true)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.listStory.addItemDecoration(itemDecoration)

        preferences = Preferences(this)

        userModel = preferences.getToken()
        token = userModel.token.toString()


        binding.fabAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, CreateStoryActivity::class.java)
            intent.putExtra(EXTRA_TOKEN, token)
            startActivity(intent)
        }
        isFirstLoad = true
        getAllStory()
    }

    private fun getAllStory() {
        Log.i("token", token)
        val adapter = StoryAdapter()
        binding.listStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        mainViewModel.getStory(token).observe(this){
            adapter.submitData(lifecycle, it)
            if (isFirstLoad) {
                layoutManager.scrollToPositionWithOffset(0, 0)
                isFirstLoad = false
            }
            adapter.setOnItemClickListener(object : StoryAdapter.OnItemClickListener{
                override fun onItemClick(data: ListStoryItem) {
                    val intentDetail = Intent(this@MainActivity, DetailActivity::class.java)
                    intentDetail.putExtra(DetailActivity.EXTRA_USERNAME, data.name)
                    intentDetail.putExtra(DetailActivity.EXTRA_PHOTO, data.photoUrl)
                    intentDetail.putExtra(DetailActivity.EXTRA_DESCRIPTION, data.description)
                    startActivity(intentDetail)
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.btn_logout -> {
                preferences.logout()
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            R.id.btn_location -> {
                val intent = Intent(this@MainActivity, MapsActivity::class.java)
                intent.putExtra(EXTRA_TOKEN, token)
                startActivity(intent)
                true
            }
            else -> true
        }
    }

    companion object{
        const val EXTRA_TOKEN = "extra_token"
    }

}