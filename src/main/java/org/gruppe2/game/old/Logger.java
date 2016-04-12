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
		
		String filePath = System.getProperty("user.home") + File.separator;
		String fileName = filePath + String.format("%d.%03d_%02d.%02d.txt", dateTime.getYear(), dateTime.getDayOfYear(),
				dateTime.getHour(), dateTime.getMinute());
		logFile = new File(fileName);
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
			// Sparkly pretty UNICODE, please don't bully the macs.	
			writer.write(String.format("\u2554%50s\u2557%n", "").replace(' ', '\u2550')); // Top frame.
			writer.write(String.format("\u2551 %02d\u00b7%02d\u00b7%d", dateTime.getDayOfMonth(), dateTime.getMonthValue(),
					dateTime.getYear())); // Logs the date of creation.
			writer.write(String.format("%39s\u2551%n", "")); // Completes the frame.
			
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
			writer.write(String.format("\u2551  %s  \u00BB  %-35s\u2551%n", currentTime, event));
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
				writer.write(String.format("\u2551  %s  \u00BB  %-10s: Fold %18s\u2551%n", currentTime, player.getName(),""));
			else if (action instanceof Action.Check)
				writer.write(String.format("\u2551  %s  \u00BB  %-10s: Check %17s\u2551%n", currentTime, player.getName(),""));
			else if (action instanceof Action.Call)
				writer.write(String.format("\u2551  %s  \u00BB  %-10s: Call %18s\u2551%n", currentTime, player.getName(),""));
			else if (action instanceof Action.Raise)
				writer.write(String.format("\u2551  %s  \u00BB  %-10s: Raise %-5d %11s\u2551%n", currentTime, player.getName(),
						((Action.Raise) action).getAmount(),""));
			else if (action instanceof Action.AllIn)
				writer.write(String.format("\u2551  %s  \u00BB  %-10s: All-in%n", currentTime, player.getName()));
			else
				writer.write(String.format("\u2551  %s  \u00BB  %-10s: Unrecognized action%n", currentTime, player.getName()));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Cosmetic method to finish the frame.
	 */
	public void done() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
			writer.write(String.format("\u255A%50s\u255D%n", "").replace(' ', '\u2550')); // Bottom frame.
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public File getLog() {
		return logFile;
	}

} // EoC
