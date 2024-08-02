package com.aperalta.store.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractService {

    protected final Logger log ;
    public AbstractService(Class c) {
        this.log = LoggerFactory.getLogger(c);
    }

}
