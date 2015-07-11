function [ ] = Atd3_1(  )
%exercico 1.3
fprintf('exercicio 1.3\n\n');
f=70;
Ts=1/f;
T0=2/5;


t = linspace(0, T0, 500); %gera 500 numero
N=T0/Ts; 
n = 0:N-1;%gera numeros de 1 a 1 ate N
%continuo
xt = -1 + 3*cos(30*pi*t - pi/2) + 2*cos(65*pi*t - pi/4) - 2*cos(25*pi*t + pi/4);
%discreto
xn = -1 + 3*cos(30*pi*n*Ts - pi/2) + 2*cos( 65*pi*Ts*n - pi/4) + -2*cos(25*pi*Ts*n + pi/4);
%como todos o |W| <1 está de acordo com o teorema de amostragem

subplot(1,1,1);
plot(t,xt,n*Ts,xn,'g*');
legend('x(t)', 'x[n]');
title('Sinal amostrado e Sinal Original');


 
%exercico 1.4
fprintf('Prima uma tecla para continuar\n ');
pause();
fprintf('exercicio 1.4\n\n');

%n2=0:N-1;

XN =fftshift(fft(xn));


if(mod(N,2)==0)
    omega=linspace(-pi,pi-2*pi/N,N);
else
    omega=linspace(-pi+pi/N,pi-pi/N,N);
end
  
subplot(2,1,1);
 
stem(omega,abs(XN));
title('Representação em modulo ');

subplot(2,1,2);
stem(omega,angle(XN));
title('Representação em fase ');


fprintf('Prima uma tecla para continuar\n');

%exercicio 1.5
pause();
fprintf('exercicio 1.5\n\n');

%X(n)/Periodo tal como nos slides
%cm = XN/N;
cm = XN/T0;
disp(cm);


fprintf('Prima uma tecla para continuar\n');
pause();

%exercicio 1.6
pause();
fprintf('exercicio 1.6\n\n');
meio = floor(N/2);
Cm = abs(cm(meio+1:N))*2;
Cm(1) = Cm(1)/2;
O = angle(cm(meio+1:N));

subplot(2,1,1);
stem(0:meio-1, Cm);
title('Cm');
subplot(2,1,2);
plot(0:meio-1, O, '*');
title('Om');


fprintf('Prima uma tecla para continuar\n ');
pause();
%exercico 1.7

fprintf('exercicio 1.7\n\n');

xtReconstruido= 0;

for i=1: length(Cm)
    
    xtReconstruido = xtReconstruido +Cm(i)*cos((i-1)*pi*t+O(i));
    
end

subplot(1,1,1);

plot(t,xt,t,xtReconstruido,'r*');
title('sinal original e reconstruido');
end

