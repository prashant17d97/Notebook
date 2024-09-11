package app.debugdesk.notebook.presentations.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import app.debugdesk.notebook.datamodel.Note
import notebook.composeapp.generated.resources.Res
import notebook.composeapp.generated.resources.icon_pinned
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
    Column(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(visible = showAllCheckedBox) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
                selectAll = !selectAll
                homeViewModel.markSelectAllNote(selectAll)
            }) {
                Checkbox(
                    checked = selectAll,
                    onCheckedChange = {
                        selectAll = !selectAll
                        homeViewModel.markSelectAllNote(selectAll)
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
                homeViewModel.modifiedNote(it)
            },
            onSelected = {
                homeViewModel.modifiedNote(it)
            },
            onLongPressed = {
                homeViewModel.modifiedNote(it)
                homeViewModel.enableAllCheckBox()
            },
            onClick = {
                homeViewModel.onNoteClick(navHostController, it)
            },
        )

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
                onSelected = onSelected,
                onLongPressed = onLongPressed,
                onPinClick = onPinClick,
                onClick = onClick
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
                onSelected = onSelected,
                onLongPressed = onLongPressed,
                onClick = onClick
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
            painter = painterResource(resource = Res.drawable.icon_pinned),
            contentDescription = "Pinned",
            modifier = Modifier.rotate(90f)
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
    onSelected: (Note) -> Unit = {},
    onPinClick: (Note) -> Unit = {},
    onLongPressed: (Note) -> Unit,
    onClick: (Note) -> Unit,
) {


    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .combinedClickable(
                onLongClick = {
                    onLongPressed(
                        note.copy(
                            isSelected = true,
                        )
                    )
                },
                onClick = { onClick(note) }
            ),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(space = 4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    4.dp,
                    alignment = Alignment.CenterHorizontally
                )
            ) {
                CheckBoxText(
                    modifier = Modifier.weight(1f),
                    note = note,
                    visible = showCheckBox,
                    onSelected = onSelected,
                    onClick = onClick
                )

                AnimatedVisibility(visible = note.isPinned) {
                    Icon(
                        painter = painterResource(resource = Res.drawable.icon_pinned),
                        contentDescription = "Pinned",
                        modifier = Modifier.rotate(90f)
                            .clickable {
                                onPinClick(
                                    note.copy(
                                        isPinned = false,
                                        isSelected = false
                                    )
                                )
                            }
                    )
                }
            }

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
    onSelected: (Note) -> Unit,
    onClick: (Note) -> Unit,
) {
    Row(
        modifier = modifier.clickable {
            if (visible) {
                onSelected(
                    note.copy(isSelected = !note.isSelected)
                )
            } else {
                onClick(note)
            }

        },
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
                        onSelected(note.copy(isSelected = it))
                    })
            }
        }
        Text(
            text = note.title,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}