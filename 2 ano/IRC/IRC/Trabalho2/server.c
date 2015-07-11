#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <netdb.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/select.h>
#include <arpa/inet.h>

#define SERVER_PORT     9999
#define BUF_SIZE	1024
#define MAX 100
void search(int array[], int r);
void intput_array(int array[]);
void erro(char *msg);

int main() {
	
    int fd, novofd,nbytes,j,fdmax;
    struct sockaddr_in addr, client_addr;
    int client_addr_size;
    char buffer[BUF_SIZE];
    int clientes_fd[MAX];// array com o fds dos clientes
    fd_set master;  // descritor de arquivo mestre
    fd_set ler_fds;    //  descritor de arquivo para temporario
	FD_ZERO(&master);   //limpa os conjuntos mestre e temporário
    FD_ZERO(&ler_fds);
    int yes=1;
  
	
	bzero((void *) &addr, sizeof(addr));
    addr.sin_family      = AF_INET;
    addr.sin_addr.s_addr = htonl(INADDR_ANY);
    addr.sin_port        = htons(SERVER_PORT);
    
	
    if ( (fd = socket(AF_INET, SOCK_STREAM, 0)) < 0) 
	erro("na funcao socket");
	
	if (setsockopt(fd, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof (int)) == -1) {

            perror ("setsockopt");
            exit (1);
        }
	
    if ( bind(fd,(struct sockaddr*)&addr,sizeof(addr)) < 0)
	erro("na funcao bind");
    if( listen(fd, 100) < 0)
		erro("na funcao listen");
		//adiciona o socket em escura ao conjunto master
		FD_SET(fd, &master);

	clientes_fd[0]=fd;
	fdmax=fd;


	while(1){
			
            ler_fds = master; // copia os master para o temporario
            
            if (select (fdmax+1,  &ler_fds, NULL, NULL, NULL)== -1) {
                
                perror ("select");
                exit(1);
            }

            int i;// percorre as conexões existentes em busca de dados
            for ( i = 0; i <=fdmax; i++) {
        
                if  ( FD_ISSET ( i,  &ler_fds))  { 
                    
                    if ( i == clientes_fd[0]) {
                     
                        //trata novas conexões
                       client_addr_size = sizeof(client_addr);
                        
                        if (( novofd = accept(fd,(struct sockaddr *)&client_addr, &client_addr_size)) == -1)  {
                            perror ("accept");
                        } else {
                            FD_SET ( novofd , &master);//adiciona ao mestre
                            char print[150]="->";
							char t[5];
							
							strcat(print,"servidor : nova conexão de ");
							strcat(print, inet_ntoa(client_addr.sin_addr));
							strcat(print," no  socket ");
							sprintf(t, "%d", novofd);
							strcat(print, t);
							strcat(print, "\n");
							
							for (j = 0; j<MAX; j++)  {
                                
                                //envia a todos os clientes
                                if (FD_ISSET (j, &master)) {
                                    // exceto a nós e ao socket em escuta
                                  
                                    if (j != fd &&j != novofd) {
                                         if (write(j,print,strlen(print)) == -1) {
                                        perror ("write");
                                       
                            }}}}
                            print[0]='\0';
                            
                            if(fdmax<novofd){
								fdmax=novofd;
								novofd=0;
							}
							clientes_fd[coloca_array(clientes_fd)]=novofd;
							
                        
                    }} else {

                        // cuida dos dados do cliente
                      
                           if  ( (nbytes=(read (i, buffer, BUF_SIZE-1)))>= 0) {
                            
                            // recebeu erro ou a conexão foifechada pelo cliente
                            if (nbytes == 0) {
								char printR[150]="->";
								char p[5];
								strcat(printR," servidor : o Cliente foi desligado ");
								strcat(printR, inet_ntoa(client_addr.sin_addr));
								strcat(printR," no  socket ");
								sprintf(p, "%d", i);
								strcat(printR, p);
								strcat(printR, "\n");
							
								for (j = 0; j<MAX; j++)  {
                                
                                //envia a todos os clientes
									if (FD_ISSET (j, &master)) {
                                    // menos aos nós e ao socket em escuta
                                  
										if (j != fd &&j != novofd) {
											if (write(j,printR,strlen(printR)) == -1) {
											perror ("write");
                                       
                            }}}}
                            printR[0]='\0';
                                FD_CLR( i , &master); // clear do conjunto mestre
                                search(clientes_fd, i);
                            } else if(nbytes==-1) {
                                perror ("read"); 
                            }
                           
                        }
                            // temos alguns dados do cliente
                            for (j = 0; j<MAX; j++)  {
                                
                                //envia a todos os clientes
                                if (FD_ISSET (j, &master)) {
                                    // exceto a nós e ao socket em escuta
                                  
                                    if (j != fd &&j!=i ) {
                                         if (write(j,buffer,nbytes) == -1) {
                                        perror ("write");
                                         }
                                    }
                                }
                            }
                        }
                    } 
                }
            }
       
    
  
  return 0;
}


void erro(char *msg)
{
	printf("Erro: %s\n", msg);
	exit(-1);
}


int coloca_array(int array[]){
	int j;
	for( j=0;j<=sizeof(array);j++)
	{
		if (array[j]==0)
		{
			return j;
		}
		
	}
	return 0;
}


void search(int array[], int r){
	int j;
	for( j=0;j<=sizeof(array);j++)
	{
		if (array[j]==r)
		{
		array[j]=0;
		}
		
	}
	
}
