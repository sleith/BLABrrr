package com.fatdino.blabrrr.injection.component

import com.fatdino.blabrrr.MyApplication
import com.fatdino.blabrrr.injection.module.ServiceModule
import com.fatdino.blabrrr.injection.module.StorageModule
import com.fatdino.blabrrr.ui.home.HomeFragmentViewModel
import com.fatdino.blabrrr.ui.inbox.InboxFragmentViewModel
import com.fatdino.blabrrr.ui.landing.LandingActivityViewModel
import com.fatdino.blabrrr.ui.login.LoginActivityViewModel
import com.fatdino.blabrrr.ui.main.MainActivityViewModel
import com.fatdino.blabrrr.ui.myposts.MyPostActivityViewModel
import com.fatdino.blabrrr.ui.notifications.NotificationsFragmentViewModel
import com.fatdino.blabrrr.ui.post.PostActivityViewModel
import com.fatdino.blabrrr.ui.search.SearchFragmentViewModel
import com.fatdino.blabrrr.ui.signup.SignUpStep1ViewModel
import com.fatdino.blabrrr.ui.signup.SignUpStep2ViewModel
import com.fatdino.blabrrr.ui.splash.SplashActivityViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ServiceModule::class), (StorageModule::class)])
interface AppComponent {

    @Component.Builder
    interface Builder {
        fun serviceModule(serviceModule: ServiceModule): Builder
        fun storageModule(storageModule: StorageModule): Builder
        fun build(): AppComponent
    }

    fun inject(application: MyApplication)

    fun inject(viewModel: MainActivityViewModel)
    fun inject(viewModel: SplashActivityViewModel)
    fun inject(viewModel: LandingActivityViewModel)
    fun inject(viewModel: LoginActivityViewModel)
    fun inject(viewModel: SignUpStep1ViewModel)
    fun inject(viewModel: SignUpStep2ViewModel)
    fun inject(viewModel: PostActivityViewModel)
    fun inject(viewModel: HomeFragmentViewModel)
    fun inject(viewModel: MyPostActivityViewModel)
    fun inject(viewModel: InboxFragmentViewModel)
    fun inject(viewModel: NotificationsFragmentViewModel)
    fun inject(viewModel: SearchFragmentViewModel)
}