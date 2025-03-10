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

import org.apache.bcel.ExceptionConstants;

/** 
 * NEW - Create new object
 * <PRE>Stack: ... -&gt; ..., objectref</PRE>
 *
 * @version $Id$
 * @author  <A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>
 */
public class NEW extends CPInstruction implements LoadClass, AllocationInstruction,
        ExceptionThrower, StackProducer {

    /**
     * Empty constructor needed for the Class.newInstance() statement in
     * Instruction.readInstruction(). Not to be used otherwise.
     */
    NEW() {
    }


    public NEW(int index) {
        super(org.apache.bcel.Constants.NEW, index);
    }


    public Class[] getExceptions() {
        Class[] cs = new Class[2 + ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION.length];
        System.arraycopy(ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION, 0, cs, 0,
                ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION.length);
        cs[ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION.length + 1] = ExceptionConstants.INSTANTIATION_ERROR;
        cs[ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION.length] = ExceptionConstants.ILLEGAL_ACCESS_ERROR;
        return cs;
    }


    public ObjectType getLoadClassType( ConstantPoolGen cpg ) {
        return (ObjectType) getType(cpg);
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
        v.visitLoadClass(this);
        v.visitAllocationInstruction(this);
        v.visitExceptionThrower(this);
        v.visitStackProducer(this);
        v.visitTypedInstruction(this);
        v.visitCPInstruction(this);
        v.visitNEW(this);
    }
}
