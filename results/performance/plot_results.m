clc,clear, close all;


fmigo = readmatrix('fmigo.csv') ./ 1000;
fmpy = readmatrix('fmpy.csv') ./ 1000;
om = readmatrix('omsimulator.csv') ./ 1000;
omMat = readmatrix('omsimulatorMat.csv') ./ 1000;
omCsv = readmatrix('omsimulatorCsv.csv') ./ 1000;
vico = readmatrix('vico.csv') ./ 1000;
vicoCsv = readmatrix('vicoCsv.csv') ./ 1000;
cosim = readmatrix('cosim.csv') ./ 1000;
cosimCsv = readmatrix('cosimCsv.csv') ./ 1000;


x = (1:5);
y = [fmigo fmpy cosim cosimCsv om omMat omCsv vico vicoCsv];

figure;
hold on;
boxplot(y, 0);
title('Quarter-truck performance benchmark');
ylabel('Time[s]')
set(gca,'xticklabel', {'fmigo';'fmpy';'cosim';'cosimCsv';'om';'omMat';'omCsv';'vico';'vicoCsv'})
grid on;
set(gca,'Ytick',0:10:170)


print('../figures/performance.eps', '-depsc')
