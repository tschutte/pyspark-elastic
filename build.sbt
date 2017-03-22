name := "pyspark-elastic"

version := io.Source.fromFile("version.txt").mkString.trim

organization := "TargetHolding"

scalaVersion := "2.11.8"

credentials += Credentials(Path.userHome / ".ivy2" / ".sbtcredentials")

licenses += "Apache-2.0" -> url("http://opensource.org/licenses/Apache-2.0") 

libraryDependencies ++= Seq(
	"org.elasticsearch" %% "elasticsearch-spark-20" % "5.1.2"
)

spName := "TargetHolding/pyspark-elastic"

sparkVersion := "1.5.1"

sparkComponents += "streaming"

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

assemblyOption in assembly := (assemblyOption in assembly).value.copy(
	includeScala = false
)

mergeStrategy in assembly := {
  case PathList("org", "apache", "spark", "unused", "UnusedStubClass.class") => MergeStrategy.first
  case x => (mergeStrategy in assembly).value(x)
}

val ignore = Set(
  "commons-beanutils-1.7.0.jar",
  "commons-beanutils-core-1.8.0.jar",
  "commons-logging-1.1.1.jar",
  "hadoop-yarn-api-2.2.0.jar",
  "guava-14.0.1.jar",
  "kryo-2.21.jar"
)

assemblyExcludedJars in assembly := { 
  val cp = (fullClasspath in assembly).value
  cp filter { x => ignore.contains(x.data.getName) }
}

EclipseKeys.withSource := true
