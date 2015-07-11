#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <string.h>
#include <sys/select.h>
#include <crypt.h>
#include "desencriptar.h"
#define STUDENTS_FILE "lista_de_passwords_dos_alunos.txt"
#define NUMERO_LINHAS 175
#define PIPE_NAME "pipe_1"
#define PIPE_D_C "pipe_2"
#define FINAL_FILE "final.txt"
#define N_PROCESSOS 5
//ficheiro principal do programa!! 2 headers serao anexados: um com funcoes que estejam incluidas na abordagem brut-force ao ficheiro e outro de managenement a operacao//
void controler();
void dispacher();
void menu();
int numero_de_linhas();
void sigint(int signum);
pid_t pid[N_PROCESSOS-1];
int fd[4][2];
int main() {
		// Creates the named pipe if it doesn't exist yet
	int i;
	int idpai=getpid();
	pid_t pid[N_PROCESSOS-1];
	char frase[50];
	char frase2[50];
  	if ((mkfifo(PIPE_NAME, O_CREAT|O_EXCL|0600)<0) && (errno!= EEXIST)) {
    	perror("Cannot create pipe: ");
		exit(0);
	}	
	if ((mkfifo(PIPE_D_C, O_CREAT|O_EXCL|0600)<0) && (errno!= EEXIST)) {
        perror("Cannot create pipe: ");
                exit(0);
        } 
	for(i=0;i<N_PROCESSOS-1;i++){
		if((pid[i]=fork())==0){
			if(pid[i]<0){
				printf("erro ao fazer fork");
				exit(0);
				}
			
			if(i==0)//dispacher processo dedicado
				dispacher();	
			if(i==1){
				pipe(fd[0]); //processo dedicado a descodificacao de passes
				close(fd[0][1]);
				while(1){	
                	read(fd[0][0],frase,50);   //Leitura de uma pipe que contem a frase que conterá o mail e a password
                	desencriptar(frase);
				}
			}
			if(i==2){
				pipe(fd[1]);
				close(fd[1][1]); // o mesmo que em cima
				while(1){
	            	close(fd[1][1]);
        	    	read(fd[1][0],frase2,50);
                	desencriptar(frase2);
				}
			}
			if(i==3)
				controler(); // processo dedicado ao controler
		}
		else {
			menu(pid);// a funcao menu e chamada pelo pai
			kill( pid[0],SIGKILL);// mata os restantes processos
			kill( pid[1],SIGKILL);
			kill( pid[2],SIGKILL);
			kill(pid[3],SIGKILL);
			wait(NULL); //pai espera pelos processo filhos
			wait(NULL);
			wait(NULL);
			wait(NULL);
			}
	}
	return 0;
}

void menu() {//responsavel pela leitura de cada linha e envio para os processos filhos
     signal(SIGINT, sigint);
	int k;
	char linha[50];
	int check=0;
	char choice;
	int numero=numero_de_linhas();// funcao responsavel por contar o numero de linhas do ficheiro
	printf("1-ataque\n2-sair\n");
    choice=getchar();
    FILE *f;
    while(check==0){
        if(choice=='1'||choice=='2') {
            if(choice=='1') {
                f=fopen(STUDENTS_FILE,"r");
				close(fd[0][0]);
				close(fd[1][0]);
				for(k=0;k<(numero);k++){
                    while(fgets(linha,50,f)!=0){
						if(k%2==0) { // um processo recebe linhas de indice par e outro as linhas de indice impar
							write(fd[0][1],linha,(strlen(linha)*(sizeof(char)))); //diz que não é vector nem ponteiro e poucos argument0s			}
						}
						else {
							write(fd[1][1],linha,(strlen(linha)*(sizeof(char))));
						}	
				
						}
					}
                	fclose(f);
			}
            else if (choice=='2') {
                printf("A sair do programa\n");
		menu(pid);
                        kill( pid[0],SIGKILL);// mata os restantes processos
                        kill( pid[1],SIGKILL);
                        kill( pid[2],SIGKILL);
                        kill(pid[3],SIGKILL);
                        wait(NULL);
                        wait(NULL);
                        wait(NULL);
                        wait(NULL);

                exit(0);
            }
}
}
}

void controler() { ///recebe a informacao do dispacher por um named pipe e escreve a num ficheiro
	char texto[50];
	int fdd;
	int r;
	if ((fdd=open(PIPE_D_C, O_RDONLY))<0){ //AQUI!!!!!!!!!!!
		printf("Cannot open pipe for reading\n");
		exit(1);
	}	
	while(1){
		
		FILE *fw;
		fd_set fds;
                FD_ZERO(&fds);

                FD_SET(fdd,&fds);
                r=select(fdd,&fds,NULL,NULL,NULL);
                if (r==-1) {

                        perror("select failed");

                }

		read(fdd,texto,50*sizeof(char));  
	 	if ((fw=fopen(FINAL_FILE,"a+")) == NULL)
            fw=fopen(FINAL_FILE,"w");
        else{
            fw=fopen(FINAL_FILE,"a+");
			fprintf(fw,"%s\n",texto);
		}
		fclose(fw);
	}	
}

int numero_de_linhas() {
    FILE *f;
    int i=0;
    char aux[50];
    f=fopen(STUDENTS_FILE,"r");
    while(fgets(aux,50,f)!=NULL) {
        i++;
	}
    return i;
}

void dispacher() { 	////ler pipe com os dados dos filhos e mandar para o controler
	int fdd1;
	int fdd2;
	int r;
	int r1;
    char texto[50];
    if ((fdd1=open(PIPE_NAME, O_RDONLY))<0){ 
       		 printf("Cannot open pipe for reading\n");
        	 exit(1);
    }
	if ((fdd2=open(PIPE_D_C, O_WRONLY))<0){ 
       	 	printf("Cannot open pipe for writing\n");
        	exit(1);
    }
	while(1){
		fd_set fds;
		fd_set fds1;
		FD_ZERO(&fds);

		FD_SET(fdd1,&fds);
		r=select(fdd1,&fds,NULL,NULL,NULL);
		if (r==-1) {

			perror("select failed");

		}
		read(fdd1,texto,50*sizeof(char)); 
		 FD_ZERO(&fds1);
		 FD_SET(fdd2,&fds);
		r1=select(fdd2,&fds1,NULL,NULL,NULL);
                if (r1==-1) {

                        perror("select failed");

                }

	
	
		write(fdd2 ,texto,50*sizeof(char)); 
	}
}

void sigint(int signum) { //sinal para sair do programa
 	char option;
  	printf("\n ^C pressed. Do you want to abort? ");
  	option=getchar();
  	if (option == 'y') {
    	printf("Ok, bye bye!\n");
	kill( pid[0],SIGKILL);// mata os restantes processos
        kill( pid[1],SIGKILL);
        kill( pid[2],SIGKILL);
        kill(pid[3],SIGKILL); 
        wait(NULL);
        wait(NULL);
        wait(NULL);
        wait(NULL);

	   exit(0);
  	}
}


