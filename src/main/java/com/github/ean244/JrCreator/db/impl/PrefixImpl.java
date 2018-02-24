package com.github.ean244.jrcreator.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ean244.jrcreator.db.DbHandler;
import com.github.ean244.jrcreator.db.api.PrefixRequest;
import com.github.ean244.jrcreator.db.api.PrefixUpdate;
import com.github.ean244.jrcreator.guild.DefaultSettings;

import net.dv8tion.jda.core.entities.Guild;

public class PrefixImpl implements PrefixRequest, PrefixUpdate {

	private static final Logger LOGGER = LoggerFactory.getLogger(PrefixImpl.class);
	private static final String REQUEST_PREFIX = "SELECT * FROM Guilds WHERE id=?";
	private static final String UPDATE_PREFIX = "INSERT INTO Guilds (id, prefix) VALUES (?,?) ON DUPLICATE KEY UPDATE prefix=?";
	
	@Override
	public String request(Guild guild) {
		long id = guild.getIdLong();

		LOGGER.info("Requesting prefix for guild id {}", id);

		try (Connection connection = DbHandler.getInstance().getConn();
				PreparedStatement statement = connection.prepareStatement(REQUEST_PREFIX)) {

			statement.setLong(1, id);

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				LOGGER.info("Prefix found in db: {}" , result.getString("prefix"));
				return result.getString("prefix");
			}

		} catch (SQLException e) {
			LOGGER.error("Failed to request prefix!", e);
		}
		
		LOGGER.info("Prefix not found in db, returning default");
		return DefaultSettings.PREFIX;
	}

	@Override
	public void update(Guild guild, String prefix) {
		long id = guild.getIdLong();

		LOGGER.info("Updating prefix for guild id {}", id);

		try (Connection connection = DbHandler.getInstance().getConn();
				PreparedStatement statement = connection.prepareStatement(UPDATE_PREFIX)) {
			
			statement.setLong(1, id);
			statement.setString(2, prefix);
			statement.setString(3, prefix);
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			LOGGER.error("Failed to update prefix!", e);
		}
	}
}
