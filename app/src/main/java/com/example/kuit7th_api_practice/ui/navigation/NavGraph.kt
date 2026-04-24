package com.example.kuit7th_api_practice.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.kuit7th_api_practice.ui.post.screen.PostCreateScreen
import com.example.kuit7th_api_practice.ui.post.screen.PostDetailScreen
import com.example.kuit7th_api_practice.ui.post.screen.PostEditScreen
import com.example.kuit7th_api_practice.ui.post.screen.PostListScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: Any = PostListRoute
) {
    // TODO: 공용 PostViewModel을 만들기
    // 각 Screen에 같은 ViewModel 인스턴스를 전달해보세요
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<PostListRoute> {
            PostListScreen(
                onPostClick = { postId ->
                    navController.navigate(PostDetailRoute(postId))
                },
                onCreatePostClick = {
                    navController.navigate(PostCreateRoute)
                }
            )
        }

        composable<PostDetailRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<PostDetailRoute>()

            PostDetailScreen(
                postId = route.postId,
                onNavigateBack = { navController.popBackStack() },
                onEditClick = { postId ->
                    navController.navigate(PostEditRoute(postId))
                }
            )
        }

        composable<PostCreateRoute> {
            PostCreateScreen(
                onNavigateBack = { navController.popBackStack() },
                onPostCreated = { navController.popBackStack() }
            )
        }

        composable<PostEditRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<PostEditRoute>()

            PostEditScreen(
                postId = route.postId,
                onNavigateBack = { navController.popBackStack() },
                onPostUpdated = { navController.popBackStack() }
            )
        }
    }
}
