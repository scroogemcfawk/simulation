package cellular_automaton.twod


import javafx.application.Application
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import kotlin.system.exitProcess

class TwoDCAApp(private val height: Double = 1080.0, private val width: Double = 1920.0) : Application() {

    private fun setStage(stage: Stage) {
        stage.title = "Играл в жизнь. Проиграл."

        stage.minHeight = height / 10
        stage.minWidth = width / 10

        stage.maxHeight = height
        stage.maxWidth = width

        stage.height = 600.0
        stage.width = 1100.0

        stage.onCloseRequest = EventHandler {
            Platform.exit()
            exitProcess(0)
        }
    }

    override
    fun start(stage: Stage) {
        setStage(stage)

        val loader = FXMLLoader()
        loader.location = javaClass.getResource("twoDLayout.fxml")
        val root: Parent = loader.load()

        val s = Scene(root)

        stage.scene = s
        stage.show()
    }
}


fun main() {
    Application.launch(TwoDCAApp::class.java)
}