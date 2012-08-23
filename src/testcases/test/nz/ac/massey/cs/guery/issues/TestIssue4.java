package test.nz.ac.massey.cs.guery.issues;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mvel2.ParserContext;
import org.mvel2.compiler.CompiledExpression;
import org.mvel2.compiler.ExpressionCompiler;

public class TestIssue4 {

	@Test
	public void test() {
		String expressionDef = "x is " + TestIssue4.class.getName();
		ParserContext ctx = new ParserContext();

		// compile
		CompiledExpression compiledExpression = new ExpressionCompiler(expressionDef).compile(ctx);
		
		List<String> roleNames = new ArrayList<String>();
		
		// gather input roles
		for (Object o:ctx.getInputs().keySet()) {
			roleNames.add((String)o);
		}
		
		assertEquals(1,roleNames.size());
		assertEquals("x",roleNames.get(0));
		
	}
	


}
