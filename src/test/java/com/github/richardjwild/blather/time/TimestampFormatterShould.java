package com.github.richardjwild.blather.time;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Instant;

import static java.time.Instant.now;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TimestampFormatterShould {

    @Mock
    private Clock clock;

    private TimestampFormatter timestampFormatter;

    @Before
    public void initialize() {
        timestampFormatter = new TimestampFormatter(clock);
    }

    @Test
    public void format_a_timestamp_from_just_now_as_0_seconds_ago() {
        Instant timestamp = now();
        when(clock.now()).thenReturn(timestamp);

        String formattedTimestamp = timestampFormatter.format(timestamp);

        assertThat(formattedTimestamp).isEqualTo("(0 seconds ago)");
    }

    @Test
    public void format_a_timestamp_from_1_second_ago_as_1_second_ago() {
        Instant timestamp = now();
        when(clock.now()).thenReturn(timestamp.plusSeconds(1));

        String formattedTimestamp = timestampFormatter.format(timestamp);

        assertThat(formattedTimestamp).isEqualTo("(1 second ago)");
    }

    @Test
    public void format_a_timestamp_from_2_seconds_ago_as_2_second_ago() {
        Instant timestamp = now();
        when(clock.now()).thenReturn(timestamp.plusSeconds(2));

        String formattedTimestamp = timestampFormatter.format(timestamp);

        assertThat(formattedTimestamp).isEqualTo("(2 seconds ago)");
    }
}