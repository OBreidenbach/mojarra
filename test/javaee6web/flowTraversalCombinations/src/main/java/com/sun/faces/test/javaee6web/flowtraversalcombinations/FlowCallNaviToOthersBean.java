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

package com.sun.faces.test.javaee6web.flowtraversalcombinations;

import java.io.Serializable;
import javax.faces.flow.FlowScoped;
import javax.inject.Named;

@Named
@FlowScoped(value = "start-from-flow-call-node")
public class FlowCallNaviToOthersBean implements Serializable {
    private String inBoundPara1 = "CorrectString";
    
    public String getInBoundPara1() {
        return inBoundPara1;
    }
    
    public void setInBoundPara1(String para1) {
        this.inBoundPara1 = para1;
    }
    
    private String inBoundPara2 = "ExpectedString";
    
    public String getInBoundPara2() {
        return this.inBoundPara2;
    }
    
    public void setInBoundPara2(String para) {
        this.inBoundPara2 = para;
    }
}
