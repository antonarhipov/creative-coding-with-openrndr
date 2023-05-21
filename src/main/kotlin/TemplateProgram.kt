import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.midi.MidiTransceiver
import org.openrndr.extra.midi.openMidiDevice
import org.openrndr.extra.shapes.regularPolygon
import org.openrndr.shape.ShapeContour
import kotlin.random.Random

fun main() = application {
    configure {
        width = 800
        height = 800
    }

    program {
//        val controller = MidiTransceiver.fromDeviceVendor("KeyStep Pro", "Arturia")
//        val controller = MidiTransceiver.fromDeviceVendor("Bus 1", "Apple Inc.")
        val controller = openMidiDevice("Arturia BeatStep")
        var velocity = 0
        var key = 55
        extend {
            drawer.clear(ColorRGBa.PINK)
            val r = Random(0)
            drawer.stroke = ColorRGBa.BLUE
            drawer.circle(drawer.bounds.center, key.toDouble() + r.nextDouble())
        }
        controller.noteOn.listen {
//            it.
            velocity = it.velocity
            key = it.note
            println("note on: channel: ${it.channel}, key: ${it.note}, velocity: $velocity")
        }
    }
}
