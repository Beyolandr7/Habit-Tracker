package com.aenempeh.habittracker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.aenempeh.habittracker.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val username = binding.txtUsername.text.toString().trim()
            val password = binding.txtPassword.text.toString().trim()

            if (username == "student" && password == "123") {
                Toast.makeText(requireContext(), "Login Berhasil!", Toast.LENGTH_SHORT).show()

                val action = LoginFragmentDirections.actionHabitListFragment()
                Navigation.findNavController(it).navigate(action)
            } else {
                if (username != "student") {
                    binding.username.error = "Username salah"
                } else {
                    binding.username.error = null
                }

                if (password != "123") {
                    binding.password.error = "Password salah"
                } else {
                    binding.password.error = null
                }

                Toast.makeText(requireContext(), "Gagal masuk. Cek kembali akun Anda.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}