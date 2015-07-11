function [  ] = ex3(  )
disp('exercicio 3.1: ');

[image, map] = imread('lenna.jpg');
imshow(image);
title('lenna.jpg');

disp('Clique numa tecla para continuar');
pause();


disp('exercicio 3.2: ');


imageDCT = dct2(image);

figure(1);
imshow(log10(abs(imageDCT)+0.001), map);%%%%
colorbar;
title('Mesh da DCT da imagem lenna.jpg');

figure(2);
mesh((log(abs(imageDCT))));
colormap(jet);
colorbar;
title('DCT da imagem lenna.jpg');
rotate3d;
disp('Clique numa tecla para continuar');
pause();

%3.3
image8x8 = blkproc(image, [8 8], 'dct2');

subplot(2, 3, 1);
imshow(image8x8, map);
title('Imagem com blocos 8x8');

disp('Clique numa tecla para continuar');
pause();
%3.4

disp('exercicio 3.4: ');

mask = zeros(8, 8);

%Reconstrução da imagem com 1 Coeficiente da DCT
mask(1, 1) = 1;
idct2F = @(x) idct2(x .* mask);	
image1 = blkproc(image8x8, [8, 8], idct2F);

subplot(2, 3, 2);
imshow(image1, map);
title('Reconstrução da imagem com 1 Coeficiente da DCT');


%Reconstrução da imagem com 5 Coeficientes da DCT
mask(1:2, 1:2) = 1;
mask(3, 1) = 1;
idct2F = @(x) idct2(x .* mask);	
image2 = blkproc(image8x8, [8, 8], idct2F);

subplot(2, 3, 3);
imshow(image2, map);
title('Reconstrução da imagem com 5 Coeficientes da DCT');


%Reconstrução da imagem com 10 Coeficientes da DCT
mask(1:2, 1:3) = 1;
mask(1:4) = 1;
mask(3:2) = 1;
mask(4:1) = 1;
idct2F = @(x) idct2(x .* mask);	
image3 = blkproc(image8x8, [8, 8], idct2F);

subplot(2, 3, 4);
imshow(image3, map);
title('Reconstrução da imagem com 10 Coeficientes da DCT');

%Reconstrução da imagem com 20 Coeficientes da DCT
mask(1:2, 1:5) = 1;
mask(1:4, 1:3) = 1;
mask(1, 4) = 1;
mask(3, 4) = 1;
mask(5, 1) = 1;
mask(5, 2) = 1;
mask(6, 1) = 1;
idct2F = @(x) idct2(x .* mask);	
image4 = blkproc(image8x8, [8, 8], idct2F);

subplot(2, 3, 5);
imshow(image4, map);
title('Reconstrução da imagem com 20 Coeficientes da DCT');


%Reconstrução da imagem com todos os Coeficientes da DCT
mask = ones(8, 8);
idct2F = @(x) idct2(x .* mask);	
image5 = blkproc(image8x8, [8, 8], idct2F);

subplot(2, 3, 6);
imshow(image5, map);
title('Reconstrução da imagem com todos os Coeficientes da DCT');

end

