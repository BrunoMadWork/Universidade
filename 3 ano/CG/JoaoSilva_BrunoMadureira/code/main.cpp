#include "Header.h"


//--------------------------------- Definir cores


//================================================================================
//=========================================================================== INIT
void Timer(int value) {
	glutPostRedisplay();
	glutTimerFunc(msecDelay,Timer, 1);
}
void randomFocoType() {LanternaMode=rand()%5;}

void updateLuzGlobal(){
   /* luzGlobalCor[0]= {static_cast<GLfloat>(hour/24.0)};
    luzGlobalCor[1]={static_cast<GLfloat>(minute/60.0)};
    luzGlobalCor[2]={static_cast<GLfloat>(second/60.0)};
    luzGlobalCor[3]=1;*/
	#ifdef __APPLE__
		luzGlobalCor[0]= {static_cast<GLfloat>(fabs(0.25*sin(2*PI*hour/24.0+2)) +               //2PI/T
											   fabs(0.25*sin(2*PI*minute/60.0+4)) +
											   fabs(0.5*sin(2*PI*second/60.0+6)))};
		luzGlobalCor[1]= {static_cast<GLfloat>(fabs(0.25*sin(2*PI*hour/24.0+6)) +
											   fabs(0.25*sin(2*PI*minute/60.0+4)) +
											   fabs(0.5*sin(2*PI*second/60.0+2)))};
		luzGlobalCor[2]= {static_cast<GLfloat>(fabs(0.25*sin(2*PI*hour/24.0+4)) +
											   fabs(0.25*sin(2*PI*minute/60.0+6)) +
											   fabs(0.5*sin(2*PI*second/60.0+2)))};
		luzGlobalCor[3]=1;
	#else
		luzGlobalCor[0]= (fabs(0.25*sin(2*PI*hour/24.0+2)) +               //2PI/T
						  fabs(0.25*sin(2*PI*minute/60.0+4)) +
                          fabs(0.5*sin(2*PI*second/60.0+6)));
		luzGlobalCor[1]= (fabs(0.25*sin(2*PI*hour/24.0+6)) +
						  fabs(0.25*sin(2*PI*minute/60.0+4)) +
						  fabs(0.5*sin(2*PI*second/60.0+2)));
		luzGlobalCor[2]= (fabs(0.25*sin(2*PI*hour/24.0+4)) +
						  fabs(0.25*sin(2*PI*minute/60.0+6)) +
						  fabs(0.5*sin(2*PI*second/60.0+2)));
		luzGlobalCor[3]=1;
	#endif

    /*luzGlobalCor[0]= {static_cast<GLfloat>(hour/48.0+minute/120.0+second/180.0)};
    luzGlobalCor[1]={static_cast<GLfloat>(hour/72.0+minute/120.0+second/240.0)};
    luzGlobalCor[2]={static_cast<GLfloat>(hour/72.0+minute/240.0+second/120.0)};*/
    /*luzGlobalCor[0]=1;
    luzGlobalCor[1]=1;
    luzGlobalCor[2]=1;
    */
}
void updateEnemyPosition(){
    /*float temp;
    srand(time(NULL));
    for(int i=0;i<NROBOS;i++){
        temp=rand()%200+1-100;
        RobotMovement[i][0]+=temp/walkingEnemyConstant;
        temp=rand()%200+1-100;
        RobotMovement[i][2]+=temp/walkingEnemyConstant;
    
        }
        //printf("Posição enimigo:x=%f\nz=%f\n",PosicaoRobo[0][0],PosicaoRobo[0][1]);
*/
    
   /* float temp;
        int x=1;
        int y=1;
     for(int i=0;i<NROBOS;i++){
     x=octave_noise_2d( 10, 5, 4, x,y);
     temp=x*200+1-100;
     RobotMovement[i][0]+=temp/walkingEnemyConstant;
     y=octave_noise_2d( 10, 5, 4, x,y);
     temp=y*200+1-100;

     RobotMovement[i][2]+=temp/walkingEnemyConstant;
     */
	float temp;
    float x=0;
    float y=0;
	float r1,r2;
    srand(time(NULL));
    
	for(int i=0;i<NROBOS;i++){
		r1=(rand()%1000)/1000.0;
			 // printf("%f\n",r1);

		r2=(rand()%1000)/1000.0;
    
		 x=scaled_raw_noise_2d(0,1,r1,r2);
		// printf("%f\n",x);
		 temp=x*200+1-100;
		if(RobotMovement[i][0]>DRAW_DISTANCE){
			if(temp>0){
				temp=-temp;
			}
		}
		else if(RobotMovement[i][0]<-DRAW_DISTANCE){
			if(temp<0){
				temp=-temp;
			}
		}
		RobotMovement[i][0]+=temp/walkingEnemyConstant;
		r1=(rand()%1000)/1000.0;
		r2=(rand()%1000)/1000.0;
		y=scaled_raw_noise_2d(0,1,r1,r2);
		// printf("%f\n",y);
		temp=y*200+1-100;
		if(RobotMovement[i][2]>DRAW_DISTANCE){
			if(temp>0){
				temp=-temp;
			}
		}
		else if(RobotMovement[i][2]<-DRAW_DISTANCE){
			if(temp<0){
				temp=-temp;
			}
		}
		RobotMovement[i][2]+=temp/walkingEnemyConstant;
     }
     //printf("Posição enimigo:x=%f\nz=%f\n",PosicaoRobo[0][0],PosicaoRobo[0][1]);
}

void initLights(void){
	//…………………………………………………………………………………………………………………………………………… Ambiente
    if(StupidColor){
		glLightModelfv(GL_LIGHT_MODEL_AMBIENT, luzGlobalCor);
    }
    else{
        glLightModelfv(GL_LIGHT_MODEL_AMBIENT, LuzGlobalCorFixa);
    }
    
	//…………………………………………………………………………………………………………………………………………… Restantes luzes
	glLightfv(GL_LIGHT0, GL_POSITION, robotP );
	glLightfv(GL_LIGHT0, GL_SPOT_DIRECTION, focoDirection );
	glLightf(GL_LIGHT0, GL_SPOT_CUTOFF, 20 );
	glLightfv(GL_LIGHT0, GL_AMBIENT, focoCor);
	glLightfv(GL_LIGHT0, GL_DIFFUSE, focoCor);
	glLightfv(GL_LIGHT0, GL_SPECULAR, focoCor);
	glLightf (GL_LIGHT0, GL_CONSTANT_ATTENUATION, focoCLQ[0]);
	glLightf (GL_LIGHT0, GL_LINEAR_ATTENUATION, focoCLQ[1]) ;
	glLightf (GL_LIGHT0, GL_QUADRATIC_ATTENUATION, focoCLQ[2]) ;
}

void criaDefineTexturas() {
	//----------------------------------------- Mesa
	glGenTextures(1, &texture[0]);
	glBindTexture(GL_TEXTURE_2D, texture[0]);
    glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
	imag.LoadBmpFile("FloorsRegular0205_2_S.bmp");
    gluBuild2DMipmaps(GL_TEXTURE_2D, 3,
                      imag.GetNumCols(),
                      imag.GetNumRows(), GL_RGB, GL_UNSIGNED_BYTE,
                      imag.ImageData());
    
    glGenTextures(1, &texture[1]);
	glBindTexture(GL_TEXTURE_2D, texture[1]);
	glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
	imag.LoadBmpFile("azulejo.bmp");
    gluBuild2DMipmaps(GL_TEXTURE_2D, 3,
                      imag.GetNumCols(),
                      imag.GetNumRows(), GL_RGB, GL_UNSIGNED_BYTE,
                      imag.ImageData());
    
    glGenTextures(1, &texture[2]);
	glBindTexture(GL_TEXTURE_2D, texture[2]);
	imag.LoadBmpFile("caracol.bmp");
	glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
	glTexImage2D(GL_TEXTURE_2D, 0, 3,
                 imag.GetNumCols(),
                 imag.GetNumRows(), 0, GL_RGB, GL_UNSIGNED_BYTE,
                 imag.ImageData());
	printf("load feito texturas\n");

}

void initGenerationThingPosition(){
    for (int i = 0; i<10; i++){
        UpThingPosition[i][0]=i*2+i*i;
        UpThingPosition[i][2]=i*2-i*i;
        UpThingPosition[i][1]=25;
    }
}

void generateUpThingPosition(){
    srand(time(NULL));
    for (int i = 0; i<10; i++) {
        //  float r1=(rand()%200)/2000.0+UpThingPosition[i][1]-0.1;
        float r2=(rand()%1000)/1000.0+UpThingPosition[i][2]-1;
        float r0=(rand()%1000)/1000.0+UpThingPosition[i][0]-1;
        /*  // printf("%f\n",x);
        if(UpThingPosition[i][1]+r1>35){
            if(r1>0){
                r1=-r1;
            }
        }
        else if(UpThingPosition[i][1]+r1<12.5){
            if(r1<0){
                r1=-r1;
            }
        }
        */
        if(r0>DRAW_DISTANCE){
            if(r0>0){
                r0=-r0;
            }
        }
        else if(r0<-DRAW_DISTANCE){
            if(r0<0){
                r0=-r0;
            }
        }
        
        if(r2>DRAW_DISTANCE){
            if(r2>0){
                r2=-r2;
            }
        }
        else if(r2<-DRAW_DISTANCE){
            if(r2<0){
                r2=-r2;
            }
        }
        //UpThingPosition[i][1]+=r1;
        UpThingPosition[i][0]=r0;
        UpThingPosition[i][2]=r2;
    }
}



void drawFace(float size){
    int delta=1;
    glPushMatrix();
		glColor4f(WHITE);
	   // glTranslatef(-1.0,-1.0,0);  // meio do poligono
		//MALHA POLIGONOS POLIGONO
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D,texture[0]);
		glNormal3f(0,0,1);
		//glTranslatef(-1.0,-1.0,0);  // meio do poligono

		//glNormal3f(0,0,1/2);
		glBegin(GL_QUADS);
			for(float i = 0; i < size; i++){
				for(float j = 0; j < size; j++){
					//1 face
					glTexCoord2f(i	  /size, j	  / size);	glVertex3f(i	/size * delta, j	/size*delta, 0);
					glTexCoord2f((i+1)/size, j	  / size);	glVertex3f((i+1)/size * delta, j	/size*delta, 0);
					glTexCoord2f((i+1)/size, (j+1)/ size);	glVertex3f((i+1)/size * delta, (j+1)/size*delta, 0);
					glTexCoord2f(i	  /size, (j+1)/ size);	glVertex3f(i	/size * delta, (j+1)/size*delta, 0); 
				}
			}
		glEnd();
	glPopMatrix();

}
void drawThePoligon(int size){
    //Face da frente;
     glPushMatrix();
        glTranslatef( 0, 0, -1/2);
        drawFace(size);
     glPopMatrix();
   
    glPushMatrix();
        glRotatef(90, 0, 1, 0);
        glTranslatef(0 , 0, 1/2);
        drawFace(size);
    glPopMatrix();
    //Face da direita;
 
    glPushMatrix();
       // glRotatef(0, 0, 1, 0);
         glTranslatef(0, 0, -1);
         drawFace(size);
    glPopMatrix();
    
    //face da esquerda
    glPushMatrix();
        glRotatef(90, 0, 1, 0);
         glTranslatef(0, 0, 1);
         drawFace(size);
    glPopMatrix();
    
    //face de tras
    glPushMatrix();
		glTranslatef(-1/2, 1+1/2, -1);
		glRotatef(90, 1, 0, 0);
		drawFace(size);
    glPopMatrix();
    /*
    glPushMatrix();
    glTranslatef(-1/2, -1+1/2, -1);
    glRotatef(90, 1, 0, 0);
    drawFace(size);
    
    glPopMatrix();
    */
}
void drawTheI(){
    glPushMatrix();
        glColor4f(VERMELHO);
        glMaterialfv(GL_FRONT,GL_AMBIENT, goldAmb);
        glMaterialfv(GL_FRONT,GL_DIFFUSE, goldDif);
        glMaterialfv(GL_FRONT,GL_SPECULAR, goldSpec);
        glMateriali(GL_FRONT,GL_SHININESS, goldCoef);
        glScalef(1, 5, 1);
        glutSolidCube(lado);
    glPopMatrix();
}

void drawTheD(){
    glPushMatrix();
		glScalef(1,1,0.25);
		drawTheI();
		glPushMatrix();
			glTranslatef(0, 0, 4*lado+lado/2);
			drawTheI();
		glPopMatrix();
    glPopMatrix();
   
	//baixo
    glPushMatrix();
        glScalef(1,0.25,1);
        glTranslatef(0, 9.5*lado, lado/2);
        glutSolidCube(lado);
    glPopMatrix();
    
    //cima
    glPushMatrix();
        glScalef(1,0.25,1);
        glTranslatef(0, -9.5*lado, lado/2);
        glutSolidCube(lado);
    glPopMatrix();
}

void drawTheE(){
    //pau
	glPushMatrix();
		glColor4f(VERMELHO);
		glMaterialfv(GL_FRONT,GL_AMBIENT, goldAmb);
		glMaterialfv(GL_FRONT,GL_DIFFUSE, goldDif);
		glMaterialfv(GL_FRONT,GL_SPECULAR, goldSpec);
		glMateriali(GL_FRONT,GL_SHININESS, goldCoef);
    
		glPushMatrix();
			glScalef(1, 5, 1);
			glutSolidCube(lado);
		glPopMatrix();
    
		//1
		glPushMatrix();
			glTranslatef(0, 0, lado);
			glutSolidCube(lado);
		glPopMatrix();
    
		//2
		glPushMatrix();
			glTranslatef(0, 2*lado, lado);
			glutSolidCube(lado);
		glPopMatrix();
    
		//3
		glPushMatrix();
			glTranslatef(0, -2*lado, lado);
			glutSolidCube(lado);
		glPopMatrix();
	glPopMatrix();
}



void draw_dei(){
    glPushMatrix();
		glTranslatef( 20, (10/2)*lado,  0);
        glPushMatrix();
			glTranslatef( 0, (5/2)*lado,  0);
			drawTheD();
        glPopMatrix();
        glPushMatrix();
			glTranslatef( 0, (5/2)*lado,  5);
			drawTheE();
        glPopMatrix();
        glPushMatrix();
			glTranslatef( 0, (5/2)*lado,  10);
			drawTheI();
        glPopMatrix();
    glPopMatrix();
}


void draw_wheel(){
    glPushMatrix();
        glColor4f(BLACK);
        glutSolidSphere(0.5, 16, 16);
    glPopMatrix();
}
void draw_faixa(){
    glPushMatrix();
        glColor4f(BLACK);
        glScalef(1, 1, 5);
        glTranslatef(0, sizerobo1/2+sizeFaixa/2, 0);
        glutSolidCube(sizeFaixa);
    glPopMatrix();
}
void draw_viseira(){
    glPushMatrix();
    glColor4f(VERMELHO);
    glScalef(0.5, 0.5, 4);
    glTranslatef(sizerobo1, 0.5*sizerobo1,0);
    glutSolidCube(sizeFaixa);
    glPopMatrix();
}


void drawRobot(){
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~robot
	glPushMatrix();
		glColor4f(ROBOCOLOR);
		glMaterialfv(GL_FRONT,GL_AMBIENT,  esmeraldAmb);
		glMaterialfv(GL_FRONT,GL_DIFFUSE,  esmeraldDif);
		glMaterialfv(GL_FRONT,GL_SPECULAR,  esmeraldSpec);
		glMateriali(GL_FRONT,GL_SHININESS,  esmeraldCoef);
    
		glScalef(0.5, 1, 1);
		// desenhar rodas
		glPushMatrix();
			glTranslatef(robotP[0], robotP[1]+(sizerobo1/2)  , robotP[2]+sizerobo1/2);
			draw_wheel();
		glPopMatrix();
		glPushMatrix();
			glTranslatef(robotP[0], robotP[1]+(sizerobo1/2)  , robotP[2]-sizerobo1/2);
			draw_wheel();
		glPopMatrix();
		glTranslatef(robotP[0], robotP[1] + (sizerobo1) , robotP[2]);

		/* glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D,texture[1]);*/
		//parte de baixo sem rodas
		//glColor4f(AZUL);
		glColor4f(ROBOCOLOR);
		glutSolidCube(sizerobo1);
		draw_faixa();
		//glColor4f(VERDE);
		//glutWireCube(sizerobo1);
		glPushMatrix();
			glTranslatef(robotP[0], robotP[1] + (sizerobo1)+sizeFaixa , robotP[2]);
			// glColor4f(WHITE);
			glColor4f(ROBOCOLOR);
			glutSolidCube(sizerobo1);
			draw_faixa();
			//glColor4f(AZUL);
			//glutWireCube(sizerobo1);
			//canhao
    
			glPushMatrix();
			glTranslatef((3.5*sizeBraco)/2-sizerobo1/2, 0 , sizerobo1/2+sizeBraco/2);
				//glColor4f(AZUL);
				glScalef(3.5, 0.75, 1);
				glColor4f(ROBOCOLOR);
				glutSolidCube(sizeBraco);
			glPopMatrix();
    
			//braco
			glPushMatrix();
				glTranslatef(0, -sizeBraco , -sizeBraco);
				// glColor4f(AZUL);
				glScalef(1, 2, 1);
				glColor4f(ROBOCOLOR);
				glutSolidCube(sizeBraco);
			glPopMatrix();
			draw_wheel();
			glTranslatef(robotP[0], robotP[1] + (sizerobo1)+sizeFaixa , robotP[2]);
			// glColor4f(VERMELHO);
			glColor4f(ROBOCOLOR);
			glutSolidCube(sizerobo1);
			// glColor4f(AZUL);
			draw_viseira();
			/*glPopMatrix(); METER SE DER BRONCA*/
		glPopMatrix();
	glPopMatrix();
   // glDisable(GL_TEXTURE_2D);
}
/*void draw_circle(){
    float r=0.1;
    
    float delta_theta = 0.01;
    glPushMatrix();
    glColor4f(VERMELHO);
    glBegin( GL_POLYGON ); // OR GL_LINE_LOOP
    
    for( float angle = 0; angle < 2*PI; angle += delta_theta )
        glVertex3f(0,  r*cos(angle),  r*sin(angle ));
    
    glEnd();
    glPopMatrix();
}
*/

void drawMouth(){
    glPushMatrix();
		glRotatef(180, 1, 0, 0);
		//glScalef(1/2, 1/2, 1/2);
		glBegin(GL_TRIANGLES);
			glColor4f(AZUL);
			glVertex3f(0, 0.1f, 0);    // lower left vertex
			glVertex3f( 0, -0.1f, -0.1f);    // lower right vertex
			glVertex3f( 0, -0.21, 0.1f);    // lower right vertex
		glEnd();
    glPopMatrix();
}


void drawEyes(){
    glPushMatrix();
        glColor4f(WHITE);
        glutSolidSphere(buletSize/2, 8, 8);
    glPopMatrix();
}

void drawWings(){
    glPushMatrix();
    glColor4f(VERMELHO);
        glScaled(1, 0.1, 0.5);
        glutSolidCube(sizeBird);
    glPopMatrix();
}

void drawBird(){
    glPushMatrix();
		glMaterialfv(GL_FRONT,GL_AMBIENT, copperAmb);
		glMaterialfv(GL_FRONT,GL_DIFFUSE, copperDif);
		glMaterialfv(GL_FRONT,GL_SPECULAR, copperSpec);
		glMateriali(GL_FRONT,GL_SHININESS, copperCoef);

		glColor4f(WHITE);
		glutSolidCube(sizeBird);
    //esquerda
        glPushMatrix();
            glTranslatef(0, 0, sizeBird*0.75);
            drawWings();
        glPopMatrix();
    //direita
        glPushMatrix();
            glTranslatef(0, 0, -sizeBird*0.75);
            drawWings();
        glPopMatrix();
       /* glPushMatrix();
            glTranslatef(0, -sizeBird/2, 0);
            drawEyes();
        glPopMatrix();*/
    glPopMatrix();
}
void drawEnemyRobot(int i){
    
    glPushMatrix();
		glTranslatef(0, 2.5*sizerobo1, 0);
		glMaterialfv(GL_FRONT,GL_AMBIENT, copperAmb);
		glMaterialfv(GL_FRONT,GL_DIFFUSE, copperDif);
		glMaterialfv(GL_FRONT,GL_SPECULAR, copperSpec);
		glMateriali(GL_FRONT,GL_SHININESS, copperCoef);

		glScalef(1, 5, 1);
        glColor4f(VERDE);
        glutSolidCube(sizerobo1);
        glPushMatrix();
			glTranslatef(-sizerobo1/2, sizerobo1/4 , sizerobo1/4 );
			drawEyes();
			glPopMatrix();
			glPushMatrix();
			glTranslatef(-sizerobo1/2, sizerobo1/4 , -sizerobo1/4 );
			drawEyes();
        glPopMatrix();
       /* glPushMatrix();
        glTranslatef(-sizerobo1/2, 0, -sizerobo1/4 );
        drawMouth();
        glPopMatrix();*/
    glPopMatrix();
}
void drawFloor(){
	glPushMatrix();
		glBegin(GL_QUADS);
			glVertex3f(-100.0f, 0, -100.0f); // The bottom left corner
			glVertex3f(-100.0f, 0, 100.0f); // The top left corner
			glVertex3f(100.0f, 0, 100.0f); // The top right corner
			glVertex3f(100.0f, 0, -100.0f); // The bottom right corner
		glEnd();
	glPopMatrix(); 
}

void drawWall(){
    glPushMatrix();
		glTranslatef(0, 10/2, 0);
		glScalef(2*DRAW_DISTANCE, 10,2 );
		glColor4f(VERDE);
		//glutSolidCube(1);
		drawThePoligon(polyNumWall);
    glPopMatrix();  
}

void drawAirStuff(){
    for (int i = 0; i<10; i++) {
        glPushMatrix();
			glTranslatef(UpThingPosition[i][0], UpThingPosition[i][1], UpThingPosition[i][2]);
			drawBird();
        glPopMatrix();
    }
}
void drawWalls(){
    //1
    glPushMatrix();
		glTranslatef(-DRAW_DISTANCE, -5, DRAW_DISTANCE);
		glRotatef(90, 0, 1, 0);
		drawWall();
    glPopMatrix();
    
    //2
    glPushMatrix();
		glTranslatef(DRAW_DISTANCE, -5,DRAW_DISTANCE);
		glRotatef(90, 0, 1, 0);
		drawWall();
    glPopMatrix();
   
	//3
    glPushMatrix();
		glTranslatef(-DRAW_DISTANCE, -5, DRAW_DISTANCE);
		drawWall();
    glPopMatrix();

    //4
    glPushMatrix();
		glTranslatef(-DRAW_DISTANCE, -5, -DRAW_DISTANCE);
		drawWall();
    glPopMatrix();
}



void draw_bullet(){
    glPushMatrix();
        glTranslatef(sizerobo1, sizerobo1*2.5, sizerobo1);
        glColor4f(AMARELO);
        glMaterialfv(GL_FRONT,GL_AMBIENT, obsidianAmb);
        glMaterialfv(GL_FRONT,GL_DIFFUSE, obsidianDif);
        glMaterialfv(GL_FRONT,GL_SPECULAR, obsidianSpec);
        glMateriali(GL_FRONT,GL_SHININESS, obsidianCoef);
        glutSolidSphere(buletSize, 16, 16);
       // printf("BOLA DESENHADA\n");
	glPopMatrix();
    
}
void shoot_bullet(){/*
    if(!bullet){
        bullet=true;
        moveBala=0;
        draw_bullet();
    }*/
    bullet=true;
    moveBala=0;
    draw_bullet();
    //printf("SB\n\n\n");
}
void move_bullet(){
    if(bullet){
        glPushMatrix();
			glTranslatef(moveBala,0 , 0);
			draw_bullet();
			moveBala += 0.2;
			if(moveBala >= 100){
				bullet = false;
			}
        glPopMatrix();
    }
}


void drawBuilding(){
    glPushMatrix();
		glTranslatef(building[0]+movimento[0],building[0]+ movimento[1],building[0]+ movimento[2]);
		glColor4f(VERDE);
		glScalef(0.5,1,0.5);
		glutSolidCube(size_building);
		glColor4f(VERDE);
		glutWireCube(size_building);
	glPopMatrix(); 
}

void reset(){
    vidaUser=120;
    for(int i=0;i<6;i++){
        robotState[i]=100;
    }
    movimento[0]=0;
    movimento[1]=0;
    movimento[2]=0;
    angXZ=0.0;
    AngRotateSelf=tan(3.0/8.0)+PI;
    bullet=false;
    //printf("Copiou\n");
    /*for(int i=0;i<6;i++){
        memcpy(PosicaoRobo[i], PosicaoRoboReset[i], 3*sizeof(GLfloat));
    }*/

    for(int i=0;i<6;i++){
        for(int j=0;j<3;j++){
            RobotMovement[i][j]=PosicaoRoboReset[i][j]; // é o robot movement
         }
     }
    //randomFocoType();
}

void reset_wall(){
    movimento[0]=0;
    movimento[1]=0;
    movimento[2]=0;
    angXZ=0.0;
    bullet=false;
    AngRotateSelf=tan(3.0/8.0)+PI;
    //randomFocoType();
}

void drawEnemies(){
    
    for(int i=0;i<NROBOS;i++){
        glPushMatrix();
			glTranslatef(RobotMovement[i][0],0,RobotMovement[i][2]);
			if(robotState[i]!=0){
				if(i==0||i==2||i==4){
					glRotatef(180, 0, 1, 0);
				}
				drawEnemyRobot(i);
			}
			///DEBUG!!!!
        glPopMatrix();
        
    }
    
}

float xBala() {return moveBala;}

float zBala() {return 0;}

//float zBalaBruno(float raio, float ang) {return raio*tan(ang+PI/2);}

void drawScene(){
    
    glPushMatrix();
		drawRobot();
		move_bullet();
    glPopMatrix();

    glPushMatrix();
		glRotatef((angXZ*360.0)/(2.0*PI), 0, 1, 0);
		glTranslatef(movimento[0], 0, movimento[2]);
		//debug
		glPushMatrix();
			glTranslatef(2, 2,2);
			//glScalef(10, 10, 10);
			drawThePoligon(16);
		glPopMatrix();
		// drawBuilding();
		drawAirStuff();
		draw_dei();
		drawWalls();
		drawEnemies();
    
		glEnable(GL_STENCIL_TEST);
		glColorMask(0,0,0,0);
		glDisable(GL_DEPTH_TEST);
		glStencilFunc(GL_ALWAYS, 1, 1);
		glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);
		drawFloor();
    
		glColorMask(1, 1, 1, 1);
		glEnable(GL_DEPTH_TEST);
		glStencilFunc(GL_EQUAL, 1, 1);
		glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
    
		glPushMatrix();
			glScaled(1, -1, 1);
			drawAirStuff();
			draw_dei();
			drawWalls();
			//debug
			glPushMatrix();
				glTranslatef(2, 2,2);
				//glScalef(10, 10, 10);
				drawThePoligon(16);
			glPopMatrix();
    
			drawEnemies();

			glPushMatrix();
				glTranslatef(-movimento[0], 0, -movimento[2]);
				glRotatef((-angXZ*360)/(2.0*PI), 0, 1, 0);
				drawRobot();
				//move_bullet();
			glPopMatrix();
		glPopMatrix();
		glDisable(GL_STENCIL_TEST);

		//Blending
		glEnable(GL_BLEND);
		glColor4f(1, 1, 1, 1);
		//drawFloor();
		glDisable(GL_BLEND);
    
		//glColor4f(WHITE);
	glPopMatrix();
    
	/* if((abs(PosicaoRobo[0][0])-abs(movimento[0])<1)||(abs(PosicaoRobo[0][2])-abs(movimento[2])<1    )){
	printf("TOUCOU!!!!!!\nx:%f\nz:%f\n",PosicaoRobo[0][0],PosicaoRobo[0][2]);
	}*/
    for(int i = 0; i < 6; i++){
        // if(i==0){
		//   printf("gajo %d\nx:%f\nz:%f\n",i+1,enemies[i].getX(),enemies[i].getZ());
        if(robotState[i] > 0){
            if((fabs(enemies[i].getX() - 0.5*sizerobo1) < 3) && 
			   (fabs(enemies[i].getZ() - 0.5*sizerobo1) < 3))	{ ///o x tá bem
                printf("TOUCOU no %d!!!!!!\tx:%f\tz:%f\n", i, enemies[i].getX(), enemies[i].getZ());
                if(vidaUser > 0){
                    vidaUser = vidaUser-10;
                    //  glutPostRedisplay();
                }
                //  }
            }
        }
		if(bullet){
			// if(i==0){
			//if((fabs(enemies[i].getX()-moveBala)<3)&&(fabs(enemies[i].getZ())-sizerobo1<3)){//-moveBala*sin(angXZ)
			printf("bala:%f\n", moveBala);
			//printf("angXZ:%f\tbala:%f\n", +angXZ,moveBala);
			//printf("bala:%f x=%f z=%f\n", moveBala, fabs(enemies[i].getX()), fabs(enemies[i].getZ()));
            if((fabs(enemies[i].getX() - xBala()) < 7) && 
			   (fabs(enemies[i].getZ() - zBala()) < 7))	{
				//-moveBala*sin(angXZ) -sizerobo 
			//printf("bala:%f x=%f z=%f", moveBala, fabs(enemies[i].getX()), fabs(enemies[i].getZ()));
              //  printf("\tAcertaste no %d!!!!!!!!!!\n",i+1);
                if(robotState[i] > 0){
                    robotState[i] = robotState[i] - 10;
                    // bullet=false;
                    //  glutPostRedisplay();
					// }
				}
            }
        }
    }
}

void desenhaTexto(char *string, GLfloat x, GLfloat y, GLfloat z){
	glRasterPos3f(x,y,z);
	while(*string)
        glutBitmapCharacter(GLUT_BITMAP_HELVETICA_10, *string++);
}

int checkWin(){
    int check=6;
    for(int i=0;i<6;i++){
        if(robotState[i]==0){
            check--;
        }
    }
    return check;
}

void display(void){
    
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~[ Apagar ]
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
    glDisable(GL_LIGHTING);
    generateUpThingPosition();
    updateEnemyPosition();
    
    for(int i=0;i<6;i++){
        enemies[i].update(RobotMovement[i][0]+movimento[0], 0,RobotMovement[i][2]+movimento[2],angXZ);
        //if(i==0){
           // printf("id=%d/tx=%f---z=%f\n",enemies[i].getId(),enemies[i].getX(),enemies[i].getZ());
            //printf("ang---%f\n",angXZ);
        // }
    }
    if(vidaUser<1){
        reset();
        glutPostRedisplay();

    }else{
        if(checkWin()==0){
            exit(0);
        }
    }
    
    //================================================================= Viewport1
	glViewport (0, hScreen/8, wScreen/8, hScreen/8);
  	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glOrtho (-xC,xC, -xC,xC, -zC,zC);
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	gluLookAt( 0, 10,0, 0,0,0, 0, 0, -1);
	//--------------------- desenha objectos
	drawScene();
	//--------------------- Informacao
   

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~[ Janela Visualizacao ]
	switch (ViewEstado) {
	case 0:
		glViewport(0, 0, wScreen, hScreen);
		break;
	case 1:
		glViewport(0, hScreen / 2, wScreen / 2, hScreen/2);
		break;
	case 2:
		glViewport(wScreen / 2, hScreen / 2, wScreen / 2, hScreen / 2);
		break;
	case 3:
		glViewport(0, 0, wScreen / 2, hScreen / 2);
		break;
	case 4:
		glViewport(wScreen / 2, 0, wScreen / 2, hScreen / 2);
		break;
	}
    
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~[ Projeccao]
    glEnable(GL_LIGHTING);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
    
	if (!enablePersp)
		glOrtho(-xC, xC, -yC, yC, -zC*5, zC*5);
	else
		gluPerspective(65, wScreen/hScreen, 1, 400);//300
    
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~[ Modelo+View(camera/observador) ]
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	gluLookAt(obsP[0]*inversor,obsP[1],obsP[2], 0,2*inversor,0, 0,1,0);
    
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~[ Objectos ]
    initLights();
    //glLightfv(GL_LIGHT0, GL_POSITION, robotP );
	//glLightfv(GL_LIGHT0, GL_SPOT_DIRECTION, focoDirection );
    glEnable(GL_LIGHT0);
    
    time_t timer = time(0);
    current_time = localtime(&timer);
    hour   = current_time->tm_hour;
    minute = current_time->tm_min;
    second = current_time->tm_sec;
    updateLuzGlobal();
    initLights();

	drawScene();
    /*
    glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
    int x=0;int y=0;
    char string[4]="ola";
	glColor3f (1,1,1);
    
	glRasterPos2f(x, y);
    
	for (int i = 0;i<4; ++i)        
        glutBitmapCharacter(GLUT_BITMAP_HELVETICA_18, string[i]);
    */
    glDisable(GL_LIGHTING);
    glViewport (hScreen, hScreen/8, wScreen/8, hScreen/8);
  	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glOrtho (-xC,xC, -xC,xC, -zC,zC);
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	gluLookAt( 0, 10,0, 0,0,0, 0, 0, -1);
	//--------------------- desenha objectos
	//--------------------- Informacao
    char texto[200];
    
    glPushMatrix();
		glColor4f(WHITE);
		sprintf(texto, "VIDA USER:%d\n",vidaUser);
		desenhaTexto(texto,-7,1,-4);
		for(int i=0;i<6;i++){
			sprintf(texto, "Enemigo %d: %d\n",i+1,robotState[i]);
			desenhaTexto(texto,-7,1,-4+2+i*2);
		}
	glPopMatrix();
    
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Actualizacao
	glutSwapBuffers();
}

//======================================================= EVENTOS
void keyboard(unsigned char key, int x, int y){
   // float test=angXZ/(2*PI);
    float Notolerance=0.2;
	switch (key) {
    //--------------------------- Viewport
	case 'm':
		ViewEstado = (ViewEstado+1) % 5;
		glutPostRedisplay();
		break;
	case 'q':
	case 'Q':
		/*if (enablePersp)
			enablePersp = false;
		else
			enablePersp = true;
		getFocoDirection();
		glutPostRedisplay();*/
		AngRotateSelf-=0.1;
		obsP[2] = tam*sin(AngRotateSelf);
		obsP[0] = tam*cos(AngRotateSelf);
			glutPostRedisplay();
		break;
	case 'e':
		AngRotateSelf+=0.1;
		obsP[2] = tam*sin(AngRotateSelf);
		obsP[0] = tam*cos(AngRotateSelf);
		break;
		//--------------------------- Escape
	case 27:
		exit(0);
		break;         
	case 'w': //ta lixado    
		// printf("%f\n",(angXZ*360)/(2.0*PI));
		if(!(abs(movimento[0]-sizerobo1)>DRAW_DISTANCE)&&!(abs(movimento[2]-sizerobo1)>DRAW_DISTANCE)){
			movimento[0]-=1*sin(angXZ+PI/2);
			movimento[2]+=1*cos(angXZ+PI/2);
			//printf("%f\n", movimento[0]);
		}
		else{
			reset_wall();
		}
		glutPostRedisplay();
		break;
	case 's'://ta lixado
		if(!(abs(movimento[0]-sizerobo1)>DRAW_DISTANCE)&&!(abs(movimento[2]-sizerobo1)>DRAW_DISTANCE)){
			movimento[0]+=1*sin(angXZ+PI/2);
			movimento[2]-=1*cos(angXZ+PI/2);
		}
		else{
			reset_wall();
		}
		glutPostRedisplay();
		break;
	case 'a':
		if(!(abs(movimento[2]-sizerobo1)>DRAW_DISTANCE)&&!(abs(movimento[0]-sizerobo1)>DRAW_DISTANCE)){
			movimento[0]-=1*sin(angXZ);
			movimento[2]+=1*cos(angXZ);
		}
		else{
			reset_wall();
		}
		glutPostRedisplay();
		break;
	case 'd':
		if(!(abs(movimento[2]-sizerobo1-2)>DRAW_DISTANCE)&& !(abs(movimento[2]-sizerobo1)>DRAW_DISTANCE)){movimento[0]+=1*sin(angXZ);
				movimento[2]-=1*cos(angXZ);
            
				}
		else{
			reset_wall();
		}
		glutPostRedisplay();
		break;
	case 'p':
		shoot_bullet();
		glutPostRedisplay();
        
		break;
	case 't':
		if (StupidColor) {
			StupidColor=false;
			walkingEnemyConstant=10000;
		}else{
			StupidColor=true;
			walkingEnemyConstant=500;
		}
		initLights();
		glutPostRedisplay();
		break;
	case 'f':
		if(inversor==1){
			inversor=-1;
		}
		else inversor=1;
		break;
	case 'l':
		switch (LanternaMode) {
		case 1:
			LanternaMode=2;
			//mudar esquema
			focoCor[0]=1;
			focoCor[1]=0;
			focoCor[2]=0;
			break;
		case 2:
			LanternaMode=3;
			//mudar esquema
			focoCor[0]=0;
			focoCor[1]=1;
			focoCor[2]=0;
			break;
		case 3:
			LanternaMode=4;
			//mudar esquema
			focoCor[0]=0;
			focoCor[1]=0;
			focoCor[2]=1;
			break;
		case 4:
			LanternaMode=5;
			//mudar esquema
			focoCor[0]=0;
			focoCor[1]=0;
			focoCor[2]=0;
			break;
		case 5:
			LanternaMode=0;
			//mudar esquema
			focoCor[0]=luzGlobalCor[0];
			focoCor[1]=luzGlobalCor[1];
			focoCor[2]=luzGlobalCor[2];
			break;
		case 0:
			LanternaMode=1;
			//mudar esquema
			focoCor[0]=1;
			focoCor[1]=1;
			focoCor[2]=1;
			break;
		}
		initLights();
		glutPostRedisplay();
		break;
	}
    glutPostRedisplay();
}

void teclasNotAscii(int key, int x, int y){
	if (key == GLUT_KEY_UP){
        if(obsP[1]<10){
		obsP[1]+=0.5;
        }
    }
	if (key == GLUT_KEY_DOWN){
        if(obsP[1]>0.5){
            
            obsP[1]-=0.5;
        }
    }
	if (key == GLUT_KEY_LEFT){
		angXZ -= 0.1;
		//obsP[2] = tam*sin(angXZ);
		//obsP[0] = tam*cos(angXZ);
	}
	if (key == GLUT_KEY_RIGHT){
		angXZ += 0.1;
		//obsP[2] = tam*sin(angXZ);
		//obsP[0] = tam*cos(angXZ);
	}
	//printf("%lf %lf %lf %f\n", obsP[0], obsP[1], obsP[2],angXZ);
	glutPostRedisplay();
}

void init(void){
    GLfloat nevoeiroCor[] = {0.75, 0.75, 0.75, 1.0};
	glClearColor(BLACK);
	glShadeModel(GL_SMOOTH);
    glEnable(GL_COLOR_MATERIAL);
	glColorMaterial(GL_FRONT_AND_BACK, GL_AMBIENT_AND_DIFFUSE );
    
	criaDefineTexturas();
	initLights();
	
	glEnable(GL_TEXTURE_2D);
    //glEnable(GL_FOG);
    glEnable(GL_LIGHTING);
	glEnable(GL_DEPTH_TEST);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    /*glFogfv(GL_FOG_COLOR, nevoeiroCor);
     glFogi(GL_FOG_MODE, GL_LINEAR);
     glFogf(GL_FOG_START, 20.0);
     glFogf(GL_FOG_END, 80.0);*/
    for(int i=0;i<6;i++){
        enemies[i] = Enemy(i);
    }
    initGenerationThingPosition();
	glutPostRedisplay();
}


//======================================================= MAIN
int main(int argc, char** argv){
	glutInit(&argc, argv);
	glutInitDisplayMode (GLUT_DOUBLE | GLUT_RGBA | GLUT_DEPTH | GLUT_STENCIL );
	//glutInitWindowSize (wScreen, hScreen);
    glutInitWindowSize (wScreen-200, hScreen-200);
	glutInitWindowPosition (0, 0);
	glutCreateWindow ("DEI KILLER   (left,right,up,down, 'a', 'q') ");
	init();
    glutTimerFunc(msecDelay, Timer, 1);			//    :controlar o tempo de
	glutSpecialFunc(teclasNotAscii);
	glutDisplayFunc(display);
    glutTimerFunc(msecDelay, Timer, 1);			//    :controlar o tempo de
	glutKeyboardFunc(keyboard);
    //randomFocoType();
    angXZ += 0.001;
	angXZ -= 0.001;
	// printf("%s\n",glGetString(GL_VERSION));
	glutMainLoop();
	return 0;
}
