package com.example.message.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.message.data_model.ChatItem
import com.example.message.retrofit.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class FragmentViewModel : ViewModel() {

    private var originalList = mutableSetOf<ChatItem>()
    private val _chatListFlow: MutableStateFlow<List<ChatItem>> = MutableStateFlow(emptyList())
    val chatListFlow: Flow<List<ChatItem>> get() = _chatListFlow.asStateFlow()
    private val _queryFlow = MutableStateFlow("")
    var isActive: Boolean = false


    init {
        loadChatItems()
    }

    private fun loadChatItems() {
        viewModelScope.launch {
            try {
                val chatItems = RetrofitInstance.getApi.getChatItems()
                _chatListFlow.value = chatItems
                _chatListFlow.value.forEach {
                    originalList.add(it)
                }

            } catch (e: Exception) {
                // Error message
            }
        }
    }

    fun updateQuery(query: String) {
        if (isActive) {
            _queryFlow.value = query
            filterChatList()
        }

    }

    private fun filterChatList() {
        val lowercaseQuery = _queryFlow.value.lowercase(Locale.getDefault())
        val filteredList = originalList.filter {
            it.owner.lowercase(Locale.getDefault()).contains(lowercaseQuery)
        }
        _chatListFlow.value = filteredList
    }

}