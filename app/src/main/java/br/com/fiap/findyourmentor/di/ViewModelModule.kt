package br.com.fiap.findyourmentor.di

import br.com.fiap.findyourmentor.data.APIService
import br.com.fiap.findyourmentor.data.HomeRepository
import br.com.fiap.findyourmentor.data.HomeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun providesHomeRepository(apiService: APIService): HomeRepository {
        return HomeRepositoryImpl(apiService)
    }
}