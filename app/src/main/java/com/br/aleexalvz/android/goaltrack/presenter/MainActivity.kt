package com.br.aleexalvz.android.goaltrack.presenter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.br.aleexalvz.android.goaltrack.StartDestination
import com.br.aleexalvz.android.goaltrack.presenter.ui.theme.GoalTrackTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private var keepSplashScreen = true

    override fun onCreate(savedInstanceState: Bundle?) {
        // Initialize Splash Screen
        setSplashScreen()
        super.onCreate(savedInstanceState)

        // Setup viewModel
        waitStartDestination()
        mainViewModel.initializeApp()

        // Initialize view
        enableEdgeToEdge()
        setContent {
            GoalTrackTheme {
                AppNavGraph(mainViewModel = mainViewModel)
            }
        }
    }

    private fun setSplashScreen() {
        val splashscreen = installSplashScreen()
        splashscreen.setKeepOnScreenCondition { keepSplashScreen }
    }

    private fun waitStartDestination() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.startDestination.collect { destination ->
                    if (destination != StartDestination.LOADING.route) {
                        keepSplashScreen = false
                    }
                }
            }
        }
    }
}