package ru.akchibash.akchday1.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.akchibash.akchday1.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {

    // Исходные данные
    private var post = Post(
        id = 1,
        author = "Волейбольный клуб \"Зенит-Казань\"",
        content = "Шикарная победа над принципиальным соперником! Выиграли 3:2, отыгравшись с 0:2 по партиям. Команда проявила характер. Лучшим игроком матча признан наш диагональный, набравший 28 очков. Спасибо болельщикам за поддержку!",
        published = "Сегодня в 20:45",
        likedByMe = false,
        likes = 9999,
        shares = 25,
        views = 5700
    )

    // MutableLiveData, который можно изменять
    private val _data = MutableLiveData(post)

    // Внешний доступ только для чтения (LiveData, а не MutableLiveData)
    override fun get(): LiveData<Post> = _data

    override fun like() {
        // Меняем состояние лайка на противоположное
        post = post.copy(
            likedByMe = !post.likedByMe,
            likes = if (post.likedByMe) post.likes - 1 else post.likes + 1
        )
        // Оповещаем подписчиков об изменении
        _data.value = post
    }

    override fun share() {
        post = post.copy(
            shares = post.shares + 1
        )
        _data.value = post
    }

    override fun increaseViews() {
        // Можно будет реализовать позже
        post = post.copy(
            views = post.views + 1
        )
        _data.value = post
    }
}
