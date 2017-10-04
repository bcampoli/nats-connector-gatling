set -eux

echo "===================================================="
echo "Generating Docs"
echo "===================================================="
cd github-repo-master
sbt clean compile doc
export PROJECT_VERSION=$(sbt 'inspect version' | grep Setting | awk '{print $NF}' | perl -pe 's/\e([^\[\]]|\[.*?[a-zA-Z]|\].*?\a)//g')
cd ..
echo "==================Docs Generated==================="



echo "==================================================="
echo "Making Directories, setting up ENV"
echo "==================================================="
export ROOT_API="/github-repo-master/target/scala-2.11/api"
export ROOT_TMP="/github-repo-master/tmp"
echo "mkdir -p ${ROOT_TMP}/docs/${PROJECT_VERSION}/api"
mkdir -p ./${ROOT_TMP}/docs/${PROJECT_VERSION}/api
mv ./${ROOT_API}/* ./${ROOT_TMP}/docs/${PROJECT_VERSION}/api/
ls -R ./${ROOT_TMP}/
echo "==============Done Making dirs====================="

git config --global push.default simple
git config --global user.email "$GITHUB_EMAIL"
git config --global user.name "$GITHUB_USERNAME"

echo "==================================================="
echo "Updating Docs"
echo "==================================================="
export ROOT_GIT="/github-docs-updated"
git clone gh-pages ./${ROOT_GIT}


cp -r ./${ROOT_TMP}/* ./${ROOT_GIT}
cd ./${ROOT_GIT}

ls docs
ls docs | perl -e 'print "<html><body><ul>"; while(<>) { chop $_; print "<li><a href=\"./docs/$_/api\">$_</a></li>";} print "</ul></body></html>"' > index.html
echo "Last edited the $(date +'%Y-%m-%d at %H:%M:%S')" >> index.html
echo "==============Done Updates====================="

git add --all
git commit -a -m "[ci skip] Project Version: ${PROJECT_VERSION}"
