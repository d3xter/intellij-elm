package org.elm.ide.elmPackage

import org.elm.ELM_PACKAGE_FILE_NAME
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileWriter


data class ElmPackage(
        val name: String,
        val version: String,
        val summary: String,
        val repository: String,
        val license: String,
        val sourceDirectories: List<String> = listOf("src"),
        val dependencies: Map<String, String> = mapOf(),
        val exposedModules: List<String> = listOf(),
        val elmVersion: String
)


fun ElmPackage.writeToFile(folder: String) {
    val jsonObject = JSONObject().apply {
        put("name", name)
        put("version", version)
        put("summary", summary)
        put("repository", repository)
        put("license", license)

        put("source-directories", JSONArray(sourceDirectories))

        val dependencyJsonObject = JSONObject()
        dependencies.forEach { name, version ->
            dependencyJsonObject.put(name, version)
        }

        put("dependencies", dependencyJsonObject)

        put("exposed-modules", JSONArray(exposedModules))

        put("elm-version", elmVersion)
    }

    val file = File(folder, ELM_PACKAGE_FILE_NAME)

    jsonObject
            .write(FileWriter(file), 4, 0)
            .close()
}

fun parseElmPackageFromFile(file: File): ElmPackage {
    val packageJSONContent = file.readText()

    val jsonObject = JSONObject(packageJSONContent)

    val dependencies = jsonObject.getJSONObject("dependencies")

    return ElmPackage(
            name = jsonObject.getString("name"),
            version = jsonObject.getString("version"),
            summary = jsonObject.getString("summary"),
            repository = jsonObject.getString("repository"),
            license = jsonObject.getString("license"),
            sourceDirectories = jsonObject.getStringArray("source-directories"),
            exposedModules = jsonObject.getStringArray("exposed-modules"),
            dependencies = dependencies.keySet().map { it to dependencies.getString(it)  }.toMap(),
            elmVersion = jsonObject.getString("elm-version")
    )
}

private fun JSONObject.getStringArray(key: String) = this.getJSONArray(key).map { it as String }

