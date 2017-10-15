import module_using_sys

if __name__ == '__main__':
    print('This program is being run by itself')
else:
    print('I am being imported from another module')

print(module_using_sys.hello('world'))

print(dir(module_using_sys))
