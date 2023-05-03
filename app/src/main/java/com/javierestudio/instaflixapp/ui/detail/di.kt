package com.javierestudio.instaflixapp.ui.detail

import androidx.lifecycle.SavedStateHandle
import com.javierestudio.instaflixapp.di.annotations.ProgramId
import com.javierestudio.instaflixapp.di.annotations.ProgramType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DetailViewModelModule {

    @Provides
    @ViewModelScoped
    @ProgramId
    fun provideProgramId(savedStateHandle: SavedStateHandle) =
        DetailFragmentArgs.fromSavedStateHandle(savedStateHandle).programId

    @Provides
    @ViewModelScoped
    @ProgramType
    fun provideProgramType(savedStateHandle: SavedStateHandle) =
        DetailFragmentArgs.fromSavedStateHandle(savedStateHandle).programType

}
