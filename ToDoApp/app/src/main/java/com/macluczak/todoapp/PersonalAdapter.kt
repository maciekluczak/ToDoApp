package com.macluczak.todoapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.macluczak.todoapp.databinding.ItemHolderBinding
import com.macluczak.todoapp.databinding.PersonalHolderBinding


class PersonalAdapter : RecyclerView.Adapter<PersonalAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: PersonalHolderBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var users: List<User>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    override fun getItemCount() = users.size



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PersonalHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val user = users[position]



            name.text = user.name
            id.text = "ID: ${user.id}"
            company.text = user.company.name
            city.text = user.address.city
            email.text = user.email

            phone.text = "Phone: ${user.phone}"
            zip.text = user.address.zipcode
            suite.text = user.address.suite
            street.text = user.address.street
            lat.text = "lat: ${user.address.geo.lat}"
            lng.text = "lng: ${user.address.geo.lng}"
            web.text = "web: ${user.website}"
            phrase.text = "catch phrase: ${user.company.catchPhrase}"
            Bs.text = "Bs: ${user.company.bs}"
            username.text = user.username
        }


    }
}
