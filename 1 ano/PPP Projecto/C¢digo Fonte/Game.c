/*Ficheiro que define as funções que manipulam jogos (Game) */

#include "Game.h"
#include "Team.h"
#include "Date.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*Cria um novo jogo. Reserva espaço na heap para guardar a estrutura usando a função malloc, guarda as
variaveis recebidas como parametros na estrutura e por fim devolve a estrutura que foi criada*/
Game newGame(Team local, Team visitor, Date date, int lScore, int vScore) {

    Game game;
    game = (Game) malloc (sizeof(gameStruct));

    if (game != NULL) {
        game->local = local;
        game->visitor = visitor;
        game->date = date;
        game->lScore = lScore;
        game->vScore = vScore;
    }
    return game;
}

/*Liberta a memória alocada para o jogo*/
void freeGame(Game game) {
    free(game);
}

/*  Compara 2 jogos usando a data do jogo como forma de comparação. Chama a função compareDate (definida no ficheiro Date.c)
    usando como parametros para essa função a data dos jogos que se quer comparar*/
int compareGameByDate(Game game1, Game game2) {
    return compareDate(&(game1->date), &(game2->date));
}

/*Imprime o jogo recebido como parametro na consola usando o seguinte formato:
    <equipa_local>/<equipa_visitante>/<data>/<resultado_do_jogo>*/
void printGame(Game game) {

    printTeamName(game->local);
    printf(" / ");
    printTeamName(game->visitor);
    printf(" / ");
    printDate(&(game->date));
    printf(" / ");
    printf("%d-%d\n", game->lScore, game->vScore);
}
