package com.example.message.fragment

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.message.ChatRecyclerAdapter
import com.example.message.base.BaseFragment
import com.example.message.databinding.FragmentHomePageBinding
import com.example.message.view_model.FragmentViewModel
import kotlinx.coroutines.launch

class HomePageFragment : BaseFragment<FragmentHomePageBinding>(FragmentHomePageBinding::inflate) {

    private val viewModel: FragmentViewModel by activityViewModels()
    private lateinit var adapter: ChatRecyclerAdapter

    override fun start() {
        initAdapter()
    }

    override fun clickListener() {
        binding.apply {

            imgFilter.setOnClickListener {
                viewModel.isActive = !viewModel.isActive
            }

            edSearch.addTextChangedListener {
                filterList(it.toString())
            }

        }
    }

    override fun observe() {
        getApi()
    }

    private fun filterList(fullName: String) {
        viewModel.updateQuery(fullName)
    }

    private fun getApi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.chatListFlow.collect { charItem ->
                adapter.submitList(charItem)
            }
        }
    }

    private fun initAdapter() = with(binding) {
        adapter = ChatRecyclerAdapter()
        rcChatItem.layoutManager = LinearLayoutManager(requireContext())
        rcChatItem.adapter = adapter
    }

}