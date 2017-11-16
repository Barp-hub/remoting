data = ['a', 'b', 'c']
print(data)
data.append('d')
print(data)

data.sort(reverse=True)
print(data)
print(sorted(data, reverse=False))
print(data.__len__())
print(len(data))

for word in data:
    print(word)
