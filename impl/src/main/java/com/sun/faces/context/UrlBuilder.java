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

package com.sun.faces.context;

import com.sun.faces.util.Util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.lifecycle.ClientWindow;
import javax.faces.render.ResponseStateManager;

/**
 * <p>The <strong>UrlBuilder</strong> provides a convenient way to assemble a URL. It
 * follows the standard Builder Pattern. A seed URL is provided, which is broken
 * into parts to allow for dynamic assembly. When the URL is to be build, a call
 * to createUrl() assembles the parts into a relative URL. This class should
 * be extended if the developer wishes to have it deal with absolute URLs.</p>
 * 
 * <p>Note that this class is optimized to parse the query string lazily so as
 * to avoid unnecessary work if the seed URL differs little from the URL to be
 * built.</p>
 */
class UrlBuilder {
    public static final String QUERY_STRING_SEPARATOR = "?";
    public static final String PARAMETER_PAIR_SEPARATOR = "&";
    public static final String PARAMETER_NAME_VALUE_SEPARATOR = "=";
    public static final String FRAGMENT_SEPARATOR = "#";
    public static final String DEFAULT_ENCODING = "UTF-8";

	private static final List<String> NULL_LIST = Arrays.asList((String) null);

    private StringBuilder url;
    private String path;
    private String queryString;
    private String fragment;
    private Map<String, List<String>> parameters;
    private String encoding;


    // ------------------------------------------------------------ Constructors


    public UrlBuilder(String url, String encoding) {
        if (url == null || url.trim().length() == 0) {
            throw new IllegalArgumentException("Url cannot be empty");
        }
        this.url = new StringBuilder(url.length() * 2);
        extractSegments(url);
        this.encoding = encoding;
        // PERF TL lookup per-instance
    }


    public UrlBuilder(String url) {
        this(url, DEFAULT_ENCODING);
    }


    // ---------------------------------------------------------- Public Methods


    public UrlBuilder addParameters(String name, List<String> values) {
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("Parameter name cannot be empty");
        }
        addValuesToParameter(name.trim(), values, true);

        return this;
    }


    public UrlBuilder addParameters(Map<String, List<String>> params) {
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, List<String>> entry : params.entrySet()) {
                if (entry.getKey() == null || entry.getKey().trim().length() == 0) {
                    throw new IllegalArgumentException("Parameter name cannot be empty");
                }
                List<String> values = entry.getValue();
                List<String> retValues = values;
                addValuesToParameter(entry.getKey().trim(), retValues, true);
            }
        }

        return this;
    }


    public UrlBuilder setPath(String path) {
        if (path == null || path.trim().length() == 0) {
            throw new IllegalArgumentException("Path cannot be empty");
        }
        this.path = path;
        return this;
    }


    /**
     * Setting a query string consecutively will replace all but the last one. Otherwise,
     * the name/value pairs in the query string contribute to the parameters already established.
     */
    public UrlBuilder setQueryString(String queryString) {
        this.queryString = queryString;
        cleanQueryString();
        return this;
    }


    /**
     * The fragment is appended at the end of the url after a hash mark. It represents
     * the fragement of the document that should be brought into focus when the document
     * is rendered. Setting the fragment replaces the previous value.
     */
    public UrlBuilder setFragment(String fragment) {
        this.fragment = fragment;
        cleanFragment();
        return this;
    }


    public String createUrl() {
        appendPath();
        appendQueryString();
        appendFragment();
        return url.toString();
    }


    // ------------------------------------------------------- Protected Methods


    protected String getPath() {
        return path;
    }


    protected Map<String, List<String>> getParameters() {
        parseQueryString();
        return parameters;
    }


    protected void parseQueryString() {
        if (parameters == null) {
            parameters = new LinkedHashMap<>();
        }

        // if query string is null, then it has been parsed into parameters
        if (queryString == null) {
            return;
        }
        
        Map<String, Object> appMap = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();

        String[] pairs = Util.split(appMap, queryString, PARAMETER_PAIR_SEPARATOR);
        for (String pair : pairs) {
            String[] nameAndValue = Util.split(appMap, pair, PARAMETER_NAME_VALUE_SEPARATOR);
            // ignore malformed pair
            if (nameAndValue.length != 2
                || nameAndValue[0].trim().length() == 0) {
                continue;
            }

            addValueToParameter(nameAndValue[0], nameAndValue[1], false);
        }

        queryString = null;
    }


    protected void appendPath() {
        url.append(path);
    }


    protected void appendQueryString() {
        boolean hasQueryString = false;
        
        if (parameters != null) {
            String nextSeparatorChar;
            if ( queryString == null ) {
                nextSeparatorChar = QUERY_STRING_SEPARATOR;
            } else {
                nextSeparatorChar = PARAMETER_PAIR_SEPARATOR;
                url.append(QUERY_STRING_SEPARATOR).append(queryString);
            }
            
            for (Map.Entry<String, List<String>> param : parameters.entrySet()) {
                for (String value : param.getValue()) {
                    url.append(nextSeparatorChar);
                    url.append(param.getKey());
                    url.append(PARAMETER_NAME_VALUE_SEPARATOR);
                    url.append(value);
                    nextSeparatorChar = PARAMETER_PAIR_SEPARATOR;
                }
            }
            hasQueryString = true;
        } else if (queryString != null) { 
            url.append(QUERY_STRING_SEPARATOR).append(queryString);
            hasQueryString = true;
        }
        
        FacesContext context = FacesContext.getCurrentInstance();
        ClientWindow  cw = context.getExternalContext().getClientWindow();
        boolean appendClientWindow = false;
        if (null != cw) {
            appendClientWindow = cw.isClientWindowRenderModeEnabled(context);
        }
        if (appendClientWindow && -1 == url.indexOf(ResponseStateManager.CLIENT_WINDOW_URL_PARAM)) {
            if (null != cw) {
                String clientWindow = cw.getId();
                if (!hasQueryString) {
                    url.append(QUERY_STRING_SEPARATOR);
                } else {
                    url.append(PARAMETER_PAIR_SEPARATOR);
                }
                url.append(ResponseStateManager.CLIENT_WINDOW_URL_PARAM).append(PARAMETER_NAME_VALUE_SEPARATOR).append(clientWindow);
    
                Map<String, String> additionalParams = cw.getQueryURLParameters(context);
                if (null != additionalParams) {
                    for (Map.Entry<String, String> cur : additionalParams.entrySet()) {
                        url.append(PARAMETER_NAME_VALUE_SEPARATOR);
                        url.append(cur.getKey()).
                                append(UrlBuilder.PARAMETER_NAME_VALUE_SEPARATOR).
                                append(cur.getValue());                        
                    }
                }
            }
        }
        
    }


    protected void appendFragment() {
        if (fragment != null) {
            url.append(FRAGMENT_SEPARATOR).append(fragment);
        }
    }


    protected void extractSegments(String url) {
        int fragmentIndex = url.indexOf(FRAGMENT_SEPARATOR);
        if (fragmentIndex != -1) {
           fragment = url.substring(fragmentIndex + 1);
           cleanFragment();
           url = url.substring(0, fragmentIndex);
        }

        int queryStringIndex = url.indexOf(QUERY_STRING_SEPARATOR);
        if (queryStringIndex != -1) {
            queryString = url.substring(queryStringIndex + 1);
            cleanQueryString();
            path = url.substring(0, queryStringIndex);
        }
        else {
            path = url;
        }
    }


    protected void addValueToParameter(String name, String value, boolean replace) {
        List<String> values = new ArrayList<>(value == null ? 0 : 1);
        if (value != null) {
            values.add(value);
        }
        addValuesToParameter(name, values, replace);
    }


    protected void addValuesToParameter(String name, List<String> valuesRef, boolean replace) {
        List<String> values = new ArrayList<>();
        if (valuesRef != null) {
            for (Iterator<String> it = valuesRef.iterator(); it.hasNext();) {
                String string = it.next();
                    if (encoding != null) {
                        try {
                            values.add(URLEncoder.encode(string, encoding));
                        } catch (UnsupportedEncodingException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    else {
                        values.add(string);
                    }
            }
            values.removeAll(NULL_LIST);
        }

        if (parameters == null) {
            parameters = new LinkedHashMap<>();
        }
        
        if (replace) {
            parameters.put(name, values);
        }
        else {
            List<String> currentValues = parameters.get(name);
            if (currentValues == null) {
                currentValues = new ArrayList<>(1);
                parameters.put(name, currentValues);
            }
            currentValues.addAll(values);
        }
    }


    // --------------------------------------------------------- Private Methods


    private void cleanFragment() {
        if (fragment != null) {
            String f = fragment;
            f = f.trim();
            if (f.startsWith(FRAGMENT_SEPARATOR)) {
                f = f.substring(1);
            }

            if (f.length() == 0) {
                f = null;
            }

            fragment = f;
        }
    }

    
    private void cleanQueryString() {
        if (queryString != null) {
            String q = queryString;
            q = q.trim();
            if (q.startsWith(QUERY_STRING_SEPARATOR)) {
                q = q.substring(1);
            }

            if (q.length() == 0) {
                q = null;
            }
            queryString = q;
        }
    }

}
