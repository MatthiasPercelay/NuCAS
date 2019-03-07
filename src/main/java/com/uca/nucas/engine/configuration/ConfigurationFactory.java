/*
 * Developed by Matthias Percelay. Created on 07/03/19 11:21.
 * Last modified 07/03/19 11:21
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine.configuration;

public class ConfigurationFactory {

    public ConfigurationFactory(){};

    public Configuration newFrom(Configuration old, int[] newContents) throws IllegalArgumentException {
        switch (old.getConfType()) {
            case GrowingConfiguration.GROWING_CONF : {
                return new GrowingConfiguration((GrowingConfiguration)old, newContents);
            }
            case LossyConfiguration.LOSSY_CONF : {
                return new LossyConfiguration((LossyConfiguration)old, newContents);
            }
            case WrappingConfiguration.WRAPPING_CONF : {
                return new WrappingConfiguration(newContents);
            }
            default : throw new IllegalArgumentException("Not a supported Configuration type");
        }
    }
}
