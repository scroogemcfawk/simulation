package cellular_automaton.oned

import javafx.application.Application
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import kotlin.system.exitProcess

class OneDCAApp(private val height: Double = 1080.0, private val width: Double = 1920.0) : Application() {

    private fun setStage(stage: Stage) {
        stage.title = "1D автомат"

        stage.minHeight = height / 10
        stage.minWidth = width / 10

        stage.maxHeight = height
        stage.maxWidth = width

        stage.height = height / 2
        stage.width = width / 2

        stage.onCloseRequest = EventHandler {
            Platform.exit()
            exitProcess(0)
        }
    }

    override
    fun start(stage: Stage) {
        setStage(stage)

        val loader = FXMLLoader()
        loader.location = javaClass.getResource("oneDLayout.fxml")
        val root: Parent = loader.load()

        val s = Scene(root)

        stage.scene = s
        stage.show()
    }
}


fun main() {
    Application.launch(OneDCAApp::class.java)
}
