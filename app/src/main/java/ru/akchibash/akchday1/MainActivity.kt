package ru.akchibash.akchday1

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import ru.akchibash.akchday1.adapter.OnPostInteractionListener
import ru.akchibash.akchday1.adapter.PostsAdapter
import ru.akchibash.akchday1.databinding.ActivityMainBinding
import ru.akchibash.akchday1.dto.Post
import ru.akchibash.akchday1.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: PostViewModel by viewModels()

    private var editingPostId: Long = 0L
    private val interactionListener = object : OnPostInteractionListener {
        override fun onLike(post: Post) {
            viewModel.likeById(post.id)
        }

        override fun onShare(post: Post) {
            viewModel.shareById(post.id)
            Toast.makeText(this@MainActivity, "Репост +1",
                Toast.LENGTH_SHORT).show()
        }
        override fun onEdit(post: Post) {
            editingPostId = post.id
            binding.contentEditText.setText(post.content)
            // безопасный вызов для length и setSelection
            binding.contentEditText.text?.let { editable ->
                binding.contentEditText.setSelection(editable.length)
            }
            binding.contentEditText.requestFocus()
            showKeyboard(binding.contentEditText)
            binding.cancelGroup.visibility = View.VISIBLE
        }
        override fun onRemove(post: Post) {
            viewModel.removeById(post.id)
            Toast.makeText(this@MainActivity, "Пост удален",
                Toast.LENGTH_SHORT).show()
        }
        override fun onAvatarClick(post: Post) {
            Toast.makeText(this@MainActivity, "Профиль: ${post.author}",
                Toast.LENGTH_SHORT).show()
            viewModel.increaseViews(post.id)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(interactionListener)
        binding.list.adapter = adapter

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.contentEditText.addTextChangedListener { text ->
            viewModel.changeContent(text.toString())
        }

        binding.save.setOnClickListener {
            val text = binding.contentEditText.text?.toString() ?: ""  // безопасное получение текста
            if (text.isBlank()) {
                Toast.makeText(this, "Введите текст поста", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (editingPostId != 0L) {
                viewModel.saveEditedPost(editingPostId, text)
                editingPostId = 0L
            } else {
                viewModel.changeContent(text)
                viewModel.save()
            }
            // безопасная очистка
            binding.contentEditText.text?.clear()
            binding.cancelGroup.visibility = View.GONE
            hideKeyboard(binding.contentEditText)
        }
        binding.cancel.setOnClickListener {
            editingPostId = 0L
            binding.contentEditText.text?.clear()
            binding.cancelGroup.visibility = View.GONE
            hideKeyboard(binding.contentEditText)
            viewModel.cancelEdit()
        }
    }
    private fun hideKeyboard(view: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as
                android.view.inputmethod.InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    private fun showKeyboard(view: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as
                android.view.inputmethod.InputMethodManager
        imm.showSoftInput(view,
            android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT)
    }
}




