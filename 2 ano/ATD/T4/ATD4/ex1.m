function [  ] = ex1( )
%ex1.1
disp('exercicio 1.1: ');

load  sinal.mat
sinal = sumsin_freqbrk; %
subplot(1,1,1);
plot(sinal);
xlabel('n');
ylabel('amplitude');
title('representação sinal original');

disp('Clique numa tecla para continuar');
pause();

%ex 1.2
disp('exercicio 1.2:  ');
[cA, cD] = dwt(sinal, 'haar'); %transformada de wavelet usando mother havelet de haar
%cA=coecificiente de aproximação, é usado para obter os coecifientes de
%nivies superiores
%cD coecifientes de detalhe. cA frequencias baixas, cD frequencias altas
%
subplot(2,1,1);
plot(cA);
title('Coeficientes de Aproximação');

subplot(2,1,2);
plot(cD);
title('Coeficientes de Detalhe');

disp('Clique numa tecla para continuar');
pause();

%ex 1.3
disp('exercicio 1.3: ');
subplot(1,1,1);
reconstrucao=idwt(cA,cD,'haar');
plot(reconstrucao);
title('Sinal reconstruido');

disp('Clique numa tecla para continuar');
pause();
subplot(2,1,1);
plot(sinal);
xlabel('n');
ylabel('amplitude');
title('representação sinal original');
subplot(2,1,2);
plot(reconstrucao);
xlabel('n');
ylabel('amplitude');
title('representação sinal reconstruido');

disp('Clique numa tecla para continuar');
pause();

%1.4
disp('exercicio 1.4 e 1.6: ');

[C,L] = wavedec(sinal,4,'db3'); % ccoecificiente de aproximação, L lenght do coecificients
    cA4 = appcoef(C,L,'db3',4);
    cD4 = detcoef(C,L,4);
    cD3 = detcoef(C,L,3);
    cD2 = detcoef(C,L,2);
    cD1 = detcoef(C,L,1);

    n = 0:length(sinal)-1;
    n1 = linspace(0,max(n),length(cA4));
    n2 = linspace(0,max(n),length(cD4));
    n3 = linspace(0,max(n),length(cD3));
    n4 = linspace(0,max(n),length(cD2));
    n5 = linspace(0,max(n),length(cD1));

    subplot(6,1,1);
    plot(n,sinal);
    title('Sinal original');

    subplot(6,1,2);
    plot(n1,cA4);
    title(sprintf('Coeficientes de Aproximação, nível 4 (wavelet: %s)', 'db3'));

    subplot(6,1,3);
    plot(n2,cD4);
    title(sprintf('Coeficientes de Detalhe, nível 4 (wavelet: %s)', 'db3'));

    subplot(6,1,4);
    plot(n3,cD3);
    title(sprintf('Coeficientes de Detalhe, nível 3 (wavelet: %s)', 'db3'));

    subplot(6,1,5);
    plot(n4,cD2);
    title(sprintf('Coeficientes de Detalhe, nível 2 (wavelet: %s)', 'db3'));

    subplot(6,1,6);
    plot(n5,cD1);
    title(sprintf('Coeficientes de Detalhe, nível 1 (wavelet: %s)', 'db3'));

    figure(2)
    x = waverec(C,L,'db3'); %sinal construido com waverec (soma de todos os coecifientes)

    SCA4 = wrcoef('a',C,L,'db3',4);
    SCD4 = wrcoef('d',C,L,'db3',4);
    SCD3 = wrcoef('d',C,L,'db3',3);
    SCD2 = wrcoef('d',C,L,'db3',2);
    SCD1 = wrcoef('d',C,L,'db3',1);
    x2 = SCA4+SCD4+SCD3+SCD2+SCD1; %sinal reconstruido vai ser igual porque é a soma de todos os coecifientes, se faltasse um já não o seria
    % somar os coecificientes e fazer o waverec é a mesma coisa logo x==x2
    subplot(3, 1, 1);
    plot(n, sinal);
    title('Sinal Original');
    subplot(3, 1, 2);
    plot(n, x);
    title('Sinal Reconstruido com waverec');
    subplot(3, 1, 3);
    plot(n, x2);
    title('Sinal Reconstruido pela soma');
    
    figure(3);
    plot(n, sinal, n, x, '+g', n, x2, 'or');
    title(sprintf('Sinal Original + Sinais reconstruidos sobrepostos (wavelet: %s)', 'db3'));
    
    disp('Clique numa tecla para continuar');
    pause();
    
    
%1.5

disp('exercicio 1.5 e 1.6: ');



[C,L] = wavedec(sinal,4,'sym2'); % ccoecificiente de aproximação, L lenght do coecificients
    cA4 = appcoef(C,L,'sym2',4);
    cD4 = detcoef(C,L,4);
    cD3 = detcoef(C,L,3);
    cD2 = detcoef(C,L,2);
    cD1 = detcoef(C,L,1);

    n = 0:length(sinal)-1;
    n1 = linspace(0,max(n),length(cA4));
    n2 = linspace(0,max(n),length(cD4));
    n3 = linspace(0,max(n),length(cD3));
    n4 = linspace(0,max(n),length(cD2));
    n5 = linspace(0,max(n),length(cD1));

    subplot(6,1,1);
    plot(n,sinal);
    title('Sinal original');

    subplot(6,1,2);
    plot(n1,cA4);
    title(sprintf('Coeficientes de Aproximação, nível 4 (wavelet: %s)', 'sym2'));

    subplot(6,1,3);
    plot(n2,cD4);
    title(sprintf('Coeficientes de Detalhe, nível 4 (wavelet: %s)', 'sym2'));

    subplot(6,1,4);
    plot(n3,cD3);
    title(sprintf('Coeficientes de Detalhe, nível 3 (wavelet: %s)', 'sym2'));

    subplot(6,1,5);
    plot(n4,cD2);
    title(sprintf('Coeficientes de Detalhe, nível 2 (wavelet: %s)', 'sym2'));

    subplot(6,1,6);
    plot(n5,cD1);
    title(sprintf('Coeficientes de Detalhe, nível 1 (wavelet: %s)', 'sym2'));

    figure(2)
    x = waverec(C,L,'sym2');

    SCA4 = wrcoef('a',C,L,'sym2',4);
    SCD4 = wrcoef('d',C,L,'sym2',4);
    SCD3 = wrcoef('d',C,L,'sym2',3);
    SCD2 = wrcoef('d',C,L,'sym2',2);
    SCD1 = wrcoef('d',C,L,'sym2',1);
    x2 = SCA4+SCD4+SCD3+SCD2+SCD1; %sinal reconstruido vai ser igual porque é a soma de todos os coecifientes, se faltasse um já não o seria
    
    subplot(3, 1, 1);
    plot(n, sinal);
    title('Sinal Original');
    subplot(3, 1, 2);
    plot(n, x);
    title('Sinal Reconstruido com waverec');
    subplot(3, 1, 3);
    plot(n, x2);
    title('Sinal Reconstruido pela soma');
    
    figure(3);
    plot(n, sinal, n, x, '+g', n, x2, 'or');
    title(sprintf('Sinal Original + Sinais reconstruidos sobrepostos (wavelet: %s)', 'sym2' ));
    
    disp('Clique numa tecla para continuar');
pause();
end