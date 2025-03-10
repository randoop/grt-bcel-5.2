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
 * Abstract super class for Fieldref and Methodref constants.
 *
 * @version $Id$
 * @author  <A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>
 * @see     ConstantFieldref
 * @see     ConstantMethodref
 * @see     ConstantInterfaceMethodref
 */
public abstract class ConstantCP extends Constant {

    /** References to the constants containing the class and the field signature
     */
    protected int class_index, name_and_type_index;


    /**
     * Initialize from another object.
     */
    public ConstantCP(ConstantCP c) {
        this(c.getTag(), c.getClassIndex(), c.getNameAndTypeIndex());
    }


    /**
     * Initialize instance from file data.
     *
     * @param tag  Constant type tag
     * @param file Input stream
     * @throws IOException
     */
    ConstantCP(byte tag, DataInputStream file) throws IOException {
        this(tag, file.readUnsignedShort(), file.readUnsignedShort());
    }


    /**
     * @param class_index Reference to the class containing the field
     * @param name_and_type_index and the field signature
     */
    protected ConstantCP(byte tag, int class_index, int name_and_type_index) {
        super(tag);
        this.class_index = class_index;
        this.name_and_type_index = name_and_type_index;
    }


    /** 
     * Dump constant field reference to file stream in binary format.
     *
     * @param file Output file stream
     * @throws IOException
     */
    public final void dump( DataOutputStream file ) throws IOException {
        file.writeByte(tag);
        file.writeShort(class_index);
        file.writeShort(name_and_type_index);
    }


    /**
     * @return Reference (index) to class this field or method belongs to.
     */
    public final int getClassIndex() {
        return class_index;
    }


    /**
     * @return Reference (index) to signature of the field.
     */
    public final int getNameAndTypeIndex() {
        return name_and_type_index;
    }


    /**
     * @param class_index points to Constant_class 
     */
    public final void setClassIndex( int class_index ) {
        this.class_index = class_index;
    }


    /**
     * @return Class this field belongs to.
     */
    public String getClass( ConstantPool cp ) {
        return cp.constantToString(class_index, Constants.CONSTANT_Class);
    }


    /**
     * @param name_and_type_index points to Constant_NameAndType
     */
    public final void setNameAndTypeIndex( int name_and_type_index ) {
        this.name_and_type_index = name_and_type_index;
    }


    /**
     * @return String representation.
     */
    public final String toString() {
        return super.toString() + "(class_index = " + class_index + ", name_and_type_index = "
                + name_and_type_index + ")";
    }
}
