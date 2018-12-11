package de.luuuuuis;

public class TimeManager {

	/**
	 * 
	 * Enter time in seconds
	 * 
	 * @param timeInSecond
	 * @return
	 */
	public static String calc(long timeInSecond) {

		if (timeInSecond == -1) {
			return "Permanent";
		}

		if (timeInSecond >= 86400L) {
			long res1 = timeInSecond / 60L;
			long res2 = res1 / 60L;
			long res3 = res2 / 24L;

			long ubrig = res2 - res3 * 24L;

			if (res3 == 1) {
				return res3 + " day and " + ubrig + " hours";
			} else {
				return res3 + " days and " + ubrig + " hours";
			}
		}

		if (timeInSecond >= 3600L) {
			long res1 = timeInSecond / 60L;
			long res2 = res1 / 60L;
			if (res2 == 1) {
				return res2 + " hour";
			} else {
				return res2 + " hours";
			}
		}

		if (timeInSecond >= 60L) {
			long res1 = timeInSecond / 60L;

			if (res1 == 1) {
				return res1 + " minute";
			} else {
				return res1 + " minutes";
			}
		}

		if (timeInSecond <= 60L && timeInSecond > 0) {
			if (timeInSecond == 1) {
				return timeInSecond + " second";
			} else {
				return timeInSecond + " seconds";
			}
		}

		return "0 seconds";
	}

}
