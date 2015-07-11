function [ ] = ex33( )

Fs = 44100;%frequencia
%http://en.wikipedia.org/wiki/44,100_Hz
t = 0:(1/Fs):0.5;%para simplificar o codigo
A = zeros(1, (18000-200)/100);%construir a matriz
k = 1;
n = 50;
maior = 0;

for f = 200:100:18000
    
    xt = sin(2*pi*f*t);%define o sinal em som
    
    wavplay(xt,Fs,'async');%reproduz o som
    
    
    y = wavrecord(0.5*Fs+1, Fs);% grava o som em y

    %preparar para dividir em partes de 50
    intervalo = zeros(50);
    maior = 0;
    tamanho = round(length(y)/n);%divide em n partes iguais o y e aredonda para cima
    
    for i=1:n%ciclo para gravar os dados e guardar a soma maior e fazer a media
        intervalo = y(((i-1)*tamanho+1):(i*tamanho));
        maior = maior + max(abs(intervalo));
    end
    
    A(k) = maior/n;%fazer a media
    k = k + 1;
end

x = (200:100:18000);

plot(x, A, 'b *');%construir o grafico com *aazuis