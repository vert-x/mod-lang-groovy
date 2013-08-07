import org.codehaus.groovy.control.customizers.ImportCustomizer

groovy {
    source.encoding = 'ISO1252'
    script.base = 'groovy.lang.Script'
}
customizer = { cConf ->
    def importCustomizer = new ImportCustomizer()
    importCustomizer.addImport('org.codehaus.groovy.control.CompilerConfiguration')
    cConf.addCompilationCustomizers(importCustomizer)
}
