fprintf('exercicio 3\n\n');
%2.1
%2.1.1
T0 = input('Insira o periodo fundamental: ');
W0 = 2*pi/T0;
%2.1.2
t = linspace(0, T0, 500);

%2.1.3
escolha = menu('Escolha um sinal x(t):', 'Onda Quadrada', 'Dente de Serra', 'Introduzir expressão x(t)','ler de ficheiro');

if (escolha==1)
    
    xt = zeros(size(t));
    xt(1:round(length(t)/2)) = 1; %de um até metade xt(t)=1
    
elseif (escolha == 2)
    
    xt = t/T0;
    
elseif(escolha==3)
    
    x = input('Insira a sua expressão x(t): ', 's');
    syms x;
    xta = eval(x); %xt é a expressao
    xt=subs(xta,x,t); %passa de simbolico a vector 
else
    file = 'dadostp2_3';
        read = fopen(file);
        xt = (fscanf(read,'%f'))';
        fclose(read);
end
mmax = 100;
r = menu('Escolha um tipo de ruído:', 'Ruído completamente aleatório', 'Ruído aleatório numa dada gama de frequências', 'Ruído definido por uma expressão', 'Sem ruído');

if(r == 1)
    
    ruido = 0.4*rand(size(t))-0.2; %gama de valores entre -0.2 e 0.2
    
elseif(r == 2) 
    
    fmin = input('Insira a frequencia minima: ');
    fmax = input('Insira a frequencia maxima: ');
    
    
    mrmin = ceil(fmin/W0); %arredonda para cima
    mrmax = ceil(fmax/W0); %arredonda para cima
    
    
    
    mr = mrmin:mrmax; %array de valores de fmin ate fmax
    
    Cmr = 0.2*rand(1, mmax+1);
    tetamr = 2*pi*rand(1, mmax+1) - pi;
    
    ruido = zeros(size(t));
    for mr = mrmin:mrmax
        ruido = ruido + Cmr(mr)*cos((mr-1)*W0*t + tetamr(mr));%onda ruido, %mr-1 porque indices matlab começam em 1
    end
        
elseif(r == 3)
    ru = input('Insira a expressão do ruído: ', 's');
    syms ru;
    rui=eval(ru);
    ruido = subs(rui,ru, t);
    
    
else
    ruido = zeros(size(t)); %array de 500 0
    
end

xrt = xt+ruido; %SOMA SINAIS
plot(t, xt, 'r', t, xrt);


hold off

f = menu('Escolha um tipo de filtro:', 'Passo-baixo', 'Passo-Alto', 'Passo-Banda', 'Rejeito-Banda');

if(f == 1)
    pmin = 1;
    pmax = input('Insira o valor máximo de frequência: ');

elseif(f == 2)
    pmin = input('Insira o valor minimo de frequência: ');
    pmax = mmax;

elseif(f == 3)
    pmin = input('Insira o valor minimo de frequência: ');
    pmax = input('Insira o valor máximo de frequência: ');

elseif(f == 4)
    pmin = input('Insira o valor minimo de frequência: ');
    pmax = input('Insira o valor máximo de frequência: ');

end


fmmin = ceil(pmin/W0);
fmmax = ceil(pmax/W0);

[Cmr, tetamr] = SFourier(t', ruido', T0, mmax); %serie de fourier sobre ruido

[Cmxr, tetamxr] = SFourier(t', xrt', T0, mmax); %serie de fourier sobre  sinal + ruido
amps1 = abs(Cmxr);% modulo
fases1 = angle(Cmxr);

filtro = zeros(size(t));

%filtros
if (f == 1 || f == 2 || f == 3)
    for fm = fmmin:fmmax
		filtro = filtro + Cmr(fm)*cos(W0*(fm-1)*t+tetamr(fm));
    end
    
else
    for fm = 1:fmmin-1
		filtro = filtro + Cmr(fm)*cos(W0*(fm-1)*t+tetamr(fm));
    end
    
    for fm = fmmax+1:mmax+1
		filtro = filtro + Cmr(fm)*cos(W0*(fm-1)*t+tetamr(fm));
    end
end

xrft = xt + filtro;

[Cmxf, tetamxf] = SFourier(t', xrft', T0, mmax);
amps2 = abs(Cmxf); %modulo da amplitude
fases2 = angle(Cmxf); %angulo feito pelo vector

figure(1); %plot sobrepoe 
zero100=0:100;
subplot(2, 1, 1);
plot(zero100, amps1, '.');
title('Cm com ruído');
subplot(2, 1, 2);
plot(zero100, fases1, '.');
title('Fase com ruído');

figure(2);  %plot sobrepoe
subplot(3, 1, 1);
plot(t, xt); %sinal normal

subplot(3, 1, 2);
plot(t, xrt, 'g'); %sinal  com ruido

subplot(3,1,3);  %sinal com filtro
plot(t, xrft, 'r');

figure(3); %plot sobrepoe 
subplot(2, 1, 1);
zerommax=0:mmax;
plot(zerommax, amps2, '.');
title('Cm com filtro');
subplot(2,1,2);
plot(zerommax, fases2, '.');
title('Fase com filtro');