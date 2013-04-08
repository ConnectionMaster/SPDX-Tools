/**
 * Copyright (c) 2011 Source Auditor Inc.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
*/
package org.spdx.rdfparser;


import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.StringWriter;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Gary O'Neall
 *
 */
public class TestSPDXStandardLicense {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCreate() throws InvalidSPDXAnalysisException {
		Model model = ModelFactory.createDefaultModel();
		String name = "name";
		String id = "AFL-3.0";
		String text = "text";
		String[] sourceUrls = new String[] {"source url1", "source url2"};
		String notes = "notes";
		String standardLicenseHeader = "Standard license header";
		String template = "template";
		SPDXStandardLicense stdl = new SPDXStandardLicense(name, id, text,
				sourceUrls, notes, standardLicenseHeader, template, true);
		Resource licResource = stdl.createResource(model);
		SPDXStandardLicense compLic = new SPDXStandardLicense(model, licResource.asNode());
		assertEquals(id, compLic.getId());
		assertEquals(text, compLic.getText());
		ArrayList<String> verify = stdl.verify();
		assertEquals(0, verify.size());
		verify = compLic.verify();
		assertEquals(0, verify.size());
/*		assertEquals(name, compLic.getName());
		assertEquals(sourceUrl, compLic.getSourceUrl());
		assertEquals(notes, compLic.getNotes());
		assertEquals(standardLicenseHeader, compLic.getStandardLicenseHeader());
		assertEquals(template, compLic.getTemplate());
*/
	}
	
	@Test
	public void testSetComment() throws InvalidSPDXAnalysisException {
		Model model = ModelFactory.createDefaultModel();
		String name = "name";
		String id = "AFL-3.0";
		String text = "text";
		String[] sourceUrls = new String[] {"source url2", "source url3"};
		String comments = "comments1";
		String comments2 = "comments2";
		String standardLicenseHeader = "Standard license header";
		String template = "template";
		SPDXStandardLicense stdl = new SPDXStandardLicense(name, id, text,
				sourceUrls, comments, standardLicenseHeader, template, true);
		Resource licResource = stdl.createResource(model);
		SPDXStandardLicense compLic = new SPDXStandardLicense(model, licResource.asNode());
		assertEquals(comments, compLic.getComment());
		
		compLic.setComment(comments2);
		assertEquals(comments2, compLic.getComment());
		SPDXStandardLicense compLic2 = new SPDXStandardLicense(model, licResource.asNode());
		assertEquals(comments2, compLic2.getComment());
		StringWriter writer = new StringWriter();
		model.write(writer);
		@SuppressWarnings("unused")
		String rdfstring = writer.toString();

		ArrayList<String> verify = stdl.verify();
		assertEquals(0, verify.size());
		verify = compLic.verify();
		assertEquals(0, verify.size());
	}
	
	@Test
	public void testSetIDandText() throws InvalidSPDXAnalysisException {
		Model model = ModelFactory.createDefaultModel();
		String name = "name";
		String id = "AFL-3.0";
		String text = "text";
		String[] sourceUrls = new String[] {"source url2", "source url3"};
		String notes = "notes";
		String standardLicenseHeader = "Standard license header";
		String template = "template";
		SPDXStandardLicense stdl = new SPDXStandardLicense(name, id, text,
				sourceUrls, notes, standardLicenseHeader, template, true);
		Resource licResource = stdl.createResource(model);
		SPDXStandardLicense compLic = new SPDXStandardLicense(model, licResource.asNode());
		assertEquals(id, compLic.getId());
		assertEquals(text, compLic.getText());
		
		String newID = "newID";
		String newText = "new Text";
		compLic.setId(newID);
		compLic.setText(newText);
		assertEquals(newID, compLic.getId());
		assertEquals(newText, compLic.getText());
		SPDXStandardLicense compLic2 = new SPDXStandardLicense(model, licResource.asNode());
		assertEquals(newID, compLic2.getId());
		assertEquals(newText, compLic2.getText());
		ArrayList<String> verify = stdl.verify();
		assertEquals(0, verify.size());
		verify = compLic.verify();
		assertEquals(0, verify.size());
	}
	
	@Test
	public void testCreateMultile() {
		// test to make sure if we create a node with the same id, we
		// get back the same URI
		Model model = ModelFactory.createDefaultModel();
		String name = "name";
		String id = "AFL-3.0";
		String text = "text";
		String[] sourceUrls1 = new String[] {"source url1", "source url2"};
		String[] sourceUrls2 = new String[] {"source url3", "source url4"};
		String notes = "notes";
		String standardLicenseHeader = "Standard license header";
		String template = "template";
		String id2 = "Apache-1.0";
		String name2 = "name2";
		
		try {
    		SPDXStandardLicense stdl = new SPDXStandardLicense(name, id, text,
    				sourceUrls1, notes, standardLicenseHeader, template, true);
    		Resource licResource = stdl.createResource(model);
    		
    		SPDXStandardLicense stdl3 = new SPDXStandardLicense(name2, id2, text,
    				sourceUrls2, notes, standardLicenseHeader, template, true);
    		@SuppressWarnings("unused")
    		Resource compResource3 = stdl3.createResource(model);
    		
    		SPDXStandardLicense stdl2 = new SPDXStandardLicense(name2, id, text,
    				sourceUrls2, notes, standardLicenseHeader, template, true);
            
    		Resource compResource = stdl2.createResource(model);
            assertTrue(licResource.equals(compResource));
            assertEquals(licResource.getURI(), compResource.getURI());		
		} catch (InvalidSPDXAnalysisException e) {
		    throw new RuntimeException(e);
		}
	}
	
	
}
