package org.familysearch.sampleapp.listener;

import org.familysearch.sampleapp.model.user.User;

/**
 * @author Eduardo Flores
 */
public interface LoginListener {

    void onLoginSucceeded(User user);
}
