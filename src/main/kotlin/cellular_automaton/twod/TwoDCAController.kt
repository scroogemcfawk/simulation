package cellular_automaton.twod

import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.input.MouseEvent
import javafx.scene.layout.GridPane
import javafx.scene.layout.Region

class TwoDCAController {
    private var init = false
    lateinit var gp: GridPane
    lateinit var startStop: Button

    val horizontalSize = 7
    val verticalSize = 7
    var matrix = Array(verticalSize) { BooleanArray(horizontalSize) }

    val t = Thread {
        while (!Thread.interrupted()) {
            regenMatrix()
            redraw()
            Thread.sleep(750)
        }
    }

    private fun redraw() {
        for (i in 0 until verticalSize) {
            for (j in 0 until horizontalSize) {
                if (matrix[i][j]) {
                    gp.children[i * horizontalSize + j].styleClass.add("colored")
                } else {
                    gp.children[i * horizontalSize + j].styleClass.remove("colored")
                }
            }
        }
    }

    private fun getNeighbors(i: Int, j: Int): Int {
        var c = 0
        for (k in -1..1) {
            for (l in -1..1) {
                if (!(k == 0 && l == 0)) {
                    when (i + k) {
                        in 0 until verticalSize -> {
                            when (j + l) {
                                in 0 until horizontalSize -> {
                                    if (matrix[i + k][j + l]) ++c
                                }
                                else -> {}
                            }
                        }
                        else -> {}
                    }
                }
            }
        }
//        println("CELL[${i}][${j}]: ${c}")
        return c
    }

    private fun regenMatrix() {
        val newMatrix = Array(matrix.size) { BooleanArray(matrix[0].size) }
        for (i in matrix.indices) {
            for (j in matrix[0].indices) {
                when (getNeighbors(i, j)) {
                    2 -> {
                        newMatrix[i][j] = matrix[i][j]
                    }
                    3 -> {
                        newMatrix[i][j] = true
                    }
                    else -> {
                        newMatrix[i][j] = false
                    }
                }
            }
        }
        matrix = newMatrix
    }

    @FXML
    fun initialize() {
        gp.alignment = Pos.CENTER
        for (i in 0 until verticalSize) {
            for (j in 0 until horizontalSize) {
                val r = Region()
                r.styleClass.add("rect")
                r.styleClass.add("interactive")
                r.onMouseClicked = EventHandler { redrawZero(i, j) }
                gp.add(r, j, i)
            }
        }
    }

    private fun redrawZero(i: Int, j: Int) {
        matrix[i][j] = !matrix[i][j]
        if (matrix[i][j]) {
            gp.children[i * horizontalSize + j].styleClass.add("colored")
        } else {
            gp.children[i * horizontalSize + j].styleClass.remove("colored")
        }
    }

    private fun init() {
        init = true
        for (i in 0 until verticalSize) {
            for (j in 0 until horizontalSize) {
                gp.children[i * horizontalSize + j].styleClass.remove("interactive")
                gp.children[i * horizontalSize + j].onMouseClicked = EventHandler { }
            }
        }
    }

    fun handle(e: MouseEvent) {
        if (!init) init()
        if (startStop.text == "Start") {
            startStop.text = "Stop"
            if (t.isAlive) {
                t.resume()
            } else {
                t.start()
            }
        } else {
            startStop.text = "Start"
            if (t.isAlive) {
                t.suspend()
            }
        }
    }

}