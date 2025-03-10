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
 * Super class for the IFxxx family of instructions.
 *
 * @version $Id$
 * @author  <A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>
 */
public abstract class IfInstruction extends BranchInstruction implements StackConsumer {

    /**
     * Empty constructor needed for the Class.newInstance() statement in
     * Instruction.readInstruction(). Not to be used otherwise.
     */
    IfInstruction() {
    }


    /**
     * @param opcode opcode of instruction
     * @param target Target instruction to branch to
     */
    protected IfInstruction(short opcode, InstructionHandle target) {
        super(opcode, target);
    }


    /**
     * @return negation of instruction, e.g. IFEQ.negate() == IFNE
     */
    public abstract IfInstruction negate();
}
