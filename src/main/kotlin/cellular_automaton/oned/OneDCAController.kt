package cellular_automaton.oned

import javafx.application.Platform
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.input.MouseEvent
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.GridPane
import javafx.scene.layout.Priority
import javafx.scene.layout.Region
import java.util.LinkedList

class OneDCAController {

    private var curArr = BooleanArray(16)

    private val ruleTable = BooleanArray(8)

    lateinit var ruleN: TextField
    lateinit var startStop: Button
    lateinit var gp: GridPane

    private val rectSize = 40.0
    var init = false
    var iteration = 0
    var complete = 17

    val t = Thread {
        while (!Thread.interrupted() && complete == 17) {
            addNewRaw()
            removeExceeded()
            Thread.sleep(1000)
        }
    }


    private fun removeExceeded() {
        val toRemove = gp.children.size
        if (toRemove > 170) {
            val removeList = LinkedList<Node>()
            for (i in 0..16) {
                removeList.add(gp.children[i])
            }
            Platform.runLater {
                gp.children.removeAll(removeList)
            }
        }
    }

    private fun addNewRaw() {
        updateIterationArray()

        val parentList = LinkedList<Parent>()
        val label = Label("${iteration}: ")
        label.styleClass.add("iter")
        parentList.add(label)

        for (i in 0..15) {
            val r = Region()
            r.styleClass.add("rect")
            if (curArr[i]) {
                r.styleClass.add("colored")
            }
            if (i > 1) {
                r.styleClass.add("no-l-b")
            }
            parentList.add(r)
        }
        complete = 0
        for (p in parentList) {
            Platform.runLater {
                gp.addRow(iteration, p)
                ++complete
            }
        }

        ++iteration
    }

    private fun updateIterationArray() {
        val newArr = BooleanArray(16)
        newArr[0] = curArr[0]
        newArr[newArr.lastIndex] = curArr.last()
        for (i in 1..14) {
            val ruleIntex = (if (curArr[i - 1]) 1 else 0) * 4 +
                    (if (curArr[i]) 1 else 0) * 2 +
                    (if (curArr[i + 1]) 1 else 0)
            newArr[i] = ruleTable[ruleIntex]
        }
        curArr = newArr
    }

    @FXML
    fun initialize() {
        gp.alignment = Pos.CENTER
        val cc = ColumnConstraints()
        cc.minWidth = rectSize
        cc.hgrow = Priority.NEVER
        ruleN.text = "127"

        val label = Label("0: ")
        label.styleClass.add("iter")
        gp.add(label, 0, 0)

        for (i in 1..16) {
            val r = Region()
            r.styleClass.add("rect")
            r.styleClass.add("interactive")
            r.onMouseClicked = EventHandler { redrawZero(i) }
            if (i > 1) {
                r.styleClass.add("no-l-b")
            }
            gp.add(r, i, 0)
            gp.columnConstraints.add(cc)
        }
    }

    fun handle(e: MouseEvent) {
        if (!init) {
            init()
        }
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

    private fun init() {
        var fail = false
        try {
            val rule = ruleN.text.toInt()
            if (rule < 0 || rule > 255) {
                fail = true
            }
        } catch (e: Exception) {
            fail = true
        }
        if (fail) {
            Alert(Alert.AlertType.WARNING, "Rule must be in range 0 to 255").show()
            return
        }
        init = true
        var rule = ruleN.text.toInt()
        for (i in 0..7) {
            ruleTable[i] = rule % 2 != 0
            rule /= 2
        }
        iteration = 1
        for (i in 1..16) {
            gp.children[i].styleClass.remove("interactive")
            gp.children[i].onMouseClicked = EventHandler {  }
        }
        println("111 110 101 100 011 010 001 000")
        println(" " + ruleTable.map { if (it) 1 else 0 }.joinToString("   "))
    }

    private fun redrawZero(n: Int) {
        curArr[n] = !curArr[n]
        if (curArr[n]) {
            gp.children[n].styleClass.add("colored")
        } else {
            gp.children[n].styleClass.remove("colored")
        }
    }

}