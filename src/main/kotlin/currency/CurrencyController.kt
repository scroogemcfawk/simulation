package currency

import javafx.concurrent.Task
import javafx.fxml.FXML
import javafx.scene.chart.LineChart
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.input.MouseEvent
import java.util.Random
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

class CurrencyController {


    lateinit var startStop: Button
    lateinit var lc: LineChart<Int, Double>
    lateinit var yAxis: NumberAxis
    lateinit var xAxis: NumberAxis
    lateinit var iPrice2: TextField
    lateinit var iPrice1: TextField

    val bubS = XYChart.Series<Int, Double>()
    val lolS = XYChart.Series<Int, Double>()

    var init = false
    var day = 1

    val r = Random()
    val dayBoundary = 30
    val factor = 1.0 / dayBoundary

    val t = Thread {
        while (!Thread.interrupted()) {
            bubS.data.add(XYChart.Data(day, getPrice(bubS.data[bubS.data.lastIndex].yValue)))
            lolS.data.add(XYChart.Data(day, getPrice(lolS.data[lolS.data.lastIndex].yValue)))
            ++day
            adjustAxis()
            Thread.sleep(1000)
        }
    }

    private fun adjustAxis() {
        yAxis.upperBound = max(bubS.data.maxBy { it.yValue }.yValue, lolS.data.maxBy { it.yValue }.yValue) * 1.3
        yAxis.lowerBound = min(bubS.data.minBy { it.yValue }.yValue, lolS.data.minBy { it.yValue }.yValue) * 0.7

        xAxis.lowerBound = max(0, day - 30).toDouble()
        xAxis.upperBound = max(30.0, day * 1.3).roundToInt().toDouble()
    }


    @FXML
    fun initialize() {
        bubS.name = "BUB"
        lolS.name = "LOL"
        iPrice1.text = "1.0"
        iPrice2.text = "1.0"
        yAxis.upperBound = 1.5
        yAxis.label = "Price"
        xAxis.upperBound = 30.0
        lc.data.add(bubS)
        lc.data.add(lolS)
        lc.title = "График динамики валют Бубль и Лоллар"
    }

    private fun init() {
        bubS.data.add(XYChart.Data(0, iPrice1.text.toDouble()))
        lolS.data.add(XYChart.Data(0, iPrice2.text.toDouble()))
    }


    @FXML
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

    private fun getPrice(price: Double): Double {
        // 0.42 означает 8% рост
        val res = price + factor * (price * (r.nextDouble() - 0.42))
        return if (res > 0) res else 0.01
    }
}