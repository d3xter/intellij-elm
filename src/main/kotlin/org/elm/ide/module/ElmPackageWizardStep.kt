package org.elm.ide.module

import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import org.elm.ide.elmPackage.ElmPackage
import java.awt.BorderLayout
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField


class ElmPackageWizardStep(private val context: WizardContext) : ModuleWizardStep() {
    lateinit var elmPackage: ElmPackage

    private val nameTextField = JTextField()
    private val versionTextField = JTextField("1.0.0")
    private val summaryTextField = JTextField()
    private val repositoryTextField = JTextField("http://github.com/user/project.git")
    private val licenseTextField = JTextField("BSD3")
    private val elmVersionTextField = JTextField("0.18.0 <= v < 0.19.0")

    override fun updateDataModel() {
        elmPackage = ElmPackage(
                name = nameTextField.text,
                version = versionTextField.text,
                summary = summaryTextField.text,
                repository = repositoryTextField.text,
                license = licenseTextField.text,
                elmVersion = elmVersionTextField.text
        )

        context.defaultModuleName = nameTextField.text

        context.projectName = "Blabla Blub"
    }

    override fun getComponent(): JComponent {
        val panelPane = JPanel(BorderLayout())
        val contentPanel = JPanel(GridBagLayout())
        panelPane.add(contentPanel, BorderLayout.NORTH)


        val headerLabel = JLabel("Elm Package:")
        val headerConstraints = getLabelConstraints(0).apply {
            gridwidth = 2
            anchor = GridBagConstraints.LINE_START
        }

        headerLabel.font = headerLabel.font.deriveFont(headerLabel.font.size)
        contentPanel.add(headerLabel, headerConstraints)

        //Name
        val nameLabel = JLabel("Name:");
        contentPanel.add(nameLabel, getLabelConstraints(1))

        contentPanel.add(nameTextField, getTextFieldConstraints(1))


        //Version
        val versionLabel = JLabel("Version:");
        contentPanel.add(versionLabel, getLabelConstraints(2))

        contentPanel.add(versionTextField, getTextFieldConstraints(2))

        //Summary
        val summaryLabel = JLabel("Summary:");
        contentPanel.add(summaryLabel, getLabelConstraints(3))

        contentPanel.add(summaryTextField, getTextFieldConstraints(3))

        //Repository
        val repositoryLabel = JLabel("Repository:");
        contentPanel.add(repositoryLabel, getLabelConstraints(4))

        contentPanel.add(repositoryTextField, getTextFieldConstraints(4))

        //License
        val licenseLabel = JLabel("License:");
        contentPanel.add(licenseLabel, getLabelConstraints(5))

        contentPanel.add(licenseTextField, getTextFieldConstraints(5))

        //Elm-Version
        val elmVersionLabel = JLabel("Elm-Version:");
        contentPanel.add(elmVersionLabel, getLabelConstraints(6))

        contentPanel.add(elmVersionTextField, getTextFieldConstraints(6))

        return panelPane
    }

    private fun getLabelConstraints(row: Int) = getGridBagConstrains(row).apply {
        fill = GridBagConstraints.VERTICAL
        anchor = GridBagConstraints.LINE_START
        gridx = 0
    }

    private fun getTextFieldConstraints(row: Int) = getGridBagConstrains(row).apply {
        fill = GridBagConstraints.HORIZONTAL
        weightx = 1.0
        gridx = 1
    }

    private fun getGridBagConstrains(row: Int) = GridBagConstraints().apply {
        insets = Insets(5, 5, 5, 5)

        gridy = row
    }
}