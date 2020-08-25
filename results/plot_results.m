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

fmigo = readmatrix("fmigo/results.csv");
t_fmigo = fmigo(:,1);
chassis_fmigo = fmigo(:,3);
wheel_fmigo = fmigo(:,6);

omsimulator = readmatrix("omsimulator/results.csv");
omsimulator(2:2:end,:) = [];
omsimulator(end,:) = [];
t_om = omsimulator(:,1);
chassis_om = omsimulator(:,99);
wheel_om = omsimulator(:,46);

wheel_ref_rmse = wheel_ref(10:10:end,:);
wheel_ref_rmse(501) = wheel_ref(end);

chassis_ref_rmse = chassis_ref(10:10:end,:);
chassis_ref_rmse(501) = chassis_ref(end);

wheel_cosim_rmse = sqrt(mean(wheel_ref_rmse-wheel_cosim(:,3)).^2);
wheel_vico_rmse = sqrt(mean(wheel_ref_rmse-wheel_vico(:,3)).^2);
wheel_fmigo_rmse = sqrt(mean(wheel_ref_rmse-wheel_fmigo).^2);
wheel_fmpy_rmse = sqrt(mean(wheel_ref_rmse-wheel_fmpy(:,2)).^2);
wheel_oms_rmse = sqrt(mean(wheel_ref_rmse-wheel_om).^2);

%% Chassis

figure;
hold on;
grid on;
title("zChassis")
plot(t_ref, chassis_ref)
plot(t_fmigo, chassis_fmigo)
plot(chassis_fmpy(:,1), chassis_fmpy(:,2))
plot(chassis_vico(:,1), chassis_vico(:,3))
plot(chassis_cosim(:,1), chassis_cosim(:,3))
plot(t_om(:,1), chassis_om)

xlabel('Time[s]')
ylabel('Vertical displacment[m]')

legend("ref", "fmigo", "fmpy", "vico", "libcosim", "om")
legend('Location', 'southeast')

print('figures/chassis_100hz.eps', '-depsc')

%% Wheel

figure;
hold on;
grid on;
title("zWheel")
plot(t_ref, wheel_ref)
plot(t_fmigo, wheel_fmigo)
plot(wheel_fmpy(:,1), wheel_fmpy(:,2))
plot(wheel_vico(:,1), wheel_vico(:,3))
plot(wheel_cosim(:,1), wheel_cosim(:,3))
plot(t_om(:,1), wheel_om)

xlabel('Time[s]')
ylabel('Vertical displacment[m]')

legend("ref", "fmigo", "fmpy", "vico", "libcosim", "om")
legend('Location', 'southeast')

print('figures/wheel_100hz.eps', '-depsc')

%% Wheel detail

figure;
hold on;
grid on;
title("zWheel")
plot(t_ref, wheel_ref)
plot(t_fmigo, wheel_fmigo)
plot(wheel_fmpy(:,1), wheel_fmpy(:,2))
plot(wheel_vico(:,1), wheel_vico(:,3))
plot(wheel_cosim(:,1), wheel_cosim(:,3))
plot(t_om(:,1), wheel_om)

xlim([0, 0.95]);
xlabel('Time[s]')
ylabel('Vertical displacment[m]')

legend("ref", "fmigo", "fmpy", "vico", "libcosim", "om")

print('figures/wheel_detail_100hz.eps', '-depsc')
