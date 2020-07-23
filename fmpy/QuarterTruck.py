"""
Example that demonstrates the simulation of SSPs (System Structure and Parameterization)
"""

from fmpy.ssp.simulation import simulate_ssp
from fmpy.util import plot_result, write_csv


class Parameter:

    def __init__(self, name, value):
        self.name = name
        self.value = value


class ParameterSet:

    def __init__(self):
        self.parameters = []

    def add(self, name, value):
        self.parameters.append(Parameter(name, value))


def simulate_quarter_truck(show_plot=False):
    """Simulate QuarterTruck.ssp

    Parameters:
        show_plot     plot the results
    """

    ssp_filename = r'../QuarterTruck_FMPy.ssp'

    parameter_set = ParameterSet()
    parameter_set.add("chassis.C.mChassis", 400.0)
    parameter_set.add("chassis.C.kChassis", 15000.0)
    parameter_set.add("chassis.R.dChassis", 1000.0)
    parameter_set.add("wheel.C.mWheel", 40.0)
    parameter_set.add("wheel.C.kWheel", 150000.0)
    parameter_set.add("wheel.R.dWheel", 0.0)

    print("Simulating quarter-truck...")
    result = simulate_ssp(ssp_filename, stop_time=10, step_size=0.01, parameter_set=parameter_set)

    if show_plot:
        print("Plotting results...")
        plot_result(result, names=['chassis.zChassis', 'wheel.zWheel', 'ground.zGround'], window_title=ssp_filename)

    write_csv(r'..\results\fmpy\chassis.csv', result, columns=['chassis.zChassis'])
    write_csv(r'..\results\fmpy\wheel.csv', result,columns=['wheel.zWheel'])

    print('Done.')

    return result


if __name__ == '__main__':

    simulate_quarter_truck()
