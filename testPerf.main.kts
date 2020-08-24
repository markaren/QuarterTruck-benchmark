import java.io.File
import java.io.FileWriter
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

val currentDir = File(".")

val measurments = mutableMapOf<String, MutableList<Long>>()

val vicoMeasurments = mutableListOf<Long>()
val fmigoMeasurments = mutableListOf<Long>()
val omMeasurments = mutableListOf<Long>()
val fmpyMeasurments = mutableListOf<Long>()
val cosimMeasurments = mutableListOf<Long>()

File(currentDir, "results/fmigo").mkdirs()
File(currentDir, "results/omsimulator").mkdirs()
File(currentDir, "results/fmpy").mkdirs()

val numRuns = 15

for (i in 0 until numRuns) {

    println("Run $i of $numRuns")

    measureTimeMillis {
        "vico simulate-ssp -stop 1000 -dt 0.001 -log \"extra/LogConfig.xml\" -p \"initialValues\" -res \"../results/vico\" ../QuarterTruck_10.ssp".runCommand(
            File(currentDir, "vico")
        )
    }.also { elapsed ->
        println("Invoking vico took ${elapsed}ms")
        measurments.computeIfAbsent("vico") { mutableListOf() }.add(elapsed)
    }


    measureTimeMillis {
        "python ssp-launcher.py QuarterTruck_GO.ssp".runCommand(
            File(currentDir, "fmigo")
        )
    }.also { elapsed ->
        println("Invoking fmigo took ${elapsed}ms")
        measurments.computeIfAbsent("fmigo") { mutableListOf() }.add(elapsed)
    }

    measureTimeMillis {
        "python QuarterTruck.py".runCommand(
            File(currentDir, "fmpy")
        )
    }.also { elapsed ->
        println("Invoking fmpy took ${elapsed}ms")
        measurments.computeIfAbsent("fmpy") { mutableListOf() }.add(elapsed)
    }

    measureTimeMillis {
        "OMSimulator -t=1000 --numProcs=2 ../QuarterTruck_10.ssp".runCommand(
            File(currentDir, "omsimulator")
        )
    }.also { elapsed ->
        println("Invoking omsimulator took ${elapsed}ms")
        measurments.computeIfAbsent("omsimulator") { mutableListOf() }.add(elapsed)
    }

    measureTimeMillis {
        "cosim run -d 1000 --output-config \"LogConfig.xml\" --output-dir=\"../results/libcosim\" ../QuarterTruck_10.ssp".runCommand(
            File(currentDir, "libcosim")
        )
    }.also { elapsed ->
        println("Invoking cosim took ${elapsed}ms")
        measurments.computeIfAbsent("cosim") { mutableListOf() }.add(elapsed)
    }

}

measurments.forEach { (t, u) ->

    val file = File("results", "$t.csv")
    FileWriter(file).buffered().use { fw ->
        fw.write(u.joinToString("\n"))
    }

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
