/*Define a estrutura da equipa e declara as fun��es que permitem a sua utiliza��o*/

#ifndef TEAM_H
#define TEAM_H

/*"Forward Declaration" de forma a que o compilador saiba que o tipo "TeamGames" � um ponteiro para
  game_lnode ou seja � um ponteiro para uma lista de jogos. O uso de "Forward Declaration" aqui justifica-se
  para evitar uma dependencia ciclica quando se faz o include de LinkedGame.h"*/
typedef struct game_lnode *TeamGames;

/*Estrutura Team:

  name - Nome da equipa
  town - localidade da equipa
  leagueScore - Pontua��o da equipa no campeonato
  teamGames - Ponteiro para uma lista de jogos onde est�o guardados os jogos realizados pela equipa
*/
typedef struct tm *Team;
typedef struct tm{
    char* name;
    char* town;
    int leagueScore;
    TeamGames teamGames;
}teamStruct;

/*Cria uma nova equipa

  Parametros:
  name - Nome da equipa
  town - localidade da equipa
  leagueScore - Pontua��o da equipa no campeonato

  Devolve: Uma nova equipa.
*/
Team newTeam(char* name, char* town, int leagueScore);

/*Liberta a mem�ria alocada para a equipa recebida como parametro e respectivo nome, localidade e lista ligada de jogos*/
void freeTeam(Team team);

/*  Compara 2 jogos usando o nome da equipa como forma de compara��o.
    Devolve:
    -1 se nome de team1 < nome de team2
    0  se nome de team1 = nome de team2
    1  se nome de team1 > nome de team2
*/
int compareTeamByName(Team team1, Team team2) ;

/*Imprime o nome da equipa recebida como parametro na consola*/
void printTeamName(Team team);

/*Imprime a equipa recebida como parametro na consola*/
void printTeam(Team team);

#endif
