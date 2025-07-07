package com.example.proyectofinaldemviles.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.proyectofinaldemviles.R
import com.example.proyectofinaldemviles.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicializar RetrofitRepository con el contexto de la app
        com.example.proyectofinaldemoviles.repository.RetrofitRepository.init(applicationContext)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Acceso directo a categorías si hay token guardado
        val prefs = getSharedPreferences("auth_prefs", MODE_PRIVATE)
        val token = prefs.getString("access_token", null)
        if (!token.isNullOrEmpty()) {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
            val navController = navHostFragment?.navController
            val currentDest = navController?.currentDestination?.id
            if (currentDest == R.id.homeFragment || currentDest == R.id.loginFragment) {
                navController.navigate(R.id.categoriasFragment)
            }
        }
    }
}