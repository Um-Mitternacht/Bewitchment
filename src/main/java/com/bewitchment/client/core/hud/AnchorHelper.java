package com.bewitchment.client.core.hud;

public abstract class AnchorHelper {

	public abstract double getData(double x, int scaledSize, int componentSize);

	public abstract double getPixel(double x, int scaledSize, int componentSize);

	public static class AbsoluteStartHelper extends AnchorHelper {

		@Override
		public double getData(double x, int scaledSize, int componentSize) {
			return x;
		}

		@Override
		public double getPixel(double x, int scaledSize, int componentSize) {
			return x;
		}

	}

	public static class AbsoluteEndHelper extends AnchorHelper {

		@Override
		public double getData(double x, int scaledSize, int componentSize) {
			return scaledSize - componentSize - x;
		}

		@Override
		public double getPixel(double x, int scaledSize, int componentSize) {
			return scaledSize - componentSize - x;
		}

	}

	public static class AbsoluteCenterHelper extends AnchorHelper {

		@Override
		public double getData(double x, int scaledSize, int componentSize) {
			return x - (scaledSize - componentSize) / 2;
		}

		@Override
		public double getPixel(double x, int scaledSize, int componentSize) {
			return (scaledSize - componentSize) / 2 + x;
		}

	}

	public static class RelativeVersion extends AnchorHelper {

		AnchorHelper used;

		public RelativeVersion(AnchorHelper helper) {
			used = helper;
		}

		@Override
		public double getData(double x, int scaledSize, int componentSize) {
			return used.getData(x, scaledSize, componentSize) / scaledSize;
		}

		@Override
		public double getPixel(double x, int scaledSize, int componentSize) {
			return used.getPixel(x * scaledSize, scaledSize, componentSize);
		}

	}

}
