@echo off

E:/program/protoc-2.6.1-win32/protoc --proto_path=E:/repository/github/remoting/learning/hello-google-rpc/src/main/resources/proto/ --java_out=E:/repository/github/remoting/learning/hello-google-rpc/src/main/java E:/repository/github/remoting/learning/hello-google-rpc/src/main/resources/proto/rpc.proto

pause & exit