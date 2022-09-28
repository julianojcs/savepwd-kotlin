package com.apfjuliano.savepass

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

//class PasswordAdapter(private var passwordList: ArrayList<Password>)
class PasswordAdapter(var clickedItem: ClickedItem)
    : RecyclerView.Adapter<PasswordAdapter.PasswordViewHolder>(), Filterable {

    private lateinit var passwordList: ArrayList<Password>
    private lateinit var passwordListFilter: ArrayList<Password>
    var size: Int = 0
    var originalSize: Int = 0

    @SuppressLint("NotifyDataSetChanged")
    fun setData(passwordList: ArrayList<Password>){
        this.passwordList = passwordList
        this.passwordListFilter = passwordList
        originalSize = passwordList.size
        size = passwordListFilter.size
        notifyDataSetChanged()
    }

    interface ClickedItem{
        fun clickedItem(password: Password)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.password_item,
            parent,
            false
        )
        return PasswordViewHolder(view)
    }

    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {
        val password = passwordList[position]
        holder.bind(password)

        holder.itemView.setOnClickListener {
            clickedItem.clickedItem(password)
        }
    }

    override fun getItemCount(): Int {
        originalSize = if (passwordList.size > originalSize) passwordList.size else (originalSize)
        size = passwordList.size
        return passwordList.size // passwordList.count()
    }

    inner class PasswordViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val tvId: TextView = itemView.findViewById(R.id.tvId)
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvUser: TextView = itemView.findViewById(R.id.tvUser)
        private val tvPassword: TextView = itemView.findViewById(R.id.tvPassword)

        @SuppressLint("SetTextI18n")
        fun bind(password: Password) {
            tvId.text = "${password.id}: "
            tvName.text = password.name
            tvUser.text = "User: ${password.user}"
            tvPassword.text = "pwd: ${password.password}"
        }
    }

    override fun getFilter(): Filter {
        return object: Filter(){
            override fun performFiltering(text: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (text == null || text.isEmpty()) {
                    filterResults.count = passwordListFilter.size
                    filterResults.values = passwordListFilter
                } else {
                    val searchText: String = text.toString().lowercase(Locale.getDefault())
                    val passwordListResult = ArrayList<Password>()
                    for(items in passwordListFilter) {
                        if (items.name.lowercase(Locale.getDefault()).contains(searchText)) {
                            passwordListResult.add(items)
                        }
                    }
                    filterResults.count = passwordListResult.size
                    filterResults.values = passwordListResult
                }
                size = filterResults.count
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                passwordList = (results!!.values as? ArrayList<Password>)!!
                notifyDataSetChanged()
            }

        }
    }

}