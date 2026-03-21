package ru.akchibash.akchday1.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.akchibash.akchday1.dto.Post
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class PostRepositoryInMemoryImpl : PostRepository {

    // Счетчик для генерации ID
    private var nextId = 5L

    // Текущий пользователь (для демонстрации)
    private val currentUserId = 1L
    private val currentUserName = "Я"


    // Исходные данные
    private var posts = listOf(
        Post(
            id = 1,
            author = "Волейбольный клуб \"Зенит-Казань\"",
            authorId = 2,
            content = "Шикарная победа над принципиальным соперником! Выиграли 3:2, отыгравшись с 0:2 по партиям. Команда проявила характер. Лучшим игроком матча признан наш диагональный, набравший 28 очков. Спасибо болельщикам за поддержку!",
            published = "Сегодня в 20:45",
            likedByMe = false,
            likes = 9999,
            shares = 25,
            views = 5700
        ),
        Post(
            id = 2,
            author = "Волейбольный клуб \"Зенит-Казань\"",
            authorId = 3,
            content = "Сенсационная победа в финале Лиги чемпионов! В драматичном пятисетовом матче обыграли итальянский «Трентино» — 3:2. Решающее очко принес капитан команды. Это пятый титул в истории клуба! Спасибо всем, кто был с нами в этом невероятном сезоне!",
            published = "Вчера в 22:10",
            likedByMe = false,
            likes = 15200,
            shares = 340,
            views = 8900
        ),
        Post(
            id = 3,
            author = "Волейбольный клуб \"Зенит-Казань\"",
            authorId = 4,
            content = "Официально: клуб продлил контракт с лучшим связующим последних лет! Наш номер один остаётся в команде ещё на два сезона. Его видение площадки и пасы — наше главное оружие. Вместе к новым вершинам!",
            published = "2 дня назад в 14:30",
            likedByMe = false,
            likes = 7200,
            shares = 120,
            views = 4300
        ),
        Post(
            id = 4,
            author = "Волейбольный клуб \"Зенит-Казань\"",
            authorId = 5,
            content = "Наш доигровщик признан MVP прошедшего чемпионата России! В среднем за матч он набирал 22 очка, реализуя 65% атак. Закономерный итог фантастического сезона. Поздравляем!",
            published = "5 дней назад в 11:15",
            likedByMe = false,
            likes = 11100,
            shares = 210,
            views = 6700
        ),
        Post(
            id = 5,
            author = "Волейбольный клуб \"Зенит-Казань\"",
            authorId = 6,
            content = "Сегодня команда провела открытую тренировку для юных воспитанников спортивных школ. Ребята увидели мастер-класс от звёзд, получили автографы и массу эмоций. Растим новое поколение чемпионов!",
            published = "Сегодня в 17:05",
            likedByMe = false,
            likes = 5300,
            shares = 85,
            views = 3200
        ),
        Post(
            id = 6,
            author = "Волейбольный клуб \"Зенит-Казань\"",
            authorId = 7,
            content = "Золотой дубль оформлен! Помимо чемпионства, наша команда выиграла и Кубок России, обыграв в финале московское «Динамо» в трёх партиях. Идеальный сезон — 12 побед подряд в плей-офф!",
            published = "3 дня назад в 19:20",
            likedByMe = false,
            likes = 18400,
            shares = 460,
            views = 11200
        ),
        Post(
            id = 7,
            author = "Волейбольный клуб \"Зенит-Казань\"",
            authorId = 8,
            content = "Наш новичок из Бразилии уже в Казани! Прошёл медосмотр и провёл первую тренировку с командой. Ждём ярких подач и мощных атак. Добро пожаловать в семью «Зенит»!",
            published = "Сегодня в 12:40",
            likedByMe = false,
            likes = 8900,
            shares = 210,
            views = 5100
        ),
        Post(
            id = 8,
            author = "Волейбольный клуб \"Зенит-Казань\"",
            authorId = 9,
            content = "Рекордная посещаемость на домашнем матче! 5200 болельщиков заполнили трибуны «Казань-Арены». Такой поддержки не было с прошлого сезона. Спасибо, вы — настоящая шестая партия!",
            published = "Вчера в 23:10",
            likedByMe = false,
            likes = 13700,
            shares = 380,
            views = 8900
        ),
        Post(
            id = 9,
            author = "Волейбольный клуб \"Зенит-Казань\"",
            authorId = 10,
            content = "Ветеран команды провёл юбилейный матч в форме «Зенита» — 300 официальных игр. За эти годы завоевано 12 трофеев, и это не предел! Легенда клуба продолжает бить рекорды.",
            published = "6 дней назад в 16:30",
            likedByMe = false,
            likes = 9500,
            shares = 210,
            views = 6200
        ),
        Post(
            id = 9,
            author = "Волейбольный клуб \"Зенит-Казань\"",
            authorId = 11,
            content = "Детская академия «Зенит» объявляет набор мальчиков и девочек 2013–2015 годов рождения. Тренировки проводят тренеры основной команды. Стань частью системы чемпионов!",
            published = "Вчера в 10:00",
            likedByMe = false,
            likes = 4100,
            shares = 160,
            views = 2800
        ),

    )

    private val _data = MutableLiveData(posts)



    override fun getAll(): LiveData<List<Post>> = _data

    override fun likeById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(
                    likedByMe = !post.likedByMe,
                    likes = if (post.likedByMe) post.likes - 1 else post.likes + 1
                )
            } else {
                post
            }
        }
        _data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(shares = post.shares + 1)
            } else {
                post
            }
        }
        _data.value = posts
    }

    override fun increaseViews(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(views = post.views + 1)
            } else {
                post
            }
        }
        _data.value = posts
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            // Создание нового поста
            val newPost = post.copy(
                id = nextId++,
                author = currentUserName,
                authorId = currentUserId,
                published = formatDate(Date()),
                likedByMe = false,
                likes = 0,
                shares = 0,
                views = 0
            )
            posts = listOf(newPost) + posts
        } else {
            // Обновление существующего поста
            posts = posts.map { existingPost ->
                if (existingPost.id == post.id) {
                    // Сохраняем автора, дату и счетчики, обновляем только контент
                    existingPost.copy(content = post.content)
                } else {
                    existingPost
                }
            }
        }
        _data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        _data.value = posts
    }

    private fun formatDate(date: Date): String {
        val format = SimpleDateFormat("d MMM в HH:mm", Locale("ru"))
        return format.format(date)
    }

}
