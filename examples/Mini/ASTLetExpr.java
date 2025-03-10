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
/* Generated By:JJTree: Do not edit this line. ASTLetExpr.java */
/* JJT: 0.3pre1 */

package Mini;
import org.apache.bcel.generic.BasicType;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.ISTORE;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.LocalVariableGen;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.Type;

/**
 *
 * @version $Id$
 * @author <A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>
 */
public class ASTLetExpr extends ASTExpr implements org.apache.bcel.Constants {
  private ASTIdent[]  idents;
  private ASTExpr[]   exprs;
  private ASTExpr     body;

  // Generated methods
  ASTLetExpr(int id) {
    super(id);
  }

  ASTLetExpr(MiniParser p, int id) {
    super(p, id);
  }

  public static Node jjtCreate(MiniParser p, int id) {
    return new ASTLetExpr(p, id);
  }


  /**
   * Overrides ASTExpr.closeNode()
   * Cast children nodes to appropiate types.
   */
  public void closeNode() {
    int i, len_2 = children.length / 2; /* length must be a multiple of 
					 * two (ident = expr) + 1 (body expr) */
    idents = new ASTIdent[len_2];
    exprs  = new ASTExpr[len_2];

    // At least one assignment is enforced by the grammar
    for(i=0; i < len_2; i++) {
      idents[i] = (ASTIdent)children[i * 2];
      exprs[i]  = (ASTExpr)children[i * 2 + 1];
    }

    body = (ASTExpr)children[children.length - 1]; // Last expr is the body
    children=null; // Throw away old reference
  }

  /**
   * Overrides ASTExpr.traverse()
   */
  public ASTExpr traverse(Environment env) {
    this.env = env;
    
    // Traverse RHS exprs first, so no references to LHS vars are allowed
    for(int i=0; i < exprs.length; i++) {
        exprs[i] = exprs[i].traverse((Environment)env.clone());
    }
    
    // Put argument names into hash table aka. environment
    for(int i=0; i < idents.length; i++) {
      ASTIdent id    = idents[i];
      String   name  = id.getName();
      EnvEntry entry = env.get(name);

      if(entry != null) {
        MiniC.addError(id.getLine(), id.getColumn(),
        	       "Redeclaration of " + entry + ".");
    } else {
        env.put(new Variable(id));
    }
    }

    body = body.traverse(env);
    
    return this;
  }
  
  /**
   * Second pass
   * Overrides AstExpr.eval()
   * @return type of expression
   * @param expected type
   */
  public int eval(int expected) {
    //is_simple = true;

    for(int i=0; i < idents.length; i++) {
      int t = exprs[i].eval(T_UNKNOWN);
      
      idents[i].setType(t);
      //      is_simple = is_simple && exprs[i].isSimple();
    }

    return type = body.eval(expected);
  }

  /**
   * Fifth pass, produce Java code.
   */
  public void code(StringBuffer buf) {
    for(int i = 0; i < idents.length; i++) {
      String ident = idents[i].getName();
      int    t     = idents[i].getType(); // can only be int

      /* Idents have to be declared at start of function for later use.
       * Each name is unique, so there shouldn't be a problem in application.
       */
      exprs[i].code(buf);

      buf.append("    " + TYPE_NAMES[t] + " " + ident + " = " +
		 ASTFunDecl.pop() + ";\n");
    }

    body.code(buf);
  }

  /**
   * Fifth pass, produce Java byte code.
   */
  public void byte_code(InstructionList il, MethodGen method, ConstantPoolGen cp) {
    int size = idents.length;
    LocalVariableGen[] l = new LocalVariableGen[size];

    for(int i=0; i < size; i++) {
      String           ident = idents[i].getName();
      Variable         entry = (Variable)env.get(ident);
      Type             t     = BasicType.getType((byte)idents[i].getType());
      LocalVariableGen lg    = method.addLocalVariable(ident, t, null, null);
      int              slot  = lg.getIndex();

      entry.setLocalVariable(lg);
      InstructionHandle start = il.getEnd();
      exprs[i].byte_code(il, method, cp);
      start = (start == null)? il.getStart() : start.getNext();
      lg.setStart(start);
      il.append(new ISTORE(slot));     ASTFunDecl.pop();
      l[i] = lg;
    }

    body.byte_code(il, method, cp);
    InstructionHandle end = il.getEnd();
    for(int i=0; i < size; i++) {
        l[i].setEnd(end);
    }
  }

  public void dump(String prefix) {
    System.out.println(toString(prefix));

    for(int i=0; i < idents.length; i++) {
      idents[i].dump(prefix + " ");
      exprs[i].dump(prefix + " ");
    }

    body.dump(prefix + " ");
  }

}
