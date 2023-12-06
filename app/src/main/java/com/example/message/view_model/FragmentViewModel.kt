package com.example.message.view_model

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.example.message.data_model.ChatItem
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import java.io.InputStream
import java.util.Locale

class FragmentViewModel : ViewModel() {

    var originalList = mutableSetOf<ChatItem>()

    inline fun <reified T> parseJsonFromRaw(resources: Resources, rawResourceId: Int): T? {
        val inputStream: InputStream = resources.openRawResource(rawResourceId)
        val jsonString = inputStream.bufferedReader().use { it.readText() }

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val type = Types.newParameterizedType(List::class.java, ChatItem::class.java)
        val adapter: JsonAdapter<T> = moshi.adapter(type)
        return adapter.fromJson(jsonString)
    }

    private val _queryFlow = MutableStateFlow<String>("")

    private val _filteredChatListFlow: MutableStateFlow<List<ChatItem>> = MutableStateFlow(emptyList())
    val filteredChatListFlow: StateFlow<List<ChatItem>> get() = _filteredChatListFlow

    fun updateQuery(query: String) {
        _queryFlow.value = query
        filterChatList()
    }

    private fun filterChatList() {
        val lowercaseQuery = _queryFlow.value.lowercase(Locale.getDefault())
        val filteredList = originalList.filter {
            it.owner.lowercase(Locale.getDefault()).contains(lowercaseQuery)
        }
        _filteredChatListFlow.value = filteredList
    }

}