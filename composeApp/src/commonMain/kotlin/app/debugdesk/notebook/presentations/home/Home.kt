package app.debugdesk.notebook.presentations.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import app.debugdesk.notebook.domain.model.Note
import app.debugdesk.notebook.utils.SharedObjects.toFormattedDate
import app.debugdesk.notebook.utils.log.Logcat
import notebook.composeapp.generated.resources.Res
import notebook.composeapp.generated.resources.icon_created_on
import notebook.composeapp.generated.resources.icon_filled_pin
import notebook.composeapp.generated.resources.icon_pin_outlined
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun Home(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    homeViewModel: HomeViewModel = koinInject()
) {
    val lazyState = rememberLazyListState()
    var selectAll by remember { mutableStateOf(false) }
    val showAllCheckedBox by homeViewModel.showAllCheckedBox.collectAsState()
    val notes by homeViewModel.notes.collectAsState()

    LaunchedEffect(notes) {
        selectAll = notes.none { !it.isSelected }
    }
    AnimatedVisibility(
        visible = notes.isNotEmpty(),
        enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
        exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
    ) {
        Column(modifier = modifier.fillMaxSize()) {
            AnimatedVisibility(visible = showAllCheckedBox) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
                    selectAll = !selectAll
                    homeViewModel.toggleSelectionForAllNote(selectAll)
                }) {
                    Checkbox(
                        checked = selectAll,
                        onCheckedChange = {
                            selectAll = it
                            homeViewModel.toggleSelectionForAllNote(it)
                        }
                    )
                    Text(text = "Select All", style = MaterialTheme.typography.titleLarge)
                }
            }

            CardList(
                lazyState = lazyState,
                notes = notes,
                showCheckBox = showAllCheckedBox,
                onPinClick = {
                    Logcat.d("Home", "onPinClick: $it")
                    homeViewModel.pinNote(it.copy(isPinned = !it.isPinned))
                },
                onSelected = {
                    Logcat.d("Home", "onSelected: $it")
                    homeViewModel.toggleSelectionForNote(it.copy(isSelected = !it.isSelected))
                },
                onLongPressed = {
                    Logcat.d("Home", "onLongPressed: $it")
                    homeViewModel.toggleSelectionForNote(it.copy(isSelected = !it.isSelected))
                    homeViewModel.enableAllCheckBox()
                },
                onClick = {
                    Logcat.d("Home", "onClick: $it")
                    homeViewModel.onNoteClick(navHostController, it)
                },
            )

        }
    }

    AnimatedVisibility(
        visible = notes.isEmpty(),
        enter = fadeIn() + scaleIn(),
        exit = fadeOut() + scaleOut()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No notes found!")
        }
    }
}


@Composable
fun CardList(
    modifier: Modifier = Modifier,
    lazyState: LazyListState = rememberLazyListState(),
    showCheckBox: Boolean,
    notes: List<Note>,
    onSelected: (Note) -> Unit = {},
    onPinClick: (Note) -> Unit = {},
    onLongPressed: (Note) -> Unit,
    onClick: (Note) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = lazyState
    ) {
        item {
            if (notes.any { it.isPinned }) {
                PinnedHeader()
            }
        }

        items(notes.filter { it.isPinned }) { note ->
            NoteBookCard(
                note = note,
                showCheckBox = showCheckBox,
                onSelected = { onSelected(note) },
                onLongPressed = { onLongPressed(note) },
                onPinClick = { onPinClick(note) },
                onClick = { onClick(note) }
            )
        }

        item {
            if (notes.any { !it.isPinned }) {
                AllNotesHeader()
            }
        }

        items(notes.filter { !it.isPinned }) { note ->
            NoteBookCard(
                note = note,
                showCheckBox = showCheckBox,
                onSelected = { onSelected(note) },
                onLongPressed = { onLongPressed(note) },
                onPinClick = { onPinClick(note) },
                onClick = { onClick(note) }
            )
        }
    }
}

@Composable
fun PinnedHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            "Pinned Notes",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.titleLarge
        )
        Icon(
            painter = painterResource(resource = Res.drawable.icon_pin_outlined),
            contentDescription = "Pinned",
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
fun AllNotesHeader() {
    Text(
        text = "All Notes",
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteBookCard(
    modifier: Modifier = Modifier,
    note: Note,
    showCheckBox: Boolean = false,
    onSelected: () -> Unit = {},
    onPinClick: () -> Unit = {},
    onLongPressed: () -> Unit,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .combinedClickable(
                onLongClick = {
                    onLongPressed()
                }, onClick = { onClick() }
            ),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(space = 4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(
                    4.dp,
                    alignment = Alignment.CenterHorizontally
                )
            ) {
                CheckBoxText(
                    modifier = Modifier.weight(1f),
                    visible = showCheckBox,
                    note = note,
                    onSelected = onSelected
                )

                AnimatedVisibility(visible = note.isPinned) {
                    Icon(
                        painter = painterResource(resource = if (note.isPinned) Res.drawable.icon_filled_pin else Res.drawable.icon_pin_outlined),
                        contentDescription = "Pinned",
                        modifier = Modifier.size(24.dp).clickable(onClick = onPinClick),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            HorizontalDivider(Modifier.fillMaxWidth())

            Text(
                text = note.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun CheckBoxText(
    modifier: Modifier = Modifier,
    visible: Boolean,
    note: Note,
    onSelected: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = 4.dp)
    ) {
        Row(
            modifier = Modifier.then(if (visible) Modifier.clickable(onClick = onSelected) else Modifier),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                4.dp,
                alignment = Alignment.Start
            )
        ) {
            AnimatedVisibility(visible = visible) {
                CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 0.dp) {
                    Checkbox(
                        checked = note.isSelected,
                        onCheckedChange = {
                            onSelected()
                        })
                }
            }
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleLarge,
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Icon(
                painter = painterResource(resource = Res.drawable.icon_created_on),
                contentDescription = "icon_created_on",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = note.createdAt.toFormattedDate(),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}