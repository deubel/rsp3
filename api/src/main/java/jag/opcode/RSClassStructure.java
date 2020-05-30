package jag.opcode;

import jag.RSNode;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface RSClassStructure extends RSNode {

    byte[][][] getMethodArgs();

    Method[] getMethods();

    Field[] getFields();

    int[] getErrors();

}