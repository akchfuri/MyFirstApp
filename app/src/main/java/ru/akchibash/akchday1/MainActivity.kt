package ru.akchibash.akchday1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import ru.akchibash.akchday1.databinding.ActivityMainBinding
import ru.akchibash.akchday1.dto.Post
import ru.akchibash.akchday1.util.FormatUtils
import java.text.DecimalFormat



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var post: Post

    val formatter = FormatUtils()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Создаем экземпляр Binding
        binding = ActivityMainBinding.inflate(layoutInflater)

        // 2. Устанавливаем корневой View как content view
        setContentView(binding.root)

        // 3. Создаем тестовые данные
        post = Post(
            id = 1,
            author = "Волейбольный клуб \"Зенит-Казань\"",
            content = "Шикарная победа над принципиальным соперником! Выиграли 3:2, отыгравшись с 0:2 по партиям. Команда проявила характер. Лучшим игроком матча признан наш диагональный, набравший 28 очков. Спасибо болельщикам за поддержку!",
            published = "Сегодня в 20:45",
            likedByMe = false,
            likes = 9999,
            shares = 25,
            views = 5700
        )

        // 4. Отображаем данные на экране
        bindPost(post)

        // 5. Обработка кликов
        setupClickListeners()
    }

    private fun bindPost(post: Post) {
        // Используем View Binding для доступа к View
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content

            // Устанавливаем текст для счетчиков с форматированием
            likeCount.text = formatter.formatCount(post.likes)
            shareCount.text = formatter.formatCount(post.shares)
            viewsCount.text = formatter.formatCount(post.views)

            // Устанавливаем правильную иконку лайка в зависимости от состояния
            if (post.likedByMe) {
                like.setImageResource(R.drawable.ic_like_favorite)
            } else {
                like.setImageResource(R.drawable.ic_favorite_border)
            }

            // Пример со ссылкой (заполняем, если есть)
            linkTitle.text = "Зенит - Локомотив: видео лучших моментов"
            linkUrl.text = "volley.ru"
        }
    }

    private fun setupClickListeners() {
        binding.apply {
            // Обработка лайка
            like.setOnClickListener {
                println("CLICK: лайк")
                // Меняем состояние
                post = post.copy(
                    likedByMe = !post.likedByMe,
                    likes = if (post.likedByMe) post.likes - 1 else post.likes + 1
                )

                // Обновляем отображение
                bindPost(post)

                // Показываем подсказку (для наглядности)
                Toast.makeText(this@MainActivity,
                    if (post.likedByMe) "Лайк поставлен" else "Лайк убран",
                    Toast.LENGTH_SHORT).show()
            }

            // Обработка репоста
            share.setOnClickListener {
                // Увеличиваем счетчик репостов на 1
                post = post.copy(
                    shares = post.shares + 1
                )

                // Обновляем отображение
                bindPost(post)

                Toast.makeText(this@MainActivity, "Репост +1", Toast.LENGTH_SHORT).show()
            }

            // Обработка меню (просто показать сообщение)
            menu.setOnClickListener {
                println("CLICK: меню")
                Toast.makeText(this@MainActivity, "Меню поста", Toast.LENGTH_SHORT).show()
            }

            // Обработка аватарки
            avatar.setOnClickListener {
                println("CLICK: аватар")
                Toast.makeText(this@MainActivity, "Профиль автора", Toast.LENGTH_SHORT).show()
            }

            // Обработка всего корневого layout (для исследования)
            root.setOnClickListener {
                println("CLICK: фон")
                Toast.makeText(this@MainActivity, "Клик по фону", Toast.LENGTH_SHORT).show()
            }

            content.setOnClickListener {
                println("CLICK: текст поста")
                Toast.makeText(this@MainActivity, "Клик по тексту поста", Toast.LENGTH_SHORT).show()
            }

        }
    }
}
