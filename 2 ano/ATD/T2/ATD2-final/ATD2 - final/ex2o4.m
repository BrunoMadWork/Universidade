function [  ] = ex2o4(    )


syms m;
syms t;

Tf223 = 2/5; %periodo de f3
Tf4 = pi;   %periodo de f4

xt3 = (1+2*cos(20*pi*t-(pi/4))*sin(45*pi*t)); %f3
xt4 = -2 + 4*cos(4*t + pi/3) - 2*sin(10*t); %f4

cm3 = int(xt3*exp(-1i*m*3*pi*t), t, -Tf223/2, Tf223/2)/Tf223; 
matriz_cm3 = zeros(1, 101);

for k=0:100
    matriz_cm3(k+1) = limit(cm3, k);
end

cm4 = int(xt4*exp(-1i*m*2*t), t, -Tf4/2, Tf4/2)/Tf4; 
matriz_cm4 = zeros(1, 101);

for k=0:100
    cm4Matrix(k+1) = limit(cm4, k);
    
end
fprintf('valores de cm de x3(t):\n');
disp(matriz_cm3);

fprintf('valores de cm de x4(t):\n');
disp(matriz_cm4);


end

