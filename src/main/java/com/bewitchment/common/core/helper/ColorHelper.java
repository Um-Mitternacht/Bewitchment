package com.bewitchment.common.core.helper;

public class ColorHelper {

	public static int blendColor(int colorA, int colorB, float ratio) {
		if (ratio > 1f) {
			ratio = 1f;
		} else if (ratio < 0f) {
			ratio = 0f;
		}
		float iRatio = 1.0f - ratio;

		int aA = (colorB >> 24 & 0xff);
		int aR = ((colorB & 0xff0000) >> 16);
		int aG = ((colorB & 0xff00) >> 8);
		int aB = (colorB & 0xff);

		int bA = (colorA >> 24 & 0xff);
		int bR = ((colorA & 0xff0000) >> 16);
		int bG = ((colorA & 0xff00) >> 8);
		int bB = (colorA & 0xff);

		int A = ((int) (aA * iRatio) + (int) (bA * ratio));
		int R = ((int) (aR * iRatio) + (int) (bR * ratio));
		int G = ((int) (aG * iRatio) + (int) (bG * ratio));
		int B = ((int) (aB * iRatio) + (int) (bB * ratio));

		int c = (A << 24 | R << 16 | G << 8 | B);
		if (c == Integer.MAX_VALUE) {
			c--;
		}
		return c;
	}

}
