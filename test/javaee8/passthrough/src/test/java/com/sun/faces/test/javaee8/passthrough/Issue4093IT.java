/*
 * Copyright (c) 2017, 2018 Oracle and/or its affiliates. All rights reserved.
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sun.faces.test.javaee8.passthrough;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.sun.faces.test.junit.JsfTestRunner;
import static org.junit.Assert.assertFalse;

@RunWith(JsfTestRunner.class)
public class Issue4093IT {

    private String webUrl;
    private WebClient webClient;

    @Before
    public void setUp() {
        webUrl = System.getProperty("integration.url");
        webClient = new WebClient();
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.setJavaScriptTimeout(120000);
    }

    @Test
    public void testSpec4093RequiredWithoutPassthrough() throws Exception {

        HtmlPage page = webClient.getPage(webUrl + "issue4093.xhtml");
        HtmlTextInput input = (HtmlTextInput) page.getElementById("requiredwithoutpassthrough:value");
        input.setAttribute("value", "");


	HtmlSubmitInput button = (HtmlSubmitInput) page.getHtmlElementById("requiredwithoutpassthrough:submit");

        page = button.click();

        String output = page.asText();

        assertTrue(output.contains("requiredwithoutpassthrough:value: Validation Error: Value is required."));

    }
    
    @Test
    public void testSpec4093RequiredWithPassthrough() throws Exception {

        HtmlPage page = webClient.getPage(webUrl + "issue4093.xhtml");
        HtmlTextInput input = (HtmlTextInput) page.getElementById("requiredwithpassthrough:value");
        input.setAttribute("value", "");


	HtmlSubmitInput button = (HtmlSubmitInput) page.getHtmlElementById("requiredwithpassthrough:submit");

        page = button.click();

        String output = page.asText();

        assertFalse(output.contains("Please fill out this field"));

    }
    
    @Test
    public void testSpec4093ValidateWithoutPassthrough() throws Exception {

        HtmlPage page = webClient.getPage(webUrl + "issue4093.xhtml");
        HtmlTextInput input = (HtmlTextInput) page.getElementById("validatewithoutpassthrough:value");
        input.setAttribute("value", "");


	HtmlSubmitInput button = (HtmlSubmitInput) page.getHtmlElementById("validatewithoutpassthrough:submit");

        page = button.click();

        String output = page.asText();

        assertTrue(output.contains("validatewithoutpassthrough:value: Validation Error: Value is required."));

    }
    
    /**
     * This test should yield no JSF message response, as the inputText component
     * is using passthrough to HTML.
     * @throws Exception 
     */
    @Test
    public void testSpec4093ValidateWithPassthrough() throws Exception {

        HtmlPage page = webClient.getPage(webUrl + "issue4093.xhtml");
        HtmlTextInput input = (HtmlTextInput) page.getElementById("validatewithpassthrough:value");
        input.setAttribute("value", "");


	HtmlSubmitInput button = (HtmlSubmitInput) page.getHtmlElementById("validatewithpassthrough:submit");

        page = button.click();

        String output = page.asText();

        assertFalse(output.contains("Please fill out this field"));

    }
    
    /**
     * This test should yield no JSF message response, as the inputText component
     * is using passthrough to HTML.
     * @throws Exception 
     */
    @Test
    public void testSpec4093ValidateWithPassthroughId() throws Exception {

        HtmlPage page = webClient.getPage(webUrl + "issue4093.xhtml");
        HtmlTextInput input = (HtmlTextInput) page.getElementById("validatewithpassthrough:value");
        input.setAttribute("value", "");


	HtmlSubmitInput button = (HtmlSubmitInput) page.getHtmlElementById("validatewithpassthrough:submit");

        page = button.click();

        String output = page.asText();

        assertFalse(output.contains("Please fill out this field"));

    }

    @After
    public void tearDown() {
        webClient.close();
    }

}
