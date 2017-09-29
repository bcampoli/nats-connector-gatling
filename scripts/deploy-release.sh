mkdir -p /pipeline/source
echo "${SONATYPE_PGP_SECRING_64}" | base64 -d > /pipeline/source/secring.asc
echo "${SONATYPE_PGP_PUBRING_64}" | base64 -d > /pipeline/source/pubring.asc
cd github-repo-master/
sbt clean compile publishSigned sonatypeRelease
