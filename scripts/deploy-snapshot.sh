pwd
cd github-repo-master/
chmod 777 -R .
sbt 'inspect version'
export PROJECT_VERSION=$(sbt 'inspect version' | grep Setting | awk '{print $NF}' | perl -pe 's/\e([^\[\]]|\[.*?[a-zA-Z]|\].*?\a)//g')
ls -als */*
sbt clean compile test publish
