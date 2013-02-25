package org.dodgybits.shuffle.gwt.gin;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import org.dodgybits.shuffle.client.ShuffleRequestFactory;
import org.dodgybits.shuffle.gwt.core.*;
import org.dodgybits.shuffle.gwt.core.tasklist.TaskListPresenter;
import org.dodgybits.shuffle.gwt.core.tasklist.TaskListView;
import org.dodgybits.shuffle.gwt.cursor.ContextEntityCache;
import org.dodgybits.shuffle.gwt.cursor.ProjectEntityCache;
import org.dodgybits.shuffle.gwt.cursor.TaskNavigator;
import org.dodgybits.shuffle.gwt.place.ClientPlaceManager;
import org.dodgybits.shuffle.gwt.place.DefaultPlace;
import org.dodgybits.shuffle.gwt.place.ErrorPlace;
import org.dodgybits.shuffle.gwt.place.NameTokens;
import org.dodgybits.shuffle.gwt.settings.RestoreFromBackupPresenter;
import org.dodgybits.shuffle.gwt.settings.RestoreFromBackupView;
import org.dodgybits.shuffle.shared.EntityService;

public class ClientModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		install(new DefaultModule(ClientPlaceManager.class));

		bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.taskList);

		bindConstant().annotatedWith(ErrorPlace.class).to(NameTokens.error);

        bindPresenter(ErrorPresenter.class, ErrorPresenter.MyView.class,
                ErrorView.class, ErrorPresenter.MyProxy.class);

		bindPresenter(MainPresenter.class, MainPresenter.MyView.class,
				MainView.class, MainPresenter.MyProxy.class);

		bindPresenter(TaskListPresenter.class, TaskListPresenter.MyView.class,
				TaskListView.class, TaskListPresenter.MyProxy.class);

		bindPresenter(EditActionPresenter.class,
				EditActionPresenter.MyView.class, EditActionView.class,
				EditActionPresenter.MyProxy.class);

		bindPresenterWidget(NavigationPresenter.class,
				NavigationPresenter.MyView.class, NavigationView.class);

        bindPresenter(RestoreFromBackupPresenter.class, RestoreFromBackupPresenter.MyView.class,
                RestoreFromBackupView.class, RestoreFromBackupPresenter.MyProxy.class);

		bind(ShuffleRequestFactory.class).in(Singleton.class);
        
        bind(TaskNavigator.class).in(Singleton.class);
        
        bind(ProjectEntityCache.class).in(Singleton.class);
        bind(ContextEntityCache.class).in(Singleton.class);

	}

	@Provides
    EntityService provideEntityService(ShuffleRequestFactory requestFactory) {
		return requestFactory.entityService();
	}

}