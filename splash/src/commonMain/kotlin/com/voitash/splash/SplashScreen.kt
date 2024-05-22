package com.voitash.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.voitash.splash.resources.Res
import io.github.alexzhirkevich.compottie.LottieAnimation
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SplashScreen(
    doAfterSplash: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        var compositionBytes by remember {
            mutableStateOf(ByteArray(0))
        }
        LaunchedEffect(Unit) {
            compositionBytes = Res.readBytes("files/splash.json")
        }
        val composition by rememberLottieComposition(LottieCompositionSpec.JsonString(compositionBytes.decodeToString()))
        val progress = animateLottieCompositionAsState(composition)
        LottieAnimation(
            composition = composition,
            progress = { progress.value },
        )
        if (progress.value == 1.0f) {
            doAfterSplash()
        }
    }
}