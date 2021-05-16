package br.com.chicorialabs.libghi

import android.app.Application
import br.com.chicorialabs.libghi.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@AppApplication)
            modules(viewModelModule)
        }
    }

}