    function [  ] = Main(  )
clear();
disp('Exercicio 1');
s=input('Insira os simbolos:\n');
d=input('Insira o dicionario:\n');
disp('Histograma');
Histograma(s,d);


fprintf('Prima qualquer tecla para continuar...\n\n');
pause();
disp('Exercicio 2');

s=input('Insira os simbolos:\n');
d=input('Insira o dicionario:\n');
disp('O valor da entropia é');
valor_entropia=Entropia(s,d)


fprintf('Prima qualquer tecla para continuar...\n\n');
pause();

disp('Exercicio 3 e 4 e 5');


% kid.bmp
file = 'kid.bmp';
kid = imread(file);
kid_Alfabeto = unique(kid); %A função unique devolve um array 1*n contendo os n elementos únicos do array passado como parâmetro
subplot(3,2,1);


% Probabilidade
ocorrencias = Histograma(kid,kid_Alfabeto);
%bar(kid_Alfabeto,ocorrencias);
xlabel('Simbolos');
ylabel('Ocorrências');
title('kid.bmp');
% Entropia
str = sprintf('Informação do ficheiro: %s',file);
disp(str);
entropia = Entropia(kid,kid_Alfabeto);
str = sprintf('Entropia: %.3f',entropia);
disp(str);
% Entropia com agrupamentos
entropiaAgrupada = Entropia2a2(kid,kid_Alfabeto);
str = sprintf('Entropia Conjunta: %.3f / 2 = %.3f',entropiaAgrupada,entropiaAgrupada/2);
disp(str);
% Hufflen
[m,n] = size(kid);
probabilidade = ocorrencias/ (m*n);
Hlen = sum(hufflen(ocorrencias).*probabilidade);
str = sprintf('Huffman: %.3f\n',Hlen);
disp(str);

fprintf('Prima qualquer tecla para continuar...\n\n');
pause();    

% homer.bmp
file = 'homer.bmp';
homer = imread(file);
homerAlfabeto = unique(homer);
subplot(3,2,2);
% Probabilidade
ocorrencias = Histograma(homer,homerAlfabeto);
bar(homerAlfabeto,ocorrencias);
xlabel('Simbolos');
ylabel('Ocorrências');
title('homer.bmp ');
% Entropia
str = sprintf('Informação do ficheiro: %s',file);
disp(str);
entropia = Entropia(homer,homerAlfabeto);
str = sprintf('Entropia: %.3f',entropia);
disp(str);
% Entropia com agrupamentos
entropiaAgrupada = Entropia2a2(homer,homerAlfabeto);
str = sprintf('Entropia Conjunta: %.3f / 2 = %.3f',entropiaAgrupada,entropiaAgrupada/2);
disp(str);
% Hufflen
[m,n] = size(homer);
probabilidade = ocorrencias/ (m*n);
Hlen = sum(hufflen(ocorrencias).*probabilidade);
str = sprintf('Huffman: %.3f\n',Hlen);
disp(str);

fprintf('Prima qualquer tecla para continuar...\n\n');
pause();    

% homerBin.bmp
file = 'homerBin.bmp';
homerBin = imread(file);
homerBinAlfabeto = unique(homerBin);
subplot(3,2,3);
% Probabilidade
ocorrencias = Histograma(homerBin,homerBinAlfabeto);
bar(homerBinAlfabeto,ocorrencias);
xlabel('Simbolos');
ylabel('Ocorrências');
title('homerBin.bmp ');
% Entropia
str = sprintf('Informação do ficheiro: %s',file);
disp(str);
entropia = Entropia(homerBin,homerBinAlfabeto);
str = sprintf('Entropia: %.3f',entropia);
disp(str);
% Entropia com agrupamentos
entropiaAgrupada = Entropia2a2(homerBin,homerBinAlfabeto);
str = sprintf('Entropia Agrupada: %.3f / 2 = %.3f',entropiaAgrupada,entropiaAgrupada/2);
% disp(homerBin);
% disp(entropiaAgrupada);
% str = sprintf('Entropia Agrupada: %.3f / 2 = %.3f', entropiaAgrupada);
disp(str);
% Hufflen
[m,n] = size(homerBin);
probabilidade = ocorrencias/ (m*n);
Hlen = sum(hufflen(ocorrencias).*probabilidade);
str = sprintf('Huffman: %.3f\n',Hlen);
disp(str);

fprintf('Prima qualquer tecla para continuar...\n\n');
pause();    

% guitarSolo.wav
file = 'guitarSolo.wav';
[sound, fs ,nbits] = wavread(file);

% sound(Y,fs);
% é igual a calcular a entropia com x + 1 pontos, so com as probabilidades
% diferentes de 0!
% soundAlfabeto = unique(Y);
% d = (1 -(-1)) / (2^nbits)
% + 1 por causa do 1 nao fazer parte do intervalo
soundAlfabeto = linspace(-1,1,256 + 1);
% soundAlfabeto = [:-1];
subplot(3,2,4);
ocorrencias = Histograma(sound,soundAlfabeto);
bar(soundAlfabeto,ocorrencias);
xlabel('Simbolos');
ylabel('Ocorrências');
title('guitarSolo.wav ');
% Entropia
str = sprintf('Informação do ficheiro: %s',file);
disp(str);
entropia = Entropia(sound,soundAlfabeto);
str = sprintf('Entropia: %.3f',entropia);
disp(str);
% Entropia com agrupamentos
entropiaAgrupada = Entropia2a2(sound,soundAlfabeto);
str = sprintf('Entropia Agrupada: %.3f / 2 = %.3f',entropiaAgrupada,entropiaAgrupada/2);
disp(str);
% Hufflen
[m,n] = size(sound);
probabilidade = ocorrencias/ (m*n);
Hlen = sum(hufflen(ocorrencias).*probabilidade);
str = sprintf('Huffman: %.3f\n',Hlen);
disp(str);

fprintf('Prima qualquer tecla para continuar...\n\n');
pause();    

% english.txt
file = 'english.txt';
fid = fopen(file,'r');
fileInfo = dir(file);
fileSize = [fileInfo.bytes];
txt = fread(fid, fileSize, 'uint8');
k = 1;
% exclui caracteres indesejados
while(k ~= length(txt) + 1)
    if (txt(k) < 65 || (txt(k) > 90  && txt(k) < 97) || txt(k) > 122)
       txt(k) = [];
    else
       k = k + 1; 
    end
end
fclose(fid);
txtAlfabeto = unique(txt);


% Probabilidade
ocorrencias = Histograma_sem_grafico(txt,txtAlfabeto);
subplot(3,2,5:6);
bar(txtAlfabeto,ocorrencias);
xlabel('Simbolos');
ylabel('Ocorrências');
title('english.txt ');
% Entropia
str = sprintf('Informação do ficheiro: %s',file);
disp(str);
entropia = Entropia(txt,txtAlfabeto);
str = sprintf('Entropia: %.3f',entropia);
disp(str);
% Entropia com agrupamentos
entropiaAgrupada = Entropia2a2(txt,txtAlfabeto);
str = sprintf('Entropia Agrupada: %.3f / 2 = %.3f',entropiaAgrupada,entropiaAgrupada/2);
disp(str);
% Hufflen
[m,n] = size(txt);
probabilidade = ocorrencias/ (m*n);
Hlen = sum(hufflen(ocorrencias).*probabilidade);
str = sprintf('Huffman: %.3f\n',Hlen);
disp(str);

fprintf('Prima qualquer tecla para continuar...\n\n');
pause();    



% Exercicio 6 b)
fprintf('\nEx 6 b) - Informação Mútua\n')

%alfabeto do ficheiro som
[Query, fs, nbits] = wavread('guitarSolo.wav');

d = 2/(2^nbits);
Alfabeto = -1:d:1-d;
step = ceil(length(Query)/4);

Target = wavread('target01 - repeat.wav');
vecI = vectorInformacaoMutua(Query, Target, Alfabeto, step);

%Apresenta a variação da info mutua de cada um dos ficheiros
fprintf('Variação da informação mútua entre "guitarSolo.wav e "target01 - repeat.wav"\n');
for i=1:length(vecI)
    fprintf('Janela %d - %.3f\n',i,vecI(i));
end
%Grafico do Repeat 
subplot(211);
bar(vecI);
title('target01 - repeat.wav');

Target = wavread('target02 - repeatNoise.wav');
vecI = vectorInformacaoMutua(Query, Target, Alfabeto, step);

fprintf('\nVariação da informação mútua entre "guitarSolo.wav e "target02 - repeatNoise.wav"\n');
for i=1:length(vecI)
    fprintf('Janela %d - %.3f\n',i,vecI(i));
end
%Grafico do Repeat_Noise
subplot(212);
bar(vecI);
title('target02 - repeatNoise.wav');

fprintf('\nPrima qualquer tecla para continuar...\n\n');
pause();

% Exercicio 6 c)
fprintf('\nEx 6 c) - Simulador de identificação musical\n');

% Vector para guardar os valores de informação mútua máximos para cada caso
maximos = zeros(1,7);

%alfabeto do ficheiro som
[Query, fs, nbits] = wavread('guitarSolo.wav');

d = 2/(2^nbits);
Alfabeto = -1:d:1-d;
step = ceil(length(Query)/4);

for i = 1:7
    target = sprintf('Song0%d.wav', i);
    Target = wavread(target);
    vecI = vectorInformacaoMutua(Query, Target, Alfabeto, step);
    subplot(4,2,i);
    bar(vecI);
    title(target);
    maximos(i) = max(vecI);
end

% Ordena por ordem decresnte de valores
[maximos, index] = sort(maximos, 'descend');

%Apresenta a informação mutua de cada um dos ficheiros
for i = 1:7
    fprintf('Informação mútua entre guitarSolo.wav e Song0%d.wav: %.3f\n', index(i), maximos(i));
end

end