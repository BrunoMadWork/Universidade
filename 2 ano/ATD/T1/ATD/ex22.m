function ex22()
u = zeros(100);
n = -50:50;

function u = unit(n) 
u = zeros(size(n));
u(n >= 0) = 1;
end

function x = fx(n)
x = 1.5*cos(0.025*pi*n).*(unit(n+40)-unit(n-40));
end

x = fx(n);
function [result,ruidos] = ruido(values,n)
    ruidos = zeros(length(n),1);
    for i=1:length(n)
        ruidos(i) = 0.4*rand()-0.2;
    end
    result = zeros(length(n),1);
    for i=1:length(n)
        result(i) = values(i) + ruidos(i);
    end
end

[x1, ruidos_result] = ruido(x, n);
Yruido = 0.4 * (fx(n - 1) + ruidos_result') + 0.4 * (fx(n - 3) + ruidos_result') + -0.1 * (fx(n - 4) + ruidos_result');

subplot(3,1,1)
plot(n, x);
subplot(3,1,2)
plot(n, x1);
subplot(3,1,3)
plot(n, x);
hold all;
plot(n,Yruido);
end