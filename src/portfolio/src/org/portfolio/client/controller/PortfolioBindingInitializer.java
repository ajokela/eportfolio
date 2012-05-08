/* $Name:  $ */
/* $Id: PortfolioBindingInitializer.java,v 1.4 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.controller;

import java.beans.PropertyEditorSupport;

import org.portfolio.model.EntryKey;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

public class PortfolioBindingInitializer implements WebBindingInitializer {
    
    public void initBinder(WebDataBinder binder, WebRequest req) {
        PropertyEditorSupport entryKeyEditor = new PropertyEditorSupport() {
            @Override
            public String getAsText() {
                EntryKey entryKey = (EntryKey) this.getValue();
                return entryKey == null ? null : entryKey.getId();
            }

            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (StringUtils.hasText(text)) {
                    this.setValue(new EntryKey(text));
                }
            }
        };
        binder.registerCustomEditor(EntryKey.class, entryKeyEditor);
    }
}
