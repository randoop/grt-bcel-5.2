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
package org.apache.bcel.util;

/**
 * Used for BCEL comparison strategy
 * 
 * @author <A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>
 * @version $Id$
 * @since 5.2
 */
public interface BCELComparator {

    /**
     * Compare two objects and return what THIS.equals(THAT) should return
     * 
     * @param THIS
     * @param THAT
     * @return true if and only if THIS equals THAT
     */
    public boolean equals( Object THIS, Object THAT );


    /**
     * Return hashcode for THIS.hashCode()
     * 
     * @param THIS
     * @return hashcode for THIS.hashCode()
     */
    public int hashCode( Object THIS );
}
