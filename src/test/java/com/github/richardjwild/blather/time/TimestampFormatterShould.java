package com.github.richardjwild.blather.time;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.time.Instant;

import static java.time.Instant.now;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(JUnitParamsRunner.class)
public class TimestampFormatterShould {

    @Mock
    private Clock clock;

    private TimestampFormatter timestampFormatter;

    @Before
    public void initialize() {
        initMocks(this);
        timestampFormatter = new TimestampFormatter(clock);
    }

    @Test
    @Parameters({
            "0 | (0 seconds ago)",
            "1 | (1 second ago)",
            "59 | (59 seconds ago)",
            "60 | (1 minute ago)",
            "119 | (1 minute ago)",
            "120 | (2 minutes ago)"
    })
    public void format_a_timestamp_from_an_interval(int secondsAgo, String expectedFormattedValue) {
        Instant timestamp = now();
        when(clock.now()).thenReturn(timestamp.plusSeconds(secondsAgo));

        String formattedTimestamp = timestampFormatter.format(timestamp);

        assertThat(formattedTimestamp).isEqualTo(expectedFormattedValue);
    }
}