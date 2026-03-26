package ru.akchibash.akchday1.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.akchibash.akchday1.dto.Post
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//import java.util.

class PostRepositoryFileImpl(
    private val context: Context
) : PostRepository {

    private val gson = Gson()
    private val filename = "posts.json"

    // Тип для десериализации списка постов
    private val type = object : TypeToken<List<Post>>() {}.type

    // Счетчик для генерации ID
    private var nextId = 1L

    // Текущий пользователь
    private val currentUserId = 1L
    private val currentUserName = "Я"

    // Данные в памяти
    private var posts = emptyList<Post>()
    private val _data = MutableLiveData(posts)

    init {
        // При создании репозитория пытаемся загрузить данные из файла
        loadData()
    }

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
        saveData()
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
        saveData()
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
        saveData()
    }

    override fun save(post: Post) {
        posts = if (post.id == 0L) {
            // Создание нового поста
            val newPost = post.copy(
                id = generateNextId(),
                author = currentUserName,
                authorId = currentUserId,
                published = formatDate(Date()),
                likedByMe = false,
                likes = 0,
                shares = 0,
                views = 0
            )
            listOf(newPost) + posts
        } else {
            // Обновление существующего поста
            posts.map { existingPost ->
                if (existingPost.id == post.id) {
                    existingPost.copy(content = post.content)
                } else {
                    existingPost
                }
            }
        }
        _data.value = posts
        saveData()
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        _data.value = posts
        saveData()
    }


    //Загрузка данных из файла

    private fun loadData() {
        val file = getPostsFile()
        if (!file.exists()) {
            // Если файла нет, создаем начальные данные
            createInitialData()
            saveData()
            return
        }

        try {
            context.openFileInput(filename).bufferedReader().use { reader ->
                val loadedPosts: List<Post> = gson.fromJson(reader, type)
                if (loadedPosts.isNotEmpty()) {
                    posts = loadedPosts
                    // Вычисляем следующий ID на основе максимального существующего
                    nextId = (posts.maxOfOrNull { it.id } ?: 0) + 1
                    _data.value = posts
                } else {
                    createInitialData()
                    saveData()
                }
            }
        } catch (e: Exception) {
            // В случае ошибки создаем начальные данные
            e.printStackTrace()
            createInitialData()
            saveData()
        }
    }


    //Сохранение данных в файл

    private fun saveData() {
        try {
            context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use { writer ->
                gson.toJson(posts, writer)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    //Получение файла для хранения постов

    private fun getPostsFile(): File = context.filesDir.resolve(filename)


    //Создание начальных данных при первом запуске

    private fun createInitialData() {
        posts = listOf(
            Post(
                id = generateNextId(),
                    author = "Волейбольный клуб \"Зенит-Казань\"",
                    authorId = 2,
                    content = "Шикарная победа над принципиальным соперником! Выиграли 3:2, отыгравшись с 0:2 по партиям. Команда проявила характер. Лучшим игроком матча признан наш диагональный, набравший 28 очков. Спасибо болельщикам за поддержку!",
                    published = "Сегодня в 20:45",
                    likedByMe = false,
                    likes = 9999,
                    shares = 25,
                    views = 5700,
                    video = "https://www.youtube.com/watch?v=S-eJJwvCVPQ"
                ),
                Post(
                    id = generateNextId(),
                    author = "Волейбольный клуб \"Зенит-Казань\"",
                    authorId = 3,
                    content = "Сенсационная победа в финале Лиги чемпионов! В драматичном пятисетовом матче обыграли итальянский «Трентино» — 3:2. Решающее очко принес капитан команды. Это пятый титул в истории клуба! Спасибо всем, кто был с нами в этом невероятном сезоне!",
                    published = "Вчера в 22:10",
                    likedByMe = false,
                    likes = 15200,
                    shares = 340,
                    views = 8900,
                    video = "https://rutube.ru/video/179ba2674f5f3d8f5629cdde8444a949/"
                ),
                Post(
                    id = generateNextId(),
                    author = "Волейбольный клуб \"Зенит-Казань\"",
                    authorId = 4,
                    content = "Официально: клуб продлил контракт с лучшим связующим последних лет! Наш номер один остаётся в команде ещё на два сезона. Его видение площадки и пасы — наше главное оружие. Вместе к новым вершинам!",
                    published = "2 дня назад в 14:30",
                    likedByMe = false,
                    likes = 7200,
                    shares = 120,
                    views = 4300,
                    video = "https://youtu.be/ALMw0Pnw3Mg"
                ),
                Post(
                    id = generateNextId(),
                    author = "Волейбольный клуб \"Зенит-Казань\"",
                    authorId = 5,
                    content = "Наш доигровщик признан MVP прошедшего чемпионата России! В среднем за матч он набирал 22 очка, реализуя 65% атак. Закономерный итог фантастического сезона. Поздравляем!",
                    published = "5 дней назад в 11:15",
                    likedByMe = false,
                    likes = 11100,
                    shares = 210,
                    views = 6700,
                    video = null
                ),
                Post(
                    id = generateNextId(),
                    author = "Волейбольный клуб \"Зенит-Казань\"",
                    authorId = 6,
                    content = "Сегодня команда провела открытую тренировку для юных воспитанников спортивных школ. Ребята увидели мастер-класс от звёзд, получили автографы и массу эмоций. Растим новое поколение чемпионов!",
                    published = "Сегодня в 17:05",
                    likedByMe = false,
                    likes = 5300,
                    shares = 85,
                    views = 3200,
                    video = null
                ),
                Post(
                    id = generateNextId(),
                    author = "Волейбольный клуб \"Зенит-Казань\"",
                    authorId = 7,
                    content = "Золотой дубль оформлен! Помимо чемпионства, наша команда выиграла и Кубок России, обыграв в финале московское «Динамо» в трёх партиях. Идеальный сезон — 12 побед подряд в плей-офф!",
                    published = "3 дня назад в 19:20",
                    likedByMe = false,
                    likes = 18400,
                    shares = 460,
                    views = 11200,
                    video = null
                ),
                Post(
                    id = generateNextId(),
                    author = "Волейбольный клуб \"Зенит-Казань\"",
                    authorId = 8,
                    content = "Наш новичок из Бразилии уже в Казани! Прошёл медосмотр и провёл первую тренировку с командой. Ждём ярких подач и мощных атак. Добро пожаловать в семью «Зенит»!",
                    published = "Сегодня в 12:40",
                    likedByMe = false,
                    likes = 8900,
                    shares = 210,
                    views = 5100,
                    video = null
                ),
                Post(
                    id = generateNextId(),
                    author = "Волейбольный клуб \"Зенит-Казань\"",
                    authorId = 9,
                    content = "Рекордная посещаемость на домашнем матче! 5200 болельщиков заполнили трибуны «Казань-Арены». Такой поддержки не было с прошлого сезона. Спасибо, вы — настоящая шестая партия!",
                    published = "Вчера в 23:10",
                    likedByMe = false,
                    likes = 13700,
                    shares = 380,
                    views = 8900,
                    video = null
                ),
                Post(
                    id = generateNextId(),
                    author = "Волейбольный клуб \"Зенит-Казань\"",
                    authorId = 10,
                    content = "Ветеран команды провёл юбилейный матч в форме «Зенита» — 300 официальных игр. За эти годы завоевано 12 трофеев, и это не предел! Легенда клуба продолжает бить рекорды.",
                    published = "6 дней назад в 16:30",
                    likedByMe = false,
                    likes = 9500,
                    shares = 210,
                    views = 6200,
                    video = null
                ),
                Post(
                    id = generateNextId(),
                    author = "Волейбольный клуб \"Зенит-Казань\"",
                    authorId = 11,
                    content = "Детская академия «Зенит» объявляет набор мальчиков и девочек 2013–2015 годов рождения. Тренировки проводят тренеры основной команды. Стань частью системы чемпионов!",
                    published = "Вчера в 10:00",
                    likedByMe = false,
                    likes = 4100,
                    shares = 160,
                    views = 2800,
                    video = null
                ),
        )
        _data.value = posts
    }


    //Генерация следующего ID

    private fun generateNextId(): Long = nextId++


    //Форматирование даты

    private fun formatDate(date: Date): String {
        val format = SimpleDateFormat("d MMM в HH:mm", Locale("ru"))
        return format.format(date)
    }
}
