name := "nats-connector-gatling"

organization := "com.logimethods"

// Your profile name of the sonatype account. The default is the same with the organization value
//// sonatypeProfileName := "laurent.magnin"

version := "0.4.0-SNAPSHOT"

scalaVersion := "2.11.7"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
resolvers += "Sonatype OSS Release" at "https://oss.sonatype.org/content/groups/public/"

libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.2.2" % "provided"
libraryDependencies += "io.gatling"            % "gatling-test-framework"    % "2.2.2" % "test"
libraryDependencies += "io.nats" 			   % "java-nats-streaming"		 % "0.1.2"

// enablePlugins(GatlingPlugin)

updateOptions := updateOptions.value.withCachedResolution(true)

// PUBLISH
// See http://www.scala-sbt.org/0.13.5/docs/Detailed-Topics/Publishing.html

val SONATYPE_USERNAME = scala.util.Properties.envOrElse("SONATYPE_USERNAME", "NOT_SET")
val SONATYPE_PASSWORD = scala.util.Properties.envOrElse("SONATYPE_PASSWORD", "NOT_SET")
credentials += Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", SONATYPE_USERNAME, SONATYPE_PASSWORD)

licenses := Seq("MIT License" -> url("https://github.com/Logimethods/nats-connector-gatling/blob/master/LICENSE"))
homepage := Some(url("https://github.com/Logimethods/nats-connector-gatling"))
scmInfo := Some(ScmInfo(url("https://github.com/Logimethods/nats-connector-gatling"), "scm:git:git://github.com:Logimethods/nats-connector-gatling.git"))

publishMavenStyle := true
publishArtifact in Test := false

val SONATYPE_PASSPHRASE = scala.util.Properties.envOrNone("SONATYPE_PASSPHRASE")
pgpPassphrase := Some(SONATYPE_PASSPHRASE.getOrElse("").toCharArray)

pgpSecretRing := file("/pipeline/source/secring.asc")
pgpPublicRing := file("/pipeline/source/pubring.asc")

// http://www.scala-sbt.org/0.13/docs/Howto-Scaladoc.html
apiURL := Some(url(s"http://logimethods.github.io/nats-connector-gatling/docs/${version}/api/"))

pomIncludeRepository := { _ => false }

pomExtra := (
  <issueManagement>
    <url>https://github.com/Logimethods/nats-connector-gatling/issues/</url>
    <system>GitHub Issues</system>
  </issueManagement>
  <developers>
    <developer>
        <id>laugimethods</id>
        <name>Laurent Magnin</name>
        <email>laurent.magnin@logimethods.com</email>
        <url>https://github.com/laugimethods</url>
        <organization>Logimethods</organization>
        <organizationUrl>http://www.logimethods.com/</organizationUrl>
        <roles>
            <role>Senior Consultant</role>
        </roles>
        <timezone>America/Montreal</timezone>
    </developer>
  </developers>
)

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}