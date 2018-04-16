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

import com.gargoylesoftware.htmlunit.html.*;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AjaxEchoITCase  extends HtmlUnitFacesITCase {

    public AjaxEchoITCase(String name) {
        super(name);
    }

    /*
     * Set up instance variables required by this test case.
     */
    public void setUp() throws Exception {
        super.setUp();
    }


    /*
     * Return the tests included in this test suite.
     */
    public static Test suite() {
        return (new TestSuite(AjaxEchoITCase.class));
    }


    /*
     * Tear down instance variables required by this test case.
     */
    public void tearDown() {
        super.tearDown();
    }


    // Test basic ajax functionality
    public void testAjaxEcho() throws Exception {
        getPage("/faces/ajax/ajaxEcho.xhtml");

        // First we'll check the first page was output correctly
        checkTrue("form1:out1","");
        checkTrue("form1:in1","");

        HtmlTextInput in1 = (HtmlTextInput) lastpage.getHtmlElementById("form1:in1");

        in1.type("hello");

        // Submit the ajax request
        HtmlSubmitInput button1 = (HtmlSubmitInput) lastpage.getHtmlElementById("form1:button1");
        lastpage = (HtmlPage) button1.click();

        // Check that the ajax request succeeds
        checkTrue("form1:out1","hello");
    }

    // Test using a valid string as the request identifier, instead of an element
    public void testAjaxEchoWithStringId() throws Exception {
        getPage("/faces/ajax/ajaxEcho.xhtml");

        // First we'll check the first page was output correctly
        checkTrue("form1:out1","");
        checkTrue("form1:in1","");

        HtmlTextInput in1 = (HtmlTextInput) lastpage.getHtmlElementById("form1:in1");

        in1.type("hello");

        // Submit the ajax request
        HtmlSubmitInput button2 = (HtmlSubmitInput) lastpage.getHtmlElementById("form1:button2");
        lastpage = (HtmlPage) button2.click();

        // Check that the ajax request succeeds
        checkTrue("form1:out1","hello");
    }

    // Test basic ajax functionality
    public void testAjaxEchoLT() throws Exception {
        getPage("/faces/ajax/ajaxEcho.xhtml");

        // First we'll check the first page was output correctly
        checkTrue("form1:out1","");
        checkTrue("form1:in1","");

        HtmlTextInput in1 = (HtmlTextInput) lastpage.getHtmlElementById("form1:in1");

        in1.type("<");

        // Submit the ajax request
        HtmlSubmitInput button1 = (HtmlSubmitInput) lastpage.getHtmlElementById("form1:button1");
        lastpage = (HtmlPage) button1.click();

        // Check that the ajax request succeeds
        checkTrue("form1:out1","<");
    }


    /*
     * Regression test for bug #939
     */
//    public void testCdataEscape1() throws Exception {
//        getPage("/faces/ajax/ajaxEcho.xhtml");
//
//       // First we'll check the first page was output correctly
//      checkTrue("form1:out1","");
//        checkTrue("form1:in1","");
//
//        HtmlTextInput in1 = (HtmlTextInput) lastpage.getHtmlElementById("form1:in1");
//
//        in1.type("]]>");
//
//        // Submit the ajax request
//        HtmlSubmitInput button1 = (HtmlSubmitInput) lastpage.getHtmlElementById("form1:button1");
//        lastpage = (HtmlPage) button1.click();
//
//        // Check that the ajax request succeeds
//        checkTrue("form1:out1","]]>");
//    }
//    public void testCdataEscape2() throws Exception {
//        getPage("/faces/ajax/ajaxEcho.xhtml");
//
//        // First we'll check the first page was output correctly
//        checkTrue("form1:out1","");
//        checkTrue("form1:in1","");
//
//        HtmlTextInput in1 = (HtmlTextInput) lastpage.getHtmlElementById("form1:in1");
//
//        in1.type("<!");
//
//        // Submit the ajax request
//        HtmlSubmitInput button1 = (HtmlSubmitInput) lastpage.getHtmlElementById("form1:button1");
//        lastpage = (HtmlPage) button1.click();
//
//        // Check that the ajax request succeeds
//        checkTrue("form1:out1","<!");
//    }
//    public void testCdataEscape3() throws Exception {
//        getPage("/faces/ajax/ajaxEcho.xhtml");
//
//        // First we'll check the first page was output correctly
//        checkTrue("form1:out1","");
//        checkTrue("form1:in1","");
//
//        HtmlTextInput in1 = (HtmlTextInput) lastpage.getHtmlElementById("form1:in1");
//
//        in1.type("]");
//
//        // Submit the ajax request
//        HtmlSubmitInput button1 = (HtmlSubmitInput) lastpage.getHtmlElementById("form1:button1");
//        lastpage = (HtmlPage) button1.click();
//
//        // Check that the ajax request succeeds
//        checkTrue("form1:out1","]");
//    }
//    public void testCdataEscape4() throws Exception {
//        getPage("/faces/ajax/ajaxEcho.xhtml");
//
//        // First we'll check the first page was output correctly
//        checkTrue("form1:out1","");
//        checkTrue("form1:in1","");
//
//        HtmlTextInput in1 = (HtmlTextInput) lastpage.getHtmlElementById("form1:in1");
//
//        in1.type("]");
//
//        // Submit the ajax request
//        HtmlSubmitInput button1 = (HtmlSubmitInput) lastpage.getHtmlElementById("form1:button1");
//        lastpage = (HtmlPage) button1.click();
//
//        // Check that the ajax request succeeds
//        checkTrue("form1:out1","]");
//    }
//    public void testCdataEscape5() throws Exception {
//        getPage("/faces/ajax/ajaxEcho.xhtml");
//
//        // First we'll check the first page was output correctly
//        checkTrue("form1:out1","");
//        checkTrue("form1:in1","");
//
//        HtmlTextInput in1 = (HtmlTextInput) lastpage.getHtmlElementById("form1:in1");
//
//        in1.type("<![CDATA[ ]]>");
//
//        // Submit the ajax request
//        HtmlSubmitInput button1 = (HtmlSubmitInput) lastpage.getHtmlElementById("form1:button1");
//        lastpage = (HtmlPage) button1.click();
//
//        // Check that the ajax request succeeds
//        checkTrue("form1:out1","<![CDATA[ ]]>");
//    }
//
//    // Test for bug #1284
//    public void testCdataEscape6() throws Exception {
//        getPage("/faces/ajax/ajaxEcho.xhtml");
//
//        // First we'll check the first page was output correctly
//        checkTrue("form1:out1","");
//        checkTrue("form1:in1","");
//
//        HtmlTextInput in1 = (HtmlTextInput) lastpage.getHtmlElementById("form1:in1");
//
//        in1.type("[");
//
//        // Submit the ajax request
//        HtmlSubmitInput button1 = (HtmlSubmitInput) lastpage.getHtmlElementById("form1:button1");
//        lastpage = (HtmlPage) button1.click();
//
//        // Check that the ajax request succeeds
//        checkTrue("form1:out1","[");
//    }
//    // Test for bug #1284
//    public void testCdataEscape7() throws Exception {
//        getPage("/faces/ajax/ajaxEcho.xhtml");
//
//        // First we'll check the first page was output correctly
//        checkTrue("form1:out1","");
//        checkTrue("form1:in1","");
//
//        HtmlTextInput in1 = (HtmlTextInput) lastpage.getHtmlElementById("form1:in1");
//
//        in1.type("var a=[");
//
//        // Submit the ajax request
//        HtmlSubmitInput button1 = (HtmlSubmitInput) lastpage.getHtmlElementById("form1:button1");
//        lastpage = (HtmlPage) button1.click();
//
//        // Check that the ajax request succeeds
//        checkTrue("form1:out1","var a=[");
//    }
//
//    public void testProjectStage() throws Exception {
//        getPage("/faces/ajax/ajaxProjectStage.xhtml");
//
//        // First we'll check the first page was output correctly
//        checkTrue("stage","Development");
//    }

    public void testTextArea() throws Exception {
        getPage("/faces/ajax/ajaxEchoArea.xhtml");

        // First we'll check the first page was output correctly
        checkTrue("form1:out1","");
        checkTrue("form1:in1","");

        HtmlTextArea in1 = (HtmlTextArea) lastpage.getHtmlElementById("form1:in1");

        in1.type("test value");

        // Submit the ajax request
        HtmlButtonInput button1 = (HtmlButtonInput) lastpage.getHtmlElementById("form1:button1");
        lastpage = (HtmlPage) button1.click();

        // Check that the ajax request succeeds
        checkTrue("form1:out1","test value");
    }


}
