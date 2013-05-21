import AssemblyKeys._

assemblySettings

jarName in assembly := "ssscs.jar"

name := "ssscs"

version := "0.1"
 
scalaVersion := "2.10.1"
 
libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1" % "test"
 
libraryDependencies += "org.mockito" % "mockito-core" % "1.9.5" % "test"

libraryDependencies += "org.jsoup" % "jsoup" % "1.7.2"

libraryDependencies += "com.github.nikita-volkov" % "sext" % "0.2.3"

libraryDependencies += "org.rogach" %% "scallop" % "0.9.2"

libraryDependencies += "com.itextpdf" % "itextpdf" % "5.1.3"
