function [] = ex4()
    clear all;
    clc;
    
    %ex 4.1
    disp('exercicio 4.1: ');
    sinal = load('seriestemp.dat');
    [nl, nc] = size(sinal);
    t = (0:nl - 1)';
    
    plot(t, sinal, '-+');
    legend('Serie temporal original 1', 'Serie temporal original 2', 2);
    title('Contagens em 48 horas');
    xlabel('Tempo (horas)');
    ylabel('Contagens');
    
    disp('Clique numa tecla para continuar');
    pause();
    close all;
    disp('exercicio 4.2: ');
    
    
    %ex 4.2
    haNaN = any(isnan(sinal)); %verifica se ha NaN por colunas
    colunas = find(haNaN);
    sinal_nan = sinal;
    
    %elimina linhas com NaN e reconstroi
    if(any(haNaN))
        for c = colunas %ciclo que serve para percorrer colunas que tenham pelo menos um not a number
            td = [t, sinal_nan(:, c)];
            %elimina linhas com NaN
            td(any(isnan(td), 2), :) = [];
            %reconstroi linhas
            sinal_nan(:, c) = interp1(td(:, 1), td(:, 2), t, 'linear'); %repor pontos em falta
        end
    end
    
    plot(t, sinal(:, 1), '-+', t, sinal_nan(:, 1), '-o'); %plot de sinal com pontos novos interpolados
    legend('Serie temporal original 1', 'Serie temporal sem NaN 1', 2);
    title('Contagens em 48 horas');
    xlabel('Tempo (horas)');
    ylabel('Contagens');
    disp('Clique numa tecla para continuar');
    pause();
    close all;
    disp('exercicio 4.3: ');
    
    
    %ex 4.3
    disp('Media: '); 
    mu = mean(sinal_nan)
    disp('Desvio-Padrao: ');
    sigma = std(sinal_nan)
    disp('Correlacao: ');
    corr = corrcoef(sinal_nan)
    disp('Clique numa tecla para continuar');
    pause();
    
    disp('exercicio 4.4: ');
    
    %ex 4.4
    MeanMat = repmat(mu, nl, 1); %repete media
    sigmaMat = repmat(sigma, nl, 1);
    %identificar outliers
    outliers = abs(sinal_nan - MeanMat) > 3 * sigmaMat;
    disp('Nº de outliers por coluna: ');
    nout = sum(outliers)
    haOutlier = any(nout); %verifica se ha outliers
    colunas = find(nout); %colunas com outliers
    sinal_outliers = sinal_nan;
    
    %elimina linhas com outliers e reconstroi
    if(any(haOutlier))
        for c = colunas
            td = [t, sinal_outliers(:, c)];
            %elimina linhas outliers
            td(find(outliers(:, c) == 1), :) = [];
            %reconstroi linhas
            sinal_outliers(:, c) = interp1(td(:, 1), td(:, 2), t, 'linear');
        end
    end
    
    plot(t, sinal_nan(:, 2), '-+', t, sinal_outliers(:, 2), '-o');
    legend('Serie temporal sem NaN 2', 'Serie temporal sem outliers 2', 2);
    title('Contagens em 48 horas');
    xlabel('Tempo (horas)');
    ylabel('Contagens');

    disp('Clique numa tecla para continuar');
    pause();
    close all;
    disp('exercicio 4.5: ');
    %ex 4.5   gera os graficos com as tendencias
    %trend de ordem 2
    disp('Modelo linear de 2º ordem');
    p1 = polyfit(t, sinal_outliers(:, 1), 2)
    p2 = polyfit(t, sinal_outliers(:, 2), 2)
    tr1 = polyval(p1, t);
    tr2 = polyval(p2, t);
    tr = [tr1, tr2];
    outliers_dt = sinal_outliers - tr;
    
    plot(t, tr, '-+');
    legend('Tendencia da Serie Temporal 1', 'Tendencia da Serie Temporal 2', 2);
    xlabel('Tempo (horas)');
    ylabel('Contagens');

    disp('Clique numa tecla para continuar');
    pause();
    close all;
    
    plot(t, outliers_dt, '-+');
    legend('Serie Temporal 1 sem as componentes de tendencia', 'Serie Temporal 2 sem as componentes de tendencia', 2);
    xlabel('Tempo (horas)');
    ylabel('Contagens');
    disp('Clique numa tecla para continuar');
    pause();
    close all;
    
    %ex 4.7
    disp('exercicio 4.7: ');
    ho = repmat((1:24)', 2, 1);
    sx = dummyvar(ho);
    bs = sx \ outliers_dt;
    st = sx * bs;
    sem_sazonalidade = outliers_dt - st;
    
    plot(t, st, '-+');
    legend('Sazonalidade da Serie Temporal 1', 'Sazonalidade da Serie Temporal 2', 2);
    xlabel('Tempo (horas)');
    ylabel('Contagens');
    disp('Clique numa tecla para continuar');
    pause();
    close all;
    
    plot(t, sem_sazonalidade, '-+');
    legend('S. T. 1 sem componentes de tendencia e sazonal', 'S. T. 2 sem componentes de tendencia e sazonal', 2);
    xlabel('Tempo (horas)');
    ylabel('Contagens');
    disp('Clique numa tecla para continuar');
    pause();
    close all;
    
    %ex 4.8
    disp('exercicio 4.8: ');
    irreg_d4 = sinal_outliers - st - tr;
    d4_irreg = sinal_outliers - irreg_d4;
    
    plot(t, irreg_d4);
    legend('componente irregular da Serie Temporal 1', 'componente irregular da Serie Temporal 2', 2);
    xlabel('Tempo (horas)');
    ylabel('Contagens');
    disp('Clique numa tecla para continuar');
    pause();
    close all;
    
    plot(t, d4_irreg);
    legend('S. T. 1 sem componente irregular', 'S. T. 2 sem componente irregular', 2);
    xlabel('Tempo (horas)');
    ylabel('Contagens');
    disp('Clique numa tecla para continuar');
    pause();
    close all;
    
    %ex 4.9
    disp('exercicio 4.9: ');
    b = [1, 1, 1] / 3;
    a = 1;
    y1 = filter(b,a,sinal_outliers(:,1));
    y2 = filter(b,a,sinal_outliers(:,2));
    y = [y1 y2];
    
    plot(t, sinal_outliers, '-+', t, y, '-o');
    legend('Serie Temporal 1 original', 'Serie Temporal 2 original', 'Serie Temporal 1 apos aplicacao do filtro', 'Serie Temporal 2 apos aplicacao do filtro', 4);
    xlabel('Tempo (horas)');
    ylabel('Contagens');
    disp('Clique numa tecla para continuar');
    pause();
    close all;
end