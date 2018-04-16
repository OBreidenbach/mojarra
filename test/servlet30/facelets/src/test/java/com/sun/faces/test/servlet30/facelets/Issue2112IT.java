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

package com.sun.faces.test.servlet30.facelets;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class Issue2112IT {

    private String webUrl;
    private WebClient webClient;

    @Before
    public void setUp() {
        webUrl = System.getProperty("integration.url");
        webClient = new WebClient();
    }

    @After
    public void tearDown() {
        webClient.close();
    }
    
    @Test
    public void testOutputScript1() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "/faces/outputTextScript1.xhtml");
        HtmlTextArea input = (HtmlTextArea) page.getElementById("testForm:textarea");
        String inputValue = '"' + "?><script>alert('JSF Security hole!!')</script>";
        input.setText(inputValue);
        HtmlSubmitInput submit = (HtmlSubmitInput) page.getElementById("testForm:button");
        page = (HtmlPage) submit.click();
        assertTrue(page.asXml().contains("Put the following input and JavaScript will be executed."));
        assertTrue(input.getText().contains(inputValue));    }
    
    @Test
    public void testOutputScript2() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "/faces/outputTextScript2.xhtml");
        HtmlTextArea input = (HtmlTextArea) page.getElementById("testForm:textarea");
        String inputValue = "--><script>alert('JSF Security hole!!')</script>";
        input.setText(inputValue);
        HtmlSubmitInput submit = (HtmlSubmitInput) page.getElementById("testForm:button");
        page = (HtmlPage) submit.click();
        assertTrue(page.asXml().contains("Put the following input and JavaScript will be executed."));
        assertTrue(input.getText().contains(inputValue));
    }
}
