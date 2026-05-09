package com.example.kuit7th_api_practice.data.repository

import com.example.kuit7th_api_practice.ui.post.state.PostCreateFormState

interface temporarySaveRepository {

    suspend fun getForm(): PostCreateFormState

    suspend fun saveForm(form: PostCreateFormState)

    suspend fun clearForm()
}