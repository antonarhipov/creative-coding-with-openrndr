import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.color.spaces.toOKHSLa
import org.openrndr.extra.compositor.compose
import org.openrndr.extra.compositor.draw
import org.openrndr.extra.compositor.layer
import org.openrndr.extra.compositor.post
import org.openrndr.extra.envelopes.ADSRTracker
import org.openrndr.extra.fx.blur.LaserBlur
import org.openrndr.extra.fx.distort.Fisheye
import org.openrndr.extra.fx.distort.Lenses
import org.openrndr.extra.gui.GUI
import org.openrndr.extra.gui.addTo
import org.openrndr.extra.midi.bindMidiControl
import org.openrndr.extra.midi.openMidiDevice
import org.openrndr.extra.noise.uniform
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.math.IntVector2
import org.openrndr.math.Vector2

// Beatstep mappings
// cc:
// 7
// 10, 74, 71, 76, 77, 93, 73, 75
// 114, 18, 19, 16, 17, 91, 79, 72
// notes:
// 44, 45, 46, 47, 48, 49, 50, 51
// 36, 37, 38, 39, 40, 41, 42, 43


fun main() {


    application {
        configure {
            width = 1600
            height = 800
            windowAlwaysOnTop = true
            position = IntVector2(100, 100)
        }

        oliveProgram {
//        listMidiDevices().forEach(::println)

            val gui = GUI().apply {
                name = "Settings"
            }

            val midi = openMidiDevice("Arturia BeatStep")

//            extend(MidiConsole()) {
//                register(midi)
//                historySize = 10
//            }

            val tracker = ADSRTracker(this)
            tracker.addTo(gui)

            bindMidiControl(tracker::attack, midi, 0, 10)
            bindMidiControl(tracker::decay, midi, 0, 74)
            bindMidiControl(tracker::sustain, midi, 0, 71)
            bindMidiControl(tracker::release, midi, 0, 76)

            midi.noteOn.listen {
                val firstNote = 36.0
                val numberOfNotes = 48.0

                val cx = (it.note - firstNote) * (width / numberOfNotes)
                val color = ColorRGBa.RED.toOKHSLa().shiftHue((it.note - firstNote) * 360.0 / numberOfNotes).toRGBa()
                val c = Vector2(cx, height / 2.0 + Double.uniform(-200.0, 200.0))
                tracker.triggerOn(it.note) { _, value, _ ->
                    drawer.fill = null
                    drawer.stroke = color
                    drawer.strokeWeight = 7.0
                    drawer.circle(c, value * 200)
                }
            }

            midi.noteOff.listen {
                tracker.triggerOff(it.note)
            }

            val filter = LaserBlur()
            bindMidiControl(filter::radius, midi, 0, 7)

            val compose = compose {
                layer {
                    draw {
                        for (v in tracker.values()) {
                            v.draw()
                        }
                    }

                    post(filter).apply {
                        this.addTo(gui)
                    }
                    post(Fisheye()).apply {
                        this.addTo(gui)
                    }
                    post(Lenses()).apply {
                        this.addTo(gui)
                    }
                }
            }

            extend(gui)
            extend {
                gui.visible = mouse.position.x < 200.0
                compose.draw(drawer)
            }
        }


    }
}