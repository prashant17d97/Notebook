package app.debugdesk.notebook.presentations.uicomponent

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDialog(modifier: Modifier = Modifier) {
    ModalBottomSheet(onDismissRequest = { /* Executed when the sheet is dismissed */ }) {
        // Sheet content
    }

}