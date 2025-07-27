package order.main.login.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Modifier

object LoginStaticModifier {

    object LoginScreen {

        val ContainerModifier = Modifier
            .statusBarsPadding()
            .imePadding()
            .navigationBarsPadding()
            .fillMaxSize()
    }
}