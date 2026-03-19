package ke.don.faah.data

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import javax.swing.JLabel
import javax.swing.JPanel

class FaahToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(
        project: Project,
        toolWindow: ToolWindow
    ) {
        val db = project.getService(JsonDb::class.java)
        val faahService = FaahService(db)

        val contentFactory = ContentFactory.getInstance()

        val panel = JPanel()
        panel.add(JLabel("Faah is alive"))

        val content = contentFactory.createContent(panel, "", false)
        toolWindow.contentManager.addContent(content)

        // TEMP: test your file logic
        val items = faahService.getAll()
        println(items)
    }
}