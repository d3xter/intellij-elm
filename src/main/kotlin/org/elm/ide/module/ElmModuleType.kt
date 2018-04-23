package org.elm.ide.module

import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.module.ModuleTypeManager
import org.elm.MODULE_TYPE
import org.elm.ide.icons.ElmIcons


class ElmModuleType: ModuleType<ElmModuleBuilder>(MODULE_TYPE) {
    override fun getDescription() = "Elm Modules are used to build Elm projects"

    override fun getName() = "Elm Module"

    override fun getNodeIcon(isOpened: Boolean) = ElmIcons.LOGO

    override fun getIcon() = ElmIcons.LOGO

    override fun createModuleBuilder() = ElmModuleBuilder()

    /*override fun createWizardSteps(wizardContext: WizardContext, moduleBuilder: ElmModuleBuilder, modulesProvider: ModulesProvider): Array<ModuleWizardStep> {
        return arrayOf(object : ModuleWizardStep() {
            override fun updateDataModel() {
            }

            override fun getComponent(): JComponent {
                return JLabel("Put your content here");
            }
        })
    }*/

    companion object {
        val instance: ElmModuleType by lazy { ModuleTypeManager.getInstance().findByID(MODULE_TYPE) as ElmModuleType }
    }
}
