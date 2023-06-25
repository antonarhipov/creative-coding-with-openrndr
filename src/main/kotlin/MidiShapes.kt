import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.midi.openMidiDevice
import org.openrndr.extra.shapes.regularPolygon

fun main() = application {


    configure {
        width = 800
        height = 800
    }

    program {
        val controller = openMidiDevice("Arturia BeatStep")
        var velocity = 1.0
        var key = 55
        var value = 0
        extend {
            drawer.clear(ColorRGBa.PINK)

            val shape = regularPolygon(
                sides = key / 7 ,
                center = drawer.bounds.center,
                radius = velocity * 2.0 + value,
                phase = velocity
            ).contour

            drawer.contour(shape)
        }
        controller.noteOn.listen {
            key = it.note
            velocity = it.velocity.toDouble()
            println("Note: $it")
        }
        controller.controlChanged.listen {
            println("CC: $it")
            value = it.value
        }
        controller.programChanged.listen {
            println("PC: $it")
        }


    }
}
