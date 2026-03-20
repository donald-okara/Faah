package ke.don.faah.ui

import androidx.compose.ui.awt.ComposePanel
import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import ke.don.faah.data.FaahService
import ke.don.faah.data.JsonDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

class FaahToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(
        project: Project,
        toolWindow: ToolWindow
    ) {
        val db = project.getService(JsonDb::class.java)
        val faahService = FaahService(db)

        val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

        val contentFactory = ContentFactory.getInstance()

        val composePanel = ComposePanel().apply {
            setContent {
                FaahComposeView(faahService)
            }
        }

        val content = contentFactory.createContent(composePanel, "Faah", false).apply {
            setDisposer(Disposable { scope.cancel() })
        }
        toolWindow.contentManager.addContent(content)
    }
}