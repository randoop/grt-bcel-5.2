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
 * TABLESWITCH - Switch within given range of values, i.e., low..high
 *
 * @version $Id$
 * @author  <A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>
 * @see SWITCH
 */
public class TABLESWITCH extends Select {

    /**
     * Empty constructor needed for the Class.newInstance() statement in
     * Instruction.readInstruction(). Not to be used otherwise.
     */
    TABLESWITCH() {
    }


    /**
     * @param match sorted array of match values, match[0] must be low value, 
     * match[match_length - 1] high value
     * @param targets where to branch for matched values
     * @param defaultTarget default branch
     */
    public TABLESWITCH(int[] match, InstructionHandle[] targets, InstructionHandle defaultTarget) {
        super(org.apache.bcel.Constants.TABLESWITCH, match, targets, defaultTarget);
        length = (short) (13 + match_length * 4); /* Alignment remainder assumed
         * 0 here, until dump time */
        fixed_length = length;
    }


    /**
     * Dump instruction as byte code to stream out.
     * @param out Output stream
     */
    public void dump( DataOutputStream out ) throws IOException {
        super.dump(out);
        int low = (match_length > 0) ? match[0] : 0;
        out.writeInt(low);
        int high = (match_length > 0) ? match[match_length - 1] : 0;
        out.writeInt(high);
        for (int i = 0; i < match_length; i++) {
            out.writeInt(indices[i] = getTargetOffset(targets[i]));
        }
    }


    /**
     * Read needed data (e.g. index) from file.
     */
    protected void initFromFile( ByteSequence bytes, boolean wide ) throws IOException {
        super.initFromFile(bytes, wide);
        int low = bytes.readInt();
        int high = bytes.readInt();
        match_length = high - low + 1;
        fixed_length = (short) (13 + match_length * 4);
        length = (short) (fixed_length + padding);
        match = new int[match_length];
        indices = new int[match_length];
        targets = new InstructionHandle[match_length];
        for (int i = low; i <= high; i++) {
            match[i - low] = i;
        }
        for (int i = 0; i < match_length; i++) {
            indices[i] = bytes.readInt();
        }
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
        v.visitVariableLengthInstruction(this);
        v.visitStackProducer(this);
        v.visitBranchInstruction(this);
        v.visitSelect(this);
        v.visitTABLESWITCH(this);
    }
}
