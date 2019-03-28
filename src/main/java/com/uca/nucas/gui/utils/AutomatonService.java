/*
 * Developed by Matthias Percelay. Created on 27/03/19 18:58.
 * Last modified 14/03/19 10:53
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.gui.utils;

import com.uca.nucas.engine.Automaton;
import com.uca.nucas.engine.configuration.Configuration;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class AutomatonService extends Service<Configuration> {
    Automaton automaton;
    Configuration configuration;

    public AutomatonService(Automaton automaton, Configuration configuration) {
        this.automaton = automaton;
        this.configuration = configuration;
    }

    @Override
    protected Task<Configuration> createTask() {
        return new Task<Configuration>() {
            @Override
            protected Configuration call() throws Exception {
                configuration = automaton.evaluate(configuration);
                return configuration;
            }
        };
    }
}
