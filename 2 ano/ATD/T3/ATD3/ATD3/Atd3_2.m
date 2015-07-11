function [ ] = Atd3_2(  )
%exercico 2.1
fprintf('exercicio 2.1\n\n');
[x, fs] = wavread('saxriff.wav');
wavplay(x, fs);
ws = 2*pi*fs; %2pi *frequencia angular de amostragem.
t = 0: 1/fs : (length(x)-1)/fs;
plot(t, x);
title('x(t)');

N = 2^16; %
X = fftshift(fft(x, N));

%Representar X para visualizar as componentes frequência

if(mod(N,2)==0)
	w = linspace(-fs/2, fs/2-fs/N, N);
%else
	%w = linspace(-fs/2+fs/N/2, fs/2-fs/N/2, N);
end

stem(w, abs(X));
title('Espectro de x(t)');

fprintf('Para continuar, prima uma tecla\n');
pause();

%exercico  2.2

%Encontrar o max
 fprintf('exercicio 2.2\n\n');
 pause();
 Xabsmax = max(abs(X)); %valor maximo de X
 indmax = find(abs(X) == Xabsmax);%% retorna os indices que que o modulo de x é igual ao maximo
 fXabsmax = w(indmax(2));      %para ser o positivo em Hz

 fprintf('A   frequência da componente com aplitude máxima é %f, e a sua frequencia %f\n',fXabsmax, max(abs(X)));

fprintf('Para continuar, prima uma tecla\n');
pause();
%exercico 2.3

%Adicionar ruido
fprintf('exercicio 2.3\n\n');
 fr1 = 8500; %8.5khz
 fr2 = 9500; %9.5 khz
 indpositivo = find(w>=fr1 & w<=fr2); %retorna indice positivo
 indnegativo = find(w>=-fr2 & w<=-fr1);%retorna indice negativo
 
 Xrabs = 0.1*Xabsmax*rand(size(indpositivo));
 Xrangle = 2*pi*rand(size(indpositivo))-pi;
 
 Xrpos = Xrabs.*(cos(Xrangle)+j*sin(Xrangle));
 Xrneg = conj(Xrpos);
 
%Adicionar a transformada do ruido ao que tinhamos antes
 
 Xr = X;
 Xr(indpositivo) = X(indpositivo)'+Xrpos;
 Xr(indnegativo) = X(indnegativo)'+Xrneg(end:-1:1); %para ser ao contrário
 
%Representar
 
 stem(w, abs(Xr));
 title('Espectro do sinal com ruído entre 8500 e 9500');
 
fprintf('Para continuar, prima uma tecla\n');
pause();
fprintf('exercicio 2.4\n\n');
%exercico 2.4 

%Calcular a inversa para voltar ao sinal mas com o ruído
 xr = real(ifft(ifftshift(Xr))); %obteve se o sinal e extraiu se a componente real
 wavplay(xr, fs);
 
fprintf('Para continuar, prima uma tecla\n');

%exercico 2.5
%Aplicar um filtro para eliminar o ruído

pause();
fprintf('exercicio 2.5\n\n');
fc = 8000;
wn = 2*fc/fs;
[b a] = butter(6, wn); %6 é a ordem o filtro, wn frequencia de corte em em função de metade da frequência de amostragem
xfiltrado = filter(b, a, xr);
Xfiltrado = fftshift(fft(xfiltrado));

polos = roots(a);
zeros = roots(b);
fprintf('Pólos:\n');
disp(polos);
fprintf('Zeros:\n');
disp(zeros);

fprintf('Coeficientes do numerador de G(Z):\n');
disp(b);
fprintf('Coeficientes do denominador de G(Z):\n');
disp(a);

zplane(b,a)

%exercico 2.6
%Representar

fprintf('Para continuar, prima uma tecla\n');
pause();

fprintf('exercicio 2.6\n\n');
stem(w, abs(Xfiltrado));
title('Espectro do sinal com filtro');
wavplay(xfiltrado, fs);

 
%2.6

fr1 = 2000; %2khz
 fr2 = 3000; %3khz
 indpositivo = find(w>=fr1 & w<=fr2); %mesma logica que cima, buscar indices
 indnegativo = find(w>=-fr2 & w<=-fr1);
 
 Xrabs = 0.1*Xabsmax*rand(size(indpositivo));
 Xrangle = 2*pi*rand(size(indpositivo))-pi;
 
 Xrpos = Xrabs.*(cos(Xrangle)+j*sin(Xrangle));
 Xrneg = conj(Xrpos);
 
%Adicionar a transformada do ruido ao que tinhamos antes
 
 Xr = X;
 Xr(indpositivo) = X(indpositivo)'+Xrpos;
 Xr(indnegativo) = X(indnegativo)'+Xrneg(end:-1:1); %para ser ao contrário

 stem(w, abs(Xr));
 title('Espectro do sinal com ruído entre 2000 e 3000');
 
 %Calcular a inversa para voltar ao sinal mas com o ruído
 xr = real(ifft(ifftshift(Xr)));
 wavplay(xr, fs);

fprintf('Para continuar, prima uma tecla\n');
pause();

fc1 = 2000;
fc2 = 3000;
wn = [2*fc1/fs, 2*fc2/fs];
[b, a] = butter(6, wn, 'stop'); %6 é a ordem o filtro, wn frequencia de corte em em função de metade da frequência de amostragem
xfiltrado = filter(b, a, xr);
Xfiltrado = fftshift(fft(xfiltrado));

polos = roots(a);
zeros = roots(b);
fprintf('Pólos:\n');
disp(polos);
fprintf('Zeros:\n');
disp(zeros);

fprintf('Coeficientes do numerador de G(Z):\n');
disp(b);
fprintf('Coeficientes do denominador de G(Z):\n');
disp(a);

zplane(b,a)

fprintf('Para continuar, prima uma tecla\n');
pause();
stem(w, abs(Xfiltrado));
title('Espectro do sinal com filtro');
wavplay(xfiltrado, fs);