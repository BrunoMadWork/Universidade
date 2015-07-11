#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <crypt.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <errno.h>
#include <sys/select.h>
#define MAX 10
#define PIPE_NAME "pipe_1"
#define PIPE_D_C "pipe_2"

int desencriptar(char linha[]) { //funcao que desencripta passes e as envia para o dispacher
	int i=0,n_p=0,fd;
	char passe[14];
	char possivel[MAX+1];
	char mail[23];
	char frase[50];
	char *pass;
	char *mail1;
	char linha1[50];
	int n=0;
	strcpy(linha1, linha);	 
	mail1 = strtok(linha1, ":");
	while(n!=1){
	pass=strtok(NULL, ":"); //separacao do mail e password
	n=1;
		}


	if ((fd=open(PIPE_NAME, O_WRONLY))<0){

                perror("Cannot open pipe for writing\n");
                exit(0);
        }
	int r;
	 if(decrypter(possivel,pass,i,n_p)==1) {
		fd_set fds;
                FD_ZERO(&fds);

                FD_SET(fd,&fds);
                r=select(fd,&fds,NULL,NULL,NULL);
                if (r==-1) {

                        perror("select failed");

                }

                write(fd, frase, 50*sizeof(char));
	}
return 1;
}


char *crypter(char possivel[]) { //funcao que encripta passes, para serem usadas na abordagem prute force
        char *encriptada;
        encriptada=crypt(possivel,"10");
        return encriptada;
}

int decrypter(char possivel[], char pass[], size_t i, int n_p){ //abordagem recursiva brute force
	int j;
	n_p++;//rever//
	printf("tou aqui");
	static char lista[] = "abcdefghijklmnopqrstuwvxyzABCDEFGHIJKLMNOPQRSTUWVXYZ1234567890";
	if (i == MAX) {
		possivel[i] = 0;
		if (strcmp(crypter(possivel), pass) == 0){
			return 1;
		}
		return 0;
	}
	for (j = 0; j < strlen(lista); j++){
		possivel[i] = lista[j];
		if (decrypter(possivel, pass, i+1, n_p) == 1){
			return 1;
		}
	}
	return 0;
}
