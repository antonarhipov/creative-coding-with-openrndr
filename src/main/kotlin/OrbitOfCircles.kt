import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extensions.Screenshots
import org.openrndr.extra.color.presets.DARK_BLUE
import org.openrndr.extra.color.presets.DARK_SALMON
import org.openrndr.extra.color.presets.DARK_SLATE_BLUE
import org.openrndr.extra.color.presets.LIGHT_STEEL_BLUE
import org.openrndr.extra.kdtree.kdTree
import org.openrndr.extra.noise.scatter
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.extra.shapes.hobbyCurve
import org.openrndr.ffmpeg.H264Profile
import org.openrndr.ffmpeg.ScreenRecorder
import org.openrndr.shape.Circle
import org.openrndr.shape.intersections
import kotlin.math.cos
import kotlin.math.exp
import kotlin.random.Random

fun main() = application {
    configure {
        width = 800
        height = 800
    }
    oliveProgram {
        extend(Screenshots())

        extend {
            drawer.clear(ColorRGBa.LIGHT_STEEL_BLUE)

            val c = Circle(width/2.0, height/2.0, 200.0).contour
            val pts = c.equidistantPositions(180)
            val pts2 = c.equidistantPositions(50)

            drawer.fill = null
            drawer.circles(pts, 50.0)
            drawer.circles(pts2, 70.0)
        }
    }
}