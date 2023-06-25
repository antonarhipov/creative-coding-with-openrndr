import org.openrndr.WindowMultisample
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.DepthTestPass
import org.openrndr.draw.DrawPrimitive
import org.openrndr.draw.shadeStyle
import org.openrndr.extra.camera.Orbital
import org.openrndr.extra.color.presets.ANTIQUE_WHITE
import org.openrndr.extra.meshgenerators.dodecahedronMesh
import org.openrndr.ffmpeg.H264Profile
import org.openrndr.ffmpeg.ScreenRecorder
import org.openrndr.math.Vector3

fun main() = application {
    configure {
        multisample = WindowMultisample.SampleCount(4)
        width = 800
        height = 800
    }

    program {
//        val mesh = boxMesh(100.0, 100.0, 100.0)
        val mesh = dodecahedronMesh(100.0)

        val cam = Orbital()
        cam.eye = -Vector3.UNIT_Z * 550.0

        extend(cam)

        extend(ScreenRecorder()) {
            profile = H264Profile()
        }

        extend {
            drawer.clear(ColorRGBa.ANTIQUE_WHITE)
            drawer.perspective(70.0, width * 1.0 / height, 0.01, 1000.0)
            drawer.depthWrite = true
            drawer.depthTestPass = DepthTestPass.LESS_OR_EQUAL

            drawer.fill = ColorRGBa.PINK
            drawer.shadeStyle = shadeStyle {
                fragmentTransform = """
                        vec3 lightDir = normalize(vec3(0.3, 1.0, 0.5));
                        float l = dot(va_normal, lightDir) * 0.4 + 0.5;
                        x_fill.rgb *= l; 
                    """.trimIndent()
            }
            drawer.translate(0.0, 0.0, -150.0)
            drawer.rotate(Vector3.UNIT_X, seconds * 15 + 30)
            drawer.rotate(Vector3.UNIT_Y, seconds * 5 + 60)
            drawer.vertexBuffer(vertexBuffer = mesh, DrawPrimitive.TRIANGLES)

        }
    }
}