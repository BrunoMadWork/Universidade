# Cria o objecto Simulador
set ns [new Simulator]

# Abre o ficheiro para escrita do data trace
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


$router1 shape box
$router2 shape box
$router3 shape box
$router4 shape box


$estacao_coimbra label "Estação  Coimbra"
$estacao_berlin label "Estação  Berlim"

$router1 label "Router 1"
$router2 label "Router 2"
$router3 label "Router 3"
$router4 label "Router 4"

#cria um agente udp e liga-o ao no n0
set udpA [new Agent/UDP]
$ns attach-agent $estacao_coimbra $udpA

#cria uma fonte de trafego cbr e liga-a ao udp0
set cbr0 [new Application/Traffic/CBR]
$cbr0 set packetSize_ 500
$cbr0 set maxpkts_ 2098
$cbr0 set interval_ 0.005
$cbr0 attach-agent $udpA

#Cria um agente Null e liga-o ao no n1
set null0 [new Agent/Null]
$ns attach-agent $estacao_berlin $null0

$ns connect $udpA $null0
#---------------------------------
#---------------------------------
#------ Ligação b - 20% ---------
#cria um agente udp e liga-o ao no n0
set udpB [new Agent/UDP]
$ns attach-agent $estacao_antes $udpB

#cria uma fonte de trafego cbr e liga-a ao udp0
set cbr1 [new Application/Traffic/CBR]
$cbr1 set rate_ 0.2Mb
$cbr1 attach-agent $udpB

#Cria um agente Null e liga-o ao no n1
set null1 [new Agent/Null]
$ns attach-agent $estacao_depois $null1

$ns connect $udpB $null1


# When to start and to stop sending
$ns at 0.0 "$cbr1 start"
$ns at 0.5 "$cbr0 start"
$ns at 11.0 "$cbr0 stop"
$ns at 11.5 "$cbr1 stop"
$ns at 12.5 "fim"

$udpA set class_ 1
$udpB set class_ 2


# Start the simulation
$ns run

