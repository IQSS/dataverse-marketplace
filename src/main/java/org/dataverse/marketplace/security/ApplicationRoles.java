package org.dataverse.marketplace.security;

import org.dataverse.marketplace.model.enums.RoleEnum;

public abstract class ApplicationRoles {

    public static final String ADMIN_ROLE = "hasAuthority('" + RoleEnum.ADMIN + "')";
}
