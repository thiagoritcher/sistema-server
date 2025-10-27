PATHC="$HOME/dev/tmp/spring/sistema/src/main/java/br/com/ritcher/sistema"
PNAME="br.com.ritcher.sistema"

execute () {
    local cmd="mvn compile exec:java -Dexec.mainClass=br.com.ritcher.sistema.CreateLayers -Dexec.args=\"$1 $2 $PATHC $PNAME $3 $4\""
    echo $cmd | bash
}

execute "usuario" "seguranca" "seg" \
 "'username:String,password:String'"
