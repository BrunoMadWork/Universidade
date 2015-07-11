/*O ficheiro main.c é o ficheiro onde a interface entre o programa e o utilizador é construida usando para isso um menu numérico*/

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <conio.h>

#include "Date.h"
#include "Team.h"
#include "Game.h"
#include "LinkedGame.h"

#define TEAMS_MAX 40 /*Define uma constante que representa a quantidade máxima de equipas num campeonato*/
#define MAX 200 /*Define uma constante que é usada como tamanho máximo para arrays se não se souber a quantidade de informação que estes irão guardar*/
#define DAYS_MAX 31 /*Define o número máximo de dias num mês*/
#define MONTHS_MAX 12 /*Define o número máximo de meses num ano*/
#define YEARS_MAX 2100 /*Define um limite máximo para os anos*/
#define SCORE_MAX 200 /*Define um limite máximo para a quantidade de golos que uma equipa pode marcar por jogo*/

Team* NTeams; /*Variável global que guarda um ponteiro para um array de equipas ordenadas por nome*/
Team* STeams; /*Variável global que guarda um ponteiro para um array de equipas ordenadas por pontuação*/
GameList gameList; /*Variável global que representa uma lista ligada de jogos ordenados por data*/

/*Informa o utilizador que ocorreu um erro ao alocar espaço na heap ou ao abrir/escrever num ficheiro e termina o programa*/
void error() {
    printf("Ocorreu um erro\n");
    printf("O programa vai encerrar\n");
    getch();
    exit(1);
}

/*Recebe um ponteiro para um array de equipas e devolve o tamanho do array
    Nota: Considera-se que o array acaba quando quando um elemento for NULL*/
int size (Team* teams) {

    int count = 0;

    while(teams[count] != NULL) {
        count++;
    }
    return count;
}

/*Recebe um ponteiro para um array de equipas e imprime o array na consola*/
void printTeams(Team* teams) {

    int i;
    for(i=0; i<size(teams); i++) {
        printTeam(teams[i]);
        printf("\n");
    }
}

/*Recebe um ponteiro para um array de equipas e ordena o array por nome usando o algoritmo de ordenação BubbleSort*/
void sortByName (Team *teams, int n) {

    Team aux;
    int end = 0;
    int i;
    int j;

    for (i=0; i < n-1 && end == 0; i++) {
        end = 1;
        for (j = 0; j < n-i-1; j++) {
            if (compareTeamByName(*(teams+j) , *(teams+j+1)) == 1) {
                end = 0;
                aux = *(teams+j);
                *(teams+j) = *(teams+j+1);
                *(teams+j+1) = aux;
            }
        }
    }
}

/*Recebe um ponteiro para um array de equipas e ordena o array por pontuação usando o algoritmo de ordenação BubbleSort*/
void sortByScore (Team *teams, int n) {

    Team aux;
    int end = 0;
    int i;
    int j;

    for (i=0; i < n-1 && end == 0; i++) {
        end = 1;
        for (j = 0; j < n-i-1; j++) {
            if ((*(teams+j))->leagueScore < (*(teams+j+1))->leagueScore) {
                end = 0;
                aux = *(teams+j);
                *(teams+j) = *(teams+j+1);
                *(teams+j+1) = aux;
            }
        }
    }
}

/*Conta o número de digitos de um inteiro*/
int countDigits(int number) {

    int n = number;
    int digits = 0;

    while(n!=0) {
        n=n/10;
        digits++;
    }

    return digits;
}


/*Função onde é realizada toda a interacção com o utilizador.

    O obectivo da função é aceitar apenas como válidas as opções que respeitem os seguintes critérios:

        - O texto introduzido pelo utilizador apenas pode conter digitos
        - Se for introduzido algo que não seja um digito (ex: espaço, tab, letra) a opção é considerada inválida
        - O número máximo que pode ser introduzido é especificado pelo parametro "args". Se for introduzido um numero superior a args a opção é considerada inválida
        - Não são aceites números negativos

    Parametros:

        args - Número máximo que o utilizador pode introduzir
        zero - Variável booleana. Se zero = 1 o número 0 é considerado uma opção válida. Se zero = 0 o número 0 é uma opção inválida

    Devolve:

        A opção escolhida pelo utilizador ou -1 se o utilizador escrever uma opção inválida
*/
int option(int args, int zero) {

    int choice; /*Guarda a opção introduzida pelo utilizador*/
    int check = 0;  /*Variável booleana que verifica se foi encontrado o caracter '\n' ou não*/
    int check_digit = 1; /*Variável booleana que verifica se o utilizador escreveu apenas digitos*/
    int digits = countDigits(args);

    char scan[digits+2];
    fgets(scan, digits+2, stdin);

    int i;
    int end;

    /*Para que a opção seja válida no array "scan" tem de existir apenas digitos (0-9) e obrigatoriamente um "\n".
    O "\n" significa que o utilizador não escreveu algo que apesar de no inicio estar correcto não é válido. Ex: "12fgb" para args=2*/
    for(i=0; i<=digits && check != 1; i++) {
        if (scan[i] == '\n') {
            check = 1;
            end = i;
        }
    }
    if (check == 0) {
        while (fgetc(stdin) != '\n');
    }
    if (end == 0) check = 0;

    if (check == 1) {
        for(i=0; i<end; i++) {
            if (isdigit(scan[i]) == 0) {
                check_digit = 0;
            }
        }
    }

    choice = atoi(scan);

    /*A função devolve -1 (erro) caso: as variáveis booleanas sejam diferentes de 1 ou choice seja maior do que args ou igual a 0 no caso zero = 0*/
    if(zero == 0 && !(check_digit == 1 && check == 1 && (choice > 0 && choice <= args))) return -1;
    else if(zero == 1 && !(check_digit == 1 && check == 1 && (choice >= 0 && choice <= args))) return -1;

    return choice;
}

/*Aloca espaço na heap para os arrays de equipas "NTeams e STeams" e inicializa-os com as equipas guardadas no ficheiro "Teams.txt"*/
void initializeTeamList(void) {

    NTeams = (Team*) calloc(TEAMS_MAX, sizeof(Team));
    if (NTeams == NULL) error();

    STeams = (Team*) calloc(TEAMS_MAX, sizeof(Team));
    if (STeams == NULL) error();

    FILE *f = fopen("Teams.txt", "r");
    if (f == NULL) error();

    char line[MAX];

    int i = 0;
    while (fgets(line, MAX, f) != NULL) {

        char* name = (char*) malloc (MAX * sizeof(char));
        if (name == NULL) error();
        char* town = (char*) malloc (MAX * sizeof(char));
        if (town == NULL) error();
        int leagueScore;

        /*A função sscanf vai ler a string "line" e dividi-la em várias strings.
        Lê até encontrar o caractér '/' e guarda numa string depois salta esse caracter e assim sucessivamente até encontrar '\0'*/
        sscanf(line,"%[^/]/%[^/]/%d", name, town, &leagueScore);

        Team team = newTeam(name, town, leagueScore);
        if (team == NULL) error();

        /*Insere a equipa que foi lida do ficheiro nos arrays*/
        NTeams[i] = team;
        STeams[i] = team;

        i++;
    }

    sortByName(NTeams, size(NTeams));
    sortByScore(STeams, size(NTeams));

    fclose(f);
}

/*Inicializa a lista ligada "gameList" com os jogos guardados no ficheiro Games.txt*/
void initializeGameList(void) {

    gameList = createGameList();
    if (gameList == NULL) error();

    FILE *f = fopen("Games.txt", "r");

    if (f == NULL) {
        error();
    }

    Team local;
    Team visitor;
    Date date;
    int lScore;
    int vScore;

    char line[MAX];
    char lName[MAX];
    char vName[MAX];
    char sDate[MAX];

    int day;
    int month;
    int year;

    int i;

    while (fgets(line, MAX, f) != NULL) {

        /*A função sscanf vai ler a string "line" e dividi-la em várias strings.
        Lê até encontrar o caractér '/' e guarda numa string depois salta esse caracter e assim sucessivamente até encontrar '\0'*/
        sscanf(line,"%[^/]/%[^/]/%[^/]/%d/%d", lName, vName, sDate, &lScore, &vScore);
        sscanf(sDate,"%d-%d-%d", &day, &month, &year);

        date = newDate(day, month, year);

        /*Procura no array NTeams uma equipa que tenha um nome igual a lName*/
        i = 0;
        while (strcmp(NTeams[i]->name, lName) != 0 ) {
            i++;
        }
        local = NTeams[i];

        /*Procura no array NTeams uma equipa que tenha um nome igual a vName*/
        i = 0;
        while (strcmp(NTeams[i]->name, vName) != 0 ) {
            i++;
        }
        visitor = NTeams[i];

        Game game = newGame(local, visitor, date, lScore, vScore);
        if (game == NULL) error();

        int cr;
        insertGame(gameList, game); /*Insere o jogo que foi lido do ficheiro na lista ligada "gameList"*/
        if (cr == 0) error();
        cr = insertGame(local->teamGames, game); /*Insere o jogo que foi lido do ficheiro na lista ligada da equipa local*/
        if (cr == 0) error();
        cr = insertGame(visitor->teamGames, game); /*Insere o jogo que foi lido do ficheiro na lista ligada da equipa visitante*/
        if (cr == 0) error();

    }

    fclose(f);
}

/*Função usada para libertar toda a memória que foi alocada na heap durante a execução do programa.

    Liberta o espaço aloca para:
        - Todas as equipas e respectivos nomes, localidades e lista ligada de jogos
        - Todos os jogos e respectivas datas
        - Os arrays "NTeams" e "STeams"
        - A lista ligada "gameList"*/
void destroy() {

    int i;
    for(i=0; i < size(NTeams); i++) {
        freeTeam(NTeams[i]);
    }

    free(NTeams);
    free(STeams);

    GameList aux = gameList->next;

    while(aux != NULL) {
        freeGame(aux->info);
        aux = aux->next;
    }
    destroyGameList(gameList);

}

/*Guarda no ficheiro "Games.txt" todos os jogos guardados na lista ligada "gameList"*/
void saveGameList() {

    FILE *f = fopen("Games.txt", "w");
    if (f == NULL) error();

    char line[MAX];

    GameList aux = gameList->next;

    while (aux != NULL) {
        Game game = aux->info;
        /*A função sprintf junta todas as strings especificadas separando-as pelo caracter '/' e guarda a string resultante em "line"*/
        sprintf(line,"%s/%s/%d-%d-%d/%d/%d\n", game->local->name , game->visitor->name, game->date.day, game->date.month, game->date.year, game->lScore, game->vScore);
        fputs(line, f);
        aux = aux->next;
    }

    fclose(f);
}

/*Guarda no ficheiro "Teams.txt" todas as equipas guardadas no array "STeams"*/
void saveTeamList() {

    FILE *f = fopen("Teams.txt", "w");
    if (f == NULL) error();

    char line[MAX];

    int i;
    for (i=0; i < size(STeams); i++) {
        sprintf(line,"%s/%s/%d\n", STeams[i]->name , STeams[i]->town, STeams[i]->leagueScore);
        fputs(line, f);
    }

    fclose(f);
}

/*Pede ao utilizador para escolher uma equipa das que estão guardadas em "NTeams"
    Devolve: a equipa escolhida*/
Team selectTeam(char* toPrint) {

    if (toPrint != NULL) {
        printf("%s\n\n", toPrint);
    }

    Team team;
    int choice;

    int i;
    do {
        printf("Escolha uma equipa:\n\n");
        for(i=0; i<size(NTeams); i++) {
            printf("%d - %s\n", i+1, NTeams[i]->name);
        }
        printf("\nOpcao: ");
        choice = option(size(NTeams), 0);
        printf("\n");
        system("cls");
    } while(choice == -1);

    team = NTeams[choice - 1];
    return team;
}

/*Pede ao utilizador para escolher um jogo dos que estão guardados em "gameList"
    Devolve o jogo escolhido*/
Game selectGame() {

    Game game;
    GameList aux = gameList->next;
    int choice;


    int i;
    do {
        printf("Escolha um jogo:\n\n");
        for(i=1; i<=gameListSize(gameList); i++) {
            printf("%d - ", i);
            printGame(aux->info);
            printf("\n");
            aux = aux->next;
        }
        printf("\nOpcao: ");

        choice = option(gameListSize(gameList), 0);
        printf("\n");
        aux = gameList->next;
        system("cls");
    } while(choice == -1);

    for(i=1; i<choice; i++) {
        aux = aux->next;
    }
    game = aux->info;

    return game;
}

/*Pede ao utilizador introduzir uma data
    Devolve a data introduzida*/
Date selectDate() {

    Date date;
    int day;
    int month;
    int year;

    do {
        printf("Introduza a data do jogo: \n\n");
        do {
            printf("Dia: ");
            day = option(DAYS_MAX, 0);
        } while (day == -1);

        do {
            printf("Mes: ");
            month = option(MONTHS_MAX, 0);
        } while (month == -1);

        do {
            printf("Ano: ");
            year = option(YEARS_MAX, 0);
        } while (year == -1);
        system("cls");
        date = newDate(day, month, year);

        if (!isValid(&date)) {
            printf("Data inserida invalida\n");
            getch();
            system("cls");
        }
    } while(!isValid(&date));

    return date;
}

/*Cria um novo jogo pedindo ao utilizador informações sobre as equipas que jogaram, a data do jogo e o resultado do encontro
    Devolve o jogo criado ou NULL caso o utilizador escolha a opção retroceder*/
Game createGame() {

    Game game;

    Team local;
    Team visitor;
    Date date;
    int lScore;
    int vScore;

    int choice;

    int check;
    do {
        check = 1;

        local = selectTeam("EQUIPA LOCAL");
        visitor = selectTeam("EQUIPA VISITANTE");

        if ( local != visitor) {

            date = selectDate();

            do {
                printf("Golos de: ");
                printTeamName(local);
                printf(" - ");
                lScore = option(SCORE_MAX, 1);
            } while (lScore == -1);

            do {
                printf("Golos de: ");
                printTeamName(visitor);
                printf(" - ");
                vScore = option(SCORE_MAX, 1);
                system("cls");
            } while (vScore == -1);

            game = newGame(local, visitor, date, lScore, vScore);
            if (game == NULL) error();

            printf("Pretende criar um jogo com a seguintes caracteristicas:\n\n");

            do {
                printGame(game);
                printf("\n\n");
                printf("Menu:\n\n");
                printf("1 - Sim\n");
                printf("2 - Nao\n");
                printf("3 - Retroceder\n");
                printf("\nOpcao: ");

                choice = option(3, 0);
                system("cls");
            } while (choice == -1);

            if (choice == 2) {
                freeGame(game);
                check = 0;
            } else if(choice == 3) {
                freeGame(game);
                game = NULL;
                break;
            } else {
                /*Altera a pontuação geral das equipas com base no resultado do jogo*/
                if(lScore > vScore) local->leagueScore += 3;
                else if(lScore < vScore) visitor->leagueScore += 3;
                else {
                    local->leagueScore++;
                    visitor->leagueScore++;
                }

                printf("O jogo foi criado com sucesso\n");
                getch();
            }
            system("cls");

        } else {
            printf("Equipa local e equipa visitante escolhidas sao iguais");
            getch();
            system("cls");
            check = 0;
        }
    } while(check == 0);

    return game;
}

/*Imprime a classificação geral das equipas do campeonato*/
void printGlobalRanking() {

    int i;
    for(i=0; i<size(STeams); i++) {

        printf("%do - ", i+1);
        printTeamName(STeams[i]);
        printf(" - %d pontos", STeams[i]->leagueScore);
        printf("\n");
    }

}

/*Remove um jogo da lista ligada "gameList" e das listas ligadas de jogos especificas
  para cada equipa interveniente e por fim altera a pontuação geral das equipas de forma
  a anular as pontuações que tinham sido obtidas com o jogo que irá ser apagado.*/
void removeGame(Game game) {

    if(game->lScore > game->vScore) game->local->leagueScore -= 3;
    else if(game->lScore < game->vScore) game->visitor->leagueScore -= 3;
    else {
        game->local->leagueScore--;
        game->visitor->leagueScore--;
    }
    deleteGame(gameList, game);
    deleteGame(game->local->teamGames, game);
    deleteGame(game->visitor->teamGames, game);
    freeGame(game);
}

int main(void) {

    initializeTeamList();
    initializeGameList();
    Game game;

    while (1) {

        int choice;
        do {
            printf("Menu:\n\n");
            printf("1 - Introduzir Jogo\n");
            printf("2 - Retirar Jogo\n");
            printf("3 - Mostrar classificacao geral\n");
            printf("4 - Listar equipas\n");
            printf("5 - Mostrar jogos de equipa\n");
            printf("6 - Mostrar todos os jogos do campeonato\n");
            printf("7 - Exit\n\n");
            printf("Opcao: ");
            choice = option(7, 0);

            system("cls");

        } while(choice == -1);

        switch (choice) {

        case 1:
            game = createGame();
            if(game != NULL) {
                if (insertGame(gameList, game) == 0) error();
                if (insertGame(game->local->teamGames, game) == 0) error();
                if (insertGame(game->visitor->teamGames, game) == 0) error();
            }
            saveGameList();
            sortByScore(STeams, size(STeams));
            saveTeamList();
            break;

        case 2:
            if(!gameListIsEmpty(gameList)) {

                game = selectGame();
                removeGame(game);
                saveGameList();
                sortByScore(STeams, size(STeams));
                saveTeamList();
            } else {
                printf("Nao existem jogos na base de dados\n");
                getch();
                system("cls");
            }
            break;

        case 3:
            printf("Classificacao geral:\n\n");
            printGlobalRanking();
            getch();
            system("cls");
            break;

        case 4:
            printf("Lista de Clubes:\n\n");
            printTeams(NTeams);
            getch();
            system("cls");
            break;

        case 5:
            printGameList(selectTeam(NULL)->teamGames);
            getch();
            system("cls");
            break;

        case 6:
            printf("Lista de Jogos do Campeonato:\n\n");
            printGameList(gameList);
            getch();
            system("cls");
            break;

        case 7:
            destroy();
            return 0;
        }
    }
    return 0;
}
