#include <sys/time.h>
#include <sys/types.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <netdb.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <netdb.h>
#include <sys/select.h>


#define BUF_SIZE 1024 // espaco da memoria
int finish(int n, int j);
int procurar(char *array);
void erro(char *msg);

int main(int argc, char *argv[]) {
	
	
  char endServer[100];
  int fd,i=0,T,j=0;
  struct sockaddr_in addr;
  struct hostent *hostPtr;
  char buffer[BUF_SIZE][BUF_SIZE];
  char enviar[BUF_SIZE];

 
  char enviar_total[BUF_SIZE][BUF_SIZE];
  char r[5]=" : ";

  
    if (argc != 4) {
		printf("cliente <host> <port> <Nome>\n");
		exit(-1);
  }
  strcpy(endServer, argv[1]);
  if ((hostPtr = gethostbyname(endServer)) == 0) {
		printf("Couldn't get host address.\n");
		exit(-1);
  }

  bzero((void *) &addr, sizeof(addr));
  addr.sin_family = AF_INET;
  addr.sin_addr.s_addr = ((struct in_addr *)(hostPtr->h_addr))->s_addr;
  addr.sin_port = htons((short) atoi(argv[2]));

  if((fd = socket(AF_INET,SOCK_STREAM,0)) == -1)
	erro("socket");
  if( connect(fd,(struct sockaddr *)&addr,sizeof (addr)) < 0)
	erro("Connect");
	
  write(fd, argv[3], 1 + strlen(argv[3])); //envia o nome ao server
	pid_t fpid;

	fpid = fork();
	if(fpid != 0){
	do{
		
		fgets(enviar,500,stdin);
		strcpy(enviar_total[i],"------");
		strcat(enviar_total[i] ,argv[3]);
		strcat(enviar_total[i], r);
		strcat(enviar_total[i], enviar);
		T=(procurar(enviar));
		if(T!=1){ //if not TRUE
			write(fd,enviar_total[i], strlen(enviar_total[i]));//enviar para o servidor
		}
		else{
			kill(0); //mata os dois processos
			exit(0);// dispensabel
		}
		i++;
	}while(T!=1);
	
    
}
else
{
	
while(1){//ciclo infinito
    read(fd, &buffer[j], BUF_SIZE-1);// read
	printf("%s\n ", buffer[j]);
	j++;
	
}
}
  close(fd);
  return 1;
}

void erro(char *msg)
{
	printf("Erro: %s\n", msg);
	exit(-1);
}

int procurar(char *array){
	char txt[]=".quit";
	if (strcmp(txt, array)==0){
		return 1;
	}
	return 0;

}
int finish(int n, int j){
	if (j==0){
		n=1;
		return n;
	
	}
	
	return n;
}

