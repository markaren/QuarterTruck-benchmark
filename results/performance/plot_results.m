clc,clear, close all;


%fmigo = readmatrix('fmigo.csv') ./ 1000;
%fmpy = readmatrix('fmpy.csv') ./ 1000;
%om = readmatrix('omsimulator.csv') ./ 1000;
%omMat = readmatrix('omsimulatorMat.csv') ./ 1000;
%omCsv = readmatrix('omsimulatorCsv.csv') ./ 1000;
vico = readmatrix('vico.csv') ./ 1000;
vicoCsv = readmatrix('vicoCsv.csv') ./ 1000;
vicoSingle = readmatrix('vicoSingle.csv') ./ 1000;
vicoSingleCsv = readmatrix('vicoSingleCsv.csv') ./ 1000;
cosim = readmatrix('cosim.csv') ./ 1000;
cosimCsv = readmatrix('cosimCsv.csv') ./ 1000;


x = (1:6);
y = [cosim cosimCsv vico vicoCsv vicoSingle];

figure;
hold on;
boxplot(y, 0);
title('Gunnerus performance benchmark');
ylabel('Time[s]')
set(gca,'xticklabel', {'cosim';'cosimCsv';'vico';'vicoCsv';'vicoSingle'})
grid on;
%set(gca,'Ytick',0:10:170)


print('../figures/performance2.eps', '-depsc')
