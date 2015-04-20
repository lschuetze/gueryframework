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

package test.nz.ac.massey.cs.guery.suite1;

import java.io.*;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;

import nz.ac.massey.cs.guery.io.xml.XMLMotifReader;

import org.junit.Test;

/**
 * Tests for XML schema.
 * @author jens dietrich
 *
 */
public class XMLSchemaValidationTests {
	
	public static String SCHEMA = "schema/guery.xsd";
	
	private void test(String name, boolean shouldFail) throws Exception {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(new File(SCHEMA));
        Validator validator = schema.newValidator();
        
        String src = "/test/nz/ac/massey/cs/guery/suite1/data/"+name;
        InputStream in = this.getClass().getResourceAsStream(src);
        
        Source source = new StreamSource(in);
        validator.validate(source);
        in.close();
        in = this.getClass().getResourceAsStream(src);
        new XMLMotifReader().read(in);
 
	}
	
	@Test
	public void test1() throws Exception {
		test("query1.xml",false);
	} 
	@Test
	public void test2() throws Exception {
		test("query2.xml",false);
	} 
	@Test
	public void test3() throws Exception {
		test("query3.xml",false);
	} 
	@Test
	public void test4() throws Exception {
		test("query4.xml",false);
	} 

	
	
} 

