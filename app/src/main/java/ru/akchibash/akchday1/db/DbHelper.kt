package ru.akchibash.akchday1.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ru.akchibash.akchday1.db.PostContract.Columns

class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "akchday1.db"
        private const val DATABASE_VERSION = 1

        // SQL для создания таблицы
        private const val SQL_CREATE_POSTS =
            "CREATE TABLE ${PostContract.TABLE_NAME} (" +
                    "${Columns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${Columns.AUTHOR} TEXT NOT NULL," +
                    "${Columns.AUTHOR_ID} INTEGER NOT NULL," +
                    "${Columns.CONTENT} TEXT NOT NULL," +
                    "${Columns.PUBLISHED} TEXT NOT NULL," +
                    "${Columns.LIKED_BY_ME} INTEGER NOT NULL DEFAULT 0," +
                    "${Columns.LIKES} INTEGER NOT NULL DEFAULT 0," +
                    "${Columns.SHARES} INTEGER NOT NULL DEFAULT 0," +
                    "${Columns.VIEWS} INTEGER NOT NULL DEFAULT 0," +
                    "${Columns.VIDEO} TEXT" +
                    ")"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Создаем таблицу при первом запуске
        db.execSQL(SQL_CREATE_POSTS)

        // Здесь можно добавить начальные данные
        insertInitialData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // При обновлении версии удаляем старую таблицу и создаем новую
        // В реальном проекте здесь должна быть миграция данных
        db.execSQL("DROP TABLE IF EXISTS ${PostContract.TABLE_NAME}")
        onCreate(db)
    }

    private fun insertInitialData(db: SQLiteDatabase) {
        // Вставляем начальные посты для демонстрации
        // Пост 1 (с видео)
        val contentValues1 = android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Волейбольный клуб \"Зенит-Казань\"")
            put(Columns.AUTHOR_ID, 2)
            put(Columns.CONTENT, "Шикарная победа над принципиальным соперником! Выиграли 3:2, отыгравшись с 0:2 по партиям. Команда проявила характер. Лучшим игроком матча признан наш диагональный, набравший 28 очков. Спасибо болельщикам за поддержку!")
            put(Columns.PUBLISHED, "Сегодня в 20:45")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 9999)
            put(Columns.SHARES, 25)
            put(Columns.VIEWS, 5700)
            put(Columns.VIDEO, "https://www.youtube.com/watch?v=S-eJJwvCVPQ")
        }
        db.insert(PostContract.TABLE_NAME, null, contentValues1)

// Пост 2 (с видео)
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Волейбольный клуб \"Зенит-Казань\"")
            put(Columns.AUTHOR_ID, 3)
            put(Columns.CONTENT, "Сенсационная победа в финале Лиги чемпионов! В драматичном пятисетовом матче обыграли итальянский «Трентино» — 3:2. Решающее очко принес капитан команды. Это пятый титул в истории клуба! Спасибо всем, кто был с нами в этом невероятном сезоне!")
            put(Columns.PUBLISHED, "Вчера в 22:10")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 15200)
            put(Columns.SHARES, 340)
            put(Columns.VIEWS, 8900)
            put(Columns.VIDEO, "https://rutube.ru/video/179ba2674f5f3d8f5629cdde8444a949/")
            db.insert(PostContract.TABLE_NAME, null, this)
        }

// Пост 3 (с видео)
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Волейбольный клуб \"Зенит-Казань\"")
            put(Columns.AUTHOR_ID, 4)
            put(Columns.CONTENT, "Официально: клуб продлил контракт с лучшим связующим последних лет! Наш номер один остаётся в команде ещё на два сезона. Его видение площадки и пасы — наше главное оружие. Вместе к новым вершинам!")
            put(Columns.PUBLISHED, "2 дня назад в 14:30")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 7200)
            put(Columns.SHARES, 120)
            put(Columns.VIEWS, 4300)
            put(Columns.VIDEO, "https://youtu.be/ALMw0Pnw3Mg")
            db.insert(PostContract.TABLE_NAME, null, this)
        }

// Пост 4 (без видео)
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Волейбольный клуб \"Зенит-Казань\"")
            put(Columns.AUTHOR_ID, 5)
            put(Columns.CONTENT, "Наш доигровщик признан MVP прошедшего чемпионата России! В среднем за матч он набирал 22 очка, реализуя 65% атак. Закономерный итог фантастического сезона. Поздравляем!")
            put(Columns.PUBLISHED, "5 дней назад в 11:15")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 11100)
            put(Columns.SHARES, 210)
            put(Columns.VIEWS, 6700)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }

// Пост 5 (без видео)
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Волейбольный клуб \"Зенит-Казань\"")
            put(Columns.AUTHOR_ID, 6)
            put(Columns.CONTENT, "Сегодня команда провела открытую тренировку для юных воспитанников спортивных школ. Ребята увидели мастер-класс от звёзд, получили автографы и массу эмоций. Растим новое поколение чемпионов!")
            put(Columns.PUBLISHED, "Сегодня в 17:05")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 5300)
            put(Columns.SHARES, 85)
            put(Columns.VIEWS, 3200)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }

// Пост 6 (без видео)
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Волейбольный клуб \"Зенит-Казань\"")
            put(Columns.AUTHOR_ID, 7)
            put(Columns.CONTENT, "Золотой дубль оформлен! Помимо чемпионства, наша команда выиграла и Кубок России, обыграв в финале московское «Динамо» в трёх партиях. Идеальный сезон — 12 побед подряд в плей-офф!")
            put(Columns.PUBLISHED, "3 дня назад в 19:20")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 18400)
            put(Columns.SHARES, 460)
            put(Columns.VIEWS, 11200)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }

// Пост 7 (без видео)
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Волейбольный клуб \"Зенит-Казань\"")
            put(Columns.AUTHOR_ID, 8)
            put(Columns.CONTENT, "Наш новичок из Бразилии уже в Казани! Прошёл медосмотр и провёл первую тренировку с командой. Ждём ярких подач и мощных атак. Добро пожаловать в семью «Зенит»!")
            put(Columns.PUBLISHED, "Сегодня в 12:40")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 8900)
            put(Columns.SHARES, 210)
            put(Columns.VIEWS, 5100)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }

// Пост 8 (без видео)
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Волейбольный клуб \"Зенит-Казань\"")
            put(Columns.AUTHOR_ID, 9)
            put(Columns.CONTENT, "Рекордная посещаемость на домашнем матче! 5200 болельщиков заполнили трибуны «Казань-Арены». Такой поддержки не было с прошлого сезона. Спасибо, вы — настоящая шестая партия!")
            put(Columns.PUBLISHED, "Вчера в 23:10")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 13700)
            put(Columns.SHARES, 380)
            put(Columns.VIEWS, 8900)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }

// Пост 9 (без видео)
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Волейбольный клуб \"Зенит-Казань\"")
            put(Columns.AUTHOR_ID, 10)
            put(Columns.CONTENT, "Ветеран команды провёл юбилейный матч в форме «Зенита» — 300 официальных игр. За эти годы завоевано 12 трофеев, и это не предел! Легенда клуба продолжает бить рекорды.")
            put(Columns.PUBLISHED, "6 дней назад в 16:30")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 9500)
            put(Columns.SHARES, 210)
            put(Columns.VIEWS, 6200)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }

// Пост 10 (без видео)
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Волейбольный клуб \"Зенит-Казань\"")
            put(Columns.AUTHOR_ID, 11)
            put(Columns.CONTENT, "Детская академия «Зенит» объявляет набор мальчиков и девочек 2013–2015 годов рождения. Тренировки проводят тренеры основной команды. Стань частью системы чемпионов!")
            put(Columns.PUBLISHED, "Вчера в 10:00")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 4100)
            put(Columns.SHARES, 160)
            put(Columns.VIEWS, 2800)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }
    }
}
