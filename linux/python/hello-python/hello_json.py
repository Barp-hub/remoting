import json

numbers = [2, 3, 5, 7, 11, 13]
filename = 'numbers.json'


def dump_to_file():
    with open(filename, 'w') as f_obj:
        json.dump(numbers, f_obj)


dump_to_file()
