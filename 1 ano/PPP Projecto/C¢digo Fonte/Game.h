/*Define a estrutura do jogo e declara as funções que permitem a sua utilização*/

#ifndef GAME_H
#define GAME_H

#include "Date.h"
#include "Team.h"

/*Estrutura Game:

  local - Equipa local
  visitor - Equipa visitante
  date - Data do jogo
  lScore - Golos marcados pela equipa local
  vScore - Golos marcados pela equipa visitante
*/
typedef struct gm* Game;
typedef struct gm{
    Team local;
    Team visitor;
    Date date;
    int lScore;
    int vScore;
}gameStruct;

/*Cria um novo jogo

  Parametros:
  local - Equipa local
  visitor - Equipa visitante
  date - Data do jogo
  lScore - Golos marcados pela equipa local
  vScore - Golos marcados pela equipa visitante

  Devolve: Um novo jogo.
*/
Game newGame(Team local, Team visitor, Date date, int lScore, int vScore);

/*Liberta a memória aloca para o jogo recebido como parametro e respectiva data*/
void freeGame(Game game);

/*  Compara 2 jogos usando a data do jogo como forma de comparação.
    Devolve:
    -1 se data de game1 < data de game2
    0  se data de game1 = data de game 2
    1  se data de game1 > data de game 2
*/
int compareGameByDate(Game game1, Game game2);

/*Imprime o jogo recebido como parametro na consola*/
void printGame(Game game);

#endif
