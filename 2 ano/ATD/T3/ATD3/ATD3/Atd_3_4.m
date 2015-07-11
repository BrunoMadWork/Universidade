function [  ] = Atd_3_4(  )


%1.1.
[x, fs, nbits] = wavread('saxriff.wav');
wavplay(x, fs);

X = fftshift(fft(x));
sizeX = length(x);

if(mod(sizeX, 2)==0)
    r = -fs*pi : 2*pi*fs/sizeX : fs*pi-2*pi*fs/sizeX;
else 
    r = -fs*pi+fs*pi/sizeX : 2*pi*fs/sizeX : fs*pi-fs*pi/sizeX;
end

plot(r, abs(X), '.');
title('Magnitude');

disp('Prima qualquer tecla para continuar');
pause();

%4.2, 4.3 e 4.4
op = menu('Escolha uma opção:', 'Sem filtro', 'Com mediana de 5', 'Com mediana de 7', 'Com mediana de 9');
window = round(46.44*fs/1000);
sobreposicao = round(5.8*fs/1000);
N = window;

if mod(sizeX,2) == 0
    f = linspace(-fs/2, fs/2 - fs/N, N);
else
    f = linspace(-fs/2 + fs/(2*N), fs/2 - fs/(2*N), N);
end

if(op == 1)
    med = 1;
elseif(op == 2)
    med = 5;
elseif(op == 3)
    med = 7;
else
    med = 9;
end

matrixsizeX = 1 : N-sobreposicao : sizeX-N;
%Janelas
freqs = zeros(size(matrixsizeX));
amps = zeros(size(matrixsizeX));

x2 = zeros(sizeX, 1);

for i=1 : sizeX-med
	x2(i) = median(x(i : i+med-1));
end

xaprox = zeros(size(x));

Ts = 1/fs;
t = 0 : Ts : sizeX*Ts-Ts;

j=1;
for i=1 : N-sobreposicao : sizeX-N
    
    janelaX = fftshift(fft(x2(i : i+N-1).*hamming(N), N));
    Xabsmax = max(abs(janelaX));
    ind = find(abs(janelaX) == Xabsmax);
    
    amps(j) = Xabsmax;
    freqs(j) = f(ind(end));   
    
    A = 10*abs(amps(j))/N;
    xaprox(i : i+N-1) = A*sin(2*pi*freqs(j)*t(i : i+N-1));
    
    j=j+1;
    
end

freqs(freqs <= 100) = 0;

figure(2);
plot(1 : 46.44-5.8 : sizeX/fs*1000-46.44, freqs, '.');
if(med == 1)
    title('Frequências fundamentais do sinal');
else
    title(sprintf('Frequências fundamentais do sinal com filtro de mediana %d',med));
end
xlabel('ms');
ylabel('Hz');

wavwrite(xaprox, fs, nbits, sprintf('som_med_%d', med));
somR = wavread(sprintf('som_med_%d', med));
wavplay(somR, fs);

figure(3);
subplot(2, 1, 1);
plot(x2);
if(med == 1)
    title('saxriff.wav');
else
    title(sprintf('saxriff.wav com filtro de mediana %d', med));
end


subplot(2, 1, 2);
plot(somR);
title(sprintf('saxriff.wav reconstruído (mediana: %d)',med));

end
