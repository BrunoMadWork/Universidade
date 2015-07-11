function [  ] = ex2(  )
%2.1
disp('exercicio 2.1: ');
[image map] = imread('lenna.jpg');




[C S] = wavedec2(image, 2, 'Haar');

%Coeficientes
CA2 = appcoef2(C, S, 2, 'Haar');
[CDH2, CDV2, CDD2] = detcoef2('all', C, S, 2);%coecificientes de detalhe horizonta,veritical e diagonal
[CDH1, CDV1, CDD1] = detcoef2('all', C, S, 1);

%Representação

%Mapa de cores
%map = colormap;
ncolors = double(max(max(image)));

%Transformação da matriz de modo que fique com indíces do map
CA2m = wcodemat(CA2, ncolors);
CDH2m = wcodemat(CDH2, ncolors);
CDV2m = wcodemat(CDV2, ncolors);
CDD2m = wcodemat(CDD2, ncolors);
CDH1m = wcodemat(CDH1, ncolors);
CDV1m = wcodemat(CDV1, ncolors);
CDD1m = wcodemat(CDD1, ncolors);

figure(1);
Q = [[CA2m CDH2m; CDV2m CDD2m] CDH1m; CDV1m CDD1m];
imshow(Q, map);

disp('Clique numa tecla para continuar');
pause();

disp('exercicio 2.2: ');


cA2 = wrcoef2('a', C, S, 'Haar', 2);
cDH2 = wrcoef2('h', C, S, 'Haar', 2);
cDV2 = wrcoef2('v', C, S, 'Haar', 2);
cDD2 = wrcoef2('d', C, S, 'Haar', 2);
cDH1 = wrcoef2('h', C, S, 'Haar', 1);
cDV1 = wrcoef2('v', C, S, 'Haar', 1);
cDD1 = wrcoef2('d', C, S, 'Haar', 1);



image2 = waverec2(C, S, 'Haar'); %reconstroi 
image3 = cA2 + cDH2 + cDV2 + cDD2 + cDH1 + cDV1 + cDD1;
image4 = cDH2 + cDV2 + cDD2;
image5 = cDH1 + cDV1 + cDD1;

figure(2);
%subplot(3, 1, 1);
imshow(image, map);
title('Imagem original');
figure(3);
%subplot(3, 1, 2);
imshow(image2, map);
title('Imagem recontruida pelo waverec');
%subplot(3, 1, 3);
figure(4);
imshow(image3, map);
title('Imagem recontruida pela soma dos coeficientes');

figure(5);
imshow(image4, map);
title('Imagem recontruida pela soma dos coeficientes de nível 2');

figure(4);
imshow(image5, map);
title('Imagem recontruida pela soma dos coeficientes de nível 1');

end

