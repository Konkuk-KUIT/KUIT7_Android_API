package com.example.kuit7th_api_practice.ui.post.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.kuit7th_api_practice.ui.post.state.PostEditUiState
import com.example.kuit7th_api_practice.ui.post.viewmodel.PostViewModel
import com.example.kuit7th_api_practice.ui.theme.KUIT7th_API_practiceTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostEditScreen(
    postId: Long,
    onNavigateBack: () -> Unit,
    onPostUpdated: () -> Unit,
    viewModel: PostViewModel = hiltViewModel()
) {
    val formState by viewModel.editFormState.collectAsStateWithLifecycle()
    val uiState by viewModel.editUiState.collectAsStateWithLifecycle()
    val isSaving = uiState is PostEditUiState.Saving

    LaunchedEffect(postId) {
        viewModel.loadPostForEdit(postId)
    }

    LaunchedEffect(uiState) {
        if (uiState is PostEditUiState.Success) {
            onPostUpdated()
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.selectEditImage(uri?.toString())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "게시글 수정",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로 가기")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(20.dp)
        ) {
            OutlinedTextField(
                value = formState.title,
                onValueChange = { viewModel.updateEditTitle(it) },
                label = { Text("제목") },
                placeholder = { Text("제목을 입력해주세요.") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = formState.content,
                onValueChange = { viewModel.updateEditContent(it) },
                label = { Text("내용") },
                placeholder = { Text("내용을 입력해주세요.") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                maxLines = 10,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "이미지 첨부",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )

            Spacer(modifier = Modifier.height(12.dp))

            val imageModel = formState.selectedImageUri ?: formState.originalImageUrl

            if (imageModel != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    AsyncImage(
                        model = imageModel,
                        contentDescription = "선택한 이미지",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentScale = ContentScale.Crop
                    )
                    IconButton(
                        onClick = { viewModel.selectEditImage(null) },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                    ) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "이미지 제거",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            } else {
                OutlinedButton(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("갤러리에서 이미지 선택")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.updatePost(postId) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = formState.title.isNotBlank() && formState.content.isNotBlank() && !isSaving,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "수정하기",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PostEditScreenPreview() {
    KUIT7th_API_practiceTheme {
        PostEditScreen(
            postId = 1L,
            onNavigateBack = {},
            onPostUpdated = {}
        )
    }
}