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

package com.sun.faces.test.javaee6web.el;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import org.junit.*;
import static org.junit.Assert.*;

public class Issue2397IT {

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
    public void testExceptionDuringValueChangeEL() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "faces/exceptionDuringValueChangeEL.xhtml");
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        HtmlSubmitInput button = (HtmlSubmitInput) page.getElementById("submit");
        page = button.click();
        assertTrue(page.asXml().contains("java.lang.NullPointerException"));
    }

    @Test
    public void testExceptionDuringMethodExpressionEL() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "faces/exceptionDuringMethodExpressionEL.xhtml");
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        HtmlSubmitInput input = (HtmlSubmitInput) page.getElementById("submit");
        page = input.click();
        assertTrue(page.asXml().contains("IllegalStateException"));
    }

    @Test
    public void testAbortDuringMethodExpressionEL() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "faces/abortDuringMethodExpressionEL.xhtml");
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        HtmlSubmitInput input = (HtmlSubmitInput) page.getElementById("submit");
        page = input.click();
        assertEquals(200, page.getWebResponse().getStatusCode());
    }
}
