package com.example.kuit7th_api_practice.data.repositoryimpl

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.kuit7th_api_practice.data.repository.temporarySaveRepository
import com.example.kuit7th_api_practice.ui.post.state.PostCreateFormState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

val Context.tsDatastore by preferencesDataStore("temporary_save")//temporary_save라는 저장소를 만들고 tsDatastore로 접근할수 있게 함
//키-벨류값으로 간단히 저장하기 위해 preferencesdatastore사용
private val AUTHOR_KEY =
    stringPreferencesKey("author")//datastore에서 author라는 이름의 값을 안전하게 사용하기 위한 객체

private val TITLE_KEY =
    stringPreferencesKey("title")

private val CONTENT_KEY =
    stringPreferencesKey("content")

class temporarySaveRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context//ApplicationContext타임의 context주입
) : temporarySaveRepository {
    override suspend fun getForm(): PostCreateFormState {
        val savedForm = context.tsDatastore.data.first()//datastore의 data flow에 현재 저장된 값을 한번만 가져옴

        return PostCreateFormState(
            author = savedForm[AUTHOR_KEY] ?: "",
            title = savedForm[TITLE_KEY] ?: "",
            content = savedForm[CONTENT_KEY] ?: ""
        )
    }

    override suspend fun saveForm(form: PostCreateFormState) {
        context.tsDatastore.edit {//datastore의 값 수정
            it[AUTHOR_KEY] = form.author
            it[TITLE_KEY] = form.title
            it[CONTENT_KEY] = form.content
        }
    }

    override suspend fun clearForm() {
        context.tsDatastore.edit {
            it.remove(AUTHOR_KEY)
            it.remove(TITLE_KEY)
            it.remove(CONTENT_KEY)
        }
    }
}