import java.io.File
import java.io.FileWriter
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

val currentDir = File(".")

val measurements = mutableMapOf<String, MutableList<Long>>()

File(currentDir, "results/fmigo").mkdirs()
File(currentDir, "results/omsimulator").mkdirs()
File(currentDir, "results/fmpy").mkdirs()
val perfDir = File(currentDir, "results/performance").apply {
    mkdirs()
}

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
        measurements.computeIfAbsent("vico") { mutableListOf() }.add(elapsed)
    }

    measureTimeMillis {
        "vico simulate-ssp -stop $tStop -dt $dt --no-parallel -p \"initialValues\" -res \"../results/vico\" QuarterTruck_${hz}hz.ssp".runCommand(
            currentDir
        )
    }.also { elapsed ->
        println("Invoking vicoCsv took ${elapsed}ms")
        measurements.computeIfAbsent("vicoCsv") { mutableListOf() }.add(elapsed)
    }



    measureTimeMillis {
        "python ssp-launcher.py QuarterTruck_${hz}hz.ssp".runCommand(
            File(currentDir, "fmigo")
        )
    }.also { elapsed ->
        println("Invoking fmigo took ${elapsed}ms")
        measurements.computeIfAbsent("fmigo") { mutableListOf() }.add(elapsed)
    }



    measureTimeMillis {
        "python QuarterTruck.py".runCommand(
            File(currentDir, "fmpy")
        )
    }.also { elapsed ->
        println("Invoking fmpy took ${elapsed}ms")
        measurements.computeIfAbsent("fmpy") { mutableListOf() }.add(elapsed)
    }



    measureTimeMillis {
        "OMSimulator -t=$tStop --numProcs=1 ../QuarterTruck_${hz}hz.ssp".runCommand(
            File(currentDir, "omsimulator")
        )
    }.also { elapsed ->
        println("Invoking omsimulatorMat took ${elapsed}ms")
        measurements.computeIfAbsent("omsimulatorMat") { mutableListOf() }.add(elapsed)
    }

    measureTimeMillis {
        "OMSimulator -t=$tStop --numProcs=1 ../QuarterTruck_${hz}hz_csv.ssp".runCommand(
            File(currentDir, "omsimulator")
        )
    }.also { elapsed ->
        println("Invoking omsimulatorCsv took ${elapsed}ms")
        measurements.computeIfAbsent("omsimulatorCsv") { mutableListOf() }.add(elapsed)
    }



    measureTimeMillis {
        "cosim run -d $tStop --output-config=\"LogConfig.xml\" --output-dir=\"../results/libcosim\" ../QuarterTruck_${hz}hz.ssp".runCommand(
            File(currentDir, "libcosim")
        )
    }.also { elapsed ->
        println("Invoking cosim took ${elapsed}ms")
        measurements.computeIfAbsent("cosim") { mutableListOf() }.add(elapsed)
    }

    measureTimeMillis {
        "cosim run -d $tStop --output-dir=\"../results/libcosim\" ../QuarterTruck_${hz}hz.ssp".runCommand(
            File(currentDir, "libcosim")
        )
    }.also { elapsed ->
        println("Invoking cosimCsv took ${elapsed}ms")
        measurements.computeIfAbsent("cosimCsv") { mutableListOf() }.add(elapsed)
    }


}

measurements.forEach { (t, u) ->

    val file = File(perfDir, "$t.csv")
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
