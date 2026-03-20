package ru.akchibash.akchday1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.akchibash.akchday1.dto.Post
import ru.akchibash.akchday1.repository.PostRepository
import ru.akchibash.akchday1.repository.PostRepositoryInMemoryImpl

class PostViewModel : ViewModel() {

    init {
        println("ViewModel: created")
    }

    override fun onCleared() {
        super.onCleared()
        println("ViewModel: cleared")
    }


    // Создаем экземпляр репозитория
    private val repository: PostRepository = PostRepositoryInMemoryImpl()

    val data: LiveData<List<Post>> = repository.getAll()

    fun likeById(id: Long) = repository.likeById(id)

    fun shareById(id: Long) = repository.shareById(id)

    fun increaseViews(id: Long) = repository.increaseViews(id)

}

