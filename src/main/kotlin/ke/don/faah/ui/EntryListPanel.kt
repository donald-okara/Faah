package ke.don.faah.ui

import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBScrollPane
import ke.don.faah.model.FaahRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.awt.BorderLayout
import javax.swing.DefaultListModel
import javax.swing.JPanel
import javax.swing.SwingUtilities

class EntryListPanel(
    scope: CoroutineScope,
    faahService: FaahRepository
) : JPanel(BorderLayout()) {

    init {
        val listModel = DefaultListModel<String>()
        val entryList = JBList(listModel)

        add(JBScrollPane(entryList), BorderLayout.CENTER)

        scope.launch {
            faahService.getAll().collect { entries ->
                SwingUtilities.invokeLater {
                    listModel.clear()
                    entries.forEach { listModel.addElement("${it.title} — ${it.description}") }
                }
            }
        }
    }
}