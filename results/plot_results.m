clc,clear, close all;

x = (1:5);
y = [73.4; 81.1; 37.4; 172; 45];

figure;
title('Performance benchmark');
bar(x,y);
ylabel('Time[s]')
set(gca,'xticklabel', {'libcosim';'fmpy';'vico';'om';'fmigo'})
grid on;

print('figures/performance.eps', '-depsc')
