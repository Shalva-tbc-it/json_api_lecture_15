package com.example.message.fragment

import android.os.Bundle
import android.util.Log.e
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.message.ChatRecyclerAdapter
import com.example.message.R
import com.example.message.base.BaseFragment
import com.example.message.data_model.ChatItem
import com.example.message.databinding.FragmentHomePageBinding
import com.example.message.view_model.FragmentViewModel
import kotlinx.coroutines.launch

class HomePageFragment : BaseFragment<FragmentHomePageBinding>(FragmentHomePageBinding::inflate) {

    private val viewModel: FragmentViewModel by activityViewModels()
    private lateinit var adapter: ChatRecyclerAdapter
    private val chatItem: MutableList<ChatItem> = mutableListOf()

    override fun start() {
        getJson()
        initAdapter()
        filterList()
    }

    override fun clickListener() {
        binding.edSearch.addTextChangedListener {
            viewModel.updateQuery(it.toString())
        }
    }

    private fun getJson() {
        val chatItemList: List<ChatItem>? = viewModel.parseJsonFromRaw(resources, R.raw.chat_data)

        chatItemList?.forEach { chatItems ->
            viewModel.originalList.add(chatItems)
            chatItem.add(chatItems)
        }
    }

    private fun filterList () {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.filteredChatListFlow.collect {
                if (it.isNotEmpty()) {
                    adapter.submitList(it)
                }
            }
        }
    }

    private fun initAdapter() = with(binding) {
        adapter = ChatRecyclerAdapter()
        rcChatItem.layoutManager = LinearLayoutManager(requireContext())
        rcChatItem.adapter = adapter
        adapter.submitList(chatItem)
    }

}