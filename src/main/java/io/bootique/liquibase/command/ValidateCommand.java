/**
 *  Licensed to ObjectStyle LLC under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ObjectStyle LLC licenses
 *  this file to you under the Apache License, Version 2.0 (the
 *  “License”); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package io.bootique.liquibase.command;

import com.google.inject.Inject;
import com.google.inject.Provider;
import io.bootique.cli.Cli;
import io.bootique.command.CommandOutcome;
import io.bootique.command.CommandWithMetadata;
import io.bootique.liquibase.LiquibaseRunner;
import io.bootique.meta.application.CommandMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 0.11
 */
public class ValidateCommand extends CommandWithMetadata {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateCommand.class);

    private Provider<LiquibaseRunner> runnerProvider;

    @Inject
    public ValidateCommand(Provider<LiquibaseRunner> runnerProvider) {
        super(CommandMetadata.builder(ValidateCommand.class)
                .name("lb-validate")
                .shortName('v')
                .description("Checks the changelog for errors.")
                .build());
        this.runnerProvider = runnerProvider;
    }

    @Override
    public CommandOutcome run(Cli cli) {

        LOGGER.info("Will run 'liquibase validate'...");

        return runnerProvider.get().runWithLiquibase(lb -> {
            try {
                lb.validate();
                return CommandOutcome.succeeded();
            } catch (Exception e) {
                return CommandOutcome.failed(1, e);
            }
        });
    }
}
