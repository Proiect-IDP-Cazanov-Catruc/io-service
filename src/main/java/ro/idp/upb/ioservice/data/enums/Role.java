/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.data.enums;

import static ro.idp.upb.ioservice.data.enums.Permission.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
	USER(Collections.emptySet(), "USER"),
	ADMIN(
			Set.of(
					ADMIN_READ,
					ADMIN_UPDATE,
					ADMIN_DELETE,
					ADMIN_CREATE,
					MANAGER_READ,
					MANAGER_UPDATE,
					MANAGER_DELETE,
					MANAGER_CREATE),
			"ADMIN"),
	MANAGER(Set.of(MANAGER_READ, MANAGER_UPDATE, MANAGER_DELETE, MANAGER_CREATE), "MANAGER");

	private final Set<Permission> permissions;
	@Getter private final String name;

	public List<String> getAuthorities() {
		var authorities =
				new ArrayList<>(getPermissions().stream().map(Permission::getPermission).toList());
		authorities.add(this.name());
		return authorities;
	}
}
