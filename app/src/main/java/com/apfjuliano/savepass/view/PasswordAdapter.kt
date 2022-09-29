package com.apfjuliano.savepass.view

import android.annotation.SuppressLint
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.apfjuliano.savepass.R
import com.apfjuliano.savepass.database.PasswordDao
import com.apfjuliano.savepass.database.PwdDb
import com.apfjuliano.savepass.model.Password
import java.util.*

//class PasswordAdapter(private var passwordList: ArrayList<Password>)
class PasswordAdapter(var clickedItem: ClickedItem)
    : RecyclerView.Adapter<PasswordAdapter.PasswordViewHolder>(), Filterable {

    interface ClickedItem{
        fun clickedItem(password: Password)
    }

    private fun String.asterisk(): String {
        return "".padStart(this.length, '*')
    }

    private fun String.encode(): String {
        return Base64.encodeToString(this.toByteArray(charset("UTF-8")), Base64.DEFAULT)
    }

    private fun String.decode(): String {
        return Base64.decode(this, Base64.DEFAULT).toString(charset("UTF-8"))
    }

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
        private val btnVisibility: ImageView = itemView.findViewById(R.id.btnVisibility)
        private val btnDelete: ImageView = itemView.findViewById(R.id.btnDelete)
        private lateinit var passwordDao: PasswordDao

        private fun toggleVisibility() {
            val encrypted: Boolean = tvPassword.text.toString().replace("*", "") == ""
            if (encrypted) {
                tvPassword.text = tvPassword.tag.toString().decode()
                btnVisibility.setImageResource(R.drawable.ic_visibility_on)
            } else {
                tvPassword.text = "********"
                btnVisibility.setImageResource(R.drawable.ic_visibility_off)
            }
        }

        init {
            tvPassword.setOnClickListener {
                toggleVisibility()
            }
            btnVisibility.setOnClickListener {
                toggleVisibility()
            }
            btnDelete.setOnClickListener {
                val accountName = tvName.text
                passwordDao = PwdDb.getInstance(it.context).passwordDao()
                passwordDao.deleteById(tvId.tag.toString().toInt())
                passwordList.removeAt(adapterPosition)
//                The function notifyDataSetChanged essentially considers all data in your dataset has changed. This causes all VISIBLE views using this data to be redrawn. This is unnecessary when only some data has changed.
//                You need to identify the position that data has change and notify your adapter to update only those items.
//                you can notify change of the particular position using this methods

//                notifyItemChanged(int)
//                notifyItemInserted(int)
//                notifyItemRemoved(int)
//                notifyItemRangeChanged(int, int)
//                notifyItemRangeInserted(int, int)
//                notifyItemRangeRemoved(int, int)
//                https://stackoverflow.com/questions/68602157/it-will-always-be-more-efficient-to-use-more-specific-change-events-if-you-can
                notifyItemRemoved(adapterPosition)
                Toast.makeText(it.context,"Account \"${accountName}\" deleted",Toast.LENGTH_SHORT).show()
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(password: Password) {
            tvId.text = "${password.id}: "
            tvId.tag = password.id
            tvName.text = password.name
            tvUser.text = "Username: ${password.user}"
            tvPassword.tag = password.password
            tvPassword.text = "********"
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