package ru.akchibash.akchday1.adapter
import ru.akchibash.akchday1.dto.Post
interface OnPostInteractionListener {
    fun onLike(post: Post) {}
    fun onShare(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onAvatarClick(post: Post) {}
    fun onPostClick(post: Post) {}  // новый метод для клика на карточку
}

