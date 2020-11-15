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

val numRuns = 10
val tStop = 500
val hz = 20
val dt = 1.0 / hz

for (i in 1..numRuns) {

    println("Run $i of $numRuns")


    /*measureTimeMillis {
        "vico simulate-ssp -stop $tStop -dt $dt --no-parallel --no-log -p \"initialValues\"  gunnerus-trajectory-proxy.ssp".runCommand(
            currentDir
        )
    }.also { elapsed ->
        println("Invoking vico took ${elapsed}ms")
        measurements.computeIfAbsent("vicoSingle") { mutableListOf() }.add(elapsed)
    }

    measureTimeMillis {
        "vico simulate-ssp -stop $tStop -dt $dt --no-parallel -p \"initialValues\" -res \"../results/vico\" gunnerus-trajectory-proxy.ssp".runCommand(
            currentDir
        )
    }.also { elapsed ->
        println("Invoking vico took ${elapsed}ms")
        measurements.computeIfAbsent("vicoSingleCsv") { mutableListOf() }.add(elapsed)
    }*/

    /*measureTimeMillis {
        "vico simulate-ssp -stop $tStop -dt $dt --no-log -p \"initialValues\" gunnerus-trajectory-proxy.ssp".runCommand(
            currentDir
        )
    }.also { elapsed ->
        println("Invoking vico took ${elapsed}ms")
        measurements.computeIfAbsent("vico") { mutableListOf() }.add(elapsed)
    }

    measureTimeMillis {
        "vico simulate-ssp -stop $tStop -dt $dt -p \"initialValues\" -res \"../results/vico\" gunnerus-trajectory-proxy.ssp".runCommand(
            currentDir
        )
    }.also { elapsed ->
        println("Invoking vicoCsv took ${elapsed}ms")
        measurements.computeIfAbsent("vicoCsv") { mutableListOf() }.add(elapsed)
    }*/


/*    measureTimeMillis {
        "python ssp-launcher.py gunnerus-trajectory-proxy.ssp".runCommand(
            File(currentDir, "fmigo")
        )
    }.also { elapsed ->
        println("Invoking fmigo took ${elapsed}ms")
        measurements.computeIfAbsent("fmigo") { mutableListOf() }.add(elapsed)
    }*/



    /* measureTimeMillis {
        "python QuarterTruck.py".runCommand(
            File(currentDir, "fmpy")
        )
    }.also { elapsed ->
        println("Invoking fmpy took ${elapsed}ms")
        measurements.computeIfAbsent("fmpy") { mutableListOf() }.add(elapsed)
    }*/

    /*measureTimeMillis {
        "OMSimulator -t=$tStop --numProcs=0 ../gunnerus-trajectory-proxy.ssp".runCommand(
            File(currentDir, "omsimulator")
        )
    }.also { elapsed ->
        println("Invoking omsimulator took ${elapsed}ms")
        measurements.computeIfAbsent("omsimulator") { mutableListOf() }.add(elapsed)
    }*/

    /*measureTimeMillis {
        "OMSimulator -t=$tStop --numProcs=1 ../gunnerus-trajectory-proxy_mat.ssp".runCommand(
            File(currentDir, "omsimulator")
        )
    }.also { elapsed ->
        println("Invoking omsimulatorMat took ${elapsed}ms")
        measurements.computeIfAbsent("omsimulatorMat") { mutableListOf() }.add(elapsed)
    }

    measureTimeMillis {
        "OMSimulator -t=$tStop --numProcs=1 ../gunnerus-trajectory-proxy_csv.ssp".runCommand(
            File(currentDir, "omsimulator")
        )
    }.also { elapsed ->
        println("Invoking omsimulatorCsv took ${elapsed}ms")
        measurements.computeIfAbsent("omsimulatorCsv") { mutableListOf() }.add(elapsed)
    }*/


    measureTimeMillis {
        "cosim run -d $tStop --output-config=\"LogConfig.xml\" --output-dir=\"../results/libcosim\" ../gunnerus-trajectory-proxy.ssp".runCommand(
            File(currentDir, "libcosim")
        )
    }.also { elapsed ->
        println("Invoking cosim took ${elapsed}ms")
        measurements.computeIfAbsent("cosim") { mutableListOf() }.add(elapsed)
    }

    measureTimeMillis {
        "cosim run -d $tStop --output-dir=\"../results/libcosim\" ../gunnerus-trajectory-proxy.ssp".runCommand(
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
