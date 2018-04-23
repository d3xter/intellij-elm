package org.elm.ide.module

import com.intellij.ide.util.projectWizard.ModuleBuilder
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.Disposable
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.vfs.LocalFileSystem
import org.elm.ide.elmPackage.ElmPackage
import org.elm.ide.elmPackage.writeToFile
import java.io.File

class ElmModuleBuilder : ModuleBuilder() {
    private lateinit var wizardStep: ElmPackageWizardStep

    private lateinit var elmPackage: ElmPackage

    private var contentPath: String? = null

    override fun getModuleType(): ModuleType<*> {
        return ElmModuleType.instance
    }

    fun initializeModule(pkg: ElmPackage) {
        contentPath = contentEntryPath

        File(contentPath).mkdirs()

        pkg.sourceDirectories.forEach { sourceDir ->
            val file = File(contentPath, sourceDir)
            if(!file.exists()) {
                file.mkdirs()
            }
        }

        elmPackage = pkg
    }

    override fun setupRootModel(modifiableRootModel: ModifiableRootModel) {
        contentEntryPath?.let { contentPath ->

            val localFileSystem = LocalFileSystem.getInstance()
            val moduleContentRoot = localFileSystem.refreshAndFindFileByPath(contentPath)
                    ?: return

            val contentEntry = modifiableRootModel.addContentEntry(moduleContentRoot)

            initializeModule(wizardStep.elmPackage)

            elmPackage.sourceDirectories.forEach { dir ->
                val sourceDir = localFileSystem.refreshAndFindFileByPath(File(contentPath, dir).canonicalPath)!!

                contentEntry.addSourceFolder(sourceDir, false)
            }

            wizardStep.elmPackage.writeToFile(contentPath)
        }
    }

    override fun getCustomOptionsStep(context: WizardContext, parentDisposable: Disposable?): ModuleWizardStep? {
        wizardStep = ElmPackageWizardStep(context)

        return wizardStep
    }
}