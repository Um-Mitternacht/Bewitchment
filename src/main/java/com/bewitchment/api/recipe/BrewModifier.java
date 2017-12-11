package com.bewitchment.api.recipe;

import java.util.List;

/**
 * This class was created by Arekkuusu on 30/05/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public interface BrewModifier {

	boolean apply(List<Object> brews, Object current);
}
