package seminor_aiwolf;

import org.aiwolf.client.base.player.AbstractRoleAssignPlayer;

public class NopRoleAssignPlayer extends AbstractRoleAssignPlayer {

	public NopRoleAssignPlayer() {
		setSeerPlayer(new NopSeer());
	}

	@Override
	public String getName() {
		return "seminor_aiwolf";
	}
}
