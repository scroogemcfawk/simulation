package flight

import javafx.application.Application
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.chart.LineChart
import javafx.scene.chart.NumberAxis
import javafx.scene.layout.VBox
import javafx.stage.Stage

class HelloApplication(private val height: Double = 1080.0, private val width: Double = 1920.0) : Application() {

    @FXML
    private lateinit var root : VBox
    @FXML
    private lateinit var xAxis : NumberAxis
    @FXML
    private lateinit var yAxis : NumberAxis
    @FXML
    private lateinit var lc : LineChart<Number, Number>

    private fun setStage(stage: Stage) {
        stage.title = "1 + 1 = 10"

        stage.minHeight = height / 10
        stage.minWidth = width / 10

        stage.maxHeight = height
        stage.maxWidth = width

        stage.height = height / 2
        stage.width = width / 2
    }

    override
    fun start(stage: Stage) {
        setStage(stage)

        val loader = FXMLLoader()
        loader.location = javaClass.getResource("main.fxml")
        val root : Parent = loader.load()

        val s = Scene(root)


        stage.scene = s
        stage.show()
    }
}

fun main() {
    Application.launch(HelloApplication::class.java)
}