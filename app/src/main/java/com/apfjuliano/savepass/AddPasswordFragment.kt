package com.apfjuliano.savepass

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout

class AddPasswordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_password, container, false)
        val button = view.findViewById<Button>(R.id.btnSaveNewPassword)
        val txtAccountName = view.findViewById<TextInputLayout>(R.id.txtName)
        val txtUsername = view.findViewById<TextInputLayout>(R.id.txtUsername)
        val txtPassword = view.findViewById<TextInputLayout>(R.id.txtPassword)

        button.setOnClickListener {

            findNavController().navigate(R.id.action_passFragment_to_homeFragment)
        }

        return view
    }

}