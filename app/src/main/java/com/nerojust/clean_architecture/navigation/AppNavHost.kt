package com.nerojust.clean_architecture.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nerojust.clean_architecture.feature.repodetail.RepoDetailScreen
import com.nerojust.clean_architecture.feature.repolist.RepoListScreen

private const val ROUTE_REPO_LIST = "repoList"
private const val ROUTE_REPO_DETAIL = "repoDetail/{owner}/{name}"

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = ROUTE_REPO_LIST) {
        composable(ROUTE_REPO_LIST) {
            RepoListScreen(onRepoClick = { owner, name ->
                navController.navigate("repoDetail/$owner/$name")
            })
        }
        composable(ROUTE_REPO_DETAIL) { backStackEntry ->
            val owner = backStackEntry.arguments?.getString("owner").orEmpty()
            val name = backStackEntry.arguments?.getString("name").orEmpty()
            RepoDetailScreen(owner = owner, name = name)
        }
    }
}
