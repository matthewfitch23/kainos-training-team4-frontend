package com.kainos.training.dropwizard.login.frontends;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import com.google.common.collect.ImmutableMap;
import com.kainos.training.blackbox.client.FriendClient;
import com.kainos.training.dropwizard.login.frontends.config.LoginFrontendsConfiguration;
import com.kainos.training.dropwizard.login.frontends.resources.ViewsResource;
import com.kainos.training.jersey.client.BaseClient;

public class LoginFrontendsApplication extends Application<LoginFrontendsConfiguration> {

	public static void main(String[] args) throws Exception {
		new LoginFrontendsApplication().run(args);
	}

	@Override
    public void initialize(Bootstrap<LoginFrontendsConfiguration> bootstrap) {        
        bootstrap.addBundle(new ViewBundle<LoginFrontendsConfiguration>() {
	        @Override
	        public ImmutableMap<String, ImmutableMap<String, String>> getViewConfiguration(LoginFrontendsConfiguration config) {
	            return config.getViewRendererConfiguration();
	        }
        });               
    }
	
	@Override
	public void run(LoginFrontendsConfiguration configuration, Environment environment)
			throws Exception {
		final ViewsResource viewsResource = new ViewsResource(new BaseClient(), new FriendClient());
		environment.jersey().register(viewsResource);
	}

}
