import java.io.File
import java.io.FileWriter
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

val currentDir = File(".")

val measurments = mutableMapOf<String, MutableList<Long>>()

val vicoMeasurments = mutableListOf<Long>()
val vicoMeasurmentsCsv = mutableListOf<Long>()
val cosimMeasurments = mutableListOf<Long>()
val cosimMeasurmentsCsv = mutableListOf<Long>()

val numRuns = 15

for (i in 1 .. numRuns) {

    println("Run $i of $numRuns")

    measureTimeMillis {
        "vico simulate-ssp -stop 1000 -dt 0.05 --no-log -p \"initialValues\" -res \"../results/vico\" Gunnerus.ssp".runCommand(
            currentDir
        )
    }.also { elapsed ->
        println("Invoking vico took ${elapsed}ms")
        measurments.computeIfAbsent("vico") { mutableListOf() }.add(elapsed)
    }

    measureTimeMillis {
        "vico simulate-ssp -stop 1000 -dt 0.05 -p \"initialValues\" -res \"../results/vico\" Gunnerus.ssp".runCommand(
                currentDir
        )
    }.also { elapsed ->
        println("Invoking vico took ${elapsed}ms")
        measurments.computeIfAbsent("vicoCsv") { mutableListOf() }.add(elapsed)
    }

   /* measureTimeMillis {
        "OMSimulator -t=1000 --numProcs=1 ../QuarterTruck_10.ssp".runCommand(
            File(currentDir, "omsimulator")
        )
    }.also { elapsed ->
        println("Invoking omsimulator took ${elapsed}ms")
        measurments.computeIfAbsent("omsimulator") { mutableListOf() }.add(elapsed)
    }*/

    measureTimeMillis {
        "cosim run -d 1000 --output-config \"LogConfig.xml\" --output-dir=\"../results/libcosim\" ../Gunnerus.ssp".runCommand(
            File(currentDir, "libcosim")
        )
    }.also { elapsed ->
        println("Invoking cosim took ${elapsed}ms")
        measurments.computeIfAbsent("cosim") { mutableListOf() }.add(elapsed)
    }

    measureTimeMillis {
        "cosim run -d 1000 --output-dir=\"../results/libcosim\" ../Gunnerus.ssp".runCommand(
                File(currentDir, "libcosim")
        )
    }.also { elapsed ->
        println("Invoking cosim took ${elapsed}ms")
        measurments.computeIfAbsent("cosimCsv") { mutableListOf() }.add(elapsed)
    }


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
