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

/** 
 * ICONST - Push value between -1, ..., 5, other values cause an exception
 *
 * <PRE>Stack: ... -&gt; ..., </PRE>
 *
 * @version $Id$
 * @author  <A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>
 */
public class ICONST extends Instruction implements ConstantPushInstruction, TypedInstruction {

    private int value;


    /**
     * Empty constructor needed for the Class.newInstance() statement in
     * Instruction.readInstruction(). Not to be used otherwise.
     */
    ICONST() {
    }


    public ICONST(int i) {
        super(org.apache.bcel.Constants.ICONST_0, (short) 1);
        if ((i >= -1) && (i <= 5)) {
            opcode = (short) (org.apache.bcel.Constants.ICONST_0 + i); // Even works for i == -1
        } else {
            throw new ClassGenException("ICONST can be used only for value between -1 and 5: " + i);
        }
        value = i;
    }


    public Number getValue() {
        return new Integer(value);
    }


    /** @return Type.INT
     */
    public Type getType( ConstantPoolGen cp ) {
        return Type.INT;
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
        v.visitICONST(this);
    }
}
