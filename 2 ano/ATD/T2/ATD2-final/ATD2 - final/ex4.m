function[] = ex4()

%expressao em modulo e em fase(4.1)
    fprintf('Exercicio 4.1\n\n');
    syms t m o;
    %Omega
    w = -30*pi:pi/6:30*pi;

    %Transformada de Fourier
    xt = 2*exp(-0.7*t) * sin(4*pi*t);
    X = int(xt * exp(-1i*o*t), t, 0, 6);
    Xw = double(subs(X, w)); %criar vector

    %Grafico em modulo e em fase
    subplot(2, 1, 1);
    plot(w, abs(Xw));
    title('Módulo');
    %axis([-4 4 0.15 0.17]);
    subplot(2, 1, 2);
    plot(w, angle(Xw));
    title('Fase');
    %axis([-4 4 -0.05 0.05]);
    pause();
    close all;

%Reconstrução do sinal(4.2)
    fprintf('Exercicio 4.2\n\n');

    v = linspace(0,6,500);
    xt = 2*exp(-0.7*v).*sin(4*pi*v);
    xtreconstruido = ifourier(X, t);

    plot(v, xt, 'o g',v, double(subs(xtreconstruido, t, v)));
 
    pause();

%Coeficientes da Serie de Fourier complexa(4.3)
    fprintf('Exercicio 4.3\n\n');

    syms m;
    w = linspace(-25, 25, 500);

    % Determinação dos Coeficientes
    Cm = int(2*exp(-0.7*t)*sin(4*pi*t)*exp(-1i*m*t),t,0,6)/(1/2);
    m = -25 : 25;
    c = subs(Cm);
    X2 = subs(X/(1/2), w);

    subplot(2, 1, 1);
    plot(w, abs(X2), m, abs(c), '*');
    title('Modulo');

    subplot(2, 1, 2);
    plot(w, unwrap(angle(X2)), m, unwrap(angle(c)), '*');
    title('Fase');

end






