/*
 * Copyright 2015 Jens Dietrich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 

package nz.ac.massey.cs.guery.mvel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nz.ac.massey.cs.guery.PropertyConstraint;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;
import org.mvel2.compiler.CompiledExpression;
import org.mvel2.compiler.ExpressionCompiler;


/**
 * Property constraint defined in MVEL2.
 * @author jens dietrich
 * @see http://mvel.codehaus.org/
 */
public class CompiledPropertyConstraint implements PropertyConstraint  {
	private String expression = null;
	private CompiledExpression compiledExpression = null;
	private List<String> roleNames = new ArrayList<String>();

	public CompiledPropertyConstraint(String expression) {
		super();
		this.expression = expression;
		this.parseExpression();
	}

	private void parseExpression() {
		ParserContext ctx = new ParserContext();

		// compile
		this.compiledExpression = new ExpressionCompiler(expression).compile(ctx);
		
		// gather input roles
		for (Object o:ctx.getInputs().keySet()) {
			this.roleNames.add((String)o);
		}
	}

	@Override
	public boolean check(Object vertexOrEdgeOrPath) {
		// TODO - reuse singleton map (pool)
		try {
			assert this.roleNames.size()==1;
			Map<String,Object> map = new HashMap<String,Object>(1);
			map.put(this.roleNames.get(0),vertexOrEdgeOrPath);
			Object result = MVEL.executeExpression(this.compiledExpression,map);
			return (Boolean)result;
			//return MVEL.evalToBoolean(this.expression,vertexOrEdgeOrPath);
		}
		catch (Exception x) {
			throw new IllegalArgumentException("Cannot evaluate " + this.expression + " for parameter " + vertexOrEdgeOrPath,x);
		}
	}
	@Override
	public boolean check(Map bindings) {
		try {
			return (Boolean) MVEL.executeExpression(this.compiledExpression,bindings);
			//return MVEL.evalToBoolean(this.expression,eov);
		}
		catch (Exception x) {
			throw new IllegalArgumentException("Cannot evaluate " + this.expression + " for parameter " + bindings,x);
		}
	}

	@Override
	public String getExpression() {
		return this.expression;
	}

	@Override
	public List<String> getRoles() {
		return this.roleNames;
	}

	@Override
	public String getFirstRole() {
		return this.roleNames.isEmpty()?null:this.roleNames.get(0);
	}

	@Override
	public boolean isSingleRole() {
		return this.roleNames.size()==1;
	}

	@Override
	public String toString() {
		return new StringBuffer()
			.append("mvel:\"")
			.append(this.expression)
			.append("\"")
			.toString();
	}




	
	
}
