package com.emelyanov.vegocity.modules.main.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compose.VegoCityTheme
import com.emelyanov.vegocity.R
import com.emelyanov.vegocity.navigation.main.MainDestinations
import com.emelyanov.vegocity.navigation.main.MainNavHolder
import kotlinx.coroutines.launch

const val NAV_BAR_HEIGHT = 80

@ExperimentalMaterial3Api
@Composable
fun VegoNavBar(
    modifier: Modifier = Modifier,
    mainNavController: NavController,
    onCartClick: () -> Unit
) {
    val backStackEntry = mainNavController.currentBackStackEntryAsState()

    val mainNavHolder: MainNavHolder = hiltViewModel()

    Surface(
        modifier = modifier.fillMaxSize(),
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp),
            ) {
                VegoNavBarItem(
                    painter = painterResource(id = R.drawable.ic_catalog),
                    contentDescription = "Catalog button",
                    caption = "Каталог",
                    isSelected = { backStackEntry.value?.destination?.route == MainDestinations.Catalog.route },
                    onClick = {
                        mainNavHolder.mainNavProvider.requestNavigate(MainDestinations.Catalog)
                    }
                )

                VegoNavBarItem(
                    painter = painterResource(id = R.drawable.ic_favorite),
                    contentDescription = "Favorites button",
                    caption = "Избранное",
                    isSelected = { backStackEntry.value?.destination?.route == MainDestinations.Favorites.route },
                    onClick = {
                        mainNavHolder.mainNavProvider.requestNavigate(MainDestinations.Favorites)
                    }
                )

                VegoNavBarItem(
                    painter = painterResource(id = R.drawable.ic_info),
                    contentDescription = "Info button",
                    caption = "О нас",
                    isSelected = { backStackEntry.value?.destination?.route == MainDestinations.AboutUs.route },
                    onClick = {
                        mainNavHolder.mainNavProvider.requestNavigate(MainDestinations.AboutUs)
                    }
                )
            }
            Button(
                modifier = Modifier
                    .padding(12.dp)
                    .size(56.dp)
                    .align(Alignment.CenterVertically),
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(0.dp),
                onClick = onCartClick
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_cart),
                    contentDescription = "Cart icon",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
private fun RowScope.VegoNavBarItem(
    painter: Painter,
    contentDescription: String,
    caption: String,
    onClick: () -> Unit = {},
    isSelected: () -> Boolean = { false }
) {
    var selectorAlpha by remember { mutableStateOf(0f) }
    var selectorWidth by remember { mutableStateOf(30f) }
    val color = animateColorAsState(targetValue = if(isSelected()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant)

    LaunchedEffect(key1 = isSelected()) {
        launch {
            if(isSelected()) {
                animate(
                    initialValue = selectorAlpha,
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = 75
                    )
                ) { value, _  ->
                    selectorAlpha = value
                }
                animate(
                    initialValue = selectorWidth,
                    targetValue = 56f,
                    animationSpec = spring()
                ) { value, _  ->
                    selectorWidth = value
                }
            } else {
                animate(
                    initialValue = selectorWidth,
                    targetValue = 30f,
                    animationSpec = tween(
                        durationMillis = 75
                    )
                ) { value, _  ->
                    selectorWidth = value
                }
                animate(
                    initialValue = selectorAlpha,
                    targetValue = 0f,
                    animationSpec = spring()
                ) { value, _  ->
                    selectorAlpha = value
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .weight(1f)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .height(48.dp)
                .sizeIn(minWidth = 56.dp)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .height(26.dp)
                    .width(selectorWidth.dp)
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = selectorAlpha))
            )

            Icon(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = 1.dp),
                painter = painter,
                contentDescription = contentDescription,
                tint = color.value
            )

            Text(
                modifier = Modifier.align(Alignment.BottomCenter),
                text = caption,
                style = MaterialTheme.typography.labelMedium
                    .copy(
                        color = color.value,
                        fontWeight = if(isSelected()) FontWeight.SemiBold else FontWeight.Medium
                    ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@ExperimentalMaterial3Api
@Composable
@Preview
private fun Preview() {
    VegoCityTheme {
        VegoNavBar(
            modifier = Modifier.height(NAV_BAR_HEIGHT.dp),
            mainNavController = rememberNavController(),
            onCartClick = {}
        )
    }
}