import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.isolatedWithTarget
import org.openrndr.draw.renderTarget
import org.openrndr.ffmpeg.ScreenRecorder
import org.openrndr.math.map

fun main() = application {
    configure {
        width = 800
        height = 800
    }

    program {
        val rt = renderTarget(width, height) {
            colorBuffer()
        }

        val c = drawer.bounds.center

        val cX = -0.7
        val cY = 0.27015

        val maxIter = 100

        drawer.isolatedWithTarget(rt) {
            drawer.background(ColorRGBa.BLACK)
            drawer.stroke = null

            for (x in 0 until width) {
                for (y in 0 until height) {
                    var zx = 1.5 * (x - width / 2) / (0.5 * width)
                    var zy = (y - height / 2) / (0.5 * height)

                    var i = maxIter

                    while (zx * zx + zy * zy < 6 && i > 0) {
                        val tmp = zx * zx - zy * zy + cX
                        zy = 2.0 * zx * zy + cY
                        zx = tmp
                        i--
                    }

                    val norm_i = map(i.toDouble(), 0.0, maxIter.toDouble(), 0.0, 1.0)

                    drawer.fill = ColorRGBa.WHITE.opacify(norm_i)
                    drawer.rectangle(x.toDouble(), y.toDouble(), 1.0, 1.0)
                }
            }
        }

        extend(ScreenRecorder())

        extend {
            drawer.image(rt.colorBuffer(0))
        }
    }
}