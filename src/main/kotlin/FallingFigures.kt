import org.openrndr.animatable.Animatable
import org.openrndr.animatable.easing.Easing
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.color.presets.CORNFLOWER_BLUE
import org.openrndr.extra.shapes.regularPolygon
import org.openrndr.math.Vector2
import kotlin.random.Random

fun main() = application {
    configure {
        width = 800
        height = 800
        windowResizable = true
        title = "Falling Figures"
    }

    program {
        val animation = object : Animatable() {
            val random = Random(0)
            var x = random.nextDouble(0.0, width.toDouble())
            var y = 0.0
            var color = ColorRGBa.WHITE
            var phase = 0.0
        }

        println(">>>>> " + animation.x)

        animation.apply {
//            ::x.animate(width.toDouble(), 5000, Easing.CubicInOut)
            ::color.animate(ColorRGBa.CORNFLOWER_BLUE, 1800)
            ::y.animate(height.toDouble(), 1800, Easing.CubicInOut)
            ::phase.animate(height.toDouble(), durationInMs = 1800, Easing.CubicOut)
        }

        extend {
            animation.updateAnimation()

            drawer.fill = animation.color

            val shape = regularPolygon(
                sides = 6,
                center = Vector2(animation.x, animation.y),
                radius = 60.0,
                phase = animation.phase
            ).contour

            drawer.contour(shape)
        }
    }
}
