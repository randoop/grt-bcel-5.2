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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.bcel.classfile.JavaClass;

/** 
 * Utility class implementing a (typesafe) set of JavaClass objects.
 * Since JavaClass has no equals() method, the name of the class is
 * used for comparison.
 *
 * @version $Id$
 * @author <A HREF="mailto:m.dahm@gmx.de">M. Dahm</A> 
 * @see ClassStack
 */
public class ClassSet implements java.io.Serializable {

    private Map _map = new HashMap();


    public boolean add( JavaClass clazz ) {
        boolean result = false;
        if (!_map.containsKey(clazz.getClassName())) {
            result = true;
            _map.put(clazz.getClassName(), clazz);
        }
        return result;
    }


    public void remove( JavaClass clazz ) {
        _map.remove(clazz.getClassName());
    }


    public boolean empty() {
        return _map.isEmpty();
    }


    public JavaClass[] toArray() {
        Collection values = _map.values();
        JavaClass[] classes = new JavaClass[values.size()];
        values.toArray(classes);
        return classes;
    }


    public String[] getClassNames() {
        return (String[]) _map.keySet().toArray(new String[_map.keySet().size()]);
    }
}
