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
package org.apache.bcel.generic;

import java.io.DataOutputStream;
import java.io.IOException;
import org.apache.bcel.util.ByteSequence;

/** 
 * BIPUSH - Push byte on stack
 *
 * <PRE>Stack: ... -&gt; ..., value</PRE>
 *
 * @version $Id$
 * @author  <A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>
 */
public class BIPUSH extends Instruction implements ConstantPushInstruction {

    private byte b;


    /**
     * Empty constructor needed for the Class.newInstance() statement in
     * Instruction.readInstruction(). Not to be used otherwise.
     */
    BIPUSH() {
    }


    /** Push byte on stack
     */
    public BIPUSH(byte b) {
        super(org.apache.bcel.Constants.BIPUSH, (short) 2);
        this.b = b;
    }


    /**
     * Dump instruction as byte code to stream out.
     */
    public void dump( DataOutputStream out ) throws IOException {
        super.dump(out);
        out.writeByte(b);
    }


    /**
     * @return mnemonic for instruction
     */
    public String toString( boolean verbose ) {
        return super.toString(verbose) + " " + b;
    }


    /**
     * Read needed data (e.g. index) from file.
     */
    protected void initFromFile( ByteSequence bytes, boolean wide ) throws IOException {
        length = 2;
        b = bytes.readByte();
    }


    public Number getValue() {
        return new Integer(b);
    }


    /** @return Type.BYTE
     */
    public Type getType( ConstantPoolGen cp ) {
        return Type.BYTE;
    }


    /**
     * Call corresponding visitor method(s). The order is:
     * Call visitor methods of implemented interfaces first, then
     * call methods according to the class hierarchy in descending order,
     * i.e., the most specific visitXXX() call comes last.
     *
     * @param v Visitor object
     */
    public void accept( Visitor v ) {
        v.visitPushInstruction(this);
        v.visitStackProducer(this);
        v.visitTypedInstruction(this);
        v.visitConstantPushInstruction(this);
        v.visitBIPUSH(this);
    }
}
