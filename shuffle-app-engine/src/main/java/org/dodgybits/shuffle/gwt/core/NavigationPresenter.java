package org.dodgybits.shuffle.gwt.core;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import org.dodgybits.shuffle.gwt.cursor.ContextEntityCache;
import org.dodgybits.shuffle.gwt.cursor.EntityCache;
import org.dodgybits.shuffle.gwt.place.NameTokens;
import org.dodgybits.shuffle.shared.ContextProxy;

import java.util.List;

public class NavigationPresenter extends
		PresenterWidget<NavigationPresenter.MyView> implements NavigationUiHandlers, EntityCache.EntityListListener<ContextProxy> {

    public interface MyView extends View, HasUiHandlers<NavigationUiHandlers> {
        void showContexts(List<ContextProxy> contexts);
	}

    private final ContextEntityCache mContextCache;
    private final PlaceManager mPlaceManager;

	@Inject
	public NavigationPresenter(final EventBus eventBus, final MyView view,
                               final ContextEntityCache contextCache,
                               final PlaceManager placeManager) {
		super(eventBus, view);
        mContextCache = contextCache;
        mPlaceManager = placeManager;

	    getView().setUiHandlers(this);
	}

    @Override
    protected void onReveal() {
        super.onReveal();

        GWT.log("NavigationPresenter onReveal()");

        mContextCache.addListener(this);
    }

    @Override
    protected void onHide() {
        super.onHide();

        mContextCache.removeListener(this);
    }

    @Override
    public void onEntityListUpdated(List<ContextProxy> contexts) {
        getView().showContexts(contexts);
    }


    public void onNewAction() {
		PlaceRequest myRequest = new PlaceRequest(NameTokens.editAction);
        mPlaceManager.revealPlace(myRequest);
	}
}