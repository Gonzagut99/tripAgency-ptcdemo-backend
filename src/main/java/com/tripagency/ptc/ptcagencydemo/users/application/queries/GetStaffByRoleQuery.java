package com.tripagency.ptc.ptcagencydemo.users.application.queries;

import com.tripagency.ptc.ptcagencydemo.users.domain.enums.DRoles;

public record GetStaffByRoleQuery(DRoles role) {
}
