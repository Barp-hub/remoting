import sys
import os

import util.date_util

print('The command line arguments are:')
for i in sys.argv:
    print(i)

print('\n\nThe PYTHONPATH is', sys.path, '\n')

print(os.getcwd())

print(os.sep)


def hello(name):
    return 'hello, ' + name


print(util.date_util.hello('michael'))
