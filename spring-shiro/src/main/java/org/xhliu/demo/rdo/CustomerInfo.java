// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: UserCustomer.proto

package org.xhliu.demo.rdo;

public final class CustomerInfo {
  private CustomerInfo() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_CustomerBasic_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CustomerBasic_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\022UserCustomer.proto\"c\n\rCustomerBasic\022\027\n" +
      "\ncustomerId\030\001 \001(\003H\000\210\001\001\022\031\n\014customerName\030\002" +
      " \001(\tH\001\210\001\001B\r\n\013_customerIdB\017\n\r_customerNam" +
      "eB$\n\022org.xhliu.demo.rdoB\014CustomerInfoP\001b" +
      "\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_CustomerBasic_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_CustomerBasic_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_CustomerBasic_descriptor,
        new java.lang.String[] { "CustomerId", "CustomerName", "CustomerId", "CustomerName", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}