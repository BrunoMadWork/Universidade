/*Ficheiro que define as fun��es que manipulam equipas (Team) */

#include "Team.h"
#include "LinkedGame.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*Cria uma nova equipa. Reserva espa�o na heap para guardar a estrutura usando a fun��o malloc, guarda as
variaveis recebidas como parametros na estrutura e por fim devolve a estrutura que foi criada*/
Team newTeam(char* name, char* town, int leagueScore) {

    Team team = (Team) malloc (sizeof(teamStruct));

    if (team != NULL) {
        team->name = name;
        team->town = town;
        team->leagueScore = leagueScore;
        team->teamGames = createGameList();
    }

    return team;
}

/*Liberta a mem�ria alocada para a equipa e respectivo nome, localidade e lista ligada de jogos.
Parte-se do principio que o nome e a localidade s�o unicos para cada equipa por isso faz sentido
que quando uma equipa � apagada o seu nome e localidade tamb�m o sejam*/
void freeTeam(Team team) {
    destroyGameList(team->teamGames);
    free(team->name);
    free(team->town);
    free(team);
}

/*Compara 2 equipas usando nome da equipa como forma de compara��o. Chama a fun��o strcmp
  usando como parametros para essa fun��o o nome das equipas que se quer compararar*/
int compareTeamByName(Team team1, Team team2) {
    return strcmp(team1->name, team2->name);
}

/*Imprime o nome da equipa recebida como parametro na consola*/
void printTeamName(Team team) {

    printf("%s", team->name);
}

/*Imprime a equipa recebida como parametro na consola*/
void printTeam(Team team) {

    printf("%s - %s", team->name, team->town);
}
