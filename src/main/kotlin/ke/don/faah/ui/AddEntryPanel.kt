package ke.don.faah.ui

import ke.don.faah.data.FaahService
import ke.don.faah.model.FaahEntry
import ke.don.faah.model.FaahRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.awt.GridLayout
import java.util.UUID
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

class AddEntryPanel(
    private val scope: CoroutineScope,
    private val faahService: FaahRepository
) : JPanel(GridLayout(3, 2, 4, 4)) {

    init {
        val titleField = JTextField(20)
        val descField = JTextField(20)
        val addButton = JButton("Add")

        add(JLabel("Title:")); add(titleField)
        add(JLabel("Description:")); add(descField)
        add(JLabel()); add(addButton)

        addButton.addActionListener {
            scope.launch {
                faahService.save(
                    FaahEntry(
                        id = UUID.randomUUID().toString(),
                        title = titleField.text,
                        description = descField.text,
                    )
                )
                titleField.text = ""
                descField.text = ""
            }
        }
    }
}