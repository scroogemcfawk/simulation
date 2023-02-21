package flight

import javafx.scene.chart.LineChart
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.scene.control.TextField
import kotlin.math.*


class SceneController {


    lateinit var IStep: TextField
    lateinit var yAxis: NumberAxis
    lateinit var xAxis: NumberAxis
    lateinit var lc: LineChart<Number, Number>
    lateinit var IHeight: TextField
    lateinit var IWeight: TextField
    lateinit var ISurface: TextField
    lateinit var IAngle: TextField
    lateinit var ISpeed: TextField



    private val sim = ArrayList<XYChart.Data<Double, Double>>()

    private fun simulate(speed: Double, height: Double, angle: Double, surface: Double, weight: Double, step: Double) {
        var t = 0.0
        var x = 0.0
        var y = height

        val a = angle * PI / 180
        val cosA = cos(a)
        val sinA = sin(a)
        val s = surface
        val m = weight
        val k = 0.5 * 0.15 * 1.29 * s / m
        val g = 9.81

        var vx = speed * cosA
        var vy = speed * sinA


        sim.add(XYChart.Data(0.0, y))

        val st = step
        while (y > 0) {
            t += st
            val v = sqrt(vx * vx + vy * vy)
            vx -= k * vx * v * st
            vy -= (g + k * vy * v) * st
            x += vx * st
            y += vy * st
            //            println("$x : $y")
            sim.add(XYChart.Data(x, y))
        }
        //        println("${st} : $x : ${sim.maxBy { it.yValue  }.yValue} : $vx" + "")
        println(
            "%.3f".format(st) + " | %.3f".format(x) + " | %.3f".format(sim.maxBy { it.yValue }.yValue) + " | %.3f"
                .format(sqrt(vx * vx + vy * vy))
        )
    }


    fun run() {
        println(" Step | Distance | Height | Speed ")
        for (i in listOf(2, 3, 5, 7, 11, 13, 17, 33, 57, 91)) {
            simulate(
                ISpeed.text.toDouble(), IHeight.text.toDouble(), IAngle.text.toDouble(), ISurface.text.toDouble(),
                IWeight.text.toDouble(), 1.0 / i
            )
            sim.clear()
        }

        sim.clear()
        lc.data.clear()

        simulate(
            ISpeed.text.toDouble(), IHeight.text.toDouble(), IAngle.text.toDouble(), ISurface.text.toDouble(),
            IWeight.text.toDouble(), IStep.text.toDouble()
        )
        val ser = XYChart.Series<Number, Number>()
        var xMax = 10.0
        var yMax = 5.0
        for (d in sim) {
            ser.data.add(XYChart.Data(d.xValue, d.yValue))
            xMax = max(abs(xMax), abs(d.xValue.toDouble()))
            yMax = max(yMax, d.yValue.toDouble())
        }
        xAxis.lowerBound = ser.data.minBy { it.xValue.toDouble() }.xValue.toDouble() - 0.5
        xAxis.upperBound = ser.data.maxBy { it.xValue.toDouble() }.xValue.toDouble() + 0.5


        yAxis.upperBound = ser.data.maxBy { it.yValue.toDouble() }.yValue.toDouble() * 1.5
        yAxis.lowerBound = 0.0


        lc.data.add(ser)

    }
}