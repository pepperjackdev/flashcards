package io.github.pepperjackdev.flashcards.controllers.loadable;

public interface Loadable<T> {
    public void load(T controller);
}
