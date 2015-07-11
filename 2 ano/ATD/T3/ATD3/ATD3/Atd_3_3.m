function [  ] = Atd_3_3( )
%3.1
fprintf('exercicio 3.1\n\n');

hold off;
%imagem é uma matriz em que cada elemento é uma indexação do vector mapa
%mapa vector com mapeamento das cores
[imagem, mapa] = imread('lena.bmp');

fprintf('exercicio 3.2\n\n');

%3.2
imshow(imagem, mapa);
title('lena.bmp');

%Aplicar a Transformada de Fourier Discreta
%como é um matriz --> 2 dimensões, tem que se usar a fft2

fprintf('Prima uma tecla para continuar\n');
pause();

fprintf('exercicio 3.3\n\n');

%3.3
Ximagem = fftshift(fft2(imagem));

%Ver se é par ou impar para definir os eixos

[nl, nc] = size(Ximagem);
if(mod(nl,2) == 0)
    eixox = -nl/2 : nl/2-1;
else
    eixox = fix(-nl/2) : fix(nl/2); %arredonda
end

if(mod(nc, 2) == 0)
    eixoy = -nc/2 : nc/2-1;
else
    eixoy = fix(-nc/2) : fix(nc/2);
end

%Representar

mesh(eixox, eixoy, 20*log(abs(Ximagem)));
axis([eixox(1) eixox(end) eixoy(1) eixoy(end)]);
view([-37,5, 30]);
rotate3d;

title('Magnitude da imagem');

fprintf('Prima uma tecla para continuar\n ');
pause();

cx = find(eixox == 0);
cy = find(eixoy == 0);

fprintf('Cor média: '); disp(Ximagem(cx, cy)/(nc*nl)); %fprintf('Hz');
fprintf('\n');
%3.4
fprintf('Para continuar, prima uma tecla\n\n');
pause();
%Aplicar o filtro ideal
coef = 1;
mask = zeros(size(Ximagem));

filtro = menu('Filtro', 'Passo Baixo', 'Passo Alto');
fc = input('Frequência de corte:');



for l=1 : nl
    for c=1 : nc
        if((l-cx)^2 + (c-cy)^2 <= fc^2)
            mask(l, c) = 1;
        end
    end
end
    
%Filtrar
if(filtro == 2)
    mask = ones(size(mask)) - mask;
    coef = 10;
end

mesh(eixox, eixoy, mask);
rotate3d;
title('Magnitude da máscara');

fprintf('Prima uma tecla para continuar\n ');
pause();
Ximagefilt = Ximagem.*mask;

%Representar
mesh(eixox, eixoy, 20*log10(abs(Ximagefilt)+1)); %+1, para não calcular log(0)
rotate3d;
title('Magnitude da imagem com filtro');

fprintf('Prima uma tecla para continuar\n ');
pause();
%Obter a imagem resultante
imagefilt = ifft2(ifftshift(real(Ximagefilt)));
imshow(real(imagefilt*coef), mapa); %coef realça as cores
title('Imagem depois do filtro');


end