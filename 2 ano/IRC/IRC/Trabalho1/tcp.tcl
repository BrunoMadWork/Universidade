#define o numero de argumentos a utilizar
if {$argc == 3} {
	set a_window [lindex $argv 0]
	set b_nr_pacotes [lindex $argv 1]
	set c_Size [lindex $argv 2]
} else {
	puts "ERRO!!!!: argumento1-> janela, argumento2-> numero de pacotes, argumento3-> tamanho dos pacotes "
	exit 1
}

# Cria o objecto Simulador
set ns [new Simulator]

# Abre o ficheiro para escrita do data trace e do out.nam
set nf [open out.nam w]
$ns namtrace-all $nf

$ns use-newtrace
set tracefd [open output.tr w]
$ns trace-all $tracefd

# Cria o procedimento 'fim'
proc fim {} {
	global ns nf
	$ns flush-trace
	close $nf
	exec nam out.nam &
	exit 0
}

#escolha de cores
$ns color 1 Red
$ns color 2 Blue
$ns color 3 Green

#-------Criaçao de emissores
set estacao_coimbra [$ns node]
set estacao_antes [$ns node]
#-------Criaçao de receptores
set estacao_berlin [$ns node]
set estacao_depois [$ns node]
#-------Criaçao de routers
set router1 [$ns node]
set router2 [$ns node]
set router3 [$ns node]
set router4 [$ns node]

#--------Estabelecimento das ligaçoes
$ns duplex-link $estacao_coimbra	$router1	1Mbps	0.00262s		DropTail
$ns duplex-link $router4	$estacao_berlin		1Mbps	0.00262s		DropTail
$ns duplex-link $router1	$router2		1Mbps	0.00262s		DropTail
$ns duplex-link $router2	$router3		1Mbps	0.00262s		DropTail
$ns duplex-link $router3	$router4		1Mbps	0.00262s		DropTail
$ns duplex-link $estacao_antes	$estacao_coimbra	1Mbps	0.00262s		DropTail
$ns duplex-link $estacao_berlin	$estacao_depois		1Mbps	0.00262s		DropTail

#orientaçao dos nos
$ns duplex-link-op $estacao_coimbra	$router1	orient left-right
$ns duplex-link-op $router4	$estacao_berlin		orient left-right
$ns duplex-link-op $router1	$router2		orient left-rightl
$ns duplex-link-op $router2	$router3		orient left-right
$ns duplex-link-op $router3	$router4		orient left-right
$ns duplex-link-op $estacao_antes	$estacao_coimbra	orient right-up
$ns duplex-link-op $estacao_berlin	$estacao_depois		orient right-up

# cor, aspecto e labels dos nós
$router1 color blue
$router2 color blue
$router3 color blue
$router4 color blue

$estacao_coimbra shape box
$estacao_berlin shape box
$router1 shape box
$router2 shape box
$router3 shape box
$router4 shape box


$estacao_coimbra label "Estação Coimbra"
$estacao_berlin label "Estação  Berlim"

$router1 label "Router 1"
$router2 label "Router 2"
$router3 label "Router 3"
$router4 label "Router 4"


#------ Ligação A - 80% ---------
set tcp1 [$ns create-connection TCP/RFC793edu $estacao_coimbra TCPSink $estacao_berlin 1]
$tcp1 set window_ $a_window
$tcp1 set packetSize_ $c_Size
$tcp1 set maxpkts_ $b_nr_pacotes
$tcp1 set interval_ 0.005
set ftp1 [new Application/FTP]
$ftp1 attach-agent $tcp1
#---------------------------------
#------ Ligação b - 20% ---------
#cria um agente udp e liga-o ao no n0

set udp0 [new Agent/UDP]
$ns attach-agent $estacao_antes $udp0

#cria uma fonte de trafego cbr e liga-a ao udp0
set cbr0 [new Application/Traffic/CBR]
$cbr0 set rate_ 0.2Mbytes
$cbr0 attach-agent $udp0

#Cria um agente Null e liga-o ao no n1
set null0 [new Agent/Null]
$ns attach-agent $estacao_depois $null0

$ns connect $udp0 $null0




$tcp1 set class_ 1
$udp0 set class_ 2
$ns color 1 Blue
$ns color 2 Red

# When to start and to stop sending
$ns at 0.5 "$ftp1 start"
$ns at 0.3 "$cbr0 start"
$ns at 16.5 "$cbr0 stop"
$ns at 15.9 "$ftp1 stop"

$ns at 17.0 "fim"


# Start the simulation
$ns run
