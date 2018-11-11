package com.bewitchment.common.integration.patchouli;

import vazkii.patchouli.api.PatchouliAPI;

public class PatchouliCompat {
	public void init() {
		PatchouliAPI.instance.setConfigFlag("bw:true", true);
	}
}
