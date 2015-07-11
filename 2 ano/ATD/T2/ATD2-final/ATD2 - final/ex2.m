function [ ] = ex2(  )

%2.1.1
fprintf('exercicio 2.1.1\n\n'); %T0 periodo fundamental
Tinput = input('Insira o periodo fundamental: ');

%2.1.2
fprintf('exercicio 2.1.2\n\n');
t = linspace(0, Tinput, 500); %cria sequencia temporal de 500 objectos espaçados igualmente na variavel t

%2.1.3
fprintf('exercicio 2.1.3\n\n');

escolha = menu('Escolha um sinal x(t):', 'Onda Quadrada', 'Dente de Serra', 'Introduzir expressão x(t)','ler de ficheiro');


if (escolha == 1)
    xt = zeros(size(t));
    xt(1:round(length(t)/2)) = 1;
    
elseif (escolha == 2) %dente de serra varia entre 0 e 1 num periodo
    xt = t/Tinput; 
    
elseif(escolha == 3)
   
    x = input('x(t) = ','s');
    xt = subs(sym(x), t);
   
else
        file = 'dadostp2_3';
        read = fopen(file);
        xt = (fscanf(read,'%f'))';
        fclose(read);
end


fprintf('Prima uma tecla para continuar.\n\n');
pause();




%2.1.4


fprintf('exercicio 2.1.4\n\n');

mmax = 100;
[Cm, tetam] = SFourier(t', xt', Tinput, mmax); % 

subplot(2, 1, 1);
plot(Cm,'. g');              %Cm e tetam(fase) são quocifientes da serie de Fourier, valor de mmax =100
ylabel('Cm');
subplot(2, 1, 2);
plot(tetam,'. b');
ylabel('tetam');

fprintf('Prima uma tecla para continuar.\n\n');
pause();


%2.1.5


fprintf('exercicio 2.1.5\n\n');
hold off;
subplot(1,1,1);
plot(t, xt);
hold on;

novaXt = zeros(1, 500);
for i=1 : length(Cm)
    novaXt = novaXt + Cm(i)*cos((i - 1)*((2*pi)/Tinput)*t + tetam(i)); 
    if(i==1 || i==2 || i==4 || i==6 || i==11 || i==51 || i==101) %+1 sempre porque os indices em matlab começam em 1
       plot(t, novaXt, 'r');
       pause(); %Para cada clique plota para um i diferente
    end
end
hold off;

fprintf('Prima uma tecla para continuar.\n\n');
%2.1.6


fprintf('exercicio 2.1.6\n\n');

cm = zeros(1,2*mmax + 1);

tam = mmax + 1; %101
cm(tam) = Cm(1) *(exp(1i*tetam(1)));

for i=2:tam
    cm(i - 1) = Cm(tam - i + 2)/2*exp(-1i*tetam(tam - i + 2));
    cm(i - 1 + tam) = Cm(i)/2*exp(1i*tetam(i));
end

amps = abs(cm);
fases = angle(cm);

subplot(2, 1, 1);
title('1- Cm (amplitude) 2- tetam ( fase) ');
plot(-mmax : mmax, amps, 'o'); %amplitudes, graficos simetricosem relaão a origem
subplot(2,1,2);
plot(-mmax : mmax, fases, 'o');%fase,

end
