package org.dataverse.marketplace.security;

public abstract class ApplicationRoles {

    public static final String ADMIN_ROLE = "hasAuthority('ADMIN')";
    public static final String EDITOR_ROLE = "hasAuthority('EDITOR')";
}
