clc,clear, close all;

libcosim = readmatrix('cosim.csv') ./ 1000;
libcosimCsv = readmatrix('cosimCsv.csv') ./ 1000;
%fmigo = readmatrix('fmigo.csv') ./ 1000;
%fmpy = readmatrix('fmpy.csv') ./ 1000;
%om = readmatrix('omsimulator.csv') ./ 1000;
vico = readmatrix('vico.csv') ./ 1000;
vicoCsv = readmatrix('vicoCsv.csv') ./ 1000;


x = (1:2);
y = [libcosim libcosimCsv vico vicoCsv];

figure;
boxplot(y, 0);
title('Gunnerus performance benchmark');
ylabel('Time[s]')
set(gca,'xticklabel', {'libcosim';'libcosimCsv';'vico';'vicoCsv';})
grid on;

print('figures/performance2.eps', '-depsc')
