import matplotlib.pyplot as plt
import numpy as np
from scipy import interpolate

servers = ['Simple', 'Blocking', 'NonBlocking']
metrics = ['ClientTime', 'ServerTime', 'TaskTime']
params = ['ClientsNumber', 'ArraySize', 'Delay']

colors = ["red", "green", "blue"]

def get_data(i, metric, param):
    name = "results/logs/" + servers[i] + '_' + param + ".txt"
    input = open(name, "r")
    xs = []
    ys = []
    i = 0
    lines = input.readlines()
    while i != len(lines) and not(lines[i].startswith(metric)):
        i = i + 1
    if (i == len(lines)):
        return [1, 2, 3], [1, 2, 3]
    i = i + 1
    n = int(lines[i])
    for line in lines[i + 1 : i + n + 1]:
        xs.append(int(float(line.split(' ')[0])))
        ys.append(int(float(line.split(' ')[1][0 : -1])))
    return xs, ys

def build_plot(metric, param):
    print(metric, param)
    name = metric + '(' + param + ')'
    plt.title(name)
    plt.xlabel(param)
    plt.ylabel(metric)
    lines  = []

    for i in range(len(servers)):
        xs, ys = get_data(i, metric, param)
        print(xs)
        print(ys)
        x_coord = np.linspace(min(xs), max(xs))
        y_coord = interpolate.spline(xs, ys, x_coord)

        line, = plt.plot(x_coord, y_coord, color=colors[i])
        lines.append(line)

    plt.grid(True, linestyle='-', color='0.75')
    plt.legend(lines, servers, loc='best')

    plt.savefig('results/plots/' + name + '.png')
    plt.clf()

if __name__ == '__main__':
    for metric in metrics:
        for param in params:

            build_plot(metric, param)