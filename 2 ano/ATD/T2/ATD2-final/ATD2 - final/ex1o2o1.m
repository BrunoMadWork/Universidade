function [  ] = ex1o2o1(  )

a1 = -2.1 -0.2 * mod(8,2);
a2 = 1.43 + 0.31 * mod(8,2);
a3 = -0.315 -0.117 * mod(8,2);
b2 = 0.9167 * mod(8+1,2);
b3 = 0.3137 * mod(8,2);
b4 = -0.5867 * mod(8+1,2);
b5 = -0.1537 * mod(8,2);


%1.2.1 
fprintf('ex 1.2.1\n\n');

b = [0 0 b2 b3 b4 b5];
a = [1 a1 a2 a3 0 0];



polos=roots(a);
fprintf('Os polos são :\n\n');
disp(polos);

zeros=roots(b);
fprintf('Os zeros são :\n\n');
disp(zeros);


zplane(b,a);
fprintf('Prima uma tecla para continuar.\n\n');
pause();

%1.2.2

fprintf('ex 1.2.2\n\n');
%O sistema é estavel se todos os polos forem menores que 1
if(all(abs(polos) < 1))
    fprintf('O sistema  estável porque todos os polos são menores que 1\n');
else
    fprintf('O sistema não é estavel porque nem todos os polos são menores que 1\n');
end


fprintf('Prima uma tecla para continuar.\n\n');
pause();



%1.2.3
fprintf('ex 1.2.3\n\n');

syms z;
%transformada inversa de z
hn = iztrans((0.9167*z^-2 - 0.5867*z^-4)/(1 - 2.1*z^-1 + 1.43*z^-2 - 0.315*z^-3));

fprintf('A resposta a impulso do sistema h[n] é\n');
pretty(hn);

fprintf('Prima uma tecla para continuar.\n\n');
pause();
hold off
%1.2.4 ainda nao acabada
title('Gráfico do exercício 1.2.4\n\n')
nn = 0:70;
h1 = subs(hn, nn);

y1 = impz(b, a, 71);
y2 = dimpulse(b, a, 71);
[X Y] = stairs(nn, h1);

plot(X, Y, nn, y1, 'or', nn, y2, 'g+');
 


 
fprintf('Prima uma tecla para continuar.\n\n');
pause(); 


%1.2.5 ainda não acabada

fprintf('ex 1.2.5\n\n');
hz = ((0.9167*z^-2 - 0.5867*z^-4)/(1 - 2.1*z^-1 + 1.43*z^-2 - 0.315*z^-3));
uz = 1/(1-z^-1);
yz =uz*hz;
yn = iztrans(yz); %transforamada inversa
fprintf('\nA expressão do sistema em resposta ao degrau unitário é:\n'); 
pretty(yn);
fprintf('Prima uma tecla para continuar.\n\n');
pause();

%1.2.6 ainda nao acabada
fprintf('ex 1.2.6\n\n');
hold on
stairs(nn, subs(yn, nn), 'o g');
dstep(b, a, length(nn));
fprintf('grafico\n\n\n\n');
hold off

%1.2.7 ainda nao acabada
syms n;
xn7 = input('\nInsira o vector de x[n]: ');

yz= 0;
for i = 1:length(xn7)
   yz = yz + xn7(i)*z^(-i+1);
end
yz7 = yz*hz;
yn7 = iztrans(yz7);
pretty(yn7);

fprintf('Prima uma tecla para continuar.\n\n');
pause();

%1.2.8 ainda nao acabada
fprintf('ex 1.2.8\n');
nn=0:length(xn7)-1;
[X Y] = stairs(nn, subs(yn7, nn));
y81 = filter(b, a, subs(xn7, nn));
y82 = dlsim(b, a, subs(xn7, nn));
plot(X, Y, nn, y81, 'or', nn, y82, 'g+');


fprintf('Prima uma tecla para continuar.\n\n');
pause();

%1.2.9 ainda nao acabada

fprintf('ex 1.2.9\n\n');


w = linspace(0, pi, 500);
H = freqz(b, a, w);

subplot(2, 1, 1);
plot(20*log10(abs(H)),'g');
ylabel('Amplitude');
subplot(2, 1, 2);
plot(unwrap(angle(H)),'b');
ylabel('Fase');

fprintf('Prima uma tecla para continuar.\n\n');
pause();


%1.2.10 ainda nao acabada

fprintf('ex 1.2.10\n\n');
ganho = ddcgain(b, a);
fprintf('O ganho do sistema em regime estacionário é: ');
disp(ganho);







end