package ke.don.faah.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import ke.don.faah.data.FaahService
import ke.don.faah.data.JsonDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.awt.BorderLayout
import javax.swing.JLabel
import javax.swing.JPanel

class FaahToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(
        project: Project,
        toolWindow: ToolWindow
    ) {
        val db = project.getService(JsonDb::class.java)
        val faahService = FaahService(db)

        val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

        val contentFactory = ContentFactory.getInstance()

        val panel = JPanel(BorderLayout()).apply {
            add(AddEntryPanel(scope, faahService), BorderLayout.NORTH)
            add(EntryListPanel(scope, faahService), BorderLayout.CENTER)
        }
        val content = ContentFactory.getInstance().createContent(panel, "Faah", false)
        toolWindow.contentManager.addContent(content)

        // TEMP: test your file logic
        val items = faahService.getAll()
        println(items)
    }
}