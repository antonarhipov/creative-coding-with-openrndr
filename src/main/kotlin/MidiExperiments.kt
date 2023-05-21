import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.color.presets.*
import org.openrndr.extra.midi.MidiDeviceDescription
import org.openrndr.extra.midi.MidiTransceiver
import org.openrndr.extra.midi.listMidiDevices
import org.openrndr.extra.midi.openMidiDevice
import org.openrndr.extra.shapes.regularPolygon
import kotlin.random.Random
import kotlin.system.exitProcess

fun main() = application {
    

    program {
        listMidiDevices().forEach(::println)

        val beatstep = openMidiDevice("Arturia BeatStep")

//        val drumbrute = MidiTransceiver.fromDeviceVendor("Arturia DrumBrute Impact", "Arturia")


        beatstep.noteOn.listen {
            println("Note on: channel: ${it.channel}, key: ${it.note}, velocity: ${it.velocity}")
        }
        beatstep.programChanged.listen {
            println("Program change: ${it.program}, key: ${it.value}")
        }
        beatstep.controlChanged.listen {
            println("Control change: channel: ${it.channel}, control: ${it.control}, value: ${it.value}")
        }
        beatstep.pitchBend.listen {
            println("Pitch bend: ${it.pitchBend}, key: ${it.value}")
        }

        ended.listen {
            exitProcess(0)
        }


        var velocity = 1.0
        var key = 55
        val random = Random(7)
        extend {
            drawer.clear(ColorRGBa.PINK)

            val shape = regularPolygon(
                sides = key % 10 + 2,
                center = drawer.bounds.center,
                radius = velocity * 2.0,
                phase = velocity
            ).contour

            drawer.fill = when(key) {
                36 -> ColorRGBa.PALE_GOLDEN_ROD
                37 -> ColorRGBa.MEDIUM_AQUAMARINE
                38 -> ColorRGBa.AQUAMARINE
                39 -> ColorRGBa.BLUE_STEEL
                41 -> ColorRGBa.CORNFLOWER_BLUE
                43 -> ColorRGBa.DARK_SALMON
                44 -> ColorRGBa.AZURE
                45 -> ColorRGBa.FIREBRICK
                else -> ColorRGBa.WHEAT
            }
            drawer.contour(shape)
        }
//        drumbrute.noteOn.listen {
//            key = it.note
//            velocity = it.velocity.toDouble()
//
//            println("note on: channel: ${it.channel}, key: ${it.note}, velocity: ${it.velocity}")
//        }


    }
}








