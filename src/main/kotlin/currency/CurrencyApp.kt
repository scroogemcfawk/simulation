package currency

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.layout.VBox
import javafx.stage.Stage

class CurrencyApp(private val height: Double = 1080.0, private val width: Double = 1920.0) : Application() {

    private fun setStage(stage: Stage) {
        stage.title = "Закупаем. Докупаем. Фиксируем прибыль."
        stage.icons.add(Image(javaClass.getResource("curicon.png").toString()))

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
        loader.location = javaClass.getResource("currencyLayout.fxml")
        val root: Parent = loader.load()

        val s = Scene(root)


        stage.scene = s
        stage.show()
    }
}

fun main() {
    Application.launch(CurrencyApp::class.java)
}