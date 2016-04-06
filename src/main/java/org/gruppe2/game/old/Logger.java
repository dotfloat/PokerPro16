package org.gruppe2.game.old;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Logger class that needs better javadoc. Chiefly tested against console.
 * Please send help.
 * 
 * @author aom035
 *
 */
public class Logger {

	private File logFile;

	/**
	 * Upon invoking the constructor a .txt file is created with the following
	 * name format: Year.DayOfYear_Hours.Minutes.
	 */
	public Logger() {
		LocalDateTime dateTime = LocalDateTime.now();
		String filePath = "logs/";
		String fileName = filePath + String.format("%d.%d_%d.%d", dateTime.getYear(), dateTime.getDayOfYear(),
				dateTime.getHour(), dateTime.getMinute());
		logFile = new File(fileName);
		// Sparkly pretty Alt code frame
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
			writer.write(String.format("╔%30s%n", "").replace(' ', '═'));
			writer.write(String.format("║ %02d·%02d·%d%n", dateTime.getDayOfMonth(), dateTime.getMonthValue(),
					dateTime.getYear()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Logs the String and adds a time of occurrence.
	 * 
	 * @param event
	 */
	public void record(String event) {
		LocalTime clock = LocalTime.now();
		String currentTime = String.format("%02d:%02d:%02d", clock.getHour(), clock.getMinute(), clock.getSecond());

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
			writer.write(String.format("║  %s  »  %s%n", currentTime, event));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Logs player name, action taken by player and adds a time of occurrence.
	 * 
	 * @param player
	 * @param action
	 */
	public void record(Player player, Action action) {
		LocalTime clock = LocalTime.now();
		String currentTime = String.format("%02d:%02d:%02d", clock.getHour(), clock.getMinute(), clock.getSecond());

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {

			if (action instanceof Action.Fold)
				writer.write(String.format("║  %s  »  %-8s: Fold.%n", currentTime, player.getName()));
			else if (action instanceof Action.Check)
				writer.write(String.format("║  %s  »  %-8s: Check.%n", currentTime, player.getName()));
			else if (action instanceof Action.Call)
				writer.write(String.format("║  %s  »  %-8s: Call.%n", currentTime, player.getName()));
			else if (action instanceof Action.Raise)
				writer.write(String.format("║  %s  »  %-8s: Raise %d.%n", currentTime, player.getName(),
						((Action.Raise) action).getAmount()));
			else if (action instanceof Action.AllIn)
				writer.write(String.format("║  %s  »  %-8s: All-in.%n", currentTime, player.getName()));
			else
				writer.write(String.format("║  %s  »  %-8s: Unrecognized action.%n", currentTime, player.getName()));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public File getLog() {
		return logFile;
	}

} // EoC
