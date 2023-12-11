package com.example.demo;

/*
 * RunTest.java
 *
 * Author: Dr. Ho
 *
 * YXSmart - Java test for SE
 *
 */

public class RunTest {
    public static void main(String[] args) {
        ProtocolTest test = new ProtocolTest();

        //Valid msg
        byte[] testMessage = {0x02, // STX
                0x31, 0x32, 0x10, 0x02, 0x33, // Data 31 32 02 33
                0x03, // ETX
                0x31 ^ 0x32 ^ 0x02 ^ 0x33 ^ 0x03}; // LRC calculated from the data (with the DLE removed) plus the ETX

        test.refresh(testMessage);
        System.out.println("msg:");
        System.out.println("STX at:" + test.getSTX() + " ETX at:" + test.getETX() + " is Message Valid:" + (((test.isValid()) ? "Y" : "N")));

        System.out.println();


        //Incomplete message (不完整msg)
        //im1: 1 valid msg followed by 1 incomplete msg.
        byte[] im1 = {0x02, // STX
                0x31, 0x32, 0x10, 0x02, 0x33, // Data 31 32 02 33
                0x03, // ETX
                0x31 ^ 0x32 ^ 0x02 ^ 0x33 ^ 0x03, //LRC
                0x02, // Another STX_2
                0x31, 0x32}; // Another data_2

        test.refresh(im1);
        System.out.println("im1:");
        System.out.println("STX at:" + test.getSTX() + " ETX at:" + test.getETX() + " is Message Valid:" + (((test.isValid()) ? "Y" : "N")));

        //im2: 1 incomplete msg followed by 1 complete msg
        byte[] im2 = {0x02, //STX
                0x5, // Data
                0x02, // STX_2
                0x31, 0x32, // data_2
                0x03, //ETX_2
                0x31 ^ 0x32 ^ 0x03}; //LRC_2

        test.refresh(im2);
        System.out.println("im2:");
        System.out.println("STX at:" + test.getSTX() + " ETX at:" + test.getETX() + " is Message Valid:" + (((test.isValid()) ? "Y" : "N")));

        //im3: 1 incomplete msg followed by 1 imcomplete msg
        byte[] im3 = {0x10, 0x02, 0x5, // Data
                0x03, // ETX
                0x02 ^ 0x05 ^ 0x03, //LRC
                0x02, // STX_2
                0x31, 0x32, // data_2
                0x03}; //ETX_2

        test.refresh(im3);
        System.out.println("im3:");
        System.out.println("STX at:" + test.getSTX() + " ETX at:" + test.getETX() + " is Message Valid:" + (((test.isValid()) ? "Y" : "N")));

        //im4: 1 valid msg followed by an incomplete msg, where it does not contain a STX
        byte[] im4 = {0x02, // STX
                0x31, 0x32, 0x10, 0x03, 0x33, // Data
                0x03, // ETX
                0x31 ^ 0x32 ^ 0x03 ^ 0x33 ^ 0x03, //LRC
                0x31, 0x32, 0x10, 0x03, 0x33, // data_2
                0x03, // ETX
                0x31 ^ 0x32 ^ 0x03 ^ 0x33 ^ 0x03}; //LRC

        test.refresh(im4);
        System.out.println("im4:");
        System.out.println("STX at:" + test.getSTX() + " ETX at:" + test.getETX() + " is Message Valid:" + (((test.isValid()) ? "Y" : "N")));


        System.out.println();

        //Multiple messages
        byte[] mm1 = {0x02, // STX
                0x31, 0x32, 0x10, 0x02, 0x33, // Data 31 32 02 33
                0x03, // ETX
                0x31 ^ 0x32 ^ 0x02 ^ 0x33 ^ 0x03, //LRC
                0x02, // Another STX_2
                0x31, 0x32, 0x10, 0x02, 0x33, // Another data_2
                0x03, // ETX_1
                0x31 ^ 0x32 ^ 0x02 ^ 0x33 ^ 0x03}; //LRC_2

        test.refresh(mm1);
        System.out.println("mm1:");
        System.out.println("STX at:" + test.getSTX() + " ETX at:" + test.getETX() + " is Message Valid:" + (((test.isValid()) ? "Y" : "N")));

        System.out.println();

        //Random data
        byte[] rd1 = {0x02, // STX
                0x31, 0x32, 0x10, 0x02, 0x33, // Data 31 32 02 33
                0x03, // ETX
                0x31 ^ 0x32 ^ 0x02 ^ 0x33 ^ 0x03, //LRC
                0x03, 0x05, //Random data
                0x02, // STX_2
                0x31, 0x32, 0x10, 0x02, 0x33, // data_2
                0x03, // ETX_1
                0x31 ^ 0x32 ^ 0x02 ^ 0x33 ^ 0x03, //LRC_2
                0x06, 0x07}; //Random data

        test.refresh(rd1);
        System.out.println("rd1:");
        System.out.println("STX at:" + test.getSTX() + " ETX at:" + test.getETX() + " is Message Valid:" + (((test.isValid()) ? "Y" : "N")));

        System.out.println();

        //Not Valid msg
        //nvm1: Data not correctly delimited, return the most recent valid msg
        byte[] nvm1 = {0x02, // STX
                0x31, 0x32, 0x10, 0x02, 0x33, // Data
                0x03, // ETX
                0x31 ^ 0x32 ^ 0x02 ^ 0x33 ^ 0x03, //LRC
                0x02, // STX_2
                0x31, 0x32, 0x02, 0x33, // data_2
                0x03, // ETX_1
                0x31 ^ 0x32 ^ 0x02 ^ 0x33 ^ 0x03}; //LRC_2

        test.refresh(nvm1);
        System.out.println("nvm1:");
        System.out.println("STX at:" + test.getSTX() + " ETX at:" + test.getETX() + " is Message Valid:" + (((test.isValid()) ? "Y" : "N")));


        //nvm2: LRC not correct
        byte[] nvm2 = {0x02, // STX
                0x31, 0x32, 0x10, 0x02, 0x33, // Data
                0x03, // ETX
                0x31 ^ 0x32 ^ 0x02 ^ 0x33 ^ 0x03, //LRC
                0x02, // STX_2
                0x31, 0x32, 0x10, 0x02, 0x33, // data_2
                0x03, // ETX_1
                0x31 ^ 0x32 ^ 0x02 ^ 0x33}; //LRC_2

        test.refresh(nvm2);
        System.out.println("nvm2:");
        System.out.println("STX at:" + test.getSTX() + " ETX at:" + test.getETX() + " is Message Valid:" + (((test.isValid()) ? "Y" : "N")));

        //err1: STX 前有其它Data，并且该Data以0x10结束
        byte[] err1 = {
                0x10, 0x10, //导致错误
                0x02, // STX
                0x31, 0x32, 0x10, 0x02, 0x33, // Data
                0x03, // ETX
                0x31 ^ 0x32 ^ 0x02 ^ 0x33 ^ 0x03, //LRC
                0x02, // STX_2
                0x31, 0x32, 0x10, 0x02, 0x03,// data_2
                0x03, // ETX_2
                0x31 ^ 0x32 ^ 0x02 ^ 0x33}; //LRC_2

        test.refresh(err1);
        System.out.println("err1:");
        System.out.println("STX at:" + test.getSTX() + " ETX at:" + test.getETX() + " is Message Valid:" + (((test.isValid()) ? "Y" : "N")));
    }
}

