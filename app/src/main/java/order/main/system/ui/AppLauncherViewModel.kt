package order.main.system.ui

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import order.main.foundation.ioScope
import order.main.foundation.runOnIO
import order.main.user.UserAccountLocalRepository
import order.main.user.data.UserAccount

class AppLauncherViewModel(
    private val userAccountRepository: UserAccountLocalRepository
) : ViewModel() {

    val userAccount = this.userAccountRepository.userAccount
        .map<UserAccount, LauncherUiState> { LauncherUiState.Success(it) }
        .catch { it.printStackTrace(); emit(LauncherUiState.Error(it)) }
        .stateIn(ioScope, SharingStarted.Lazily, LauncherUiState.Loading)

    fun resetAppState() {
        runOnIO {
            this.userAccountRepository.clear()
        }
    }
}

@Stable
sealed class LauncherUiState {
    data object Loading : LauncherUiState()
    data class Success(val data: UserAccount) : LauncherUiState()
    data class Error(val throwable: Throwable) : LauncherUiState()
}