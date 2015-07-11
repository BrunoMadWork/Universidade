function [  ] = Atd_3_5(  )



op = menu('Escolha um som:', 'escala.wav', 'piano.wav', 'flauta.wav');

if(op == 1)
    som = 'escala.wav';
elseif(op == 2)
    som = 'piano.wav';
elseif(op == 3)
    som = 'flauta.wav';

end

[x, fs, nbits] = wavread(som);

if size(x, 2) == 2
   temp = zeros(length(x), 1);
   for i = 1:length(x)
       temp(i) = (x(i, 1) + x(i, 2)) / 2;
   end
   x = temp;
end

wavplay(x, fs, 'async');

X = fftshift(fft(x));
sizeX = length(x);

if(mod(sizeX, 2)==0)
    w = -fs*pi : 2*pi*fs/sizeX : fs*pi-2*pi*fs/sizeX;
else 
    w = -fs*pi+fs*pi/sizeX : 2*pi*fs/sizeX : fs*pi-fs*pi/sizeX;
end

subplot(2, 1, 1);
plot(x);
title(sprintf('sinal: %s', som));
subplot(2, 1, 2);
plot(w, abs(X), '.');
title('Magnitude');

disp('Prima qualquer tecla para continuar');
pause();


 freqNotas = [262 277 294 311 330 349 370 392 415 440 466 494];
 Notas = {'Do   ';'Do#  ';'Re   ';'Re#  ';'Mi   ';'Fa   ';'Fa#  ';'Sol  ';'Sol# ';'La   ';'La#  ';'Si   '};


janela = round(100/1000*fs);
sobreposicao = round(12.5/1000*fs);

N = janela;
if mod(sizeX,2) == 0
    f = linspace(-fs/2, fs/2 - fs/N, N);
else
    f = linspace(-fs/2 + fs/(2*N), fs/2 - fs/(2*N), N);
end
matrixsizeX = 1 : N-sobreposicao : sizeX-N;

freqs = zeros(size(matrixsizeX));
amps = zeros(size(matrixsizeX));

j=1;
for i=1 : N-sobreposicao : sizeX-N
    janelaX = fftshift(fft(x(i : i+N-1).*hamming(N), N));
    Xabsmax = max(abs(janelaX));
    ind = find(abs(janelaX) == Xabsmax);
    
    amps(j) = Xabsmax;
    freqs(j) = f(ind(floor(end/2)+1));
    
    j=j+1;
end


figure(2);
plot(1 : janela*1000/fs-sobreposicao*1000/fs : sizeX/fs*1000-janela*1000/fs, freqs, '.');
title('Frequências fundamentais');
xlabel('ms');
ylabel('Hz');
disp('Prima qualquer tecla para continuar');
pause();

fprintf('Resolução em frequência do som %s = %.2f\n', som, fs/N);
disp('Prima qualquer tecla para continuar');
pause();


fprintf('Notas do som: %s\n', som);
for i=1 : length(freqs)
    
    freq = freqs(i);
    
    if freq ~= 0
        while(freq < freqNotas(1))
            freq = freq*2;
        end
        
        while(freq > freqNotas(end))
            freq = freq/2;
        end

        for j=1:length(freqNotas)
            if (freq < freqNotas(j))
                break;
            end
        end
        
        if(j ~= 1)
            if(abs(freq-freqNotas(j-1)) <= abs(freq-freqNotas(j)))
                j = j-1;
            end
        end
        
        fprintf('%-5s \n', Notas{j});
    end
    
end
 

end
