#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/mman.h>
#include <unistd.h>
#include <sys/ipc.h> 
#include <sys/shm.h>
#include <stdio.h>
#include <sys/time.h>
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
#include <pthread.h>
#define DEAD 0
#define ALIVE 1
#define FALSE 0
#define TRUE 1
//#define DEBUG //uncomment this line to add debug messages

#define GENS 1000
#define SIZE 500    		

typedef struct{ 
	int id;
	int *geracao_threads;
	int **matriz;
	int **matriz_b;
}Estrutura;
int gens;
int gen;
int size;
int n_threads;
int frequencia_snapshots;
pthread_mutex_t mutex=PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t cond=PTHREAD_COND_INITIALIZER;
pthread_mutex_t mutex_imprimir=PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t cond_imprimir=PTHREAD_COND_INITIALIZER;
int number_elements[5] = {0,0,0,0,0};
void* worker(void* parameters);
void terminate(Estrutura *estrutura,int n);
void limpa_file();
void close_threads(pthread_t *threads);
void evolve_left(  int ** matrix, int ** matrix_b, int inicio );
void evolve_right(  int ** matriz, int ** matriz_b, int inicio );
int verificacao(Estrutura *estrutura,int a) ;
void print_to_disk(int ** matriz, int k);
void inicializar_matriz(int **matriz);
void print_on_screen(int **matriz);
void troca(int ** a,int **b);
int rand_index()
{
    return  rand()%size;
}

int is_in_range(int i, int j) {
    if (j < 0 || i < 0 || j >= size || i >= size)
        return FALSE;
    
    return TRUE;
}

int is_alive(int ** matrix, int i, int j) {
    if ( matrix[i][j] == ALIVE)
        return TRUE;
    return FALSE;
}

int put_single( int ** matrix, int ** matrix_b, int i, int j) {
    if (!is_in_range(i,j))
        return FALSE;
    
    if (is_alive(matrix,i,j))
        return FALSE;
    
    matrix[i][j] = matrix_b [i][j] = ALIVE;
    return TRUE;
}

int put_block( int ** matrix, int ** matrix_b, int i, int j) {
    if (!is_in_range(i,j))
        return FALSE;
    int pos = 0;
    for (pos = 0; pos < 2; pos++) {
        if (is_alive(matrix,(i+pos)%size,j))
            return FALSE;
        if (is_alive(matrix,i,(j+pos)%size))
            return FALSE;
    }
    if (is_alive(matrix,(i+pos)%size,(j+pos)%size))
        return FALSE;
    
    matrix [i][j] = matrix_b [i][j] = ALIVE;
    matrix [(i+1)%size][j] = matrix_b [(i+1)%size][j] = ALIVE;
    matrix [i][(j+1)%size] = matrix_b [i][(j+1)%size] = ALIVE;
    matrix [(i+1)%size][(j+1)%size] = matrix_b [(i+1)%size][(j+1)%size] = ALIVE;
    return TRUE;
}

int put_glider ( int ** matrix, int ** matrix_b, int i, int j) {
    if (!is_in_range(i,j))
        return FALSE;
    
    if (is_alive(matrix,(i+1)%size,j))
        return FALSE;
    if (is_alive(matrix,(i+2)%size,(j+1)%size))
        return FALSE;
    if (is_alive(matrix,i,(j+2)%size))
        return FALSE;
    if (is_alive(matrix,(i+1)%size,(j+2)%size))
        return FALSE;
    if (is_alive(matrix,(i+2)%size,(j+2)%size))
        return FALSE;
    
    matrix [(i+1)%size][j] = matrix_b [(i+1)%size][j] = ALIVE;
    matrix [(i+2)%size][(j+1)%size] = matrix_b [(i+2)%size][(j+1)%size] = ALIVE;
    matrix [i][(j+2)%size] = matrix_b [i][(j+2)%size] = ALIVE;
    matrix [(i+1)%size][(j+2)%size] = matrix_b [(i+1)%size][(j+2)%size] = ALIVE;
    matrix [(i+2)%size][(j+2)%size] = matrix_b [(i+2)%size][(j+2)%size] = ALIVE;
    return TRUE;
}

int put_lightweight_space_ship( int ** matrix, int ** matrix_b, int i, int j) {
    if (!is_in_range(i,j))
        return FALSE;
    
    if (is_alive(matrix,i,j))
        return FALSE;
    if (is_alive(matrix,(i+3)%size,j))
        return FALSE;
    if (is_alive(matrix,(i+4)%size,(j+1)%size))
        return FALSE;
    if (is_alive(matrix,i,(j+2)%size))
        return FALSE;
    if (is_alive(matrix,(i+4)%size,(j+2)%size))
        return FALSE;
    if (is_alive(matrix,(i+1)%size,(j+3)%size))
        return FALSE;
    if (is_alive(matrix,(i+2)%size,(j+3)%size))
        return FALSE;
    if (is_alive(matrix,(i+3)%size,(j+3)%size))
        return FALSE;
    if (is_alive(matrix,(i+4)%size,(j+3)%size))
        return FALSE;
    
    
    matrix [i][j] = matrix_b [i][j] = ALIVE;
    matrix [(i+3)%size][j] = matrix_b [(i+3)%size][j] = ALIVE;
    matrix [(i+4)%size][(j+1)%size] = matrix_b [(i+4)%size][(j+1)%size] = ALIVE;
    matrix [i][(j+2)%size] = matrix_b [i][(j+2)%size] = ALIVE;
    matrix [(i+4)%size][(j+2)%size] = matrix_b [(i+4)%size][(j+2)%size] = ALIVE;
    matrix [(i+1)%size][(j+3)%size] = matrix_b [(i+1)%size][(j+3)%size] = ALIVE;
    matrix [(i+2)%size][(j+3)%size] = matrix_b [(i+2)%size][(j+3)%size] = ALIVE;
    matrix [(i+3)%size][(j+3)%size] = matrix_b [(i+3)%size][(j+3)%size] = ALIVE;
    matrix [(i+4)%size][(j+3)%size] = matrix_b [(i+4)%size][(j+3)%size] = ALIVE;
    return TRUE;
}

int put_pulsar ( int ** matrix, int ** matrix_b, int i, int j) {
    if (!is_in_range(i,j))
        return FALSE;
    
    if (is_alive(matrix,(i+2)%size,j))
        return FALSE;
    if (is_alive(matrix,(i+3)%size,j))
        return FALSE;
    if (is_alive(matrix,(i+4)%size,j))
        return FALSE;
    if (is_alive(matrix,(i+8)%size,j))
        return FALSE;
    if (is_alive(matrix,(i+9)%size,j))
        return FALSE;
    if (is_alive(matrix,(i+10)%size,j))
        return FALSE;
    
    if (is_alive(matrix,i,(j+2)%size))
        return FALSE;
    if (is_alive(matrix,(i+5)%size,(j+2)%size))
        return FALSE;
    if (is_alive(matrix,(i+7)%size,(j+2)%size))
        return FALSE;
    if (is_alive(matrix,(i+12)%size,(j+2)%size))
        return FALSE;
    
    if (is_alive(matrix,i,(j+3)%size))
        return FALSE;
    if (is_alive(matrix,(i+5)%size,(j+3)%size))
        return FALSE;
    if (is_alive(matrix,(i+7)%size,(j+3)%size))
        return FALSE;
    if (is_alive(matrix,(i+12)%size,(j+3)%size))
        return FALSE;
    
    if (is_alive(matrix,i,(j+4)%size))
        return FALSE;
    if (is_alive(matrix,(i+5)%size,(j+4)%size))
        return FALSE;
    if (is_alive(matrix,(i+7)%size,(j+4)%size))
        return FALSE;
    if (is_alive(matrix,(i+12)%size,(j+4)%size))
        return FALSE;
    
    if (is_alive(matrix,(i+2)%size,(j+5)%size))
        return FALSE;
    if (is_alive(matrix,(i+3)%size,(j+5)%size))
        return FALSE;
    if (is_alive(matrix,(i+4)%size,(j+5)%size))
        return FALSE;
    if (is_alive(matrix,(i+8)%size,(j+5)%size))
        return FALSE;
    if (is_alive(matrix,(i+9)%size,(j+5)%size))
        return FALSE;
    if (is_alive(matrix,(i+10)%size,(j+5)%size))
        return FALSE;
    
    if (is_alive(matrix,(i+2)%size,(j+7)%size))
        return FALSE;
    if (is_alive(matrix,(i+3)%size,(j+7)%size))
        return FALSE;
    if (is_alive(matrix,(i+4)%size,(j+7)%size))
        return FALSE;
    if (is_alive(matrix,(i+8)%size,(j+7)%size))
        return FALSE;
    if (is_alive(matrix,(i+9)%size,(j+7)%size))
        return FALSE;
    if (is_alive(matrix,(i+10)%size,(j+7)%size))
        return FALSE;
    
    if (is_alive(matrix,i,(j+8)%size))
        return FALSE;
    if (is_alive(matrix,(i+5)%size,(j+8)%size))
        return FALSE;
    if (is_alive(matrix,(i+7)%size,(j+8)%size))
        return FALSE;
    if (is_alive(matrix,(i+12)%size,(j+8)%size))
        return FALSE;
    
    if (is_alive(matrix,i,(j+9)%size))
        return FALSE;
    if (is_alive(matrix,(i+5)%size,(j+9)%size))
        return FALSE;
    if (is_alive(matrix,(i+7)%size,(j+9)%size))
        return FALSE;
    if (is_alive(matrix,(i+12)%size,(j+9)%size))
        return FALSE;
    
    if (is_alive(matrix,i,(j+10)%size))
        return FALSE;
    if (is_alive(matrix,(i+5)%size,(j+10)%size))
        return FALSE;
    if (is_alive(matrix,(i+7)%size,(j+10)%size))
        return FALSE;
    if (is_alive(matrix,(i+12)%size,(j+10)%size))
        return FALSE;
    
    if (is_alive(matrix,(i+2)%size,(j+12)%size))
        return FALSE;
    if (is_alive(matrix,(i+3)%size,(j+12)%size))
        return FALSE;
    if (is_alive(matrix,(i+4)%size,(j+12)%size))
        return FALSE;
    if (is_alive(matrix,(i+8)%size,(j+12)%size))
        return FALSE;
    if (is_alive(matrix,(i+9)%size,(j+12)%size))
        return FALSE;
    if (is_alive(matrix,(i+10)%size,(j+12)%size))
        return FALSE;
    
    matrix [(i+2)%size][j] = matrix_b [(i+2)%size][j] = ALIVE;
    matrix [(i+3)%size][j] = matrix_b [(i+3)%size][j] = ALIVE;
    matrix [(i+4)%size][j] = matrix_b [(i+4)%size][j] = ALIVE;
    matrix [(i+8)%size][j] = matrix_b [(i+8)%size][j] = ALIVE;
    matrix [(i+9)%size][j] = matrix_b [(i+9)%size][j] = ALIVE;
    matrix [(i+10)%size][j] = matrix_b [(i+10)%size][j] = ALIVE;
    matrix [i][(j+2)%size] = matrix_b [i][(j+2)%size] = ALIVE;
    matrix [(i+5)%size][(j+2)%size] = matrix_b [(i+5)%size][(j+2)%size] = ALIVE;
    matrix [(i+7)%size][(j+2)%size] = matrix_b [(i+7)%size][(j+2)%size] = ALIVE;
    matrix [(i+12)%size][(j+2)%size] = matrix_b [(i+12)%size][(j+2)%size] = ALIVE;
    matrix [i][(j+3)%size] = matrix_b [i][(j+3)%size]  = ALIVE;
    matrix [(i+5)%size][(j+3)%size] = matrix_b [(i+5)%size][(j+3)%size] = ALIVE;
    matrix [(i+7)%size][(j+3)%size] = matrix_b [(i+7)%size][(j+3)%size] = ALIVE;
    matrix [(i+12)%size][(j+3)%size] = matrix_b [(i+12)%size][(j+3)%size] = ALIVE;
    matrix [i][(j+4)%size] = matrix_b [i][(j+4)%size] = ALIVE;
    matrix [(i+5)%size][(j+4)%size] = matrix_b [(i+5)%size][(j+4)%size] = ALIVE;
    matrix [(i+7)%size][(j+4)%size] = matrix_b [(i+7)%size][(j+4)%size] = ALIVE;
    matrix [(i+12)%size][(j+4)%size] = matrix_b [(i+12)%size][(j+4)%size] = ALIVE;
    matrix [(i+2)%size][(j+5)%size] = matrix_b [(i+2)%size][(j+5)%size] = ALIVE;
    matrix [(i+3)%size][(j+5)%size] = matrix_b [(i+3)%size][(j+5)%size] = ALIVE;
    matrix [(i+4)%size][(j+5)%size] = matrix_b [(i+4)%size][(j+5)%size] = ALIVE;
    matrix [(i+8)%size][(j+5)%size] = matrix_b [(i+8)%size][(j+5)%size] = ALIVE;
    matrix [(i+9)%size][(j+5)%size] = matrix_b [(i+9)%size][(j+5)%size] = ALIVE;
    matrix [(i+10)%size][(j+5)%size] = matrix_b [(i+10)%size][(j+5)%size] = ALIVE;
    matrix [(i+2)%size][(j+7)%size] = matrix_b [(i+2)%size][(j+7)%size] = ALIVE;
    matrix [(i+3)%size][(j+7)%size] = matrix_b [(i+3)%size][(j+7)%size] = ALIVE;
    matrix [(i+4)%size][(j+7)%size] = matrix_b [(i+4)%size][(j+7)%size] = ALIVE;
    matrix [(i+8)%size][(j+7)%size] = matrix_b [(i+8)%size][(j+7)%size] = ALIVE;
    matrix [(i+9)%size][(j+7)%size] = matrix_b [(i+9)%size][(j+7)%size] = ALIVE;
    matrix [(i+10)%size][(j+7)%size] = matrix_b [(i+10)%size][(j+7)%size] = ALIVE;
    matrix [i][(j+8)%size] = matrix_b [i][(j+8)%size] = ALIVE;
    matrix [(i+5)%size][(j+8)%size] = matrix_b [(i+5)%size][(j+8)%size] = ALIVE;
    matrix [(i+7)%size][(j+8)%size] = matrix_b [(i+7)%size][(j+8)%size] = ALIVE;
    matrix [(i+12)%size][(j+8)%size] = matrix_b [(i+12)%size][(j+8)%size] = ALIVE;
    matrix [i][(j+9)%size] = matrix_b [i][(j+9)%size] = ALIVE;
    matrix [(i+5)%size][(j+9)%size] = matrix_b [(i+5)%size][(j+9)%size] = ALIVE;
    matrix [(i+7)%size][(j+9)%size] = matrix_b [(i+7)%size][(j+9)%size] = ALIVE;
    matrix [(i+12)%size][(j+9)%size] = matrix_b [(i+12)%size][(j+9)%size] = ALIVE;
    matrix [i][(j+10)%size] = matrix_b [i][(j+10)%size] = ALIVE;
    matrix [(i+5)%size][(j+10)%size] = matrix_b [(i+5)%size][(j+10)%size] = ALIVE;
    matrix [(i+7)%size][(j+10)%size] = matrix_b [(i+7)%size][(j+10)%size] = ALIVE;
    matrix [(i+12)%size][(j+10)%size] = matrix_b [(i+12)%size][(j+10)%size] = ALIVE;
    matrix [(i+2)%size][(j+12)%size] = matrix_b [(i+2)%size][(j+12)%size] = ALIVE;
    matrix [(i+3)%size][(j+12)%size] = matrix_b [(i+3)%size][(j+12)%size] = ALIVE;
    matrix [(i+4)%size][(j+12)%size] = matrix_b [(i+4)%size][(j+12)%size] = ALIVE;
    matrix [(i+8)%size][(j+12)%size] = matrix_b [(i+8)%size][(j+12)%size] = ALIVE;
    matrix [(i+9)%size][(j+12)%size] = matrix_b [(i+9)%size][(j+12)%size] = ALIVE;
    matrix [(i+10)%size][(j+12)%size] = matrix_b [(i+10)%size][(j+12)%size] = ALIVE;
    return TRUE;
}


void init( int ** matrix, int ** matrix_b )
{
	 
    int i, j;
    
    for (i = 0; i < size; i++)
    {   
        for (j = 0; j < size; j++)
        {
            matrix[i][j] = DEAD;
            matrix_b[i][j] = DEAD;
        }
    }
   
    #ifdef DEBUG
    fprintf( stderr, "Boundary set\n");
    #endif
    int cnt;
    
    for (;number_elements[0] > 0; number_elements[0]--) {
        cnt = 0;
        while (!put_single(matrix, matrix_b, rand_index(), rand_index())) if (++cnt > 3) break;
        #ifdef DEBUG
        if (cnt <= 3)
            fprintf( stderr,"Single created!");
        #endif
    }
    
    for (;number_elements[1] > 0; number_elements[1]--) {
        cnt = 0;
        while (!put_block(matrix, matrix_b, rand_index(), rand_index())) if (++cnt > 3) break;
        #ifdef DEBUG
        if (cnt <= 3)
            fprintf( stderr,"Block created!");
        #endif
    }
    for (;number_elements[2] > 0; number_elements[2]--) {
        cnt = 0;
        while (!put_glider(matrix, matrix_b, rand_index(), rand_index())) if (++cnt > 3) break;
        #ifdef DEBUG        
        if (cnt <= 3)
            fprintf( stderr,"Glider created!");
        #endif
    }
    for (;number_elements[3] > 0; number_elements[3]--) {
        cnt = 0;
        while (!put_lightweight_space_ship(matrix, matrix_b, rand_index(), rand_index())) if (++cnt > 3) break;
        #ifdef DEBUG        
        if (cnt <= 3)
            fprintf( stderr,"Space ship created!");
        #endif
    }
    for (;number_elements[4] > 0; number_elements[4]--) {
        cnt = 0;
        while (!put_pulsar(matrix, matrix_b, rand_index(), rand_index())) if (++cnt > 3) break;
        #ifdef DEBUG        
        if (cnt <= 3)
            fprintf( stderr,"Pulsar created!");
        #endif
    }
    
}


void evolve(  int ** matrix, int ** matrix_b, int inicio )
{  
    int i, j, sum;
    
    for (i = inicio+1; i <(inicio+size/n_threads) -1; i++)
    {  
        for (j = 0; j < size; j++)
        {
            printf("A processar...\n");
            int x,y;
            if (i == 0)
                x = size - 1;
            else
                x = (i-1)%(inicio+size/n_threads);
            if (j == 0)
                y = size - 1;
            else
                y = (j-1)%size;
           
            sum =
            matrix[x][y] +
            matrix[x][j] +
            matrix[x][(j+1)%size] +
            matrix[i][y] +
            matrix[i][(j+1)%size] +
            matrix[(i+1)%size][y] +
            matrix[(i+1)%size][j] +
            matrix[(i+1)%size][(j+1)%size];
            
            
            if (sum < 2 || sum > 3)
                matrix_b[i][j] = DEAD ;
            else if (sum == 3)
                matrix_b[i][j] = ALIVE ;
            else
                matrix_b[i][j] = matrix[i][j] ;
        }
            
    }
		
    
}

void cleanup(int **matrix, int **matrix_b) {
    int i;
    for (i = 0; i < size; i++)
    {
        free( matrix[i] );
        free( matrix_b[i] );
    }
    free( matrix );
    free( matrix_b );
}

int main( int argn , char * argv[] ) {
	
	time_t start,end;
	int i;
	Estrutura *estrutura;
	int **matrix;
	int **matrix_b;
	double dif;
	printf("Insira o tamanho do lado\n");
	scanf("%d",&size);
	printf("Insira o numero de geracoes\n");
	scanf("%d",&gens);
	matrix = (int **)malloc( sizeof(int *) * (size) );
    matrix_b = (int **)malloc( sizeof(int *) * (size) );
    for (i = 0; i < size; i++)
    {
        matrix[i] = (int *)malloc( sizeof(int) * (size) );
        matrix_b[i] = (int *)malloc( sizeof(int) * (size) );
    }
	
	inicializar_matriz(matrix_b);
	
	printf("Numero de threads?\n");
	scanf("%d",&n_threads);
	frequencia_snapshots=1;
	time (&start);
	int geracao_threads[n_threads];
	for(i=0;i<n_threads;i++) {
		geracao_threads[i]=0;
	}
	estrutura=(Estrutura*)malloc(n_threads*sizeof(Estrutura));
	pthread_t thread[n_threads];
	
    
	//limpa_file();
        
  
   		
	
    number_elements [0] = 5; // 5 single
    number_elements [1] = 5; // 5 blocks
    number_elements [2] = 3; // 3 gliders
    number_elements [3] = 2; // 2 lightweigh space ships
    number_elements [4] = 1; // 1 pulsar
    
    //POR OBJETOS
	init(matrix,matrix_b);
	
    
    for(i=0;i<n_threads;i++) {
		estrutura[i].id=i;
		estrutura[i].geracao_threads=geracao_threads; 
		estrutura[i].matriz=matrix;
		estrutura[i].matriz_b=matrix_b;
		pthread_create(&thread[i],NULL,worker,&estrutura[i]);
		
	}
    

	close_threads(thread);	
    cleanup(matrix, matrix_b);
	time (&end);
	dif = difftime (end,start);
	printf("\n\nDemorou %.2lf segundos a concluir \n\n", dif);
    exit( 0 );
}

void limpa_file(){
	FILE *f;
	f=fopen("GAME_OF_LIFE.txt","w");
	fprintf(f,"BEM VINDO AO GAME OF LIFE!!! \n");
	fclose(f);
}
void* worker(void* argumentos) {
	
	Estrutura *estrutura;
	estrutura =((Estrutura*) argumentos);
	int inicio = estrutura->id * (size/n_threads);
	int k;
	//printf("Sou um trabalhador!!! o meu id e %d\n",estrutura->id);
	int a;
	int b;
	for(k=0;k<gens;k++) {
			//evolui o meio
		evolve(estrutura->matriz,estrutura->matriz_b,inicio);
			//evolui a esquerda
		pthread_mutex_lock(&mutex);
		a=estrutura->id-1;//default
			
		if(estrutura->id==0){
			a=n_threads-1;
		}
		while(estrutura->geracao_threads[a]<estrutura->geracao_threads[estrutura->id]){
			
			
			pthread_cond_wait(&cond,&mutex);
		}				
		pthread_mutex_unlock(&mutex);
		evolve_left(estrutura->matriz,estrutura->matriz_b,inicio);
		
							
		//evolui a direita
		pthread_mutex_lock(&mutex);
				
		b=estrutura->id+1;
		if(estrutura->id==n_threads-1){
			b=0;						
    		while(estrutura->geracao_threads[b]<estrutura->geracao_threads[estrutura->id]){			
    				pthread_cond_wait(&cond,&mutex);
    		}
    		evolve_right(estrutura->matriz,estrutura->matriz_b,inicio);
    		(estrutura->geracao_threads[estrutura->id])++;
    		pthread_cond_broadcast(&cond);
    		pthread_mutex_unlock(&mutex);
    				//acaba tudo e manda signal
		}
		else {
            
            pthread_mutex_unlock(&mutex);
            

            
			evolve_right(estrutura->matriz,estrutura->matriz_b,inicio);
			(estrutura->geracao_threads[estrutura->id])++;
		}
	

		if(verificacao(estrutura,k+1)==TRUE&&(k+1)%frequencia_snapshots==0){
			pthread_mutex_lock(&mutex_imprimir);	
			//printf("Vai escrever para disco\n,%d",k);
			//print_to_disk(estrutura->matriz_b,k);
			pthread_cond_broadcast(&cond_imprimir);
			pthread_mutex_unlock(&mutex_imprimir);
			//printf("IMPRIMIU A GERACAO %d",k);
		}
		//brodcast para escrever
		else  if(verificacao(estrutura,k+1)==FALSE&&(k+1)%frequencia_snapshots==0){
			pthread_mutex_lock(&mutex_imprimir);
		
		while(verificacao(estrutura,k+1)==FALSE){
			pthread_cond_wait(&cond_imprimir,&mutex_imprimir);
			}
			pthread_mutex_unlock(&mutex_imprimir);
		}
	
			troca(estrutura->matriz,estrutura->matriz_b);
			
		}
		
		
						

	pthread_exit(NULL);


}


void evolve_left(  int ** matrix, int ** matrix_b, int inicio )
{	
    int i, j, sum;
    
    i =inicio; 
    
        for (j = 0; j < size; j++)
        {
            
            int x,y;
            if (i == 0)
                x = size - 1;
            else
                x = (i-1)%(size);
            if (j == 0)
                y = size - 1;
            else
                y = (j-1)%size;
            
            sum =
            matrix[x][y] +
            matrix[x][j] +
            matrix[x][(j+1)%size] +
            matrix[i][y] +
            matrix[i][(j+1)%size] +
            matrix[(i+1)%size][y] +
            matrix[(i+1)%size][j] +
            matrix[(i+1)%size][(j+1)%size];
            
            
            if (sum < 2 || sum > 3)
                matrix_b[i][j] = DEAD ;
            else if (sum == 3)
                matrix_b[i][j] = ALIVE ;
            else
                matrix_b[i][j] = matrix[i][j] ;
        }
    
    
    
        
    printf("evoluio  a esquerda\n");
}
void evolve_right(  int ** matriz, int ** matriz_b, int inicio )
{
	
    int i, j, sum;
    
		i=(inicio+(size/n_threads))-1; // alterei isto, puz o menos 1
        for (j = 0; j < size; j++)
        {
            
            int x,y;
            if (i == 0)
                x = size - 1;
            else
                x = (i-1)%(size);
            if (j == 0)
                y = size - 1;
            else
                y = (j-1)%size;
            
            sum =
            matriz[x][y] +
            matriz[x][j] +
            matriz[x][(j+1)%size] +
            matriz[i][y] +
            matriz[i][(j+1)%size] +
            matriz[(i+1)%size][y] +
            matriz[(i+1)%size][j] +
            matriz[(i+1)%size][(j+1)%size];
            
            
            if (sum < 2 || sum > 3)
                matriz_b[i][j] = DEAD ;
            else if (sum == 3)
                matriz_b[i][j] = ALIVE ;
            else
                matriz_b[i][j] = matriz[i][j] ;
        }
    
    
    printf("evoluiu a direita\n");
        
}

void print_to_disk(int ** matriz, int k) {
	printf("escreveu para disco\n");
	FILE *f;
	f=fopen("GAME_OF_LIFE.txt","a");
	int i;
	int j;
	fprintf(f,"\nGeraçao %d :\n", k+1);
	for(i=0;i<size;i++){
		for(j=0;j<size;j++){
			fprintf(f,"%d ",matriz[i][j]);
				}
			
				fprintf(f,"\n");
			}

fclose(f);

}

void print_on_screen(int **matriz) {
	int i, j;
for (i = 0; i <size; i++){
        for (j = 0; j < size; j++)
        {
            printf("%d ", matriz[j][i] );
           
        }
       
        printf ("\n");
       
    }
    printf("\n");
    
	
}
void close_threads(pthread_t *threads) {
	int i;
	for(i=0;i<n_threads;i++) {
		pthread_join(threads[i],NULL);
		
	}
	

}
int verificacao(Estrutura *estrutura,int a) {
	
	int i;
	int bool=TRUE;
	for(i=0;i<n_threads;i++) {
		if(estrutura->geracao_threads[i]!=a){
			bool=FALSE;
		}
	}
	
	
	return bool;
}


void inicializar_matriz(int **matriz) {
	int i;
	int j;
	for(i=0;i<size;i++) {
		for(j=0;j<size;j++) {
			matriz[i][j]=0;
		}
	}
}
void troca(int ** a,int **b) {
int aux;
aux=*a;
*a=*b;
*b=aux;


}
