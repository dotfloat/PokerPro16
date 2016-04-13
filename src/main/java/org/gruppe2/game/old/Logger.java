package org.gruppe2.game.old;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Simple logger that saves recorded actions to a .txt file.
 */
public class Logger {

	private File logFile;

	/**
	 * Upon invoking the constructor a set of folders are made at user.home,
	 * and inside the folder a .txt file is created with the following
	 * name format: Year.DayOfYear_Hours.Minutes.
	 */
	public Logger() {
		LocalDateTime dateTime = LocalDateTime.now();

		String filePath = System.getProperty("user.home") + File.separator + "PokerPro16" + File.separator + "logs"
				+ File.separator;
		String fileName = filePath + String.format("%d.%03d_%02d.%02d.txt", dateTime.getYear(), dateTime.getDayOfYear(),
				dateTime.getHour(), dateTime.getMinute());

		new File(filePath).mkdirs();
		logFile = new File(fileName);

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
			// Unicode frame, please close your eyes while reading this.
			writer.write(String.format("\u2554%50s%n", "").replace(' ', '\u2550'));
			writer.write(String.format("\u2551 %02d\u00b7%02d\u00b7%d%n", dateTime.getDayOfMonth(),
					dateTime.getMonthValue(), dateTime.getYear()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Takes a String and adds it to the log along with a time of occurrence.
	 * @param event
	 */
	public void record(String event) {
		LocalTime clock = LocalTime.now();
		String currentTime = String.format("%02d:%02d:%02d", clock.getHour(), clock.getMinute(), clock.getSecond());
		String startOfLine = String.format("\u2551  %s  \u00BB", currentTime);

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
			writer.write(String.format("%s  %s%n", startOfLine, event));
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
		String startOfLine = String.format("\u2551  %s  \u00BB", currentTime);
		String timeAndPlayer = String.format("%s  %-10s:", startOfLine, player.getName());

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {

			if (action instanceof Action.Fold)
				writer.write(String.format("%s Fold%n", timeAndPlayer));
			else if (action instanceof Action.Check)
				writer.write(String.format("%s Check%n", timeAndPlayer));
			else if (action instanceof Action.Call)
				writer.write(String.format("%s Call%n", timeAndPlayer));
			else if (action instanceof Action.Raise)
				writer.write(String.format("%s Raise %-5d%n", timeAndPlayer, ((Action.Raise) action).getAmount()));
			else if (action instanceof Action.AllIn)
				writer.write(String.format("%s All-in%n", timeAndPlayer));
			else if (action instanceof Action.Pass)
				writer.write(String.format("%s Passed%n", timeAndPlayer));
			else
				writer.write(String.format("%s Unrecognized action%n", timeAndPlayer));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cosmetic method to finish the frame.
	 */
	public void done() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
			writer.write(String.format("\u255A%50s%n", "").replace(' ', '\u2550')); // Bottom
																							// frame.
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public File getLog() {
		return logFile;
	}

} // EoC
