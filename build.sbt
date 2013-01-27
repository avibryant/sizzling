import AssemblyKeys._ // put this at the top of the file

assemblySettings

name := "sizzling"

version := "0.0.1"

organization := "io.sizzling"

libraryDependencies += "org.apache.hadoop" % "hadoop-core" % "0.20.2"            

libraryDependencies += "com.twitter" % "chill_2.9.2" % "0.1.2"

libraryDependencies += "com.twitter" % "algebird_2.9.2" % "0.1.6"

excludedJars in assembly <<= (fullClasspath in assembly) map { cp =>
  val excludes = Set("jsp-api-2.1-6.1.14.jar", "jsp-2.1-6.1.14.jar",
    "jasper-compiler-5.5.12.jar")
  cp filter { jar => excludes(jar.data.getName)}
}

// Some of these files have duplicates, let's ignore:
mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
  {
    case s if s.endsWith(".class") => MergeStrategy.last
    case s if s.endsWith("project.clj") => MergeStrategy.concat
    case s if s.endsWith(".html") => MergeStrategy.last
    case x => old(x)
  }
}