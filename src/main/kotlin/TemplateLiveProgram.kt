import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.color.presets.DARK_SALMON
import org.openrndr.extra.color.presets.DARK_SLATE_BLUE
import org.openrndr.extra.kdtree.kdTree
import org.openrndr.extra.noise.scatter
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.extra.shapes.hobbyCurve
import org.openrndr.ffmpeg.H264Profile
import org.openrndr.ffmpeg.MP4Profile
import org.openrndr.ffmpeg.ScreenRecorder
import org.openrndr.shape.intersections
import kotlin.math.cos
import kotlin.math.exp
import kotlin.random.Random

/**
 *  This is a template for a live program.
 *
 *  It uses oliveProgram {} instead of program {}. All code inside the
 *  oliveProgram {} can be changed while the program is running.
 */

fun main() = application {
    configure {
        width = 800
        height = 800
    }
    program {

        extend(ScreenRecorder()) {
            profile = H264Profile()
        }

        extend {
            val r = Random(0)
            drawer.clear(ColorRGBa.DARK_SALMON)
            val pts = drawer.bounds.scatter(100.0, random = r)

            val curve = hobbyCurve(pts, closed = true)

            drawer.fill = null
            val sub = curve.sub(seconds * 0.05, seconds * 0.05 + 0.9)
            drawer.contour(sub)

            val ints = sub.intersections(sub)
            val intPos = ints.map { it.position }

            val kdtree = intPos.kdTree()

            drawer.fill = ColorRGBa.DARK_SLATE_BLUE
            drawer.stroke = ColorRGBa.DARK_SLATE_BLUE
//            drawer.circles(intPos, 10.0)

            val curvePts = sub.equidistantPositions(5000)
            drawer.circles(curvePts, curvePts.mapIndexed {
//                i, it -> (cos(i * 0.001) * 0.5 + 0.5) * 10.0
                i, it -> exp(-it.distanceTo(kdtree.findNearest(it)!!)*0.02)*35.0
            })
        }
    }
}