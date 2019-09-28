package com.bewitchment.registry;

import com.bewitchment.api.registry.Curse;
import com.bewitchment.common.curse.CurseConflagration;
import com.bewitchment.common.curse.CurseMisfortune;
import com.bewitchment.common.curse.CurseReturnToSender;

public class ModCurses {
	public static Curse curseReturnToSender = new CurseReturnToSender();
	public static Curse curseMisfortune = new CurseMisfortune();
	public static Curse curseConflagration = new CurseConflagration();
}
