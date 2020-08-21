import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

val currentDir = File(".")

val vicoMeasurments = mutableListOf<Long>()
val fmigoMeasurments = mutableListOf<Long>()
val omMeasurments = mutableListOf<Long>()
val fmpyMeasurments = mutableListOf<Long>()
val cosimMeasurments = mutableListOf<Long>()

File(currentDir, "results/fmigo").mkdirs()
File(currentDir, "results/omsimulator").mkdirs()
File(currentDir, "results/fmpy").mkdirs()

repeat(1) {

    measureTimeMillis {
        "vico simulate-ssp -stop 1000 -dt 0.001 -log \"extra/LogConfig.xml\" -p \"initialValues\" -res \"../results/vico\" ../QuarterTruck_10.ssp".runCommand(
            File(currentDir, "vico")
        )
    }.also { elapsed -> vicoMeasurments.add(elapsed) }


    measureTimeMillis {
        "python ssp-launcher.py QuarterTruck_GO.ssp".runCommand(
            File(currentDir, "fmigo")
        )
    }.also { elapsed -> fmigoMeasurments.add(elapsed) }

    measureTimeMillis {
        "python QuarterTruck.py".runCommand(
            File(currentDir, "fmpy")
        )
    }.also { elapsed -> fmigoMeasurments.add(elapsed) }

    measureTimeMillis {
        "python ssp-launcher.py QuarterTruck_GO.ssp".runCommand(
            File(currentDir, "fmigo")
        )
    }.also { elapsed -> fmigoMeasurments.add(elapsed) }

}

fun String.runCommand(workingDir: File) {
    val cmd = "cmd /c $this"
    ProcessBuilder(*cmd.split(" ").toTypedArray())
        .directory(workingDir)
        .redirectOutput(ProcessBuilder.Redirect.INHERIT)
        .redirectError(ProcessBuilder.Redirect.INHERIT)
        .start()
        .waitFor(3, TimeUnit.MINUTES)
}
