clc,clear, close all;

ref = readmatrix("/ref.csv");
t_ref = ref(:,1);
idx = find(ref(:,1) == 5);
t_ref = t_ref(1:idx);
chassis_ref = ref(:,2);
chassis_ref = chassis_ref(1:idx);
wheel_ref = ref(:,3);
wheel_ref = wheel_ref(1:idx);

chassis_cosim = readmatrix("libcosim/chassis.csv");
wheel_cosim = readmatrix("libcosim/wheel.csv");

chassis_vico = readmatrix("vico/chassis.csv");
wheel_vico = readmatrix("vico/wheel.csv");

chassis_fmpy = readmatrix("fmpy/chassis.csv");
wheel_fmpy = readmatrix("fmpy/wheel.csv");
wheel_fmpy(end+1,:) = [5 0.6021];

fmigo = readmatrix("fmigo/results.csv");
t_fmigo = fmigo(:,1);
chassis_fmigo = fmigo(:,3);
wheel_fmigo = fmigo(:,6);

omsimulator = readmatrix("omsimulator/results.csv");
omsimulator(2:2:end,:) = [];
t_oms = omsimulator(:,1);
chassis_oms = omsimulator(:,99);
wheel_oms = omsimulator(:,46);

wheel_cosim_rmse = sqrt(mean((wheel_ref-wheel_cosim(:,3)).^2));
wheel_vico_rmse = sqrt(mean((wheel_ref-wheel_vico(:,3)).^2));
wheel_fmigo_rmse = sqrt(mean((wheel_ref-wheel_fmigo).^2));
wheel_fmpy_rmse = sqrt(mean((wheel_ref-wheel_fmpy(:,2)).^2));
wheel_oms_rmse = sqrt(mean((wheel_ref-wheel_oms).^2));

%% Chassis

figure;
hold on;
grid on;
title("Vertical displacement of the chassis")
plot(t_ref, chassis_ref)
plot(t_fmigo, chassis_fmigo)
plot(chassis_fmpy(:,1), chassis_fmpy(:,2))
plot(chassis_vico(:,1), chassis_vico(:,3))
plot(chassis_cosim(:,1), chassis_cosim(:,3))
plot(t_oms(:,1), chassis_oms)

xlabel('Time[s]')
ylabel('Displacement[m]')

legend("ref", "fmigo", "fmpy", "vico", "libcosim", "om");
legend('Location', 'southeast')

print('figures/chassis_1000hz.eps', '-depsc')

%% Wheel

figure;
hold on;
grid on;
title("Vertical displacement of the wheel")
plot(t_ref, wheel_ref)
plot(t_fmigo, wheel_fmigo)
plot(wheel_fmpy(:,1), wheel_fmpy(:,2))
plot(wheel_vico(:,1), wheel_vico(:,3))
plot(wheel_cosim(:,1), wheel_cosim(:,3))
plot(t_oms(:,1), wheel_oms)

xlabel('Time[s]')
ylabel('Displacement[m]')

legend("ref", "fmigo", "fmpy", "vico", "libcosim", "om")
legend('Location', 'southeast')

print('figures/wheel_1000hz.eps', '-depsc')
