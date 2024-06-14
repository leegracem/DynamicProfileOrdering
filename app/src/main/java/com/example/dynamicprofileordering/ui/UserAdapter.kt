package com.example.dynamicprofileordering.ui

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.dynamicprofileordering.databinding.ItemUserProfileBinding
import com.example.dynamicprofileordering.R
import com.example.dynamicprofileordering.model.Config
import com.example.dynamicprofileordering.model.User

class UserAdapter(private var user: User?, private var config: Config) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        user?.let { holder.bind(it, config) }
    }

    override fun getItemCount(): Int {
        return if (user != null) 1 else 0
    }

    fun updateUser(newUser: User) {
        user = newUser
        notifyDataSetChanged()
    }

    fun updateConfig(newConfig: Config) {
        config = newConfig
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemUserProfileBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User, config: Config) {
            binding.container.visibility = View.GONE
            binding.container.removeAllViews()

            config.profile.forEach {fieldName ->
                when (fieldName) {
                    "photo" -> addImageView(user.photo)
                    "name" -> addTextView(user.name)
                    "gender" -> addFieldWithLabel("Gender", getGenderText(user.gender))
                    "about" -> addFieldWithLabel("About", user.about)
                    "school" -> addFieldWithLabel("School", user.school)
                    "hobbies" -> addHobbiesView(user.hobbies)
                }
            }
            binding.container.visibility = View.VISIBLE
        }

        private fun addFieldWithLabel(label: String, value: String?) {
            if (!value.isNullOrEmpty()) {
                addLabelTextView(label)
                addTextView(value)
            }
        }

        private fun addLabelTextView(label: String) {
            val textView = TextView(binding.container.context)
            textView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            textView.text = "$label:"
            textView.typeface = Typeface.DEFAULT_BOLD
            textView.textSize = 24f
            binding.container.addView(textView)
        }


        private fun addTextView(text: String?) {
            val textView = TextView(binding.container.context)
            textView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            textView.text = text
            textView.textSize = 20f
            binding.container.addView(textView)
        }

        private fun getGenderText(gender: String): String {
            return when (gender.lowercase()) {
                "m" -> "Male"
                "f" -> "Female"
                else -> gender
            }
        }

        private fun addHobbiesView(hobbies: List<String>?) {
            hobbies?.let {
                if (it.isNotEmpty()) {
                    addLabelTextView("Hobbies")
                    val hobbiesText = it.joinToString(separator = ", ")
                    addTextView(hobbiesText)
                }
            }
        }

        private fun addImageView(photoUrl: String?) {
            if (!photoUrl.isNullOrEmpty()) {
                val imageView = ImageView(binding.container.context)
                val layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                imageView.layoutParams = layoutParams
                imageView.adjustViewBounds = true
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP

                val placeholderDrawable = ContextCompat.getDrawable(
                    binding.container.context,
                    R.drawable.placeholder_background
                )
                val requestOptions = RequestOptions()
                    .placeholder(placeholderDrawable)
                    .error(android.R.drawable.ic_menu_report_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)

                Glide.with(binding.container.context)
                    .load(photoUrl)
                    .apply(requestOptions)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView)
                binding.container.addView(imageView)
            }
        }
    }
}
