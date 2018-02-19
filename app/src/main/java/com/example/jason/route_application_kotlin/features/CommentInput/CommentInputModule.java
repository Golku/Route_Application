package com.example.jason.route_application_kotlin.features.CommentInput;

import com.example.jason.route_application_kotlin.interactors.CommentInputInteractor;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Jason on 19-Feb-18.
 */

@Module
public abstract class CommentInputModule {

    @Binds
    public abstract MvpCommentInput.View provideView(CommentInputActivity view);

    @Binds
    public abstract MvpCommentInput.Presenter providePresenter(CommentInputPresenter presenter);

    @Binds
    public abstract MvpCommentInput.Interactor provideInteractor(CommentInputInteractor interactor);

}
