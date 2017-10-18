# encoding:UTF-8
# import urllib.request
#
# url = "http://www.baidu.com"
# data = urllib.request.urlopen(url).read()
# data = data.decode('UTF-8')
# print(data)

from urllib import request
from urllib import parse
import json
import chardet

if __name__ == "__main__":
    response = request.urlopen("http://www.baidu.com")

    print("geturl打印信息：%s" % (response.geturl()))
    print('**********************************************')
    print("info打印信息：%s" % (response.info()))
    print('**********************************************')
    print("getcode打印信息：%s" % (response.getcode()))

    html = response.read()
    charset = chardet.detect(html)
    print(charset)
    html = html.decode(charset['encoding'])
    print(html)
