/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.springframework.security.config.annotation.authentication;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.AbstractSecurityConfigurator;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;

/**
 *
 * @author Rob Winch
 * @since 3.2
 */
public class UserDetailsServiceSecurityBuilder<T extends UserDetailsService> extends AbstractSecurityConfigurator<AuthenticationManager,AuthenticationBuilder> {
    protected final T userDetailsService;

    public UserDetailsServiceSecurityBuilder(T userDetailsService) {
        Assert.notNull(userDetailsService);
        this.userDetailsService = userDetailsService;
    }

    public T userDetailsService() throws Exception {
        return userDetailsService;
    }

    protected void doConfigure(AuthenticationBuilder builder) throws Exception {
        builder.add(userDetailsService());
    }
}
