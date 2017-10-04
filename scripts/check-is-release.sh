export PROJECT_VERSION=$(sbt 'inspect version' | grep Setting | awk '{print $NF}' | perl -pe 's/\e([^\[\]]|\[.*?[a-zA-Z]|\].*?\a)//g')
if [[ ${PROJECT_VERSION} == *"SNAPSHOT"* ]];then exit 1; fi
exit 0
