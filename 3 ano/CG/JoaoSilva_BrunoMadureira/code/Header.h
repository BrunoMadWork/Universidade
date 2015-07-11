//
//  Header.h
//  ficha3cg
//
//  Created by Bruno Madureira on 25/04/14.
//  Copyright (c) 2014 Bruno Madureira. All rights reserved.
//

#ifndef ficha3cg_Header_h
#define ficha3cg_Header_h

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <algorithm>
#include <math.h>
#include "materiais.h"
#include "RgbImage.h"
#include "simplexnoise.h"
#include <time.h>
#ifdef __APPLE__
#include <OpenGL/OpenGL.h>
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif



#define DRAW_DISTANCE 100
#define AZUL     0.0, 0.0, 1.0, 1.0
#define VERMELHO 1.0, 0.0, 0.0, 1.0
#define AMARELO  1.0, 1.0, 0.0, 1.0
#define VERDE    0.0, 1.0, 0.0, 1.0
#define LARANJA  1.0, 0.5, 0.1, 1.0
#define WHITE    1.0, 1.0, 1.0, 1.0
#define BLACK    0.0, 0.0, 0.0, 1.0
#define GRAY     0.9, 0.92, 0.29, 1.0
//#define ROBOCOLOR 0.3, 0.9 ,0.7 ,1.0
//#define ROBOCOLOR 0.419, 0.137 ,0.556 ,1.0
#define ROBOCOLOR 0.9, 0.9 ,0.9 ,1.0
#define PI		 3.14159
#define sizeBird 3
#define NROBOS 6
#endif


class Enemy{
public:
    int numero;
    float x;
    float y;
    float z;
    float raio;
    float angXZ;
    Enemy(){}
    Enemy(int nu){numero=nu;}
    int getId(){
        return numero;
    }
    float getX(){
        return x;
    }
    float getY(){
        return y;
    }
    float getZ(){
        return z;
    }
  
    void update( float xa, float ya, float za, float angRotacao){
        y=ya;
        double root;
        root=(double) sqrt(xa*xa+za*za);
        raio= (float) root;
        double temp=atan((double) za/xa);
        angXZ=(float) temp+angRotacao;
        x=raio*cos(angXZ);
        z=raio*sin(angXZ);
       // printf("raio:%f ang:%f x:%f z:%f\n", raio, temp, x, z);
    
    }
};


//================================================================================
//===========================================================Variaveis e constantes

//------------------------------------------------------------ Sistema Coordenadas
GLfloat   xC = 10.0, yC = 10.0, zC = 14.0;
GLint     wScreen = 1440, hScreen = 900;
GLfloat   sizerobo1 = 1.5;
GLfloat   sizerobo2 = 0;
GLfloat   sizerobo3 = 0;
GLfloat   sizeBraco = 1;
GLfloat   polyNumWall = 16;

GLfloat   bule = 0.5;
GLfloat   quad = 3.0;
GLfloat	  tam = sqrt(50.0);
GLfloat   obsP[] =  { -8.0, 9.250000, -3.0 };
GLfloat   robotP[] = { 0, 0, 0 };
GLfloat   building[] = { 0, 0, 4 };
GLfloat   size_building = 5;
GLfloat   angXZ = 0.0;
GLboolean enablePersp = true;
GLint	  ViewEstado = 0;
GLfloat movimento[3] = {0};
GLfloat  sizeFaixa = 0.3;
GLfloat lado=2;
GLuint  texture[10];
GLuint  tex;
int LanternaMode=1;
GLfloat walkingEnemyConstant = 10000;
GLboolean StupidColor = false;
float hour, minute, second;
struct tm *current_time;

GLfloat luzGlobalCor[4]= {static_cast<GLfloat>(hour/24.0),static_cast<GLfloat>(minute/60.0),static_cast<GLfloat>(second/60.0),1};
//GLfloat luzGlobalCor[4]= {1,1,1,1};
GLfloat LuzGlobalCorFixa[4] = {0,0,0,1};
GLfloat polyNormal[] = {1.0, 0.0, 0.0};
GLfloat Matriz[4][4];
GLfloat inversor=1;
GLfloat posicaoD[3];
GLfloat posicaoE[3];
GLfloat posicaoI[3];
//GLfloat PosicaoRobo[6][3];
GLfloat PosicaoRoboReset[6][3] = {{15,0,17},{13,0,-2},{5,0,-7},{-15,0,7},{20,0,-5},{15,0,-1}};
Enemy enemies[6];
GLfloat RobotMovement[6][3] = {{15,0,17},{13,0,-2},{5,0,-7},{-15,0,7},{20,0,-5},{15,0,-1}};
int vidaUser = 100;
int robotState[6] = {100,100,100,100,100,100};
GLboolean foco = true;
GLfloat anguloFoco = 30;
GLfloat raioFoco = 8;
GLint   msecDelay = 2;		//20		//.. definicao do timer (actualizacao)
GLfloat focoAng = 20;

GLfloat focoCor[4] = {1.0, 0.0, 0.0, 0.0};
GLfloat focoCLQ[3] = {1.0, 0.05, 0.0};
//GLfloat  focoPfin[] ={static_cast<GLfloat>(robotP[0]+raioFoco*cos(0)), robotP[1]+sizerobo1/2, static_cast<GLfloat>(robotP[2]+raioFoco*sin(0) )};
GLfloat UpThingPosition[10][3];
GLfloat AngRotateSelf = tan(3.0/8.0)+PI;

GLfloat focoDirection[4] = {raioFoco,sizerobo1,0,0};
RgbImage imag;

GLboolean bullet = false;
GLfloat buletSize = 0.2;
float moveBala = 0.0;

