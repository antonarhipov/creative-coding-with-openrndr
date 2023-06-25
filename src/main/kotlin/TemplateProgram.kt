import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extensions.Screenshots
import org.openrndr.extra.color.presets.*
import org.openrndr.extra.noise.scatter
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.extra.shapes.hobbyCurve
import org.openrndr.shape.Circle
import kotlin.random.Random

fun main() = application {
    configure {
        width = 600
        height = 600
        windowAlwaysOnTop = true
    }
    oliveProgram {
        extend(Screenshots())

        extend {
            drawer.clear(ColorRGBa.PINK)

            val r = Random(0)
            val pts = drawer.bounds.scatter(80.0, random = r)

            val curve = hobbyCurve(pts, closed = true, curl = 1.0)
            val contour = curve.sub(seconds * 0.05, seconds * 0.05 + 0.9)

            drawer.fill = null
            drawer.circles(pts, 5.0)

            drawer.stroke = ColorRGBa.fromHex("395080FF")
            drawer.strokeWeight = 4.0
            drawer.contour(contour)

        }
    }
}
