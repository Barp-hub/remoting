// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: hello.proto

package io.grpc.examples.helloworld;

public final class HelloWorldProto {
  private HelloWorldProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_helloworld_HelloRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_helloworld_HelloRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_helloworld_HelloReply_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_helloworld_HelloReply_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_helloworld_ChatMessage_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_helloworld_ChatMessage_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\013hello.proto\022\nhelloworld\"\034\n\014HelloReques" +
      "t\022\014\n\004name\030\001 \001(\t\"\035\n\nHelloReply\022\017\n\007message" +
      "\030\001 \001(\t\"\036\n\013ChatMessage\022\017\n\007message\030\001 \001(\t2\211" +
      "\001\n\007Greeter\022>\n\010SayHello\022\030.helloworld.Hell" +
      "oRequest\032\026.helloworld.HelloReply\"\000\022>\n\004Ch" +
      "at\022\027.helloworld.ChatMessage\032\027.helloworld" +
      ".ChatMessage\"\000(\0010\001B6\n\033io.grpc.examples.h" +
      "elloworldB\017HelloWorldProtoP\001\242\002\003HLWb\006prot" +
      "o3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_helloworld_HelloRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_helloworld_HelloRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_HelloRequest_descriptor,
        new java.lang.String[] { "Name", });
    internal_static_helloworld_HelloReply_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_helloworld_HelloReply_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_HelloReply_descriptor,
        new java.lang.String[] { "Message", });
    internal_static_helloworld_ChatMessage_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_helloworld_ChatMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_ChatMessage_descriptor,
        new java.lang.String[] { "Message", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
