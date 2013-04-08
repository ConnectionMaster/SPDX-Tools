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
package org.spdx.html;

import org.spdx.rdfparser.InvalidSPDXAnalysisException;
import org.spdx.rdfparser.SPDXNonStandardLicense;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Source Auditor
 *
 */
public class ExtractedLicensingInfoContext {

	private Exception error = null;
	private SPDXNonStandardLicense license = null;
	
	/**
	 * @param e
	 */
	public ExtractedLicensingInfoContext(InvalidSPDXAnalysisException e) {
		this.error = e;
	}

	/**
	 * @param spdxNonStandardLicense
	 */
	public ExtractedLicensingInfoContext(
			SPDXNonStandardLicense spdxNonStandardLicense) {
		this.license = spdxNonStandardLicense;
	}
	
	public String licenseId() {
		if (this.license == null && this.error != null) {
			return "Error getting non-standard license: "+error.getMessage();
		}
		if (this.license != null) {
			return this.license.getId();
		} else {
			return null;
		}
	}
	
	public String extractedText() {
		if (this.license == null && this.error != null) {
			return "Error getting non-standard license: "+error.getMessage();
		}
		if (this.license != null) {
			return this.license.getText();
		} else {
			return null;
		}
	}
	
	public String comment() {
		if (this.license == null && this.error != null) {
			return "Error getting non-standard license: "+error.getMessage();
		}
		if (this.license != null) {
			return this.license.getComment();
		} else {
			return null;
		}
	}
	
	public String licenseName() {
		if (this.license == null && this.error != null) {
			return "Error getting non-standard license: "+error.getMessage();
		}
		if (this.license != null) {
			return this.license.getLicenseName();
		} else {
			return null;
		}
	}
	
	public List<String> crossReferenceUrls() {
		ArrayList<String> retval = new ArrayList<String>();
		if (this.license != null) {
			String[] crossRefUrls = this.license.getSourceUrls();
			if (crossRefUrls != null) {
				for (int i = 0; i < crossRefUrls.length; i++) {
					retval.add(crossRefUrls[i]);
				}
			}
		} else {
			if (this.error != null) {
				retval.add("Error getting extracted licensing info: "+this.error);
			}
		}
		return retval;
	}

}
