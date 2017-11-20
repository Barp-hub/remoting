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

for i in range(1, 10):
    print(i)

print(list(range(1, 20, 3)))
