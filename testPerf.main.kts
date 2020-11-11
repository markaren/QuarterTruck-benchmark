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

val numRuns = 1
val tStop = 1000
val hz = 1000
val dt = 1.0/hz

for (i in 1 .. numRuns) {

    println("Run $i of $numRuns")

    measureTimeMillis {
        "vico simulate-ssp -stop $tStop -dt $dt --no-parallel --no-log -p \"initialValues\" -res \"../results/vico\" QuarterTruck_${hz}hz.ssp".runCommand(
            currentDir
        )
    }.also { elapsed ->
        println("Invoking vico took ${elapsed}ms")
        measurments.computeIfAbsent("vico") { mutableListOf() }.add(elapsed)
    }

   /* measureTimeMillis {
        "python ssp-launcher.py QuarterTruck_${hz}hz.ssp".runCommand(
            File(currentDir, "fmigo")
        )
    }.also { elapsed ->
        println("Invoking fmigo took ${elapsed}ms")
        measurments.computeIfAbsent("fmigo") { mutableListOf() }.add(elapsed)
    }*/

   /* measureTimeMillis {
        "python QuarterTruck.py".runCommand(
            File(currentDir, "fmpy")
        )
    }.also { elapsed ->
        println("Invoking fmpy took ${elapsed}ms")
        measurments.computeIfAbsent("fmpy") { mutableListOf() }.add(elapsed)
    }*/

   /* measureTimeMillis {
        "OMSimulator -t=$tStop --numProcs=1 ../QuarterTruck_${hz}hz.ssp".runCommand(
            File(currentDir, "omsimulator")
        )
    }.also { elapsed ->
        println("Invoking omsimulator took ${elapsed}ms")
        measurments.computeIfAbsent("omsimulator") { mutableListOf() }.add(elapsed)
    }

    measureTimeMillis {
        "cosim run -d $tStop --output-dir=\"../results/libcosim\" ../QuarterTruck_${hz}hz.ssp".runCommand(
            File(currentDir, "libcosim")
        )
    }.also { elapsed ->
        println("Invoking cosim took ${elapsed}ms")
        measurments.computeIfAbsent("cosim") { mutableListOf() }.add(elapsed)
    }*/


    /*measureTimeMillis {
        "vico simulate-ssp -stop 10 -dt 0.001 -log \"extra/LogConfig.xml\" -p \"initialValues\" -res \"../results/vico-proxy\" QuarterTruck_10_proxy.ssp".runCommand(
            currentDir
        )
    }.also { elapsed ->
        println("Invoking vico-proxy took ${elapsed}ms")
        measurments.computeIfAbsent("vico-proxy") { mutableListOf() }.add(elapsed)
    }*/

    /*measureTimeMillis {
        "cosim run -d 10 --output-config \"LogConfig.xml\" --output-dir=\"../results/libcosim\" ../QuarterTruck_10_proxy.ssp".runCommand(
            File(currentDir, "libcosim")
        )
    }.also { elapsed ->
        println("Invoking cosim-proxy took ${elapsed}ms")
        measurments.computeIfAbsent("cosim-proxy") { mutableListOf() }.add(elapsed)
    }*/

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
