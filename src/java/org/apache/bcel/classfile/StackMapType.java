/*
 * Copyright  2000-2004 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License. 
 *
 */
package org.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.apache.bcel.Constants;

/**
 * This class represents the type of a local variable or item on stack
 * used in the StackMap entries.
 *
 * @version $Id$
 * @author  <A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>
 * @see     StackMapEntry
 * @see     StackMap
 * @see     Constants
 */
public final class StackMapType implements Cloneable {

    private byte type;
    private int index = -1; // Index to CONSTANT_Class or offset
    private ConstantPool constant_pool;


    /**
     * Construct object from file stream.
     * @param file Input stream
     * @throws IOException
     */
    StackMapType(DataInputStream file, ConstantPool constant_pool) throws IOException {
        this(file.readByte(), -1, constant_pool);
        if (hasIndex()) {
            setIndex(file.readShort());
        }
        setConstantPool(constant_pool);
    }


    /**
     * @param type type tag as defined in the Constants interface
     * @param index index to constant pool, or byte code offset
     */
    public StackMapType(byte type, int index, ConstantPool constant_pool) {
        setType(type);
        setIndex(index);
        setConstantPool(constant_pool);
    }


    public void setType( byte t ) {
        if ((t < Constants.ITEM_Bogus) || (t > Constants.ITEM_NewObject)) {
            throw new RuntimeException("Illegal type for StackMapType: " + t);
        }
        type = t;
    }


    public byte getType() {
        return type;
    }


    public void setIndex( int t ) {
        index = t;
    }


    /** @return index to constant pool if type == ITEM_Object, or offset
     * in byte code, if type == ITEM_NewObject, and -1 otherwise
     */
    public int getIndex() {
        return index;
    }


    /**
     * Dump type entries to file.
     *
     * @param file Output file stream
     * @throws IOException
     */
    public final void dump( DataOutputStream file ) throws IOException {
        file.writeByte(type);
        if (hasIndex()) {
            file.writeShort(getIndex());
        }
    }


    /** @return true, if type is either ITEM_Object or ITEM_NewObject
     */
    public final boolean hasIndex() {
        return ((type == Constants.ITEM_Object) || (type == Constants.ITEM_NewObject));
    }


    private String printIndex() {
        if (type == Constants.ITEM_Object) {
            if (index < 0) {
                return ", class=<unknown>";
            }
            return ", class=" + constant_pool.constantToString(index, Constants.CONSTANT_Class);
        } else if (type == Constants.ITEM_NewObject) {
            return ", offset=" + index;
        } else {
            return "";
        }
    }


    /**
     * @return String representation
     */
    public final String toString() {
        return "(type=" + Constants.ITEM_NAMES[type] + printIndex() + ")";
    }


    /**
     * @return deep copy of this object
     */
    public StackMapType copy() {
        try {
            return (StackMapType) clone();
        } catch (CloneNotSupportedException e) {
        }
        return null;
    }


    /**
     * @return Constant pool used by this object.
     */
    public final ConstantPool getConstantPool() {
        return constant_pool;
    }


    /**
     * @param constant_pool Constant pool to be used for this object.
     */
    public final void setConstantPool( ConstantPool constant_pool ) {
        this.constant_pool = constant_pool;
    }
}
