/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package com.sun.faces.test.servlet30.systest;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import static junit.framework.TestCase.assertEquals;


public class SelectOneValueExpressionHideNoSelectionITCase extends HtmlUnitFacesITCase {

    private HtmlPage page;

	/**
     * Construct a new instance of this test case.
     *
     * @param name Name of the test case
     */
    public SelectOneValueExpressionHideNoSelectionITCase(String name) {
        super(name);
    }


    /**
     * Set up instance variables required by this test case.
     */
    public void setUp() throws Exception {
        super.setUp();
        this.page = getPage(getPath());
    }


	protected String getPath() {
		return "/faces/standard/selectOneValueExpressionHideNoSelectionOption.xhtml";
	}


    /**
     * Return the tests included in this test suite.
     */
    public static Test suite() {
        return (new TestSuite(SelectOneValueExpressionHideNoSelectionITCase.class));
    }


    /**
     * Tear down instance variables required by this test case.
     */
    public void tearDown() {
        super.tearDown();
    }


    // ------------------------------------------------------------ Test Methods


    public void testHideNoSelectionOptionIsAValueExpression() throws Exception {
        HtmlSelect select = (HtmlSelect) this.page.getElementById("f:selectItemNoSelectedValue");
        //validate initial page
        assertEquals(4, select.getOptionSize());
        
        HtmlInput button = (HtmlInput) this.page.getElementById("f:command");
        select.getOptionByValue("Pippin").click();
        this.page = button.click();
        
        select = (HtmlSelect) this.page.getElementById("f:selectItemNoSelectedValue");
        assertEquals(5, select.getOptionSize());
    }
    

}
