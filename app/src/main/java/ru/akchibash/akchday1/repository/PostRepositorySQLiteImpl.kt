package ru.akchibash.akchday1.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.akchibash.akchday1.db.PostDao
import ru.akchibash.akchday1.dto.Post
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PostRepositorySQLiteImpl(
    private val postDao: PostDao
) : PostRepository {

    // Временно, пока нет системы аутентификации
    private val currentUserId = 1L
    private val currentUserName = "Я"

    private var posts = emptyList<Post>()
    private val _data = MutableLiveData(posts)

    init {
        refreshCache()
    }

    override fun getAll(): LiveData<List<Post>> = _data

    override fun likeById(id: Long) {
        postDao.likeById(id)
        refreshCache()
    }

    override fun shareById(id: Long) {
        postDao.shareById(id)
        refreshCache()
    }

    override fun increaseViews(id: Long) {
        postDao.increaseViews(id)
        refreshCache()
    }

    override fun save(post: Post): Post {
        val saved = if (post.id == 0L) {
            // Новый пост: задаём автора, дату, сбрасываем счётчики
            val newPost = Post(
                id = 0L,
                author = currentUserName,
                authorId = currentUserId,
                content = post.content,
                published = formatDate(Date()),
                likedByMe = false,
                likes = 0,
                shares = 0,
                views = 0,
                video = null
            )
            postDao.insert(newPost)
        } else {
            // Редактирование: обновляем только content, остальные поля сохраняем
            val existing = postDao.getById(post.id)
            if (existing != null) {
                val updated = existing.copy(content = post.content)
                postDao.update(updated)
                updated
            } else {
                post // fallback
            }
        }
        refreshCache()
        return saved
    }

    override fun removeById(id: Long) {
        postDao.delete(id)
        refreshCache()
    }

    private fun refreshCache() {
        posts = postDao.getAll()
        _data.value = posts
    }

    private fun formatDate(date: Date): String {
        val format = SimpleDateFormat("d MMM в HH:mm", Locale("ru"))
        return format.format(date)
    }
}