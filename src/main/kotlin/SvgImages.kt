import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.color.presets.BISQUE
import org.openrndr.shape.draw
import org.openrndr.svg.loadSVG

fun main() = application {
    configure {
        width = 800
        height = 800
    }


    program {
        val composition = loadSVG("data/images/cube1.svg")

        extend {
            drawer.clear(ColorRGBa.BISQUE)
            drawer.composition(composition)
        }
    }
}