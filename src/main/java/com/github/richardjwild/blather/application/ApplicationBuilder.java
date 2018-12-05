package com.github.richardjwild.blather.application;

import com.github.richardjwild.blather.command.factory.*;
import com.github.richardjwild.blather.io.Input;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.message.MessageRepository;
import com.github.richardjwild.blather.parsing.CommandReader;
import com.github.richardjwild.blather.parsing.InputParser;
import com.github.richardjwild.blather.persistence.JdbcMessageRepository;
import com.github.richardjwild.blather.persistence.JdbcUserRepository;
import com.github.richardjwild.blather.time.Clock;
import com.github.richardjwild.blather.time.TimestampFormatter;
import com.github.richardjwild.blather.user.UserRepository;
import oracle.jdbc.pool.OracleDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

public class ApplicationBuilder {

    public static Application build(Input input, Output output, Clock clock) throws IOException {

        String environment = ofNullable(System.getenv("ENVIRONMENT"))
                .orElseThrow(() -> new RuntimeException("Environment property is not set"));

        Properties properties = datasourceProperties(environment);
        Connection connection = connection(properties);
        UserRepository userRepository = new JdbcUserRepository(connection);
        MessageRepository messageRepository = new JdbcMessageRepository(connection);

        InputParser inputParser = new InputParser();
        Controller controller = new Controller();

        TimestampFormatter timestampFormatter = new TimestampFormatter(clock);

        CommandFactories commandFactories = new CommandFactories(
                new FollowCommandFactory(userRepository),
                new PostCommandFactory(messageRepository, userRepository, clock),
                new QuitCommandFactory(controller),
                new ReadCommandFactory(
                        messageRepository,
                        userRepository,
                        timestampFormatter,
                        output),
                new WallCommandFactory(
                        userRepository,
                        messageRepository,
                        timestampFormatter,
                        output));

        CommandReader commandReader = new CommandReader(
                input,
                inputParser,
                commandFactories);

        EventLoop eventLoop = new EventLoop(commandReader, controller);
        return new Application(eventLoop, output);
    }

    private static Connection connection(Properties properties) {
        try {
            OracleDataSource ds = new OracleDataSource();
            ds.setURL(properties.getProperty("jdbc.url"));
            ds.setUser(properties.getProperty("jdbc.username"));
            ds.setPassword(properties.getProperty("jdbc.password"));
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static Properties datasourceProperties(String environment) throws IOException {
        Properties properties = new Properties();
        String filename = format("/datasource-%s.properties", environment);
        InputStream resourceInputStream = ApplicationBuilder.class.getResourceAsStream(filename);
        if (resourceInputStream == null) {
            throw new RuntimeException("Datasource properties not found: " + filename);
        } else {
            properties.load(resourceInputStream);
            return properties;
        }
    }
}
