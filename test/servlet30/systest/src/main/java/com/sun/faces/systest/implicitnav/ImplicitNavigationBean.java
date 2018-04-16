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

package com.sun.faces.systest.implicitnav;

import java.net.MalformedURLException;
import java.net.URL;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.context.FacesContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class ImplicitNavigationBean {
    
    public String getCurrentActionUrl() throws MalformedURLException {
        FacesContext context = FacesContext.getCurrentInstance();
        ConfigurableNavigationHandler navHandler = (ConfigurableNavigationHandler)
                context.getApplication().getNavigationHandler();
        NavigationCase navCase = navHandler.getNavigationCase(context, 
                null, context.getViewRoot().getViewId());
        URL myUrl = navCase.getActionURL(context);
        
        return myUrl.toExternalForm();
    }

    public String getCurrentResourceUrl() throws MalformedURLException {
        FacesContext context = FacesContext.getCurrentInstance();
        ConfigurableNavigationHandler navHandler = (ConfigurableNavigationHandler)
                context.getApplication().getNavigationHandler();
        NavigationCase navCase = navHandler.getNavigationCase(context, 
                null, context.getViewRoot().getViewId());
        URL myUrl = navCase.getResourceURL(context);
        
        return myUrl.toExternalForm();
    }

}
