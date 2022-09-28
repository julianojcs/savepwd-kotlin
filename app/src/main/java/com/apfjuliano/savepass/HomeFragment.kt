package com.apfjuliano.savepass

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment(), PasswordAdapter.ClickedItem {
    private lateinit var recyclerView: RecyclerView
    private lateinit var passwordAdapter: PasswordAdapter
    private lateinit var passwordList: ArrayList<Password>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        val button = view.findViewById<Button>(R.id.btnPlus)
        val edtSearch = view.findViewById<EditText>(R.id.edtSearch)
        val tvTotal = view.findViewById<TextView>(R.id.tvTotal)
        val context = requireContext()

        edtSearch.maxWidth = Int.MAX_VALUE

        button.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_passFragment)
        }

        //Data
        passwordList = ArrayList()
        passwordList.add(Password(1,"Gmail account", "apfjuliano@gmail.com", "galo12345"))
        passwordList.add(Password(2,"LinkedIn", "apfjuliano@gmail.com", "l1nkp4rK"))
        passwordList.add(Password(3,"Facebook", "julianocosta.usa@gmail.com", "F4c3b00k."))
        passwordList.add(Password(4,"WhatsApp", "27981330708", "12345678"))
        passwordList.add(Password(5,"Instagram", "apfjuliano@gmail.com", "1n5t49876"))
        passwordList.add(Password(6,"www.amazon.com.br", "apfjuliano@gmail.com", "963852"))
        passwordList.add(Password(7,"Apple Id", "apfjuliano@gmail.com", "963852"))
        passwordList.add(Password(8,"Azul Airlines", "apfjuliano@gmail.com", "963852"))
        passwordList.add(Password(9,"Binance Wallet", "apfjuliano@gmail.com", "B1n4nc3"))
        passwordList.add(Password(10,"Cartão de Crédito Ourocard", "", "1234"))
        passwordList.add(Password(11,"www.mercadolivre.com.br", "julianocosta.usa@gmail.com", "m3345ki"))

        //RecyclerView + Item
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        passwordAdapter = PasswordAdapter(this)
        passwordAdapter.setData(passwordList)
        recyclerView.adapter = passwordAdapter

        tvTotal.text = "Total of ${ passwordAdapter.itemCount.toString() } items"

        return view
    }

    override fun clickedItem(password: Password) {
        findNavController().navigate(R.id.action_homeFragment_to_editPasswordFragment)

        Log.e("TAG", "====> ${password.name}")
    }

}