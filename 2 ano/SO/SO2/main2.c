//TEMPO 60:00:00

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/ipc.h> 
#include <sys/shm.h>
#include <stdio.h>
#include <sys/fcntl.h>
#include <semaphore.h>
#include <sys/msg.h>
#include <sys/sem.h>
#include <sys/shm.h>
#include <time.h>
#include <sys/wait.h>
#include <errno.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <sys/fcntl.h>
#define PERMS (S_IRUSR | S_IWUSR | S_IRGRP | S_IWGRP | S_IROTH | S_IWOTH)
#define KEY ((key_t)99887)
#define DIM 2500

typedef struct {
	char **campo;
	char **campo2;
	sem_t *acesso;
	sem_t *acesso2;
} Estrutura_de_memoria;

void inicializar(Estrutura_de_memoria* estrutura,int lado);
void colocar_pulsar(Estrutura_de_memoria* estrutura);
void colocar_lwsp(Estrutura_de_memoria* estrutura);
void colocar_individual(Estrutura_de_memoria* estrutura);
void colocar_glider(Estrutura_de_memoria* estrutura);
void colocar_block(Estrutura_de_memoria* estrutura);
void colocar_small_blinker(Estrutura_de_memoria* estrutura);
void colocar_figuras(Estrutura_de_memoria* estrutura,int n_small_blinkers,int n_individual, int n_block, int n_glider, int n_lw_spaceship, int n_pulsar);
void init(Estrutura_de_memoria* estrutura);
void alteracao(Estrutura_de_memoria* estrutura, int i);
void print_to_disk();
void controlo_escrita();
void escrita(Estrutura_de_memoria* estrutura);
void mensagem();
void terminate(Estrutura_de_memoria* estrutura,int a);
void desalocar(Estrutura_de_memoria* estrutura);
//3*3
char small_blinker[3][3] = {
      {' ',' ',' '}, 
      {'+','+','+'},
      {' ',' ',' '}};
      //3*3
char individual[3][3]= {
        {' ',' ',' '},
        {' ','+',' '},
        {' ',' ',' '}
};         //4*4                                                
char  block[4][4]={
        {' ',' ',' ',' '},
        {' ','+','+',' '},
        {' ','+','+',' '},
        {' ',' ',' ',' '}
};  //5*5
char glider[5][5]={
        {' ',' ',' ',' ',' '},
        {' ',' ','+',' ',' '},
        {' ',' ',' ','+',' '},
        {' ','+','+','+',' '},
        {' ',' ',' ',' ',' '}

};
//6*7
char lightweight_space_ship[6][7]={
        {' ',' ',' ',' ',' ',' ',' '},
        {' ','+',' ',' ','+',' ',' '},
        {' ',' ',' ',' ',' ','+',' '},
        {' ','+',' ',' ',' ','+',' '},
        {' ',' ','+','+','+','+',' '},
        {' ',' ',' ',' ',' ',' ',' '}
};
//11*15
char pulsar[15][15]={
        {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
        {' ',' ',' ','+','+','+',' ',' ',' ','+','+','+',' ',' ',' '},
        {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
        {' ','+',' ',' ',' ',' ','+',' ','+',' ',' ',' ',' ','+',' '},
        {' ','+',' ',' ',' ',' ','+',' ','+',' ',' ',' ',' ','+',' '},
        {' ','+',' ',' ',' ',' ','+',' ','+',' ',' ',' ',' ','+',' '},
        {' ',' ',' ','+','+','+',' ',' ',' ','+','+','+',' ',' ',' '},
        {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
        {' ',' ',' ','+','+','+',' ',' ',' ','+','+','+',' ',' ',' '},
        {' ','+',' ',' ',' ',' ','+',' ','+',' ',' ',' ',' ','+',' '},
        {' ','+',' ',' ',' ',' ','+',' ','+',' ',' ',' ',' ','+',' '},
        {' ','+',' ',' ',' ',' ','+',' ','+',' ',' ',' ',' ','+',' '},
        {' ',' ',' ','+','+','+',' ',' ',' ','+','+','+',' ',' ',' '},
        {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '}
		
};
int geracao =0;
int geracao_final;
int n_j;
int n_particoes;
int intervalo;
int lado;


int main() {
	int i;
        printf("Insira o lado (lado>0) :\n");
        scanf("%d",&lado);
        int n_small_blinkers;
        int n_individual;
        int n_block;
        int n_glider;
        int n_lw_spaceship;
        int n_pulsar;
	mensagem();
	printf("Quantos small blinkers queres?\n");
        scanf("%d",&n_small_blinkers);
        printf("Quantos blocos individuais quer?\n");
        scanf("%d",&n_individual);
        printf("Quantos blocos em conjunto de quatro quer?:\n");
        scanf("%d",&n_block);
        printf("Quantos gliders quer?\n");
        scanf("%d",&n_glider);
        printf("Quantas lightweigth space ships quer?:\n");
        scanf("%d",&n_lw_spaceship);
        printf("Quantos pulsares quer\n");
		scanf("%d",&n_pulsar);
        printf("Quantas rondas quer que o programa faca?\n");
        scanf("%d",&geracao_final);
        printf("De quantas em quantas geracoes quer o Snapshot\n");
        scanf("%d",&intervalo);
        printf("Quantos processos quer? \n");
        scanf("%d",&n_particoes);
        
	Estrutura_de_memoria *estrutura;
	int a= shmget(IPC_PRIVATE, sizeof(Estrutura_de_memoria), IPC_CREAT|0700);
  	if (a < 1){
		exit(0);
	}
	estrutura = (Estrutura_de_memoria*) shmat(a, NULL, 0);
	estrutura = (Estrutura_de_memoria*) malloc(sizeof(Estrutura_de_memoria));
	char **variavel1;
	char **variavel2;
	variavel1=(char**)malloc(lado*sizeof(char*));
	variavel2=(char**)malloc(lado*sizeof(char*));
	for(i=0;i<lado;i++) {
		variavel1[i]=(char*)malloc(lado*sizeof(char));
		variavel2[i]=(char*)malloc(lado*sizeof(char));
	}
  	(estrutura->campo)=variavel1;
  	(estrutura->campo2)=variavel2;
	init(estrutura); //inicializa os semaforos
	    
		
	

        pid_t id_pai=getpid();
        //pid_t id_filho[n_particoes];
        inicializar(estrutura,lado);
		colocar_figuras(estrutura, n_small_blinkers, n_individual, n_block, n_glider, n_lw_spaceship, n_pulsar);
		pid_t sid;
		//vai fazer filhos e esperar que eles morram ao fim de cada geracao incrementando assim a geracao
		for(geracao=1;geracao<=geracao_final;geracao++) {
        for(i=0;i< n_particoes;i++) {

                if(id_pai==getpid())
                  {		
                        sid=fork();
				}    
                
                if(sid==0){
					alteracao(estrutura, i);
				}
				
			}
			if(id_pai==getpid()){
						int w;
                        for(w=0;w<n_particoes;w++) {
							wait(NULL);
							
						}
						sem_wait(estrutura->acesso2);
						escrita(estrutura);
						sem_post(estrutura->acesso2);
						
                        }
		}
			
		
       if(getpid()==id_pai){
	printf("\nDONE\n");
	 desalocar(estrutura);
		terminate(estrutura,a);
	}
	
	
	return 0;
}


void alteracao(Estrutura_de_memoria* estrutura,int i)
{	
	int j,count,flag;
	int comprimento=lado/n_particoes;
	int 	a=(i)*comprimento;
		
	int limite;
	if((a+comprimento)<lado)
		limite=a+comprimento;
	else
		limite=lado;
	

	sem_wait((estrutura->acesso));  
	for(i=a;i<limite;i++) {
		for(j=0;j<lado;j++) {	
			count=0;
			flag=0;
		if(i-1>0){
			flag=1;
		}
		if((flag==1)&&(estrutura->campo[i-1][j]=='+')){
			count++;
		}
			flag=0;
		if(i+1<(lado)){
			flag=1;
		}
		if((flag==1)&&((estrutura->campo[i+1][j])=='+')){
			count++;
		}
			flag=0;
		if(j-1>0){
			flag=1;
		}
		if((flag==1)&&(estrutura->campo[i][j-1]=='+')){
                        count++;
					}
           flag=0;
           
        if((j+1<lado)){
			flag=1;
		}
        if((estrutura->campo[i][j+1]=='+')&&(flag==1)){
                        count++;
					}
		//agora os lados
		flag=0;
		if((i-1>0)&&(j-1>0)){
		flag=1;
	}
		if((flag==1)&&(estrutura->campo[i-1][j-1]=='+')){
                        count++;
          }
         flag=0;               
        if((i+1<lado)&&(j-1>0)){
		flag=1;
	}
        if((flag==1)&&(estrutura->campo[i+1][j-1]=='+')){
                        count++;
					}
         flag=0;
		if((i-1>0)&&(j+1<lado)){
		flag=1;
	}
		if((flag==1)&&(estrutura->campo[i-1][j+1]=='+')){
		count++;
	}
		flag=0;
		if(i+1<lado&&j+1<lado){
		flag=1;
	}
       if((flag==1)&&(estrutura->campo[i+1][j+1]=='+')){
                        count++;
					}
		flag=0;
		if((count<2)&&(estrutura->campo[i][j]=='+'))
			estrutura->campo2[i][j]=' ';
		else if ((count>3)&&(estrutura->campo[i][j]=='+'))
			estrutura->campo2[i][j]=' ';
		else if((count==3||count==2)&&estrutura->campo[i][j]=='+')
			estrutura->campo2[i][j]='+';
		else if((count==3)&&(estrutura->campo[i][j]==' '))
			estrutura->campo2[i][j]='+';
		
		
		//VER SE é ou nao necessario o exit!! para o filho acabar e morrer 
		}
	
	}
	sem_post((estrutura->acesso));
		exit(0);
}

void inicializar(Estrutura_de_memoria* estrutura, int lado) {
        int i,j;
        for(i=0;i<lado;i++) {
                for(j=0;j<lado;j++) {
                       estrutura->campo[i][j]=' ';
}
}
for(i=0;i<lado;i++) {
                for(j=0;j<lado;j++) {
                       estrutura->campo2[i][j]=' ';
}
}
}


void init(Estrutura_de_memoria* estrutura) {
	sem_unlink("ACESSO");
	estrutura->acesso=sem_open("ACESSO",O_CREAT|O_EXCL,0700,1);
	sem_unlink("ACESSO2");
	estrutura->acesso2=sem_open("ACESSO2",O_CREAT|O_EXCL,0700,1);	//1
}
void controlo_escrita(Estrutura_de_memoria* estrutura){
	
		if(geracao==geracao_final) {
			print_to_disk();
		}
		if((geracao%intervalo==0)&&(geracao!=geracao_final)){
			print_to_disk();	
		}
} 

void print_to_disk(Estrutura_de_memoria* estrutura) {
	FILE *f;
	f=fopen("GameOfLifeOutput.txt","a");
	int i;
	int j;
	fprintf(f,"\nGeraçao %d :\n", geracao);
	for(i=0;i<lado;i++){
		for(j=0;j<lado;j++){
			fprintf(f,"%c ",estrutura->campo[i][j]);
				}
			
				fprintf(f,"\n");
			}

fclose(f);

}
void colocar_small_blinker(Estrutura_de_memoria* estrutura) {
	int i,j,x,y;
		int check2=0;
		int check3=0;
		int check=0;
	do{ 	
		
			int teste=time(NULL);
						
			srand(teste);
                	x=rand()%lado;
                	y=rand()%lado; 
					check=0;
			
				
		if((x>0)&&(x<=lado-3)&&(y>0)&&(y<=lado-3)) {
			check=1;
			check2=1;
		}
	
		if(check==1) {
			for(i=x;i<=x+3;i++) {
				for(j=y;j<=y+3;j++) {
					if(estrutura->campo[i][j]=='+')
						check2=0;
				}

			}
		}
		if(check2==1) {
				for(i=x;i<=x+3;i++) {
                                	for(j=y;j<=y+3;j++) {
                                        	estrutura->campo[i][j]=small_blinker[i-x][j-y];
													
                                                }
				}
			check3=1;
			}
	
}while(check3==0);
}
void colocar_block(Estrutura_de_memoria* estrutura) {
		int i,j,x,y;
		int check2=0;
		int check3=0;
		int check;
	do{ 	
		
		
			int teste=time(NULL);
						
			srand(teste);
                	x=rand()%lado;
                	y=rand()%lado;  
					check=0;
				
		if((x>0)&&(x<=lado-4)&&(y>0)&&(y<=lado-4)) {
			check=1;
			check2=1;
		}
		if(check==1) {
			for(i=x;i<=x+4;i++) {
				for(j=y;j<=y+4;j++) {
					if(estrutura->campo[i][j]=='+')
						check2=0;
				}

			}
		}
	
		if(check2==1) {
				for(i=x;i<=x+4;i++) {
                                	for(j=y;j<=y+4;j++) {
                                        	estrutura->campo[i][j]=block[i-x][j-y];
                                                }
				}
			check3=1;
			}
	
	
}while(check3==0);
	
 }

void colocar_glider(Estrutura_de_memoria* estrutura) {
		int i,j,x,y;
		int check2=0;
		int check3=0;
		int check;
	do{ 	
		
		
				
				int teste=time(NULL);		
				srand(teste);
					x=rand()%lado;;
                	y=rand()%lado;  
					check=0;
				
		if((x>0)&&(x<=lado-5)&&(y>0)&&(y<=lado-5)) {
			check=1;
			check2=1;
		}
		if(check==1) {
			for(i=x;i<=x+5;i++) {
				for(j=y;j<=y+5;j++) {
					if((estrutura->campo[i][j])=='+')
							check2=0;
				}

			}
		}
	
		if(check2==1) {
				for(i=x;i<=x+5;i++) {
                                	for(j=y;j<=y+5;j++) {
                                        	estrutura->campo[i][j]=glider[i-x][j-y];
                                                }
				}
			check3=1;
			}
	
	
}while(check3==0);
}
void colocar_individual(Estrutura_de_memoria* estrutura){ 
int i,j,x,y;
		int check2=0;
		int check3=0;
		int check;
do{ 	

		
					int teste=time(NULL);
						
					srand(teste);
                	x=rand()%lado;
                	y=rand()%lado;  
					check=0;
				
		if((x>0)&&(x<=lado-3)&&(y>0)&&(y<=lado-3)) {
			check=1;
			check2=1;
		}
		if(check==1) {
			for(i=x;i<=x+3;i++) {
				for(j=y;j<=y+3;j++) {
					if(estrutura->campo[i][j]=='+')
						check2=0;
				}

			}
		}
	
		if(check2==1) {
				for(i=x;i<=x+3;i++) {
                                	for(j=y;j<=y+3;j++) {
                                        	estrutura->campo[i][j]=individual[i-x][j-y];
                                                }
				}
			check3=1;
			}
	
	
}while(check3==0);
}



void colocar_lwsp(Estrutura_de_memoria* estrutura){
		int i,j,x,y;
		int check2=0;
		int check3=0;
		int check;
	do{ 	
		
		
					int teste=time(NULL);
						
					srand(teste);
                	x=rand()%lado;
                	y=rand()%lado;  
					check=0;
				
		if((x>0)&&(x<=lado-6)&&(y>0)&&(y<=lado-7)) {
			check=1;
			check2=1;
		}
		if(check==1) {
			for(i=x;i<=x+6;i++) {
				for(j=y;j<=y+7;j++) {
					if(estrutura->campo[i][j]=='+')
						check2=0;
				}

			}
		}
	
		if(check2==1) {
				for(i=x;i<=x+6;i++) {
                                	for(j=y;j<=y+7;j++) {
                                        	estrutura->campo[i][j]=lightweight_space_ship[i-x][j-y];
                                                }
				}
			check3=1;
			}
	
}while(check3==0);	
}
void colocar_pulsar(Estrutura_de_memoria* estrutura) {
int i,j,x,y;
		int check2=0;
		int check3=0;
		int check;
do{ 	
		
		
					int teste=time(NULL);
						
					srand(teste);
                	x=rand()%lado;
                	y=rand()%lado;  
					check=0;
				
		if((x>0)&&(x<=lado-15)&&(y>0)&&(y<=lado-15)) {
			check=1;
			check2=1;
		}
		if(check==1) {
			for(i=x;i<=x+15;i++) {
				for(j=y;j<=y+15;j++) {
					if(estrutura->campo[i][j]=='+')
						check2=0;
				}

			}
		}
	
		if(check2==1) {
				for(i=x;i<=x+15;i++) {
                                	for(j=y;j<=y+15;j++) {
                                        	estrutura->campo[i][j]=pulsar[i-x][j-y];
                                                }
				}
			check3=1;
			}
	
	
}while(check3==0);
}

void colocar_figuras(Estrutura_de_memoria* estrutura,int n_small_blinkers,int n_individual, int n_block, int n_glider, int n_lw_spaceship, int n_pulsar) {
	while (n_small_blinkers!=0) {

			colocar_small_blinker(estrutura);
			n_small_blinkers--;
	}
	while (n_individual!=0) {
			colocar_individual(estrutura);
			n_individual--;
	}		
	while (n_block!=0) {
		
			colocar_block(estrutura);
			n_block--;
	}
	while(n_glider!=0) {
		colocar_glider(estrutura);
		n_glider--;
	}
	while(n_lw_spaceship!=0) {
		colocar_lwsp(estrutura);
		n_lw_spaceship--;
}
	while(n_pulsar!=0) {
		colocar_pulsar(estrutura);
		n_pulsar--;
	}
}

void escrita(Estrutura_de_memoria* estrutura){
	
	int i,j;
	for(i=0;i<lado;i++) {
		for(j=0;j<lado;j++){
			estrutura->campo[i][j]=estrutura->campo2[i][j];
		}
	
}
controlo_escrita(estrutura);
}

void mensagem() {
	
	FILE *f;
	f=fopen("GameOfLifeOutput.txt","w");
	fprintf(f,"Bem vindo ao Game of Life\n");
	fclose(f);
	
	
	
}
void terminate(Estrutura_de_memoria* estrutura,int a) {
	sem_close(estrutura->acesso);
	sem_close(estrutura->acesso2);
	sem_unlink("ACESSO");
	sem_unlink("ACESSO2");
	shmctl(a,IPC_RMID,NULL);
}
void desalocar(Estrutura_de_memoria* estrutura) {
	int i;
	for(i=0;i<lado;i++)
		free((estrutura->campo[i]));
		free((estrutura->campo2[i]));
	}



