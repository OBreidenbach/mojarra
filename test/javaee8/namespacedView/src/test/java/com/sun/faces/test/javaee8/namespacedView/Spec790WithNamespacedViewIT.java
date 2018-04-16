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

package com.sun.faces.test.javaee8.namespacedView;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.sun.faces.test.htmlunit.IgnoringIncorrectnessListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

public class Spec790WithNamespacedViewIT {

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
    public void testSpec790WithNamespacedView() throws Exception {
        webClient.setIncorrectnessListener(new IgnoringIncorrectnessListener());

        HtmlPage page = webClient.getPage(webUrl + "spec790WithNamespacedView.xhtml");
        String namingContainerPrefix = page.getHead().getId().split("(?<=:)", 2)[0];
        HtmlForm form1 = (HtmlForm) page.getHtmlElementById(namingContainerPrefix + "form1");
        HtmlInput form1ViewState = (HtmlInput) form1.getInputByName(namingContainerPrefix + "javax.faces.ViewState");
        HtmlForm form2 = (HtmlForm) page.getHtmlElementById(namingContainerPrefix + "form2");
        HtmlInput form2ViewState = (HtmlInput) form2.getInputByName(namingContainerPrefix + "javax.faces.ViewState");
        HtmlForm form3 = (HtmlForm) page.getHtmlElementById(namingContainerPrefix + "form3");
        HtmlInput form3ViewState = (HtmlInput) form3.getInputByName(namingContainerPrefix + "javax.faces.ViewState");
        assertTrue(!form1ViewState.getValueAttribute().isEmpty());
        assertTrue(!form2ViewState.getValueAttribute().isEmpty());
        assertTrue(!form3ViewState.getValueAttribute().isEmpty());

        HtmlSubmitInput form1Button = (HtmlSubmitInput) page.getHtmlElementById(namingContainerPrefix + "form1:button");
        page = form1Button.click();
        webClient.waitForBackgroundJavaScript(60000);
        namingContainerPrefix = page.getHead().getId().split("(?<=:)", 2)[0];
        form1 = (HtmlForm) page.getHtmlElementById(namingContainerPrefix + "form1");
        form1ViewState = (HtmlInput) form1.getInputByName(namingContainerPrefix + "javax.faces.ViewState");
        form2 = (HtmlForm) page.getHtmlElementById(namingContainerPrefix + "form2");
        form2ViewState = (HtmlInput) form2.getInputByName(namingContainerPrefix + "javax.faces.ViewState");
        form3 = (HtmlForm) page.getHtmlElementById(namingContainerPrefix + "form3");
        form3ViewState = (HtmlInput) form3.getInputByName(namingContainerPrefix + "javax.faces.ViewState");
        assertTrue(!form1ViewState.getValueAttribute().isEmpty());
        assertTrue(!form2ViewState.getValueAttribute().isEmpty());
        assertTrue(!form3ViewState.getValueAttribute().isEmpty());

        HtmlAnchor form2Link = (HtmlAnchor) page.getHtmlElementById(namingContainerPrefix + "form2:link");
        page = form2Link.click();
        webClient.waitForBackgroundJavaScript(60000);
        namingContainerPrefix = page.getHead().getId().split("(?<=:)", 2)[0];
        form1 = (HtmlForm) page.getHtmlElementById(namingContainerPrefix + "form1");
        form1ViewState = (HtmlInput) form1.getInputByName(namingContainerPrefix + "javax.faces.ViewState");
        form2 = (HtmlForm) page.getHtmlElementById(namingContainerPrefix + "form2");
        form2ViewState = (HtmlInput) form2.getInputByName(namingContainerPrefix + "javax.faces.ViewState");
        form3 = (HtmlForm) page.getHtmlElementById(namingContainerPrefix + "form3");
        form3ViewState = (HtmlInput) form3.getInputByName(namingContainerPrefix + "javax.faces.ViewState");
        assertTrue(!form1ViewState.getValueAttribute().isEmpty());
        assertTrue(!form2ViewState.getValueAttribute().isEmpty());
        assertTrue(!form3ViewState.getValueAttribute().isEmpty());

        HtmlAnchor form3Link = (HtmlAnchor) page.getHtmlElementById(namingContainerPrefix + "form3:link");
        page = form3Link.click();
        webClient.waitForBackgroundJavaScript(60000);
        namingContainerPrefix = page.getHead().getId().split("(?<=:)", 2)[0];
        form1 = (HtmlForm) page.getHtmlElementById(namingContainerPrefix + "form1");
        form1ViewState = (HtmlInput) form1.getInputByName(namingContainerPrefix + "javax.faces.ViewState");
        form2 = (HtmlForm) page.getHtmlElementById(namingContainerPrefix + "form2");
        form2ViewState = (HtmlInput) form2.getInputByName(namingContainerPrefix + "javax.faces.ViewState");
        form3 = (HtmlForm) page.getHtmlElementById(namingContainerPrefix + "form3");
        form3ViewState = (HtmlInput) form3.getInputByName(namingContainerPrefix + "javax.faces.ViewState");
        assertTrue(!form1ViewState.getValueAttribute().isEmpty());
        assertTrue(!form2ViewState.getValueAttribute().isEmpty());
        assertTrue(!form3ViewState.getValueAttribute().isEmpty());
    }

    @Test
    @Ignore // fails due to https://sourceforge.net/p/htmlunit/bugs/1815 TODO enable when HtmlUnit 2.24 is final
    public void testSpec790WithNamespacedViewAjaxNavigation() throws Exception {
        webClient.setIncorrectnessListener(new IgnoringIncorrectnessListener());

        HtmlPage page = webClient.getPage(webUrl + "spec790WithNamespacedViewAjaxNavigation.xhtml");
        String namingContainerPrefix = page.getHead().getId().split("(?<=:)", 2)[0];
        HtmlForm form = (HtmlForm) page.getHtmlElementById(namingContainerPrefix + "form");
        HtmlInput formViewState = (HtmlInput) form.getInputByName(namingContainerPrefix + "javax.faces.ViewState");
        assertTrue(!formViewState.getValueAttribute().isEmpty());

        HtmlSubmitInput button = (HtmlSubmitInput) page.getHtmlElementById(namingContainerPrefix + "form:button");
        page = button.click();
        webClient.waitForBackgroundJavaScript(60000);
        namingContainerPrefix = page.getHead().getId().split("(?<=:)", 2)[0];
        HtmlForm form1 = (HtmlForm) page.getHtmlElementById(namingContainerPrefix + "form1");
        HtmlInput form1ViewState = (HtmlInput) form1.getInputByName(namingContainerPrefix + "javax.faces.ViewState");
        HtmlForm form2 = (HtmlForm) page.getHtmlElementById(namingContainerPrefix + "form2");
        HtmlInput form2ViewState = (HtmlInput) form2.getInputByName(namingContainerPrefix + "javax.faces.ViewState");
        assertTrue(!form1ViewState.getValueAttribute().isEmpty());
        assertTrue(!form2ViewState.getValueAttribute().isEmpty());
    }

    @After
    public void tearDown() {
        webClient.close();
    }

}
